package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import java.util.GregorianCalendar;
import purecollections.PList;

/**
 *
 * <b>This class is the Memento of {@link BugReport}, this means it is a snapshot of the state of a BugReport.</b>
 * <br> <u>Caution:</u> Due to time/effort balancing this currently is not a fully functional Memento in the way that it
 * is designed to fit with the undo operations. This means that if it is used outside of the undo operations, for
 * example a Subsystem could get into a state it never could have without re-setting the memento of the bugReport
 * (Under_Review -> Resolved. Update Subsystem Milestone. Revert to Under_review. = inconsistent state.)
 * <br>
 * <br> Specifically it does not guarantee consistency with the Subsystem and the comments or any internal state change
 * of the Developers.
 *
 * @author Group 03
 */
class BugReportMemento {

    private long uniqueID; //stays same. np
    private String title; //immutable. np
    private String description; //immutable. np
    private GregorianCalendar creationDate; //clone. np
    private PList<Comment> commentList; //Immutable PList of Comments. np

    private User creator; //Unchanged and internal state not cared about. np
    private PList<Developer> userList; //Immutable PList + Internal state not cared about. np
    private PList<BugReport> dependencies; //Immutable PList + never changes anyway. np

    /**
     * Stays the same. The internal state of the subsystem can be changed but for undoing we'll be ignoring this; If we
     * later on don't want to ignore this an option is to recheck milestone constraint and throw Illegal if required.
     */
    private Subsystem subsystem; //Stays the same.
    private Milestone milestone; //Immutable. np
    private boolean isPrivate; //Immutable. np

    private String trigger; //Immutable. np
    private String stacktrace; //Immutable. np
    private String error; //Immutable. np

    private BugReportState state; //Immutable. np

    long getUniqueID() {
        return this.uniqueID;
    }

    String getTitle() {
        return this.title;
    }

    String getDescription() {
        return this.description;
    }

    /**
     * Gets a Clone of the stored creationDate
     *
     * @return A clone of the stored creationDate
     */
    GregorianCalendar getCreationDate() {
        return (GregorianCalendar) this.creationDate.clone();
    }

    /**
     * The list of comments directly onto the BugReport at the time of creation of this Memento.
     * <br> This currently does allow for changes to have happened to the comments (such as subComments).
     *
     * @return The List of comments directly onto the BugReport at the time of creation of this Memento.
     */
    PList<Comment> getComments() {
        return this.commentList;
    }

    User getCreator() {
        return this.creator;
    }

    PList<Developer> getUserList() {
        return this.userList;
    }

    PList<BugReport> getDependencies() {
        return this.dependencies;
    }

    /**
     * Get the subsystem stored as the subsystem in the BugReport at the time of the creation of this Memento.
     *
     * @return The reference to the Subsystem that the BugReport had at the time of the creation of this Memento.
     */
    Subsystem getSubsystem() {
        return this.subsystem;
    }

    Milestone getMilestone() {
        return this.milestone;
    }

    boolean isPrivate() {
        return this.isPrivate;
    }

    String getTrigger() {
        return this.trigger;
    }

    String getStackTrace() {
        return this.stacktrace;
    }

    String getError() {
        return this.error;
    }

    BugReportState getSate() {
        return this.state;
    }
}
