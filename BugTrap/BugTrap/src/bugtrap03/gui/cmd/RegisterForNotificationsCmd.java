package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Subject;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.cmd.general.GetSubsystemCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterForNotificationsCmd implements Cmd {

    public RegisterForNotificationsCmd(){

        this.cmdMapNotifiactionTypes = new HashMap<>();
        cmdMapNotifiactionTypes.put("updatetag", new RegisterForAllTagsNotificationsCmd());
        cmdMapNotifiactionTypes.put("specifictags", new RegisterForSpecificTagsNotificationsCmd());
        cmdMapNotifiactionTypes.put("comment", new RegisterForCommentNotificationsCmd());
        this.cmdMapAllNotificationTypes = new HashMap<>();
        cmdMapAllNotificationTypes.putAll(this.cmdMapNotifiactionTypes);
        cmdMapAllNotificationTypes.put("creation", new RegisterForCreationNotificationsCmd());
        this.mapMapSubjectTypes = new HashMap<>();
        mapMapSubjectTypes.put("project", cmdMapAllNotificationTypes);
        mapMapSubjectTypes.put("subsystem", cmdMapAllNotificationTypes);
        mapMapSubjectTypes.put("bugreport", cmdMapNotifiactionTypes);
        this.cmdMapSubjectTypes = new HashMap<>();
        cmdMapSubjectTypes.put("project", new GetProjectCmd());
        cmdMapSubjectTypes.put("subsystem", new GetSubsystemCmd());
        cmdMapSubjectTypes.put("bugreport", new SelectBugReportCmd());
    }

    private HashMap<String, Cmd> cmdMapAllNotificationTypes;
    private HashMap<String, Cmd> cmdMapNotifiactionTypes;
    private HashMap<String, Cmd> cmdMapSubjectTypes;
    private HashMap<String, HashMap> mapMapSubjectTypes;

    /**
     * Execute the Register for Notifications scenario
     * <br> 1. The issuer indicates that he wants to register for receiving notiﬁcations.
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
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
//        //precondition of issuer?
//        if (! (user instanceof Issuer)){
//            throw new IllegalArgumentException("Precondition not met: logged in user must be an issuer");
//        }

        scan.println("Select subject type.");
        String subjectype = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(this.mapMapSubjectTypes.keySet()),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subjectype = subjectype.toLowerCase();
        HashMap selectedHashMap = this.mapMapSubjectTypes.get(subjectype);
        Subject selectedSubject = (Subject) this.cmdMapSubjectTypes.get(subjectype).exec(scan, model, user);




        return null;
    }

    private String selectSubscriptiontype(TerminalScanner scan, DataModel model, ArrayList<String> subscriptionTypes)
            throws CancelException {
        scan.println("Select subscription type. (Use the first word as key for selecting)");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(subscriptionTypes),
                u -> (u.toString()), ((u, input) -> ((u.substring(0).equalsIgnoreCase(input))))).exec(scan, model, null);
        return subscriptionType;
    }
}
