package bugtrap03.gui.cmd.general;

import bugtrap03.gui.terminal.TerminalScanner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class ClearCmdTest {

    // TODO: Manually rerun clearCmd in a terminal on windows, linux and mac.
    @Test
    public void testExecNullReturn() {
        ClearCmd cmd = new ClearCmd();
        TerminalScanner scanner = new TerminalScanner(System.in, System.out);

        Object result = cmd.exec(scanner, null, null);
        assertEquals(result, null);
    }

}
