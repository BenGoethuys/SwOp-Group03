package bugtrap03.bugdomain;

import java.util.Date;
import java.util.HashSet;

import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import purecollections.PList;

/**
 * This class represents a bug report
 *
 * @author Ben Goethuys
 */
public class BugReport {

    /**
     * General constructor for initialising a bug report
     *
     * @param uniqueID The unique ID for the bug report
     * @param title The title of the bugReport
     * @param description The description of the bug report
     * @param creationDate The creationDate of the bug report
     * @param tag The tag of the bugReport
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) 
     * 										fails
     * @throws IllegalArgumentException if isValidTag(tag) fails
     * @throws IllegalArgumentException if isValidDependencies(PList<BugReport>) 
     * 										fails
     *
     * @see isValidCreator(Issuer)
     * @see isValidUniqueID(long)
     * @see isValidTitle(String)
     * @see isValidDescription(String)
     * @see isValidCreationDate(Date)
     * @see isValidTag(Tag)
     * @see isValidDependencies(PList<BugReport>)
     */
    private BugReport(Issuer creator, long uniqueID, String title, String description, Date creationDate, Tag tag, PList<BugReport> dependencies)
            throws IllegalArgumentException {
    	this.setCreator(creator);
        this.setUniqueID(uniqueID);
        this.setTitle(title);
        this.setDescription(description);
        this.setCreationDate(creationDate);
        this.setTag(tag);
        
        this.setCommentList(PList.<Comment>empty());
        this.setUserList(PList.<Developer>empty());
        this.setDependencies(dependencies);
    }

