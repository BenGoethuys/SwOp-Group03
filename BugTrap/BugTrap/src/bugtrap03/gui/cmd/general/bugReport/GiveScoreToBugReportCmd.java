package bugtrap03.gui.cmd.general.bugReport;

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
public class GiveScoreToBugReportCmd implements Cmd {

    /**
     * This is the constructor for this cmd
     *
     * @param bugReport The bug report that will have a score change
     *
     * @throws IllegalArgumentException If the given bug report was invalid, thus null.
     */
    public GiveScoreToBugReportCmd(BugReport bugReport) throws IllegalArgumentException {
        if (bugReport == null){
            throw new IllegalArgumentException("The given bug report to give a score, cannot be null");
        }
        this.bugReport = bugReport;
    }

    private BugReport bugReport;

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException When any of the arguments is null and
     *                                  shouldn't be or when in the scenario a chosen option conflicted.
     */
    @Override
    public Integer exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        scan.println("Give a score between 1 and 5: ");
        int score = new GetIntCmd().exec(scan, model, user);
        this.bugReport.giveScore(user, score);
        return score;
    }
}
