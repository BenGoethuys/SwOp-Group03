package bugtrap03.gui.cmd.general;

import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 *
 */
public class InvalidCmdTest {

    /**
     * Test exec(scan,null,null) on InvalidCmd.
     */
    @Test
    public void testExec() throws CancelException {
        ArrayDeque<String> answer = new ArrayDeque();
        ArrayDeque<String> question = new ArrayDeque();
        question.add("Invalid command.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Object val = (new InvalidCmd()).exec(scan, null, null);
        assertEquals(val, null);
    }

    /**
     * Test exec(null, null, null) on InvalidCmd expecting exception due to null
     * for scanner.
     *
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullExec() throws IllegalArgumentException {
        (new InvalidCmd()).exec(null, null, null);
    }
}
