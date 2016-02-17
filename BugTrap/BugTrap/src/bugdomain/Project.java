package bugdomain;

import java.util.Date;

public class Project extends AbstractSystem {

	private Date creationDate;
	private Date startDate;

	/**
	 * Creates a project with a given versionID, name, description, creationDate
	 * and startDate.
	 * 
	 * @param version
	 *            The versionID of the project.
	 * @param name
	 *            The name of the project.
	 * @param description
	 *            The description of the project.
	 * @param creationDate
	 *            The creation date of the project.
	 * @param startDate
	 *            The start date of the project.
	 */
	public Project(VersionID version, String name, String description, Date creationDate, Date startDate) throws IllegalArgumentException{
		super(version,name,description);
		if (isValidStartDate()) {
			setCreationDate(creationDate);
			setStartDate(startDate);
		} else {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		}
	}

	/**
	 * This method evaluates the validity of the given name.
	 * It cannot be an empty name string.
	 * @param name The given string to be set as name.
	 * @return ture if the name is not empty.
     */
	protected boolean isValidName(String name){
		if (name != ""){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return True if creation date <= start date.
	 */
	private boolean isValidStartDate() {
		if (creationDate.before(startDate) || creationDate.compareTo(startDate) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return The start date of the project.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the project to the given date.
	 * 
	 * @param startDate
	 *            The start date of the project.
	 */
	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return The creation date of the project.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date of the project to the given date.
	 * 
	 * @param creationDate
	 *            The creation date of the project.
	 */
	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	 protected boolean isValidParent(AbstractSystem Parent){
		 return false;
	 }

	protected AbstractSystem
	protected void addSubsystem(Subsystem subsystem) {
		// TODO;
		return;
	}

	protected Subsystem[] getSubsystems() {
		// TODO
		return null;
	}
}
