package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class SelectPatchFromBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can select a certain patch for the bugReport when executed.
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug reports selected patch.
     * @param patch The patch that the user wants to select.
     *
     * @throws IllegalArgumentException When bugReport == null
     */
    SelectPatchFromBugReportModelCmd(BugReport bugReport, User user, String patch) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to SelectBugReportPatchModelCmd was a null reference.");
        }

        this.bugReport = bugReport;
        this.user = user;
        this.patch = patch;
    }

    private final BugReport bugReport;
    private final User user;
    private final String patch;

    private BugReportMemento oldMem;

    private boolean isExecuted = false;

    /**
     * This method selects a patch for this bug report state
     *
     * @return True
     * @throws PermissionException If the given user doesn't have the permission to select a patch for this bugReport
     * state
     * @throws IllegalStateException If the current state doesn't allow the selecting of a patch
     * @throws IllegalStateException When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The SelectBugReportPatchModelCmd was already executed.");
        }

        oldMem = bugReport.getMemento();
        bugReport.selectPatch(user, patch);
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
        return "Selected a patch for BugReport " + bugReport.getTitle();
    }

}
