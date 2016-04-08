package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 */
public class GetStringCmdTest {
    
    /**
     * Test exec()
     * @throws CancelException 
     */
    @Test
    public void testExec() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetStringCmd cmd = new GetStringCmd();

        // Setup scenario
        question.add("enter text: ");
        answer.add("input");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        String chosen = cmd.exec(scan, null, null);

        // Test effects.
        assertEquals(chosen, "input");
    }

    /**
     * Test exec() with AbortCmd.
     * @throws CancelException 
     */
    @Test(expected = CancelException.class)
    public void testAbortExec() throws CancelException {
        
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetStringCmd cmd = new GetStringCmd();

        // Setup scenario
        question.add("enter text: ");
        answer.add("abort");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        String chosen = cmd.exec(scan, null, null);
    }

    /**
     * Test exec() with scan == null
     *
     * @throws CancelException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws CancelException {
        GetStringCmd cmd = new GetStringCmd();

        // Execute scenario
        cmd.exec(null, null, null);
    }
    
}
