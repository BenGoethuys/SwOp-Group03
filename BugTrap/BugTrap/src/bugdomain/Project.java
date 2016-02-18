package bugdomain;

import java.util.Date;

public class Project extends AbstractSystem {

	private Date creationDate;
	private Date startDate;

	// testing comments because you know

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
	 * @throws NullPointerException
	 *             if the versionID is null.
	 * @throws IllegalArgumentException
	 *             if one of the String arguments or dates is invalid.
	 */
	public Project(VersionID version, String name, String description, Date creationDate, Date startDate)
			throws IllegalArgumentException, NullPointerException {
		super(version, name, description);
		if (isValidStartDate()) {
			setCreationDate(creationDate);
			setStartDate(startDate);
		} else {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		}
	}

	/**
	 * CAN BE REMOVED WITH PARENT PUSHED DOWN IN HIERARCHY
	 *
	 * This method checks the validity of the given name with the given parent.
	 * A Project is initialised with null as parent value and will return itself
	 * as parent.
	 * 
	 * @param name
	 *            The string argument to be used as name.
	 * @param parent
	 *            The parent of the element to be named.
	 * @return true if the name is valid on its own and the parent is null or
	 *         itself
	 */
	protected boolean isValidName(String name, AbstractSystem parent) {
		if (this.isValidName(name)) {
			return parent == null || parent == this;
		}
		return false;
	}

	/**
	 * This method checks the validity of the startdate.
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
	 * This i a getter for the startdate variable.
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
	 * This is a getter for the CreationDate variable.
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

	/**
	 * This method checks the validity of a given parent. A project's parent is
	 * to be kept at null. It will however return itself as parent in the
	 * getter.
	 * 
	 * @param parent
	 *            The given parent to be checked.
	 * @return false
	 */
	protected boolean isValidParent(AbstractSystem parent) {
		// null is de standaarwaarde voor een niet geïmplementeerde parent van
		// project. Indien we true zouden teruggeven,
		// kan er een nullpointer exception ontstaan bij setParent.
		return false;
	}
}
