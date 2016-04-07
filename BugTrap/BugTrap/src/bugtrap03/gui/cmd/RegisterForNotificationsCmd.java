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
        scan.println("Select project.");
        Project selectedProj = new GetProjectCmd().exec(scan, model, user);
        if (subjectype.equals("project")){

        } else if (subjectype.equals("subsystem")){
            scan.println("Select subsystem.");
            PList<Subsystem> allSubsystems = model.getAllSubsystems(selectedProj);
            Subsystem selectedSub = new GetObjectOfListCmd<>(allSubsystems, (u -> u.getName()),
                    ((u, input) -> u.getName().equalsIgnoreCase(input)))
                    .exec(scan, model, user);

        } else if (subjectype.equals("bugreport")){
            scan.println("Select bug report.");
            BugReport selectedBug = new SelectBugReportCmd().exec(scan, model, user);

        } else {
            throw new IllegalArgumentException("Something went wrong while selection subjecttype");
        }
        return null;
    }
}
