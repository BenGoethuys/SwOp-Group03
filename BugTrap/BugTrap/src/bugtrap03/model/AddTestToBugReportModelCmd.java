package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Statistics;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
class AddTestToBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can add a certain test to the bugReport when executed.
     *
     * @param bugReport The bug report to evaluate
     * @param user      The user that wants to assign a score to this bug reports selected patch.
     * @param test      The test that the user wants to add
     * @throws IllegalArgumentException When bugReport == null
     * @throws IllegalArgumentException When the bugReport is terminated
     */
    AddTestToBugReportModelCmd(BugReport bugReport, User user, String test) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to AddBugReportTestModelCmd was a null reference.");
        }
        if (bugReport.isTerminated()) {
            throw new IllegalArgumentException("The given bug report is terminated !");
        }

        this.bugReport = bugReport;
        this.user = user;
        this.test = test;
    }

    private final BugReport bugReport;
    private final User user;
    private final String test;

    private Statistics oldStats;
    private BugReportMemento oldMem;

    private boolean isExecuted = false;

    /**
     * This method adds a given test to the bug report state
     *
     * @return True
     * @throws PermissionException      If the given user doesn't have the permission to add a test
     * @throws IllegalStateException    If the current state doesn't allow to add a test
     * @throws IllegalStateException    When this ModelCmd was already executed
     * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
     * @throws IllegalArgumentException When bugReport is terminated
     * @see BugReport#isValidTest(String)
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The AddBugReportTestModelCmd was already executed.");
        }

        if (bugReport.isTerminated()) {
            throw new IllegalArgumentException("The given bugReport is terminated.");
        }

        oldStats = user.getStats();
        oldMem = bugReport.getMemento();
        bugReport.addTest(user, test);
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

        user.setStats(oldStats);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        return "Added a test for BugReport " + bugReport.getTitle();
    }

}
