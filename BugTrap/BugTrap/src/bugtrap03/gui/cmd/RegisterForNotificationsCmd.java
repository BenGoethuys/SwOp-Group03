package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * This command represents the use case for registering a user for a notification
 *
 * @author Group 03
 */
public class RegisterForNotificationsCmd implements Cmd<AbstractMailbox> {

    public RegisterForNotificationsCmd() {
        this.cmdMapSubjectTypes = new HashMap<>();
        cmdMapSubjectTypes.put("project", new RegisterFromProjectCmd());
        cmdMapSubjectTypes.put("subsystem", new RegisterFromSubsystemCmd());
        cmdMapSubjectTypes.put("bugreport", new RegisterFromBugReportCmd());
    }

    private HashMap<String, Cmd> cmdMapSubjectTypes;

    /**
     * Execute the Register for Notifications scenario
     * <br> 1. The issuer indicates that he wants to register for receiving notifications.
     * <br> 2. The system asks if he wants to register for a project, subsystem or bug report.
     * <br> 3. The issuer indicates he wants to register for a project.
     * <br> 3.a The issuer indicates he wants to register for a subsystem.
     * <br> 3.b The issuer indicates he wants to register for a bug report.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The mailbox of the user in which the new mailbox will be stored.
     * @throws PermissionException      If the user does not have enough permissions.
     * @throws CancelException          If the abort command has been given.
     * @throws IllegalArgumentException If any of the arguments is null or invalid.
     */
    @Override
    public AbstractMailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        //precondition of issuer? Assignment talks of 'user'...
        scan.println("Select subject type.");
        String subjectype = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(new TreeSet<>(this.cmdMapSubjectTypes.keySet())),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        AbstractMailbox newMailbox = (AbstractMailbox) this.cmdMapSubjectTypes.get(subjectype.toLowerCase()).exec(scan, model, user);
        scan.println("Registration for notifications complete.");
        return newMailbox;
    }
}
