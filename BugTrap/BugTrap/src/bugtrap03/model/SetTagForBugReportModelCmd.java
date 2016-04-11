package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class SetTagForBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can set a certain tag for a bugReport when executed.
     *
     * @param bugReport The bug report of which the tag gets to be set
     * @param tag       The given tag to set
     * @param user      The user that wishes to set the tag
     *
     * @throws IllegalArgumentException When bugReport == null
     */
    SetTagForBugReportModelCmd(BugReport bugReport, Tag tag, User user) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to SetTagForReportModelCmd was a null reference.");
        }

        this.bugReport = bugReport;
        this.tag = tag;
        this.user = user;
    }

    private final BugReport bugReport;
    private final User user;
    private final Tag tag;

    private BugReportMemento oldMem;

    private boolean isExecuted = false;

    /**
     * This method lets the given user set the tag of the given bug report to the given tag
     * 
     * @return True
     * @throws PermissionException If the user doesn't have the needed permission to set the given tag to the bug report
     * @throws IllegalArgumentException If the given tag isn't a valid tag to set to the bug report
     * @throws IllegalStateException When this ModelCmd was already executed
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The SetTagForBugReportModelCmd was already executed.");
        }

        oldMem = bugReport.getMemento();
        bugReport.setTag(tag, user);
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
        String tagName = (tag != null) ? tag.toString() : "-invalid argument-";
        return "Set the Tag " + tagName + " for BugReport " + bugReport.getTitle();
    }

}
