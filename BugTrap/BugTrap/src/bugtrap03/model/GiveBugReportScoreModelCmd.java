package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Admin
 */
class GiveBugReportScoreModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can gives the selected patch of this bugReport a score.
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
    
    private int oldScore;

    private boolean isExecuted = false;

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug report
     * @param score The score that the creator wants to give
     *
     * @throws IllegalStateException If the current state doesn't allow assigning a score
     * @throws IllegalStateException When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     */
    @Override
    Boolean exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The GiveBugReportScoreModelCmd was already executed.");
        }

        oldScore = bugReport.getScore();
        bugReport.giveScore(user, score);
        isExecuted = true;
        return true;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        //TODO: Is there a bugReport state change when giving a score? Yes.
        try {
            bugReport.giveScore(user, oldScore);
        } catch (IllegalStateException | IllegalArgumentException | PermissionException ex) {
            return false;
        }
        
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

}
