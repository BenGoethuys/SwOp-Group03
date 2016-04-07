package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
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
        if (! (user instanceof Issuer)){
            throw new IllegalArgumentException("Precondition not met: logged in user must be an issuer");
        }
        ArrayList<String> subjectypes = new ArrayList<String>();
        subjectypes.add("Project");
        subjectypes.add("Subsystem");
        subjectypes.add("Bugreport");
        scan.println("Select subject type.");
        String subjectype = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(subjectypes),
                u -> (u.toString()), ((u, input) -> ((u.toLowerCase().equals(input.toLowerCase()))))).exec(scan, model, null);
        subjectype = subjectype.toLowerCase();
        if (subjectype.equals("project")){

        } else if (subjectype.equals("subsystem")){

        } else if (subjectype.equals("bugreport")){

        } else {

        }
        return null;
    }
}
