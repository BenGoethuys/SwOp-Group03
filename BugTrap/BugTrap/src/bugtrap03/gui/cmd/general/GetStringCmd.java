package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command asks the user for a string/text
 *
 * @author Group 03
 */
public class GetStringCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan The scanner used to interact with the person.
     * @param dummy2 Doesn't matter
     * @param dummy3 Doesn't matter
     * @return The text/String given by the user
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException If the given scan is null
     */
    @Override
    public String exec(TerminalScanner scan, DataModel dummy2, User dummy3) 
            throws CancelException, IllegalArgumentException {
        if (scan == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        scan.print("enter text: ");
        return scan.nextLine();
    }
}
