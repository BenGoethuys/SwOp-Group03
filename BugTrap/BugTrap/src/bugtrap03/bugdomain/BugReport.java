package bugtrap03.bugdomain;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.mail.Subject;
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import purecollections.PList;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;

/**
 * This class represents a bug report
 *
 * @author Group 03
 */
@DomainAPI
public class BugReport extends Subject implements Comparable<BugReport> {

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
                        PList<BugReport> dependencies, Subsystem subsystem, Milestone milestone)
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
     * <dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
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
                     PList<BugReport> dependencies, Subsystem subsystem, Milestone milestone)
            throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, creationDate, dependencies, subsystem, milestone);
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
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     *
     * <dt><b>Postconditions:</b><dd> new.getDate() == current date at the moment of initialization
     * <dt><b>Postconditions:</b><dd> new.getUniqueID() is an unique ID for this bug report
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
                     Milestone milestone) throws IllegalArgumentException, PermissionException {
        this(creator, BugReport.getNewUniqueID(), title, description, new GregorianCalendar(), dependencies, subsystem,
                milestone);
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
        //TODO remove old method comment
        //return tag;
    }

    /**
     * This method sets the current tag for this bug report
     *
     * @param tag    The new tag of this bug report
     * @param user The user that wants to change the tag of this bug report
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
        this.getInternState().setTag(this, user, tag);
        // TODO better heading
        // TODO remove old method comments
//        if (this.getCreator().equals(user) && this.getTag() == Tag.UNDER_REVIEW
//                && (tag == Tag.ASSIGNED || tag == Tag.RESOLVED)) {
//            this.setTag(tag);
//        } else if (tag == null) {
//            throw new IllegalArgumentException(
//                    "The given tag for bug report is not valid for this state of the bug report");
//        } else if (!user.hasRolePermission(tag.getNeededPerm(), this.getSubsystem().getParentProject())) {
//            throw new PermissionException("The given user: " + user.getFullName()
//                    + ", doens't have the needed permission to change the tag of this bug report");
//        } else {
//            this.setTag(tag);
//        }
    }

//    /**
//     * This method sets the current tag for this bug report
//     *
//     * @param tag the tag to set
//     *
//     * @throws IllegalArgumentException if isValidTag(tag) fails
//     *
//     * @see BugReport#isValidTag(Tag)
//     */
//    private void setTag(Tag tag) throws IllegalArgumentException {
//        if (!this.isValidTag(tag)) {
//            throw new IllegalArgumentException(
//                    "The given tag for bug report is not valid for this state of the bug report");
//        }
//        this.tag = tag;
//    }

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
        //TODO remove old method comments
