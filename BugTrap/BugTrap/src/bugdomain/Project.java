package bugdomain;

import java.util.Date;

public class Project extends AbstractSystem {

	private Date creationDate;
	private Date startDate;

	//testing comments because you know

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

	protected boolean isValidName(String name, AbstractSystem parent){
		if (this.isValidName(name)) {
			return parent == null;
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

	 protected boolean isValidParent(AbstractSystem parent){
		 //null is de standaarwaarde voor een niet geïmplementeerde parent van project. Indien we true zouden teruggeven,
		 //kan er een nullpointer exception ontstaan bij setParent.
		 return false;
	 }
}
