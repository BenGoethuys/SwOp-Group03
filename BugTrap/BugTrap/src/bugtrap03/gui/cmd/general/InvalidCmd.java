package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command represents a fail command that is executed when something went wrong
 *
 * @author Group 03
 */
public class InvalidCmd implements Cmd {

    /**
     * Inform the user of the invalid command.
     *
     * @param scan The scanner used to print to
     * @param dummy1 doesn't matter
     * @param dummy2 doesn't matter
     * @return A null reference.
     * @throws IllegalArgumentException When scan is a null reference.
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel dummy1, User dummy2) throws IllegalArgumentException {
        if(scan == null) {
            throw new IllegalArgumentException("InvalidCmd requires a non null reference as scan");
        }
        scan.println("Invalid command.");
        return null;
    }

}
