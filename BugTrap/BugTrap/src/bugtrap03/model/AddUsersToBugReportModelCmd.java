package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.BugReportMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
class AddUsersToBugReportModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can add users to a BugReport when executed
     *
     * @param user The user that wants to add all the given developers to the bug report
     * @param bugRep The bug report to add all the developers to
     * @param devList The developers to add to the bug report
     *
     * @throws IllegalArgumentException When bugRep == null
     */
    AddUsersToBugReportModelCmd(User user, BugReport bugRep, PList<Developer> devList) throws IllegalArgumentException {
        if (bugRep == null) {
            throw new IllegalArgumentException("The bugReport passed to AddUsersToReportModelCmd was a null reference.");
        }

        this.bugReport = bugRep;
        this.devList = devList;
        this.user = user;
    }

    private final BugReport bugReport;
    private final User user;
    private final PList<Developer> devList;

    private BugReportMemento oldMem;

    private boolean isExecuted = false;

    /**
     * This method adds all the users of the given list to the given project by the given user
     *
     * @return True
     * @throws IllegalArgumentException If the given user was null
     * @throws IllegalArgumentException If the given developer was not valid for this bug report
     * @throws PermissionException If the given user doesn't have the needed permission to add users to the given bug
     * report
     */
    @Override
    Boolean exec() throws PermissionException, IllegalArgumentException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The AddUsersToBugReportModelCmd was already executed.");
        }
        oldMem = bugReport.getMemento();
        if (devList != null) {
            for (Developer dev : devList) {
                bugReport.addUser(user, dev);
            }
        }
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
        int size = (devList != null) ? devList.size() : 0;
        String title = (bugReport != null) ? bugReport.getTitle() : "-invalid argument-";
        return "Add " + size + " Users to BugReport " + title;
    }

}
