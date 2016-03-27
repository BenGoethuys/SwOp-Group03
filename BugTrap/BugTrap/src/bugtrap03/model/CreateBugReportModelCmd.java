package bugtrap03.model;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import com.google.java.contract.Ensures;
import java.util.GregorianCalendar;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class CreateBugReportModelCmd extends ModelCmd {

    /**
     * This method creates a {@link ModelCmd} that can create and add a bug report to the list of associated bugReports
     * of this subsystem when executed.
     *
     * @param subsystem The subsystem the new bugReport belongs to
     * @param user The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param isPrivate The boolean that says if this bug report should be private or not
     * @param trigger A trigger used to trigger the bug. Can be NULL.
     * @param stacktrace The stacktrace got when the bug was triggered. Can be NULL.
     * @param error The error got when the bug was triggered. Can be NULL.
     *
     * @throws IllegalArgumentException When subsystem == null
     */
    CreateBugReportModelCmd(Subsystem subsystem, User user, String title, String description,
            GregorianCalendar creationDate, PList<BugReport> dependencies, Milestone milestone,
            boolean isPrivate, String trigger, String stacktrace, String error)
            throws IllegalArgumentException {
        if (subsystem == null) {
            throw new IllegalArgumentException("The subsystem passed to CreateBugReportModelCmd was null.");
        }

        this.subsystem = subsystem;
        this.user = user;
        this.title = title;
        this.desc = description;
        this.creationDate = creationDate;
        this.dependencies = dependencies;
        this.milestone = milestone;
        this.isPrivate = isPrivate;
        this.trigger = trigger;
        this.stacktrace = stacktrace;
        this.error = error;
    }

    /**
     * This method creates a {@link ModelCmd} that can create and add a bug report to the list of associated bugReports
     * of this subsystem when executed. Using this constructor the trigger, stacktrace and error will be null.
     *
     * @param subsystem The subsystem the new bugReport belongs to
     * @param user The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param isPrivate The boolean that says if this bug report should be private or not
     *
     * @throws IllegalArgumentException When subsystem == null
     */
    CreateBugReportModelCmd(Subsystem subsystem, User user, String title, String description,
            GregorianCalendar creationDate, PList<BugReport> dependencies, Milestone milestone,
            boolean isPrivate)
            throws IllegalArgumentException {
        if (subsystem == null) {
            throw new IllegalArgumentException("The subsystem passed to CreateBugReportModelCmd was null.");
        }

        this.subsystem = subsystem;
        this.user = user;
        this.title = title;
        this.desc = description;
        this.creationDate = creationDate;
        this.dependencies = dependencies;
        this.milestone = milestone;
        this.isPrivate = isPrivate;
        this.trigger = null;
        this.stacktrace = null;
        this.error = null;
    }

    private final Subsystem subsystem;
    private final User user;
    private final String title;
    private final String desc;
    private final GregorianCalendar creationDate;
    private final PList<BugReport> dependencies;
    private final Milestone milestone;
    private final boolean isPrivate;
    private final String trigger;
    private final String stacktrace;
    private final String error;

    private boolean isExecuted = false;
    private BugReport bugReport;

    /**
     * Create a BugReport and add it to subsystem's list of bugReports.
     *
     * @return The newly created BugReport.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws IllegalArgumentException When the {@link BugReport} constructor fails.
     * @throws IllegalArgumentException if isValidCreator(user) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> if creationDate == null: result.getDate() == current date at the moment of
     * initialization
     * <br><dt><b>Postconditions:</b><dd> result.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#BugReport(bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String,
     * java.util.GregorianCalendar, purecollections.PList, bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.Milestone,
     * boolean, java.lang.String, java.lang.String, java.lang.String)
     *
     */
    @Override
    BugReport exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateBugReportModelCmd was already executed.");
        }

        bugReport = subsystem.addBugReport(user, title, desc, creationDate, dependencies, milestone, isPrivate,
                trigger, stacktrace, error);
        return bugReport;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }
        subsystem.deleteBugReport(bugReport);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

}
