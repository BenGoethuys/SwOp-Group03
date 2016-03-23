package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * This command represents the use case of inspecting a bug report in the system
 *
 * @author Group 03
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
     * @return The BugReport inspected.
     * @throws PermissionException      When the user does not have sufficient permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException If the given scan, model or user is null
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. The issuer indicates he wants to inspect some bug report.
        // 2. Include use case Select Bug Report.
        BugReport bugRep = new SelectBugReportCmd().exec(scan, model, user);
        // 3. The system shows a detailed overview of the selected bug report
        // and all its comments
        scan.println(model.getDetails(user, bugRep));

        return bugRep;
    }
}
