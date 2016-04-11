package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;

/**
 * @author Group 03
 */
public class RegisterForNotificationsCmd implements Cmd {

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
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        //TODO check for issuer?
        //TODO if so check using userPermission
        if (! (user instanceof Issuer)){
            throw new IllegalArgumentException("Precondition not met: logged in user must be an issuer");
        }
        ArrayList<String> subjectypes = new ArrayList<String>();
        subjectypes.add("Project");
        subjectypes.add("Subsystem");
        subjectypes.add("Bugreport");
        scan.println("Select subject type.");
        String subjectype = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(subjectypes),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subjectype = subjectype.toLowerCase();

        ArrayList<String> subscriptionTypes = new ArrayList<String>();
        subscriptionTypes.add("NewTag \t = the change of tag for a bug report.");
        subscriptionTypes.add("SpecificTag \t = the change to a specific tag for a bug report.");
        subscriptionTypes.add("Comment \t = the creation of a new comment on a bug report.");

        if (subjectype.equals("project") || subjectype.equals("subsystem")) {
            subscriptionTypes.add("Creation : the creation of bug reports.");scan.println("Select project.");
            Project selectedProj = new GetProjectCmd().exec(scan, model, user);
            if (subjectype.equals("subsystem")) {
                Subsystem selectedSubsystem = this.getSubsystem(scan, model, user, selectedProj);
            }
        }
        else if (subjectype.equals("bugreport")){
            scan.println("Select bug report.");
            BugReport selectedBug = new SelectBugReportCmd().exec(scan, model, user);
        } else {
            throw new IllegalArgumentException("Something went wrong while selecting subject type.");
        }
        scan.println("Select subscription type. \n(Use the first word as key for selecting)");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(subscriptionTypes),
                u -> (u.toString()), ((u, input) -> ((u.substring(0).equalsIgnoreCase(input))))).exec(scan, model, null);

        return null;
    }

    private Subsystem getSubsystem(TerminalScanner scan, DataModel model, User user, Project selectedProj) throws CancelException {
        scan.println("Select subsystem.");
        PList<Subsystem> allSubsystems = model.getAllSubsystems(selectedProj);
        Subsystem selectedSub = new GetObjectOfListCmd<>(allSubsystems, (u -> u.getName()),
                ((u, input) -> u.getName().equalsIgnoreCase(input)))
                .exec(scan, model, user);
        return selectedSub;
    }
}
