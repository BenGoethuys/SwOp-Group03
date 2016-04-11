package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class SetDuplicateBugReportModelCmd extends ModelCmd {

    /**
     * Creates a {@link ModelCmd} that when executed can assign a bug report a duplicate bug report.
     *
     * @param bugReport The bug report that will be assigned the duplicate
     * @param duplicate The duplicate of the given bug report
     * @param user The user who wishes to set the duplicate.
     *
     * @throws IllegalArgumentException When bugReport == null
     * @throws IllegalArgumentException When duplicate == null
     * @throws IllegalArgumentException When user == null
     */
    SetDuplicateBugReportModelCmd(BugReport bugReport, BugReport duplicate, User user) throws IllegalArgumentException {
        if (bugReport == null || duplicate == null || user == null) {
            throw new IllegalArgumentException("The bugReport, duplicate or user passed to SetDuplicateBugReportModelCmd was a null reference.");
        }

        this.bugReport = bugReport;
        this.duplicate = duplicate;
        this.user = user;
    }

    private final BugReport bugReport;
    private final BugReport duplicate;
    private final User user;

    private BugReportMemento oldMem;

    private boolean isExecuted;

    /**
     * This method sets the given duplicate bug report as the duplicate of the given bug report.
     * @return The bugReport that has been changed.
     * 
     * @throws IllegalArgumentException If the given duplicate is invalid for this bug report 
     * @throws PermissionException If the given user does not have the needed permission
     * @throws IllegalStateException If the current state doesn't allow for a duplicate to be set.
     * 
     * @see SetDuplicateBugReportModelCmd#SetDuplicateBugReportModelCmd(BugReport, BugReport, User)
     */
    @Override
    BugReport exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The SetDuplicateBugReportModelCmd was already executed.");
        }

        oldMem = bugReport.getMemento();
        bugReport.setDuplicate(user, duplicate);
        isExecuted = true;
        return bugReport;
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
        return duplicate.getTitle() + " has been set as duplicate of " + bugReport.getTitle();
    }

}
