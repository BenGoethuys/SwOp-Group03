package bugtrap03.gui.cmd.general;

import org.junit.Test;

/**
 * @author Group 03
 */
public class AbortCmdTest {

    /**
     * Test exec(null,null,null) on AbortCmd.
     */
    @Test(expected = CancelException.class)
    public void testExec() throws CancelException {
        AbortCmd cmd = new AbortCmd();

        cmd.exec(null, null, null);
    }

}
