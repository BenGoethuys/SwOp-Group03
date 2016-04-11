package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author Group 03
 */
public class SetDuplicateBugReportCmd implements Cmd {

    /**
     * This is the constructor with a given bug report
     * @param bugReport The bug report that is a duplicate of another
     */
    public SetDuplicateBugReportCmd(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    /**
     * The basic constructor
     */
    public SetDuplicateBugReportCmd() {
        this.bugReport = null;
    }

    private BugReport bugReport;

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     *
     * @return The selected duplicate bug report
     *
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException When any of the arguments is null and
     *                                  shouldn't be or when in the scenario a chosen option conflicted.
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        // if no bug report was initialised -> get one
        if (this.bugReport == null){
            scan.println("Please select the bug report that will be given the Tag DUPLICATE: ");
            this.bugReport = new SelectBugReportCmd().exec(scan, model, user);
        }

        // ask what the duplicate bug report is:
        scan.println("Please select the duplicate bug report: ");
        BugReport dup = new SelectBugReportCmd().exec(scan, model, user);

        model.setDuplicate(user, bugReport, dup);
        return dup;
    }
}
