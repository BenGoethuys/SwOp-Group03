package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author  Group 03
 */
public class RegisterFromProjectCmd implements Cmd<Mailbox>{
    public RegisterFromProjectCmd(){}

    /**
     * <br> 4. The system shows a list of projects.
     * <br> 5. The issuer selects a project.
     * <br> 6. The system presents a form describing the specific system changes that can be subscribed to for the selected object of interest:
     * <br>     • The creation of a new bug report (only applicable if the object of interest is a project or subsystem)
     * <br>     • A bug report receiving a new tag
     * <br>     • A bug report receiving a specific tag
     * <br>     • A new comment for a bug report
     * <br> 7. The issuer selects the system change he wants to be notified of.
     * <br> 8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     *
     * @return The newly created mailbox representing the registration for notifications.
     *
     * @throws PermissionException If the user does not have enough permissions.
     * @throws CancelException If the abort command has been given.
     * @throws IllegalArgumentException If any of the arguments is null or invalid.
     */
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        Project selectedProject = new GetProjectCmd().exec(scan, model, user);
        Mailbox newMailbox = new RegisterFromASCmd(selectedProject).exec(scan, model, user);
        return newMailbox;
    }


}
