package bugtrap03.gui.cmd;

import org.junit.Test;

/**
 * TODO
 * 
 * @author Group 03
 *
 */
public class CmdParserTest {

    CmdParser parser;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.CmdParser#CmdParser(bugtrap03.gui.terminal.Terminal)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCmdParser() {
        parser = new CmdParser(null);
    }
}
