package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetIntCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author Group 03
 */
public class GiveScoreToBugReportCmd implements Cmd<Integer> {

    /**
     * This is the constructor for this cmd
     *
     * @param bugReport The bug report that will have a score change
     */
    public GiveScoreToBugReportCmd(BugReport bugReport) throws IllegalArgumentException {
        this.bugReport = bugReport;
    }

    /**
     * This is the basic constructor
     */
    public GiveScoreToBugReportCmd() {
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
     * @return the selected int;
     *
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException When any of the arguments is null and
     *                                  shouldn't be or when in the scenario a chosen option conflicted.
     */
    @Override
    public Integer exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        // if bug report was not defined:
        if (this.bugReport == null){
            this.bugReport = new SelectBugReportCmd().exec(scan, model, user);
        }

        // ask user for score
        scan.println("Give a score between 1 and 5: ");
        int score = new GetIntCmd().exec(scan, model, user);
        this.bugReport.giveScore(user, score);

        scan.println("The score is set to: " + score);
        return score;
    }
}
