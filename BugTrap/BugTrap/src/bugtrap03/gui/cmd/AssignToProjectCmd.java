package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * Created by Kwinten on 07/03/2016.
 */
public class AssignToProjectCmd implements Cmd{
    /**
     * <p>
     * <br> 1. The developer indicates he wants to assign another developer.
     * <br> 2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
     * <br> 2.a The logged in developer is not assigned a lead role in any project: The use case ends here.
     * <br> 3. The lead developer selects one of his projects.
     * <br> 4. The system shows a list of other developers to assign.
     * <br> 5. The lead developer selects one of these other developers.
     * <br> 6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
     * <br> 7. The lead developer selects a role.
     * <br> 8. The systems assigns the selected role to the selected developer.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return
     * @throws PermissionException
     * @throws CancelException
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        return null;
    }
}
