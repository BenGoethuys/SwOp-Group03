package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * This interface should be implemented by all executable commands in the system
 *
 * @author Group 03
 */
public interface Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalStateException    When the subject is in an illegal state to perform this cmd.
     * @throws IllegalArgumentException When any of the arguments is null and
     *                                  shouldn't be or when in the scenario a chosen option conflicted.
     */
    Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException;

}
