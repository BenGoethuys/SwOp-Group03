package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
class GiveScoreToBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can gives the selected patch of this bugReport a score when executed.
     *
     * @param bugReport The bug report to evaluate
     * @param user      The user that wants to assign a score to this bug reports selected patch.
     * @param score     The score that the creator wants to give
     * @throws IllegalArgumentException When bugReport == null
     * @throws IllegalArgumentException When the given bug report is terminated
     */
    GiveScoreToBugReportModelCmd(BugReport bugReport, User user, int score) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to GiveBugReportScoreModelCmd was a null reference.");
        }
        if (bugReport.isTerminated()) {
            throw new IllegalArgumentException("The given bug report is terminated");
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
     * @return True
     * @throws PermissionException      When the user does not have sufficient permissions to give the bugReport a score
     * @throws IllegalStateException    If the current state doesn't allow assigning a score
     * @throws IllegalStateException    When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     * @throws IllegalArgumentException If bugReport is terminated
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The GiveBugReportScoreModelCmd was already executed.");
        }

        if (bugReport.isTerminated()) {
            throw new IllegalArgumentException("The given bugReport is terminated.");
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
        return "Gave BugReport " + bugReport.getTitle() + " a score of " + this.score;
    }

}
