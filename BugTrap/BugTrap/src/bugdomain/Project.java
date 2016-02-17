package bugdomain;

import java.util.Date;

public class Project extends System {

	private VersionID version;
	private String name = "";
	private String description = "";
	private Date creationDate;
	private Date startDate;

	public Project(VersionID version, String name, String description, Date creationDate, Date startDate) {
		if (version == null) {
			throw new IllegalArgumentException("Illegal version");
		} else {
			setVersionID(version);
		}
		if (name == "") {
			throw new IllegalArgumentException("The project name can't be empty.");
		} else {
			setName(name);
		}
		if (description == "") {
			throw new IllegalArgumentException("The project description can't be empty.");
		} else {
			setDescription(description);
		}
		if (!isValidStartDate()) {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		} else {
			setCreationDate(creationDate);
			setStartDate(startDate);
		}

	}

	public VersionID getVersionID() {
		return version;
	}

	private void setVersionID(VersionID version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private boolean isValidStartDate() {
		if (creationDate.before(startDate) || creationDate.compareTo(startDate) == 0) {
			return true;
		}
		return false;
	}
	
	private void setStartDate(Date startDate) {
		
	}

	private void setCreationDate(Date creationDate) {
	
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
