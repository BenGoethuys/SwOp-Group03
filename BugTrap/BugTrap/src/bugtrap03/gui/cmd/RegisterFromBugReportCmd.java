package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.SelectTagsCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.*;

/**
 * @author Group 03
 */
public class RegisterFromBugReportCmd implements Cmd<Mailbox>{
    public RegisterFromBugReportCmd(){
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("newtag",1);
        this.subsriptionTypes.put("specifictags",2);
        this.subsriptionTypes.put("comment",3);
    }

    private HashMap<String, Integer> subsriptionTypes;

    /**
     * <br> 3.b.1. Include use case Select Bug Report.
     * <br> 3.b.2. The use case continues with step 6.
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
        BugReport selectedBugRep = new SelectBugReportCmd().exec(scan, model, user);

        scan.println("Select subscription type.");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(new TreeSet<>(this.subsriptionTypes.keySet())),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subscriptionType = subscriptionType.toLowerCase();
        Integer index = this.subsriptionTypes.get(subscriptionType);
        Mailbox newMailbox;
        switch (index) {
            case 1:
                newMailbox = model.registerForAllTagsNotifications(user, selectedBugRep);
                break;
            case 2:
                EnumSet<Tag> selectedTags = new SelectTagsCmd().exec(scan, model, user);
                newMailbox = model.registerForSpecificTagsNotifications(user, selectedBugRep, selectedTags);
                break;
            case 3:
                newMailbox = model.registerForCommentNotifications(user, selectedBugRep);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with selecting " +
                        "the type of notification registration");
        }
        return newMailbox;
    }
}
