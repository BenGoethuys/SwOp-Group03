package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class AddPatchToBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can add a certain patch to the bugReport when executed.
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug reports selected patch.
     * @param patch The patch to add.
     *
     * @throws IllegalArgumentException When bugReport == null
     */
    AddPatchToBugReportModelCmd(BugReport bugReport, User user, String patch) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to AddBugReportPatchModelCmd was a null reference.");
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
     * This method adds a given patch to this bug report state
     *
     * @return True
     * @throws PermissionException If the given user doesn't have the permission to add a patch to this bug report state
     * @throws IllegalStateException If the given patch is invalid for this bug report
     * @throws IllegalStateException When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given patch is not valid for this bug report state
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The AddBugReportPatchModelCmd was already executed.");
        }

        oldMem = bugReport.getMemento();
        bugReport.addPatch(user, patch);
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
        return "Added a patch to BugReport " + bugReport.getTitle();
    }

}
