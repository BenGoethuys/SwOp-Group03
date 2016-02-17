package bugdomain;

import java.util.Date;

public class Project extends Systems {

	private VersionID version;
	private String name = "";
	private String description = "";
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
	public Project(VersionID version, String name, String description, Date creationDate, Date startDate) {
		if (version != null) {
			setVersionID(version);
		} else {
			throw new IllegalArgumentException("Illegal version");
		}
		if (name != "") {
			setName(name);
		} else {
			throw new IllegalArgumentException("The project name can't be empty.");
		}
		if (description != "") {
			setDescription(description);
		} else {
			throw new IllegalArgumentException("The project description can't be empty.");
		}
		if (isValidStartDate()) {
			setCreationDate(creationDate);
			setStartDate(startDate);
		} else {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		}
	}

	/**
	 * 
	 * @return The versionID of the project.
	 */
	public VersionID getVersionID() {
		return version;
	}

	/**
	 * Sets the versionID of the project to the given versionID.
	 * 
	 * @param version
	 *            The versionID of the project.
	 */
	private void setVersionID(VersionID version) {
		this.version = version;
	}

	/**
	 * 
	 * @return The name of the project.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the project to the given name.
	 * 
	 * @param name
	 *            The name of the project.
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The description of the project.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the project to the given description.
	 * 
	 * @param description
	 *            The description of the project.
	 */
	private void setDescription(String description) {
		this.description = description;
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

	protected void addSubsystem(Subsystem subsystem) {
		// TODO
		return;
	}

	protected Subsystem[] getSubsystems() {
		// TODO
		return null;
	}
}
