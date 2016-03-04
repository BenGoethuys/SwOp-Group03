package bugtrap03.gui.cmd.general;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * Created by Ben on 04/03/2016.
 */
public class GetLongCmd implements Cmd {
    /**
     * Execute this command and possibly return a result.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Long exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        scan.print("Give number: ");
        Long longNb = null;
        do {
            if (scan.hasNextLong()) {
                longNb = scan.nextLong();
            } else {
                scan.println("Invalid input, please enter a number");
            }
        } while (longNb == null);
        return longNb;
    }
}