//        if (this.getTag() == null) {
//            return tag == Tag.NEW;
//        }
//        return this.getTag().isValidTag(tag);
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
     *
     * @return all the comments in this bug report
     */
    @DomainAPI
    public PList<Comment> getAllComments() {
        ArrayList<Comment> list = new ArrayList<>();
        for (Comment comment : this.getCommentList()) {
            list.addAll(comment.getAllComments());
        }
        return PList.<Comment>empty().plusAll(list);
    }

    /**
     * This method sets the comment list for this bug report
     *
     * @param commentList the comment list for this bug report
     *
     * @throws IllegalArgumentException if the given PList is not valid for this bug report
     *
     * @see BugReport#isValidCommentList(PList<Comment>)
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
        // cannot add null object to purecollections list! -> redundant to check

        // for (Comment comment: commentList){
        // if (comment == null){
        // return false;
        // }
        // }
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
        if (!creator.hasPermission(UserPerm.CREATE_BUGREPORT)) {
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

//    /**
//     * This method sets the list of developers associated with this bug report
//     *
//     * @param userList the userList to set
//     */
//    private void setUserList(PList<Developer> userList) {
//        this.userList = userList;
//    }
//
//    /**
//     * This method checks if the given list of Developers is valid for this bug report.
//     *
//     * @param userList the list of developers for this bug report
//     *
//     * @return true if the given list is a valid list of developers
//     */
//    @DomainAPI
//    public static boolean isValidUserList(PList<Developer> userList) {
//        if (userList == null) {
//            return false;
//        }
//
//        for (Developer dev : userList) {
//            PList<Developer> list = userList.minus(dev);
//            if (list.contains(dev)) {
//                return false;
//            }
//        }
//
//        // cannot add null object to purecollections list! -> redundant to check
//        // for (Developer user : userList){
//        // if (user == null){
//        // return false;
//        // }
//        // }
//        return true;
//    }

    /**
     * This method adds a developer to the associated developers list of this bug report
     *
     * @param dev he developer to check
     *
     * @throws IllegalArgumentException If the given developer was not valid for this bug report
     */
    protected void addUser(Developer dev) throws IllegalArgumentException {
        this.getInternState().addUser(this, dev);
        //TODO remove old method comments
//        if (!this.userList.contains(dev)) {
//            if (!isValidUser(dev)) {
//                throw new IllegalArgumentException( "The given developer is not a valid developer to associate with this bug report");
//            }
//
//            // first time user associated check
//            if (this.getTag() == Tag.NEW && this.getUserList().isEmpty()) {
//                this.setTag(Tag.ASSIGNED);
//            }
//            if (!this.userList.contains(dev)) {
//                this.userList = this.getUserList().plus(dev);
//            }
//        }
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
        if (user == null) {
            throw new IllegalArgumentException("the given user was null");
        }
        if (!user.hasRolePermission(RolePerm.ASSIGN_DEV_BUGREPORT, this.getSubsystem().getParentProject())) {
            throw new PermissionException(
                    "The given user has insufficient permissions to assign the developer to this bug report");
        }
        this.addUser(dev);
    }

    /**
     * This method check if the given developer is valid for adding to the associated developers list
     *
     * @param dev the developer to check
     *
     * @return true if the given developer is a valid developer for this bug report
     */
    @DomainAPI
    public boolean isValidUser(Developer dev) {
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
     * This method should only be call in the initialisation of this bug report
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
     * This method check if any dependecy of this bug report is still unresolved
     *
     * @return true if this bug report has a dependency that is still unresolved
     */
    protected boolean hasUnresolvedDependencies(){
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
        if (!BugReport.isValidSubsystem(subsystem)) {
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
        //TODO zie opdracht
        if (!isValidMilestone(milestone)) {
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
    public static boolean isValidMilestone(Milestone milestone) {
        //TODO zie opdracht voor valid milestone bij een bugreport
        if (milestone == null) {
            return false;
        }
        return true;
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
        String str = "Bug report id: " + this.getUniqueID();
        str += "\n creator: " + this.getCreator().getFullName();
        str += "\n title: " + this.getTitle();
        str += "\n description: " + this.getDescription();
        str += "\n creation date: " + this.getCreationDate().getTime();
        str += "\n tag: " + this.getTag().name();
        str += "\n comments: ";
        for (Comment comment : this.getAllComments()) {
            str += "\n \t " + comment.getText();
        }
        str += "\n dependencies: ";
        for (BugReport bugrep : this.getDependencies()) {
            str += "\n \t id: " + bugrep.getUniqueID() + ", title: " + bugrep.getTitle();
        }
        str += "\n subsystem: " + this.getSubsystem().getName();

        return str;
    }

    /**
     * This method returns the current intern state of this bug report
     *
     * @return The current intern state of this bug report
     */
    private BugReportState getInternState(){
        return this.state;
    }

    /**
     * This method sets the intern sate of this bug report
     * @param state The new intern state of this bug report
     * @throws IllegalArgumentException If the given state is null and thus invalid for a bug report
     */
    private void setInternState(BugReportState state) throws IllegalArgumentException {
        if (state == null){
            throw new IllegalArgumentException("The given state is null and invalid for a bug report");
        }
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
     */
    public void addTest(User user, String test) throws PermissionException, IllegalStateException, IllegalArgumentException{
        this.getInternState().addTest(this, user, test);
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
        this.getInternState().addPatch(user, patch);
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
        this.getInternState().selectPatch(this, user, patch);
    }

    /**
     * This method gives the selected patch of this bug report states a score
     * @param score The score that the creator wants to give
     *
     * @throws IllegalStateException    If the current state doesn't allow assigning a score
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     */
    public void giveScore(int score) throws IllegalStateException, IllegalArgumentException{
        this.getInternState().giveScore(this, score);
    }

    /**
     * This method check if the current state is a resolved state of a bug report
     *
     * @return true if the given state is considered resoled for a bug report
     */
    @DomainAPI
    public boolean isResolved(){
        return this.getInternState().isResolved();
    }

    /**
     * An interface for the different states of a bug report
     */
    interface BugReportState {

        /**
         * This method returns the tag associated with this bug report state
         *
         * @return  The tag associated with this bug report state
         */
        Tag getTag();

        /**
         * This method sets the current tag for this bug report
         *
         * @param bugReport The bug report this state belongs to
         * @param tag   The new tag of this bug report
         * @param user  The issuer that wants to change the tag of this bug report
         *
         * @throws IllegalArgumentException If isValidTag(tag) throws this exception
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         * @throws IllegalStateException    If the current state doesn't allow the new Tag
         *
         * @see #isValidTag(Tag)
         */
        @Requires("bugReport.getInternState() == this && user != null && bugReport.isValidTag(tag)")
        void setTag(BugReport bugReport, User user, Tag tag) throws IllegalArgumentException, IllegalStateException;

        /**
         * This method check if the given tag is valid for the bug report
         *
         * @param tag the tag to check
         *
         * @return true if the tag is a valid tag
         */
        boolean isValidTag(Tag tag);

        /**
         * This method adds a developer to this bug report issued by the given user
         *
         * @param bugReport The bug report this state belongs to
         * @param dev       The developer to assign to this bug report
         *
         * @throws IllegalArgumentException If the given dev was null
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Requires("bugReport.getInternState() == this")
        void addUser(BugReport bugReport, Developer dev) throws IllegalArgumentException;

        /**
         * This method adds a given test to the bug report state
         *
         * @param bugReport The bug report this state belongs to
         * @param user  The user that wants to add the test to this bug report state
         * @param test  The test that the user wants to add
         *
         * @throws PermissionException      If the given user doesn't have the permission to add a test
         * @throws IllegalStateException    If the current state doesn't allow to add a test
         * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Requires("bugReport.getInternState() == this")
        void addTest(BugReport bugReport, User user, String test) throws PermissionException, IllegalStateException, IllegalArgumentException;

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
        void addPatch(User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException;

        /**
         * This method selects a patch for this bug report state
         *
         * @param bugReport The bug report this state belongs to
         * @param user  The user that wants to select the patch
         * @param patch The patch that the user wants to select
         *
         * @throws PermissionException      If the given user doesn't have the permission to select a patch for this bug report state
         * @throws IllegalStateException    If the current state doesn't allow the selecting of a patch
         * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Requires("bugReport.getInternState() == this")
        void selectPatch(BugReport bugReport, User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException;

        /**
         * This method returns the selected patch of this bug report state
         *
         * @throws IllegalStateException    If the given state doesn't have a select patch
         *
         * @return The selected patch for this bug report
         */
        String getSelectedPatch() throws IllegalStateException;

        /**
         * This method gives the selected patch of this bug report states a score
         *
         * @param bugReport The bug report this state belongs to
         * @param score The score that the creator wants to give
         *
         * @throws IllegalStateException    If the current state doesn't allow assigning a score
         * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Requires("bugReport.getInternState() == this")
        void giveScore(BugReport bugReport, int score) throws IllegalStateException, IllegalArgumentException;

        /**
         * This method check if the current state is a resolved state of a bug report
         *
         * @return true if the given state is considered resoled for a bug report
         */
        boolean isResolved();

    }

    /**
     * This class represents the New state of a bug report
     */
    class BugReportStateNew implements BugReportState {

        /**
         * constructor for this state
         */
        BugReportStateNew(){

        }

        /**
         * This method returns the tag associated with this bug report state
         *
         * @return The tag associated with this bug report state
         */
        @Override
        public Tag getTag() {
            return Tag.NEW;
        }

        /**
         * This method sets the current tag for this bug report
         *
         * @param bugReport The bug report this state belongs to
         * @param user      The issuer that wants to change the tag of this bug report
         * @param tag       The new tag of this bug report
         * @throws IllegalArgumentException If isValidTag(tag) throws this exception
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         * @throws IllegalStateException    If the given state doesn't allow a the new tag
         * @see #isValidTag(Tag)
         */
        @Override
        @Requires("bugReport.getInternState() == this && user != null && bugReport.isValidTag(tag)")
        public void setTag(BugReport bugReport, User user, Tag tag) throws IllegalArgumentException, IllegalStateException {
            if (bugReport.hasUnresolvedDependencies()){
                throw new IllegalStateException("cannot assign this tag to the bug report in its current state");
            }
            assert (tag ==  Tag.NOT_A_BUG);
            bugReport.setInternState(new BugReportStateNatABug());
            // TODO finisch
        }

        /**
         * This method check if the given tag is valid for the bug report
         *
         * @param tag the tag to check
         * @return true if the tag is a valid tag
         */
        @Override
        public boolean isValidTag(Tag tag) {
            if (tag == Tag.NOT_A_BUG){
                return true;
            }
            // TODO is a tag that can be automatically assigned also valid? If so change this method and setTag !
            return false;
        }

        /**
         * This method adds a developer to this bug report issued by the given user
         *
         * @param bugReport The bug report this state belongs to
         * @param dev       The developer to assign to this bug report
         * @throws IllegalArgumentException If the given dev was null
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Override
        @Requires("bugReport.getInternState() == this")
        public void addUser(BugReport bugReport, Developer dev) throws IllegalArgumentException {

        }

        /**
         * This method adds a given test to the bug report state
         *
         * @param bugReport The bug report this state belongs to
         * @param user      The user that wants to add the test to this bug report state
         * @param test      The test that the user wants to add
         * @throws PermissionException      If the given user doesn't have the permission to add a test
         * @throws IllegalStateException    If the current state doesn't allow to add a test
         * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Override
        @Requires("bugReport.getInternState() == this")
        public void addTest(BugReport bugReport, User user, String test) throws PermissionException, IllegalStateException, IllegalArgumentException {

        }

        /**
         * This method adds a given patch to this bug report state
         *
         * @param user  The user that wants to add the patch to this bug report state
         * @param patch The patch that the user wants to submit
         * @throws PermissionException      If the given user doesn't have the permission to add a patch to this bug report state
         * @throws IllegalStateException    If the given patch is invalid for this bug report
         * @throws IllegalArgumentException If the given patch is not valid for this bug report state
         */
        @Override
        public void addPatch(User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException {

        }

        /**
         * This method selects a patch for this bug report state
         *
         * @param bugReport The bug report this state belongs to
         * @param user      The user that wants to select the patch
         * @param patch     The patch that the user wants to select
         * @throws PermissionException      If the given user doesn't have the permission to select a patch for this bug report state
         * @throws IllegalStateException    If the current state doesn't allow the selecting of a patch
         * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Override
        @Requires("bugReport.getInternState() == this")
        public void selectPatch(BugReport bugReport, User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException {

        }

        /**
         * This method returns the selected patch of this bug report state
         *
         * @return The selected patch for this bug report
         * @throws IllegalStateException If the given state doesn't have a select patch
         */
        @Override
        public String getSelectedPatch() throws IllegalStateException {
            return null;
        }

        /**
         * This method gives the selected patch of this bug report states a score
         *
         * @param bugReport The bug report this state belongs to
         * @param score     The score that the creator wants to give
         * @throws IllegalStateException    If the current state doesn't allow assigning a score
         * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
         * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
         */
        @Override
        @Requires("bugReport.getInternState() == this")
        public void giveScore(BugReport bugReport, int score) throws IllegalStateException, IllegalArgumentException {

        }

        /**
         * This method check if the current state is a resolved state of a bug report
         *
         * @return true if the given state is considered resoled for a bug report
         */
        @Override
        public boolean isResolved() {
            return false;
        }
    }

    /**
     * This class represents the assigned state of a bug report
     */
    class BugReportStateAssigned implements BugReportState {

    }

    /**
     * This class represents the assigned state with also an assigned test of a bug report
     */
    class BugReportStateAssignedWithTest implements BugReportState {

    }

    /**
     * This class represents the under review state of a bug report
     */
    class BugReportStateUnderReview implements BugReportState {

    }

    /**
     * This class represents the resolved state of a bug report
     */
    class BugReportStateResolved implements BugReportState {

    }

    /**
     * This class represents the closed state of a bug report
     */
    class BugReportStateClosed implements BugReportState {

    }

    /**
     * This class represents the Duplicate state of a bug report
     */
    class BugReportStateDuplicate implements BugReportState {

    }

    /**
     * This class represents the NotABug state of a bug report
     */
    class BugReportStateNatABug implements BugReportState {

    }
}
