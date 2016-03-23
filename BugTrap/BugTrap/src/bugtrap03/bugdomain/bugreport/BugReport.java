package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.mail.Subject;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import purecollections.PList;

import java.util.GregorianCalendar;
import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class represents a bug report
 *
 * @author Group 03
 */
@DomainAPI
public class BugReport extends Subject implements Comparable<BugReport> {

    //TODO additional information

    /**
     * General constructor for initializing a bug report
     *
     * @param creator      The User that wants to create this bug report
     * @param uniqueID     The unique ID for the bugReport
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @param milestone    The milestone of the bug report
     * @param isPrivate    The boolean that says if this bug report should be private or not
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     */
    @Ensures("result.getTag() == Tag.New")
    protected BugReport(User creator, long uniqueID, String title, String description, GregorianCalendar creationDate,
                        PList<BugReport> dependencies, Subsystem subsystem, Milestone milestone, boolean isPrivate)
            throws IllegalArgumentException, PermissionException {
        this.setCreator(creator);
        this.setUniqueID(uniqueID);
        this.setTitle(title);
        this.setDescription(description);
        this.setCreationDate(creationDate);

        this.setCommentList(PList.<Comment>empty());
        this.userList = PList.<Developer>empty();
        this.setDependencies(dependencies);

        this.setSubsystem(subsystem);
        this.setMilestone(milestone);
        this.isPrivate = isPrivate;

        this.setInternState(new BugReportStateNew());
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the current time as creationDate
     *
     * @param creator      The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @param milestone    The milestone of the bug report
     * @param isPrivate    The boolean that says if this bug report should be private or not
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#getNewUniqueID()
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport(User creator, String title, String description, GregorianCalendar creationDate,
                     PList<BugReport> dependencies, Subsystem subsystem, Milestone milestone, boolean isPrivate)
            throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, creationDate,
                dependencies, subsystem, milestone, isPrivate);
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the current time as creationDate
     *
     * @param creator      The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @param milestone    The milestone of the bug report
     * @param isPrivate    The boolean that says if this bug report should be private or not
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#getNewUniqueID()
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport(User creator, String title, String description, PList<BugReport> dependencies, Subsystem subsystem,
                     Milestone milestone, boolean isPrivate) throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, new GregorianCalendar(),
                dependencies, subsystem, milestone, isPrivate);
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the current time as creationDate
     *
     * @param creator      The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @param isPrivate    The boolean that says if this bug report should be private or not
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
     * <br><dt><b>Postconditions:</b><dd> new.getMileStone() == null
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#getNewUniqueID()
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport(User creator, String title, String description, PList<BugReport> dependencies,
                     Subsystem subsystem, boolean isPrivate) throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, new GregorianCalendar(),
                dependencies, subsystem, null, isPrivate);
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the current time as creationDate
     *
     * @param creator      The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
     * <br><dt><b>Postconditions:</b><dd> new.getMileStone() == null
     * <br><dt><b>Postconditions:</b><dd> new.getPrivate() == false
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#getNewUniqueID()
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport(User creator, String title, String description, GregorianCalendar calendar,
                     PList<BugReport> dependencies, Subsystem subsystem) throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, calendar,
                dependencies, subsystem, null, false);
    }

    /**
     * Constructor for creating a bug report with default tag "New" and the current time as creationDate
     *
     * @param creator      The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <br><dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
     * <br><dt><b>Postconditions:</b><dd> new.getMileStone() == null
     * <br><dt><b>Postconditions:</b><dd> new.getPrivate() == false
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#getNewUniqueID()
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport(User creator, String title, String description, PList<BugReport> dependencies,
                     Subsystem subsystem) throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, new GregorianCalendar(),
                dependencies, subsystem, null, false);
    }

    private long uniqueID;
    private String title;
    private String description;
    private GregorianCalendar creationDate;
    private PList<Comment> commentList;

    private User creator;
    private PList<Developer> userList;
    private PList<BugReport> dependencies;

    private Subsystem subsystem;
    private Milestone milestone;
    private boolean isPrivate;

    private BugReportState state;

    // HashMap to guarantee uniqueness of IDs
    private static final HashSet<Long> allTakenIDs = new HashSet<Long>();
    private static long uniqueIDCounter = 0;

    /**
     * This method returns the unique ID for the BugReport
     *
     * @return the uniqueID of this bug report
     */
    @DomainAPI
    public long getUniqueID() {
        return uniqueID;
    }

    /**
     * This method returns the first available uniqueID for a bug report
     *
     * @return a new uniqueId for a bug report
     */
    @DomainAPI
    public static long getNewUniqueID() {
        while (BugReport.allTakenIDs.contains(BugReport.uniqueIDCounter)) {
            BugReport.uniqueIDCounter++;
        }
        return BugReport.uniqueIDCounter;
    }

    /**
     * This method sets the ID of the BugReport
     *
     * @param uniqueID the uniqueID to set
     *
     * @throws IllegalArgumentException when the uniqueID is invalid
     *
     * @see BugReport#isValidUniqueID(long)
     */
    private void setUniqueID(long uniqueID) throws IllegalArgumentException {
        if (!BugReport.isValidUniqueID(uniqueID)) {
            throw new IllegalArgumentException("The given id is not unique");
        }
        BugReport.allTakenIDs.add(uniqueID);
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
    @DomainAPI
    public static boolean isValidUniqueID(long uniqueID) {
        if (BugReport.allTakenIDs.contains(uniqueID)) {
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
    @DomainAPI
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title of the bug report
     *
     * @param title the title to set
     *
     * @throws IllegalArgumentException if title is invalid
     *
     * @see BugReport#isValidTitle(String)
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (!BugReport.isValidTitle(title)) {
            throw new IllegalArgumentException("The given title for the bug report is not valid");
        }
        this.title = title;
    }

    /**
     * This method checks if the given argument is a valid argument for a bug report
     *
     * @param title the argument to check
     *
     * @return true if the argument is a valid argument
     */
    @DomainAPI
    public static boolean isValidTitle(String title) {
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
    @DomainAPI
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     *
     * @throws IllegalArgumentException if the given description is invalid
     *
     * @see BugReport#isValidDescription(String)
     */
    public void setDescription(String description) throws IllegalArgumentException {
        if (!BugReport.isValidDescription(description)) {
            throw new IllegalArgumentException("The description given for the bug report is invalid");
        }
        this.description = description;
    }

    /**
     * This method check if the given description is valid for a bug report
     *
     * @param description the description to check
     *
     * @return true if the description is valid
     */
    @DomainAPI
    public static boolean isValidDescription(String description) {
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
    @DomainAPI
    public GregorianCalendar getCreationDate() {
        return (GregorianCalendar) creationDate.clone();
    }

    /**
     * This method sets the creation date of the bug report
     *
     * @param creationDate the creationDate to set
     *
     * @throws IllegalArgumentException if the given creation date is invalid
     *
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     */
    private void setCreationDate(GregorianCalendar creationDate) throws IllegalArgumentException {
        if (!BugReport.isValidCreationDate(creationDate)) {
            throw new IllegalArgumentException("The given creation date in bug report is a nullPointer");
        }
        this.creationDate = creationDate;
    }

    /**
     * This method check if the given creationDate is valid for the bug report
     *
     * @param creationDate the Date to check
     *
     * @return true if the date is a valid date for the bug report
     */
    @DomainAPI
    public static boolean isValidCreationDate(GregorianCalendar creationDate) {
        if (creationDate == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the current tag of the bug report
     *
     * @return the tag
     */
    @DomainAPI
    public Tag getTag() {
        return this.getInternState().getTag();
    }

    /**
     * This method sets the current tag for this bug report
     *
     * @param tag   The new tag of this bug report
     * @param user  The user that wants to change the tag of this bug report
     *
     * @throws IllegalArgumentException if isValidTag(tag) throws this exception
     * @throws PermissionException      if the given user doesn't have the needed permission
     *                                  to change the tag of this bug report
     *
     * @see BugReport#isValidTag(Tag)
     */
    public void setTag(Tag tag, User user) throws IllegalArgumentException, PermissionException {
        if (! this.isValidTag(tag)){
            throw new IllegalArgumentException("The given tag is null and thus invalid for a bug report");
        }
        if (user == null){
            throw new IllegalArgumentException("The given user was null and thus cannot change the tag of the bug report");
        }
        if (! user.hasRolePermission(tag.getNeededPerm(), this.getSubsystem().getParentProject())){
            throw new PermissionException("The given user doesn't have the permission to set the requested tag");
        }
        this.getInternState().setTag(this, tag);
    }

    /**
     * This method check if the given tag is valid for the bug report
     *
     * @param tag the tag to check
     *
     * @return true if the tag is a valid tag
     */
    @DomainAPI
    public boolean isValidTag(Tag tag) {
        return this.getInternState().isValidTag(tag);
    }

    /**
     * This method returns the comments on this bug report
     *
     * @return the commentList of this bug report
     */
    @DomainAPI
    public PList<Comment> getCommentList() {
        return this.commentList;
    }

    /**
     * This method returns all comments in this bug report (deep search)
     * The top TreeNode will carry a null value.
     *
     * @return all the comments in this bug report
     */
    @DomainAPI
    public DefaultMutableTreeNode getAllComments() {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
        
        for(Comment comment : this.getCommentList()) {
            node.add(comment.getAllComments());
        }
        return node;
    }

    /**
     * This method sets the comment list for this bug report
     *
     * @param commentList the comment list for this bug report
     *
     * @throws IllegalArgumentException if the given PList is not valid for this bug report
     *
     * @see BugReport#isValidCommentList(PList)
     */
    private void setCommentList(PList<Comment> commentList) throws IllegalArgumentException {
        if (!isValidCommentList(commentList)) {
            throw new IllegalArgumentException("The given PList is invalid as comment-list for this bug report");
        }
        this.commentList = commentList;
    }

    /**
     * This method check if the given PList is valid as the comment-list of this bug report
     *
     * @param commentList the list to check
     *
     * @return true if the given PList is valid for this bug report
     */
    protected static boolean isValidCommentList(PList<Comment> commentList) {
        if (commentList == null) {
            return false;
        }
        // cannot add null object to purecollections list! -> no check needed
        return true;
    }

    /**
     * This method adds a comment to this bug report
     *
     * @param comment to add to this bug report
     *
     * @throws IllegalArgumentException if the given comment is not valid for this bug report
     *
     * @see BugReport#isValidComment(Comment)
     */
    protected Comment addComment(Comment comment) throws IllegalArgumentException {
        if (!this.isValidComment(comment)) {
            throw new IllegalArgumentException("The given comment is not a valid comment for this bug report");
        }
        this.commentList = this.getCommentList().plus(comment);
        return comment;
    }

    /**
     * This method makes a Comment object and adds it to the sub-comment list
     *
     * @param creator the creator of the comment
     * @param text    the text of the comment
     *
     * @throws IllegalArgumentException if the given parameters are not valid for a comment
     * @throws PermissionException      if the given creator doesn't have the needed permissions
     *
     * @see Comment#Comment(User, String)
     */
    public Comment addComment(User creator, String text) throws IllegalArgumentException, PermissionException {
        return this.addComment(new Comment(creator, text));
    }

    /**
     * This method checks if the given comment is a valid comment for this bug
     * report
     *
     * @param comment to check
     *
     * @return true if the given comment is valid as a comment for this bug report
     */
    @DomainAPI
    public boolean isValidComment(Comment comment) {
        if (comment == null) {
            return false;
        }
        if (this.getCommentList().contains(comment)) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the creator of this bug report
     *
     * @return the creator of the bug report
     */
    @DomainAPI
    public User getCreator() {
        return creator;
    }

    /**
     * This method sets the creator for this bug report
     *
     * @param creator the creator to set
     */
    private void setCreator(User creator) throws PermissionException, IllegalArgumentException {
        if (creator != null && !creator.hasPermission(UserPerm.CREATE_BUGREPORT)) {
            throw new PermissionException("The given creator doesn't have the permission to create a bug report");
        }
        if (!isValidCreator(creator)) {
            throw new IllegalArgumentException("The given creator is not valid for this bug report");
        }
        this.creator = creator;
    }

    /**
     * This method checks if the given creator is a valid creator for this bug report
     *
     * @param creator the creator to check
     *
     * @return true if the given creator is a valid creator
     */
    @DomainAPI
    public static boolean isValidCreator(User creator) {
        if (creator == null) {
            return false;
        }
        if (! creator.hasPermission(UserPerm.CREATE_BUGREPORT)) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the list of Developers associated with this bug report
     *
     * @return the userList the list of developers assigned to the bug report
     */
    @DomainAPI
    public PList<Developer> getUserList() {
        return userList;
    }

    /**
     * This method adds a developer to this bug report issued by the given user
     *
     * @param user The user that wants to add a developer to this bug report
     * @param dev  The developer to assign to this bug report
     *
     * @throws IllegalArgumentException If the given user was null
     * @throws PermissionException      If the given users doesn't have the needed permissions
     */
    public void addUser(User user, Developer dev) throws IllegalArgumentException, PermissionException {
        if (user == null ||
                ! user.hasRolePermission(RolePerm.ASSIGN_DEV_BUG_REPORT, this.getSubsystem().getParentProject())) {
            throw new PermissionException(
                    "The given user has insufficient permissions to assign the developer to this bug report");
        }
        if (! BugReport.isValidUser(dev)){
            throw new IllegalArgumentException("The given developer is invalid for a bug report");
        }
        this.getInternState().addUser(this, dev);
    }

    /**
     * This method adds a user to the list of users associated with this bug report
     * This method should only be used by BugReportState's !!
     *
     * @param dev   The developer to add
     */
    @Requires("BugReport.isValidUser(dev)")
    void addUser(Developer dev){
        this.userList = this.getUserList().plus(dev);
    }

    /**
     * This method check if the given developer is valid for adding to the associated developers list
     *
     * @param dev the developer to check
     *
     * @return true if the given developer is a valid developer for this bug report
     */
    @DomainAPI
    public static boolean isValidUser(Developer dev) {
        if (dev == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the dependencyList of this bug report
     *
     * @return the dependencies of the bug report
     */
    @DomainAPI
    public PList<BugReport> getDependencies() {
        return dependencies;
    }

    /**
     * This method sets the dependencies of the bug report
     * This method should only be called in the initialisation of this bug report !
     *
     * @param dependencies the dependencies to set for this bug report
     */
    private void setDependencies(PList<BugReport> dependencies) throws IllegalArgumentException {
        if (! BugReport.isValidDependencies(dependencies)) {
            throw new IllegalArgumentException("The given dependency list is invalid for this bug report");
        }
        this.dependencies = dependencies;
    }

    /**
     * This method checks if the given dependency list valid is for this bug report
     *
     * @param dependencies to check
     *
     * @return true if the given list is valid for this bug report
     */
    @DomainAPI
    public static boolean isValidDependencies(PList<BugReport> dependencies) {
        if (dependencies == null) {
            return false;
        }
        // PList cannot contain null elements
        // cannot contain itself because dependencies cannot change
        return true;
    }

    /**
     * This method check if any dependency of this bug report is still unresolved
     *
     * @return true if this bug report has a dependency that is still unresolved
     */
    @DomainAPI
    public boolean hasUnresolvedDependencies(){
        for (BugReport bugReport : this.getDependencies()){
            if (! bugReport.isResolved()){
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the subsystem to which this bug report belongs
     *
     * @return the subsystem the subsystem of this bug report
     */
    @DomainAPI
    public Subsystem getSubsystem() {
        return subsystem;
    }

    /**
     * This method sets the subsystem of this bug report
     *
     * @param subsystem the subsystem to set
     *
     * @throws IllegalArgumentException if isValidSubsystem(subsystem) fails
     *
     * @see BugReport#isValidSubsystem(Subsystem)
     */
    private void setSubsystem(Subsystem subsystem) throws IllegalArgumentException {
        if (! BugReport.isValidSubsystem(subsystem)) {
            throw new IllegalArgumentException("The given subsystem is invalid for this bug report");
        }
        this.subsystem = subsystem;
    }

    /**
     * This method check if the given subsystem is valid for this bug report
     *
     * @param subsystem the subsystem to check
     *
     * @return true if the given subsystem is valid for this bug report
     */
    @DomainAPI
    public static boolean isValidSubsystem(Subsystem subsystem) {
        if (subsystem == null) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the Milestone.
     *
     * @return The milestone of the bug report.
     */
    public Milestone getMilestone() {
        return this.milestone;
    }

    /**
     * Sets the Milestone of the bug report to the given Milestone.
     *
     * @param milestone The milestone of the bug report.
     *
     * @throws IllegalArgumentException When milestone is a invalid.
     *
     * @see #isValidMilestone(Milestone)
     */
    public void setMilestone(Milestone milestone) throws NullPointerException {
        if (! isValidMilestone(milestone)) {
            throw new IllegalArgumentException("The given Milestone is not valid for this bug report");
        }
        this.milestone = milestone;
    }

    /**
     * This method check if the given Milestone is a valid Milestone for a bug report
     *
     * @param milestone the Milestone to check
     *
     * @return true if the given Milestone is valid for a bug report.
     */
    public boolean isValidMilestone(Milestone milestone) {
        if (milestone == null) {
            return true; //It's possible a bugreport has not a milestone.
        }
        else if (milestone.compareTo(this.getSubsystem().getMilestone()) == 1) {
                return true;    
            }
            else {
                return false;
            }
    }

    /**
     * This method returns the private state of this bug report
     *
     * @return  true if this bug report is considered private
     */
    @DomainAPI
    public boolean isPrivate(){
        return isPrivate;
    }

    /**
     * This method sets the private field of this bug report to the given boolean
     *
     * @param isPrivate The new boolean for this isPrivate field of this bug report
     */
    public void setPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>
     * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>
     * Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all
     * <tt>z</tt>.
     * <p>
     * <p>
     * It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>. Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates this
     * condition should clearly indicate this fact. The recommended language is
     * "Note: this class has a natural ordering that is inconsistent with
     * equals."
     * <p>
     * <p>
     * In the foregoing description, the notation <tt>sgn(</tt><i>expression</i>
     * <tt>)</tt> designates the mathematical <i>signum</i> function, which is
     * defined to return one of <tt>-1</tt>, <tt>0</tt>, or <tt>1</tt> according
     * to whether the value of <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    @DomainAPI
    public int compareTo(BugReport o) {
        if (o.getUniqueID() < this.getUniqueID()) {
            return -1;
        }
        // cannot be the same
        return 1;
    }

    /**
     * This method returns all important details of this bug report
     *
     * @return the most important details of this bug report
     */
    public String getDetails() {
        DefaultMutableTreeNode comments = this.getAllComments();
        StringBuilder str = new StringBuilder();
        
        str.append("Bug report id: ").append(this.getUniqueID());
        str.append("\n creator: ").append(this.getCreator().getFullName());
        str.append("\n title: ").append(this.getTitle());
        str.append("\n description: ").append(this.getDescription());
        str.append("\n creation date: ").append(this.getCreationDate().getTime());
        str.append("\n is private: ").append(this.isPrivate());
        str.append("\n target milestone: ").append(this.getMilestone().toString());
        str.append("\n subsystem: ").append(this.getSubsystem().getName());
        // add state specific stuff:
        str.append(this.getInternState().getDetails());

        // print list of comments and deps
        str.append("\n comments: ").append(Comment.commentsTreeToString(comments));
        str.append("\n dependencies: ");
        for (BugReport bugrep : this.getDependencies()) {
            str.append("\n \t id: ").append(bugrep.getUniqueID()).append(", title: ").append(bugrep.getTitle());
        }

        return str.toString();
    }

    /**
     * This method returns the current intern state of this bug report
     * This method should only be used by this and the BugReportState's
     *
     * @return The current intern state of this bug report
     */
    BugReportState getInternState(){
        return this.state;
    }

    /**
     * This method sets the intern sate of this bug report
     * This method should only be used by this and the BugReportState's
     *
     * @param state The new intern state of this bug report
     *
     * @throws IllegalArgumentException If the given state is null and thus invalid for a bug report
     */
    void setInternState(BugReportState state) throws IllegalArgumentException {
        if (state == null){
            throw new IllegalArgumentException("The given state is null and invalid for a bug report");
        }
        //TODO change to @Requires? (== pre-condition) ? -> should never be called from outside anyways
        this.state = state;
    }

    /**
     * This method adds a given test to the bug report state
     *
     * @param user  The user that wants to add the test to this bug report state
     * @param test  The test that the user wants to add
     *
     * @throws PermissionException      If the given user doesn't have the permission to add a test
     * @throws IllegalStateException    If the current state doesn't allow to add a test
     * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
     *
     * @see BugReport#isValidTest(String)
     */
    public void addTest(User user, String test) throws PermissionException, IllegalStateException, IllegalArgumentException{
        if (! BugReport.isValidTest(test)){
            throw new IllegalArgumentException("The given test is not a valid test for a bug report");
        }
        if (user == null || ! user.hasRolePermission(RolePerm.ADD_TEST, this.getSubsystem().getParentProject())){
            throw new PermissionException("The given user doesn't have the permission to add a test");
        }
        this.getInternState().addTest(this, test);
    }

    /**
     * This method returns all the tests associated with this bug report
     *
     * @return  The list of tests associated with this bug report
     */
    @DomainAPI
    PList<String> getTests() {
        try {
            return this.getInternState().getTests();
        } catch (IllegalStateException e){
            // where no tests -> return empty PList
            return PList.<String>empty();
        }
    }

    /**
     * This method check if a given test would be a valid test for a bug report
     * @param test  The test to check
     * @return  True if the given test could be a valid test for a bug report
     */
    @DomainAPI
    public static boolean isValidTest(String test){
        if (test ==  null || test.equalsIgnoreCase("")){
            return false;
        }
        return true;
    }

    /**
     * This method adds a given patch to this bug report state
     *
     * @param user  The user that wants to add the patch to this bug report state
     * @param patch The patch that the user wants to submit
     *
     * @throws PermissionException      If the given user doesn't have the permission to add a patch to this bug report state
     * @throws IllegalStateException    If the given patch is invalid for this bug report
     * @throws IllegalArgumentException If the given patch is not valid for this bug report state
     */
    public void addPatch(User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException{
        if (! BugReport.isValidPatch(patch)){
            throw new IllegalArgumentException("The given patch is invalid for a bug report");
        }
        if (user == null || ! user.hasRolePermission(RolePerm.ADD_PATCH, this.getSubsystem().getParentProject())){
            throw new PermissionException("The given user doesn't have the permission to add a patch");
        }
        this.getInternState().addPatch(this, patch);
    }

    /**
     * This method returns the patches associated with this bug report
     *
     * @return  A list of patches associated with this bug report
     */
    @DomainAPI
    public PList<String> getPatches() {
        try {
            return this.getInternState().getPatches();
        } catch (IllegalStateException e){
            // where no patches -> return empty PList
            return PList.<String>empty();
        }
    }

    /**
     * This method check if the given patch could be a valid patch for a bug report
     * @param patch The patch to check
     * @return  true if the given patch is a valid patch for a bug report
     */
    @DomainAPI
    public static boolean isValidPatch(String patch){
        if (patch ==  null || patch.equalsIgnoreCase("")){
            return false;
        }
        return true;
    }

    /**
     * This method selects a patch for this bug report state
     *
     * @param user  The user that wants to select the patch
     * @param patch The patch that the user wants to select
     *
     * @throws PermissionException      If the given user doesn't have the permission to select a patch for this bug report state
     * @throws IllegalStateException    If the current state doesn't allow the selecting of a patch
     * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
     */
    public void selectPatch(User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException{
        if (user == null || ! user.hasRolePermission(RolePerm.SELECT_PATCH, this.getSubsystem().getParentProject())){
            throw new PermissionException("The given user doesn't have the permission to select a patch");
        }
        this.getInternState().selectPatch(this, patch);
    }

    /**
     * This method returns the selected patch of this bug report
     *
     * @throws IllegalStateException    If the current state of this bug report doesn't have a selected patch
     *
     * @return  The selected patch of this bug report
     */
    @DomainAPI
    public String getSelectedPatch() throws IllegalStateException {
        return this.getInternState().getSelectedPatch();
    }

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param user  The user that wants to assign a score to this bug report
     * @param score The score that the creator wants to give
     *
     * @throws IllegalStateException    If the current state doesn't allow assigning a score
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     */
    public void giveScore(User user, int score) throws IllegalStateException, IllegalArgumentException, PermissionException {
        if (! this.getCreator().equals(user)){
            throw new PermissionException("The given user doesn't have the permission to set the score");
        }
        this.getInternState().giveScore(this, score);
    }

    /**
     * This method returns the score associated with this bug report
     *
     * @throws IllegalStateException    If the current bug report has no score
     *
     * @return  The score of this bug report
     */
    @DomainAPI
    public int getScore() throws IllegalStateException {
        return this.getInternState().getScore();
    }

    /**
     * This method sets the duplicate of this bugreport to the given duplicate
     *
     * @param user      The user that wants to assign the duplicate to this bug report
     * @param duplicate The duplicate bug report that will be associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't allow for a duplicate to be set
     * @throws IllegalArgumentException If the given duplicate is invalid for this bug report
     * @throws PermissionException      If the given user doesn't have the needed permission
     *
     * @see BugReport#isValidDuplicate(BugReport)
     */
    public void setDuplicate(User user, BugReport duplicate) throws IllegalStateException, IllegalArgumentException, PermissionException {
        if (! this.isValidDuplicate(duplicate)){
            throw new IllegalArgumentException("The given duplicate is not valid for this bug report");
        }
        if (user == null || ! user.hasRolePermission(RolePerm.SET_TAG_DUPLICATE, this.getSubsystem().getParentProject())){
            throw new PermissionException("The given user doesn't have the permission to set this bug report as a duplicate");
        }
        this.getInternState().setDuplicate(this, duplicate);
    }

    /**
     * This method returns the duplicate associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't allow for a duplicate bug report
     *
     * @return  The duplicate bug report associated with this bug report
     */
    @DomainAPI
    public BugReport getDuplicate() throws IllegalStateException{
        return this.getInternState().getDuplicate();
    }

    /**
     * This method check if a given duplicate could be a valid duplicate for a bug report
     * @param duplicate The duplicate to check
     * @return  True if the given duplicate is valid duplicate for this bug report
     */
    @DomainAPI
    public boolean isValidDuplicate(BugReport duplicate){
        // TODO should duplicate be one of the deps of this bug report?
        if (duplicate == null || duplicate == this){
            return false;
        }
        return true;
    }

    /**
     * This method check if the current state is a resolved state of a bug report
     *
     * @return true if the given state is considered resoled for a bug report
     */
    @DomainAPI
    public boolean isResolved() {
        return this.getInternState().isResolved();
    }
}
