package bugdomain;

import java.util.Date;

public class BugReport {
	
	/**
	 * 
	 * @param uniqueID The unique ID for the bugReport
	 * @param title The title of the bugReport
	 * @param description The description of the bugReport
	 * @param creationDate The creationDate of the bugReport
	 * @param tag The tag of the bugReport
	 * 
	 * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
	 * @throws NullPointerException if description is null
	 * @throws NullPointerException if creationDate is null
	 * @throws NullPointerException if tag is null
	 * @throws NullPointerException if title is null
	 */
	public BugReport(long uniqueID, String title, String description, Date creationDate, Tag tag) throws IllegalArgumentException, NullPointerException {
		this.setUniqueID(uniqueID);
		this.setTitle(title);
		this.setDescription(description);
		this.setCreationDate(creationDate);
		this.setTag(tag);
	}
	
	/**
	 * 
	 * @param uniqueID The unique ID for the bugReport
	 * @param title The title of the bugReport
	 * @param description The description of the bugReport
	 * @param creationDate The creationDate of the bugReport
	 * 
	 * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
	 * @throws NullPointerException if description is null
	 * @throws NullPointerException if creationDate is null
	 * @throws NullPointerException if title is null
	 * 
	 * @post new.getTag() == Tag.New
	 */
	public BugReport(long uniqueID, String title, String description, Date creationDate) throws IllegalArgumentException, NullPointerException {
		this.setUniqueID(uniqueID);
		this.setTitle(title);
		this.setDescription(description);
		this.setCreationDate(creationDate);
		
		this.setTag(Tag.New);
	}
	
	/**
	 * 
	 * @param uniqueID The unique ID for the bugReport
	 * @param title The title of the bugReport
	 * @param description The description of the bugReport
	 * @param creationDate The creationDate of the bugReport
	 * 
	 * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
	 * @throws NullPointerException if description is null
	 * @throws NullPointerException if title is null
	 * 
	 * @post new.getDate() == current date at the moment of initialisation
	 * @post new.getTag() == Tag.New
	 */
	public BugReport(long uniqueID, String title, String description) throws IllegalArgumentException, NullPointerException {
		this.setUniqueID(uniqueID);
		this.setTitle(title);
		this.setDescription(description);
		
		this.setCreationDate(new Date());
		this.setTag(Tag.New);
	}

	private long uniqueID;
	private String title;
	private String description;
	private Date creationDate;
	private Tag tag;
	
	/**
	 * This method returns the unique ID for the BugReport
	 * @return the uniqueID
	 */
	public long getUniqueID() {
		return uniqueID;
	}
	
	/**
	 * This method sets the ID of the BugReport
	 * @param uniqueID the uniqueID to set
	 */
	private void setUniqueID(long uniqueID) {
		this.isValidUniqueID(uniqueID);
		this.uniqueID = uniqueID;
	}
	
	/**
	 * This method checks if the given ID is valid for this object.
	 * 
	 * @param uniqueID
	 */
	public boolean isValidUniqueID(long uniqueID) throws IllegalArgumentException {
		// Add check for unique ID
		return true;
	}
	
	/**
	 * This method returns the title of the bug report
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * This method sets the title of the bug report
	 * @param title the title to set
	 * 
	 * @throws NullPointerException if title is null
	 */
	private void setTitle(String title) throws NullPointerException {
		if (title == null){
			throw new NullPointerException("The given title for the bug report is a nullpointer");
		}
		this.title = title;
	}
	
	/**
	 * This method returns the description of the bug report
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 * 
	 * @throws NullPointerException if the given description is null
	 */
	private void setDescription(String description) throws NullPointerException {
		if (description == null){
			throw new NullPointerException("The description given for the bug report is a nullpointer");
		}
		this.description = description;
	}
	
	/**
	 * This method returns the creation date for the project
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return (Date) creationDate.clone();
	}
	
	/**
	 * This method sets the creation date of the bug report
	 * @param creationDate the creationDate to set
	 * 
	 * @throws NullPointerException if the given creation date is null
	 */
	private void setCreationDate(Date creationDate) throws NullPointerException {
		if (creationDate == null){
			throw new NullPointerException("The givien creation date in bug report is a nullpointer");
		}
		this.creationDate = creationDate;
	}

	/**
	 * @return the tag
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 * 
	 * @throws NullPointerException if the given tag is null
	 */
	private void setTag(Tag tag) throws NullPointerException {
		if (tag == null){
			throw new NullPointerException("The given tag for bug report is a nullpointer");
		}
		this.tag = tag;
	}
}
