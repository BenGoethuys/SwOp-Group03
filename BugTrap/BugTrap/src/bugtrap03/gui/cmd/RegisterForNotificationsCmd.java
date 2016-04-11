package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Mailbox;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterForNotificationsCmd implements Cmd {

    public RegisterForNotificationsCmd(){
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
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return
     * @throws PermissionException
     * @throws CancelException
     * @throws IllegalArgumentException
     */
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        //precondition of issuer?

        scan.println("Select subject type.");
        String subjectype = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(this.cmdMapSubjectTypes.keySet()),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        this.cmdMapSubjectTypes.get(subjectype.toLowerCase()).exec(scan, model, user);
        scan.println("Registration for notifications complete");
        return user.getMailbox();
    }
}
