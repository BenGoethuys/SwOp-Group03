package bugtrap03.gui.cmd.general;

import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 */
public class GetLongCmdTest {

    /**
     * Test exec
     * @throws CancelException never
     */
    @Test
    public void testExec() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetLongCmd cmd = new GetLongCmd();

        // Setup scenario
        question.add("Give number: ");
        answer.add("wrongInput");
        question.add("Invalid input, please enter a number");
        answer.add("-5.2");
        question.add("Invalid input, please enter a number");
        answer.add("-5");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        long chosen = cmd.exec(scan, null, null);

        // Test effects.
        assertEquals(chosen, -5);
    }

    /**
     * Test exec() while typing abort.
     * @throws CancelException 
     */
    @Test(expected = CancelException.class)
    public void testAbortExec() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetLongCmd cmd = new GetLongCmd();

        // Setup scenario
        question.add("Give number: ");
        answer.add("wrongInput");
        question.add("Invalid input, please enter a number");
        answer.add("abort");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        cmd.exec(scan, null, null);
    }

    /**
     * Test exec() with scan == null
     *
     * @throws CancelException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws CancelException {
        GetLongCmd cmd = new GetLongCmd();

        // Execute scenario
        cmd.exec(null, null, null);
    }

}
