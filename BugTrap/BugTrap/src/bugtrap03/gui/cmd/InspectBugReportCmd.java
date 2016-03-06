package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command represents the use case of inspecting a bug report in the system
 * Created by Ben Goethuys on 04/03/2016.
 */
public class InspectBugReportCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The issuer indicates he wants to inspect some bug report.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The system shows a detailed overview of the selected bug report and all its comments
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {

        BugReport bugRep = new SelectBugReportCmd().exec(scan, model, user);

        scan.println(bugRep.getDetails());

        return null;
    }
}
