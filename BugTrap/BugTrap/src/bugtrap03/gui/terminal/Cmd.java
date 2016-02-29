package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;

/**
 *
 * @author Admin
 */
public interface Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     * permissions.
     * @throws  CancelException When the users wants to abort the current cmd
     */
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException;

}
