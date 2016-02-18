package bugdomain;

import java.util.ArrayList;

/**
 * Created by Kwinten on 17/02/2016.
 *
 * TODO if parent is pushed down to subclass, methods can be pushed down to...
 */
public abstract class AbstractSystem {

	private VersionID version;
	private String name = "";
	private String description = "";
	private AbstractSystem parent;
	private ArrayList<Subsystem> childs;

	/**
	 * CAN BE PUSHED DOWN IN HIERARCHY --> remove because only necessary in
	 * Subsystem
	 *
	 * This constructor is used for elements of the subclass Subsyste, since
	 * they have a parent.
	 * 
	 * @param version
	 *            The versionID (of that type) of this element.
	 * @param name
	 *            The string name for this element.
	 * @param description
	 *            The string description of this element.
	 * @param parent
	 *            The parent (a Project or Subsystem) of this element.
	 * @throws NullPointerException
	 *             if the versionID is null.
	 * @throws IllegalArgumentException
	 *             if one of the String arguments is invalid.
	 */
	public AbstractSystem(VersionID version, String name, String description, AbstractSystem parent)
			throws NullPointerException, IllegalArgumentException {
		this(version, name, description);
		if (!this.isValidName(name, parent)) {
			throw new IllegalArgumentException("The name is invalid");
		}
		this.setParent(parent);
	}

	/**
	 * This constructor is used for all elements of type 'AbstracSystem',
	 * although possibly indirect.
	 * 
	 * @param version
	 *            The versionID (of that type) of this element.
	 * @param name
	 *            The string name for this element.
	 * @param description
	 *            The string description of this element.
	 * @throws NullPointerException
	 *             if the versionID is null.
	 * @throws IllegalArgumentException
	 *             if one of the String arguments is invalid.
	 */
	public AbstractSystem(VersionID version, String name, String description)
			throws NullPointerException, IllegalArgumentException {
		// not yet implemented
		if (version != null) {
			setVersionID(version);
		} else {
			throw new NullPointerException("Illegal version is null");
		}
		if (this.isValidName(name)) {
			this.setName(name);
		} else {
			throw new IllegalArgumentException("The name is invalid");

		}
		if (description != "") {
			setDescription(description);
		} else {
			throw new IllegalArgumentException("The project description can't be empty.");
		}
		this.childs = new ArrayList<Subsystem>();
	}
	// Exceptions werpen in de isValid in plaats van in de constructor, en de
	// isValid in de set...

	/**
	 * This is a getter for the version variable.
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
	 * This is a getter for the name variable.
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
	 * This method checks the validity of the given name.
	 * 
	 * @param name
	 *            The string argument to used as name.
	 * @return true if the name is not an empty string or null.
	 */
	protected boolean isValidName(String name) {
		return (name != "" && name != null);
	}

	/**
	 * CAN BE PUSHED DOWN IN HIERARCHY --> remove because only necessary in
	 * Subsystem
	 *
	 * This is an abstract heading for the function that chekcs the validity of
	 * the given name, in combination with its parent.
	 * 
	 * @param name
	 *            The string argument to be used as name.
	 * @param parent
	 *            The parent of the element to be named.
	 * @return true is valid.
	 */
	protected abstract boolean isValidName(String name, AbstractSystem parent);

	/**
	 * This is a getter for the description variable.
	 * 
	 * @return The description of the project.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the AbstractSystem to the given description.
	 * 
	 * @param description
	 *            The description of the project.
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * CAN BE PUSHED DOWN IN HIERARCHY--> only necessary for Subsystem
	 *
	 * Sets the parent of the AbstractSystem to the given parent, if valid. Only
	 * elements of subclass subsystems have a parent different from null.
	 * 
	 * @param parent
	 *            The given parent of the AbstractSystem
	 */
	private void setParent(AbstractSystem parent) {
		if (isValidParent(parent)) {
			this.parent = parent;
			// only a element from type subsystem has a parent
			parent.addChild((Subsystem) this);
		}
	}

	/**
	 * CAN BE PUSHED DOWN IN HIERARCHY --> remove because only necessary in
	 * Subsystem
	 *
	 * This abstract method is the heading for a validity check on the parent.
	 * 
	 * @param parent
	 *            The given parent to be checked.
	 * @return true if valid.
	 */
	protected abstract boolean isValidParent(AbstractSystem parent);

	/**
	 * CAN BE PUSHED DOWN IN HIERARCHY
	 *
	 * This is a getter for the parent of the AbstractSystem. A project is it's
	 * own parent.
	 * 
	 * @return The parent of an element with type Subclass or the Project.
	 */
	protected AbstractSystem getParent() {
		if (this.parent != null) {
			return this.parent;
		} else {
			return this;
		}
	}

	/**
	 * This method returns the head of the subsystem tree structure. This is an
	 * element from the type Project and can be recognised by his self reference
	 * in getParent().
	 * 
	 * @return the Project to which al the subsystems are linked.
	 */
	protected Project getParentProject() {
		AbstractSystem localParent = this.getParent();
		AbstractSystem localGrandParent = localParent.getParent();
		while (localParent != localGrandParent) {
			localParent = localGrandParent;
			localGrandParent = localParent.getParent();
		}
		// only an element of type project has itself as parent value
		return (Project) localParent;
	}

	/**
	 * A getter for the arraylist of childs.
	 * 
	 * @return an arraylist of childs.
	 */
	protected ArrayList<Subsystem> getChilds() {
		return this.childs;
	}

	/**
	 * This method sets the child variable to the given child. A child is of
	 * type Subsystem.
	 * 
	 * @param child
	 *            The given subsystem to set as child.
	 */
	protected void addChild(Subsystem child) {
		this.getChilds().add(child);
	}

}
