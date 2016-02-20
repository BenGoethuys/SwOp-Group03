package bugtrap03.bugdomain;

import java.util.Date;
import java.util.HashMap;

// TODO BugReport checks uniqueness locally at this point
/**
 * This class represents a bug report
 *
 * @author Ben Goethuys
 */
public class BugReport {

    /**
     * General constructor for initializing a bug report
     *
     * @param uniqueID The unique ID for the bug report
     * @param title The title of the bugReport
     * @param description The description of the bug report
     * @param creationDate The creationDate of the bug report
     * @param tag The tag of the bugReport
     *
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate)
     * fails
     * @throws IllegalArgumentException if isValidTag(tag) fails
     *
     * @see isValidUniqueID(long)
     * @see isValidTitle(String)
     * @see isValidDescription(String)
     * @see isValidCreationDate(Date)
     * @see isValidTag(Tag)
     */
    protected BugReport(long uniqueID, String title, String description, Date creationDate, Tag tag)
            throws IllegalArgumentException {
        this.setUniqueID(uniqueID);
        this.setTitle(title);
        this.setDescription(description);
        this.setCreationDate(creationDate);
        this.setTag(tag);
    }

    /**
     * Constructor for creating a bug report with default tag "New"
     *
     * @param uniqueID The unique ID for the bugReport
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     *
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate)
     * fails
     *
     * @see isValidUniqueID(uniqueID)
     * @see isValidTitle(title)
     * @see isValidDescription(description)
     * @see isValidCreationDate(creationDate)
     *
     * @post new.getTag() == Tag.New
     */
    public BugReport(long uniqueID, String title, String description, Date creationDate)
            throws IllegalArgumentException {
        this(uniqueID, title, description, creationDate, Tag.New);
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the
     * current time as creationDate
     *
     * @param uniqueID The unique ID for the bugReport
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     *
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     *
     * @see isValidUniqueID(uniqueID)
     * @see isValidTitle(title)
     * @see isValidDescription(description)
     *
     * @post new.getDate() == current date at the moment of initialisation
     * @post new.getTag() == Tag.New
     */
    public BugReport(long uniqueID, String title, String description) throws IllegalArgumentException {
        this(uniqueID, title, description, new Date());
    }

    private long uniqueID;
    private String title;
    private String description;
    private Date creationDate;
    private Tag tag;

    //HashMap to guarantee uniqueness of IDs
    private static final HashMap<Long, Long> allTakenIDs = new HashMap<Long, Long>();

    /**
     * This method returns the unique ID for the BugReport
     *
     * @return the uniqueID of this bug report
     */
    public long getUniqueID() {
        return uniqueID;
    }

    /**
     * This method sets the ID of the BugReport
     *
     * @param uniqueID the uniqueID to set
     *
     * @throws IllegalArgumentException when the uniqueID is invalid
     * @see isValidUniqueID
     */
    private void setUniqueID(long uniqueID) throws IllegalArgumentException {
        this.isValidUniqueID(uniqueID);
        BugReport.allTakenIDs.put(uniqueID, uniqueID);
        this.uniqueID = uniqueID;
    }

    /**
     * This method checks if the given ID is valid for this object.
     *
     * @param uniqueID the ID to check
     *
     * @return true if the key is not taken at this point and is a valid ID for
     * a bug report
     */
    public boolean isValidUniqueID(long uniqueID) {
        if (BugReport.allTakenIDs.containsKey(uniqueID)) {
            return false;
        }
        if (uniqueID < 0) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the title of the bug report
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title of the bug report
     *
     * @param title the title to set
     *
     * @throws IllegalArgumentException if title is invalid
     * @see isValidTitle(String)
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (!this.isValidTitle(title)) {
            throw new IllegalArgumentException("The given title for the bug report is not valid");
        }
        this.title = title;
    }

    /**
     * This method checks if the given argument is a valid argument for a bug
     * report
     *
     * @param title the argument to check
     * @return true if the argument is a valid argument
     */
    public boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the description of the bug report
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     *
     * @throws IllegalArgumentException if the given description is invalid
     * @see isValidDescription(String)
     */
    public void setDescription(String description) throws IllegalArgumentException {
        if (!this.isValidDescription(description)) {
            throw new IllegalArgumentException("The description given for the bug report is invalid");
        }
        this.description = description;
    }

    /**
     * This method check if the given description is valid for a bug report
     *
     * @param description the description to check
     * @return true if the description is valid
     */
    public boolean isValidDescription(String description) {
        if (description == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the creation date for the project
     *
     * @return the creationDate
     */
    public Date getCreationDate() {
        return (Date) creationDate.clone();
    }

    /**
     * This method sets the creation date of the bug report
     *
     * @param creationDate the creationDate to set
     *
     * @throws IllegalArgumentException if the given creation date is invalid
     * @see isValidCreationDate(Date)
     */
    private void setCreationDate(Date creationDate) throws IllegalArgumentException {
        if (!this.isValidCreationDate(creationDate)) {
            throw new IllegalArgumentException("The givien creation date in bug report is a nullpointer");
        }
        this.creationDate = creationDate;
    }

    /**
     * This method check if the given creationDate is valid for the bug report
     *
     * @param creationDate the Date to check
     * @return true if the date is a valid date for the bug report
     */
    public boolean isValidCreationDate(Date creationDate) {
        if (creationDate == null) {
            return false;
        }
        return true;
    }

    /**
     * @return the tag
     */
    public Tag getTag() {
        return tag;
    }

	//TODO make function that allows specific users to change the tag
    /**
     * @param tag the tag to set
     *
     * @throws IllegalArgumentException if the given tag is null
     */
    protected void setTag(Tag tag) throws IllegalArgumentException {
        if (!this.isValidTag(tag)) {
            throw new IllegalArgumentException("The given tag for bug report is a nullpointer");
        }
        this.tag = tag;
    }

    /**
     * This method check if the given tag is valid for the bug report
     *
     * @param tag the tag to check
     * @return true if the tag is a valid tag
     */
    public boolean isValidTag(Tag tag) {
        if (tag == null) {
            return false;
        }
        //TODO check for correct new tag (this tag can only be set if this tag is now assigned and stuff)
        return true;
    }
}