    /**
     * Constructor for creating a bug report with default tag "New"
     *
     * @param uniqueID The unique ID for the bugReport
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate)
     * 									fails
     *
     * @see isValidCreator(Issuer)
     * @see isValidUniqueID(long)
     * @see isValidTitle(String)
     * @see isValidDescription(String)
     * @see isValidCreationDate(Date)
     *
     * @post new.getTag() == Tag.New
     */
    public BugReport(Issuer creator, long uniqueID, String title, String description, Date creationDate, PList<BugReport> dependencies)
            throws IllegalArgumentException {
        this(creator, uniqueID, title, description, creationDate, Tag.NEW, dependencies);
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
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     *
     * @see isValidCreator(Issuer)
     * @see isValidUniqueID(long)
     * @see isValidTitle(String)
     * @see isValidDescription(String)
     *
     * @post new.getDate() == current date at the moment of initialisation
     * @post new.getTag() == Tag.New
     */
    public BugReport(Issuer creator, long uniqueID, String title, String description, PList<BugReport> dependencies) throws IllegalArgumentException {
        this(creator, uniqueID, title, description, new Date(), dependencies);
    }

    private long uniqueID;
    private String title;
    private String description;
    private Date creationDate;
    private Tag tag;
    private PList<Comment> commentList;
    
    private Issuer creator;
    private PList<Developer> userList;
    private PList<BugReport> dependencies;
    
    //HashMap to guarantee uniqueness of IDs
    private static final HashSet<Long> allTakenIDs = new HashSet<Long>();

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
     * @see isValidUniqueID(long)
     */
    private void setUniqueID(long uniqueID) throws IllegalArgumentException {
        this.isValidUniqueID(uniqueID);
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
    public boolean isValidUniqueID(long uniqueID) {
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
        return this.description;
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
            throw new IllegalArgumentException("The given tag for bug report is not valid for this state of the bug report");
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
    	if (this.getTag() == null){
    		return tag == Tag.NEW;
    	}
        return this.getTag().isValidTag(tag);
    }
    
    /**
	 * This method returns the comments on this bug report
	 * @return the commentList of this bug report
	 */
	public PList<Comment> getCommentList() {
		return this.commentList;
	}
	
	/**
	 * This method sets the comment list for this bug report
	 * @param commentList the comment list for this bug report
	 * 
	 * @throws IllegalArgumentException if the given PList is not valid for this bug report
	 * 
	 * @see isValidCommentList(PList<Comment>)
	 */
	private void setCommentList(PList<Comment> commentList) throws IllegalArgumentException {
		if (! isValidCommentList(commentList)){
			throw new IllegalArgumentException("The given PList is invalid as comment-list for this bug report");
		}
		this.commentList = commentList;
	}
	
	/**
	 * This method check if the given PList is valid as the comment-list of this bug report
	 * @param commentList the list to check
	 * 
	 * @return true if the given PList is valid for this bug report
	 */
	protected boolean isValidCommentList(PList<Comment> commentList){
		if (commentList == null){
			return false;
		}
		// cannot add null object to purecollections list! -> redundant to check
		
//		for (Comment comment: commentList){
//			if (comment == null){
//				return false;
//			}
//		}
		return true;
	}
	
	/**
	 * This method adds a comment to this bug report
	 * @param comment to add to this bug report
	 * 
	 * @throws IllegalArgumentException if the given comment is not valid for this bug report
	 * 
	 * @see isValidComment(Comment)
	 */
	protected void addComment(Comment comment) throws IllegalArgumentException {
		if (! this.isValidComment(comment)){
			throw new IllegalArgumentException("The given comment is not a valid comment for this bug report");
		}
		this.commentList = this.getCommentList().plus(comment);
	}
	
	/**
	 * This method makes a Comment object and adds it to the sub-comment list
	 * @param creator the creator of the comment
	 * @param text the text of the comment
	 * 
	 * @throws IllegalArgumentException if the given parameters are not valid for a comment
	 * 
	 * @see Comment(Issuer, String)
	 */
	public void addComment(Issuer creator, String text) throws IllegalArgumentException {
		this.addComment(new Comment(creator, text));
	}
	
	/**
	 * This method checks if the given comment is a valid comment for this bug report
	 * @param comment to check
	 * 
	 * @return true if the given comment is valid as a comment for this bug report
	 */
	public boolean isValidComment(Comment comment){
		if (comment == null){
			return false;
		}
		if (this.getCommentList().contains(comment)){
			return false;
		}
		return true;
	}

	/**
	 * This method returns the creator of this bug report
	 * @return the creator of the bug report
	 */
	public Issuer getCreator() {
		return creator;
	}

	/**
	 * This method sets the creator for this bug report
	 * @param creator the creator to set
	 */
	private void setCreator(Issuer creator) throws IllegalArgumentException {
		if (! isValidCreator(creator)){
			throw new IllegalArgumentException("The given creator is not valid for this bug report");
		}
		this.creator = creator;
	}
	
	/**
	 * This method checks if the given creator is a valid creator for this bug report
	 * @param creator the creator to check
	 * @return true if the given creator is a valid creator
	 */
	public boolean isValidCreator(Issuer creator){
		if (creator == null){
			return false;
		}
		return true;
	}

	/**
	 * This method returns the list of Developers associated with this bug report
	 * @return the userList the list of developers assigned to the bug report
	 */
	public PList<Developer> getUserList() {
		return userList;
	}

	/**
	 * This method sets the list of developers associated with this bug report
	 * @param userList the userList to set
	 */
	private void setUserList(PList<Developer> userList) {
		this.userList = userList;
	}
	
	/**
	 * This method checks if the given list of Developers is valid for this bug report
	 * @param userList the list of developers for this bug report
	 * @return true if the given list is a valid list of developers
	 */
	public boolean isValidUserList(PList<Developer> userList){
		if (userList == null){
			return false;
		}
		
		// cannot add null object to purecollections list! -> redundant to check
		
//		for (Developer user : userList){
//			if (user == null){
//				return false;
//			}
//		}
		return true;
	}
	
	/**
	 * This method adds a developer to the associated developers list of this bug report
	 * @param dev he developer to check
	 */
	public void addUser(Developer dev){
		if (! isValidUser(dev)){
			throw new IllegalArgumentException("The given developer is not a valid developer to associate with this bug report");
		}
		
		// first time user associated check
		if (this.getTag() == Tag.NEW && this.getUserList().isEmpty()){
			this.setTag(Tag.ASSIGNED);
		}
		this.userList = this.getUserList().plus(dev);
	}
	
	/**
	 * This method check if the given developer is valid for adding to the associated developers list
	 * @param dev the developer to check
	 * @return true if the given developer is a valid developer for this bug report
	 */
	public boolean isValidUser(Developer dev){
		if (dev == null){
			return false;
		}
		if (this.getUserList().contains(dev)){
			return false;
		}
		return true;
	}

	/**
	 * This method returns the dependencyList of this bug report
	 * @return the dependencies of the bug report
	 */
	public PList<BugReport> getDependencies() {
		return dependencies;
	}

	/**
	 * This method sets the dependencies of the bug report
	 * @param dependencies the dependencies to set for this bug report
	 */
	private void setDependencies(PList<BugReport> dependencies) throws IllegalArgumentException {
		if (! this.isValidDependencies(dependencies)){
			throw new IllegalArgumentException("The given dependency list is invalid for this bug report");
		}
		this.dependencies = dependencies;
	}
	
	/**
	 * This method checks if the given dependency list valid is for this bug report
	 * @param dependencies to check
	 * @return true if the given list is valid for this bug report
	 */
	public boolean isValidDependencies(PList<BugReport> dependencies){
		if (dependencies == null){
			return false;
		}
		return true;
	}
	
	//TODO recursive ref. for dependencies
}
