package bugtrap03.gui.cmd.general;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command asks the user for a string/text
 * Created by Ben Goethuys on 04/03/2016.
 */
public class GetStringCmd implements Cmd {
    /**
     * Execute this command and possibly return a result.
     *
     * @param scan  The scanner used to interact with the person.
     * @param dummy2 Doesn't matter
     * @param dummy3 Doesn't matter
     * @return The text/String given by the user
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public String exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws PermissionException, CancelException {
        scan.print("enter text: ");
        return scan.nextLine();
    }
}
