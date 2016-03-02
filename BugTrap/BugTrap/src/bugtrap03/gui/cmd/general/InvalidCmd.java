package bugtrap03.gui.cmd.general;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * @author Admin
 */
public class InvalidCmd implements Cmd {

    /**
     * Inform the user of the invalid command.
     *
     * @param scan The scanner used to print to
     * @param cdummy
     * @param udummy
     * @return A null reference.
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel cdummy, User udummy) {
        scan.println("Invalid command.");
        return null;
    }

}
