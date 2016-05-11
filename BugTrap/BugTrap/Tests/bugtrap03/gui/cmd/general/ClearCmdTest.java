package bugtrap03.gui.cmd.general;

import bugtrap03.gui.terminal.TerminalScanner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class ClearCmdTest {

    @Test
    public void testExecNullReturn() {
        ClearCmd cmd = new ClearCmd();
        TerminalScanner scanner = new TerminalScanner(System.in, System.out);

        Object result = cmd.exec(scanner, null, null);
        assertEquals(result, null);
    }

    /**
     * Test exec() while scan == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() {
        ClearCmd cmd = new ClearCmd();
        cmd.exec(null, null, null);
    }

}
