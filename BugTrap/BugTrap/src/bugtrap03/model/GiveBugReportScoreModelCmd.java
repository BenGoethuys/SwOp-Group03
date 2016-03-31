package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Admin
 */
class GiveBugReportScoreModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can gives the selected patch of this bugReport a score when executed.
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug reports selected patch.
     * @param score The score that the creator wants to give
     *
     * @throws IllegalArgumentException When bugReport == null
     */
    GiveBugReportScoreModelCmd(BugReport bugReport, User user, int score) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to GiveBugReportScoreModelCmd was a null reference.");
        }

        this.bugReport = bugReport;
        this.user = user;
        this.score = score;
    }

    private final BugReport bugReport;
    private final User user;
    private final int score;

    private BugReportMemento oldMem;

    private boolean isExecuted = false;

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug report
     * @param score The score that the creator wants to give
     *
     * @return True
     * @throws PermissionException When the user does not have sufficient permissions to give the bugReport a score
     * @throws IllegalStateException If the current state doesn't allow assigning a score
     * @throws IllegalStateException When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The GiveBugReportScoreModelCmd was already executed.");
        }

        oldMem = bugReport.getMemento();
        bugReport.giveScore(user, score);
        isExecuted = true;
        return true;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }
        
        try {
            bugReport.setMemento(oldMem);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        String bugTitle = (bugReport != null) ? bugReport.getTitle() : "-invalid argument-";
        return "Gave BugReport " + bugTitle + " a score of " + this.score;
    }

}
