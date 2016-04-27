package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterFromSubsystemCmd implements Cmd<AbstractMailbox> {
    public RegisterFromSubsystemCmd(){
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("alltags",1);
        this.subsriptionTypes.put("specictags",2);
        this.subsriptionTypes.put("comment",3);
        this.subsriptionTypes.put("creation",4);
    }

    private HashMap<String, Integer> subsriptionTypes;

    /**
     * <br> 3.a.1. The system shows a list of projects.
     * <br> 3.a.2. The user selects a project.
     * <br> 3.a.3. The system shows all the subsystems of the selected project.
     * <br> 3.a.4. The user selects a subsystem.
     * <br> 3.a.5. The use case continues with step 6.
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
    public AbstractMailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        Subsystem selectedSubsys = new GetSubsystemCmd().exec(scan, model, user);
        AbstractMailbox newMailbox = new RegisterFromASCmd(selectedSubsys).exec(scan, model, user);
        return newMailbox;
    }
}
