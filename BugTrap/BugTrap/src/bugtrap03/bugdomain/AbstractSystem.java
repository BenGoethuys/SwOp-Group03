package bugtrap03.bugdomain;

import java.util.ArrayList;

import bugtrap03.permission.RolePerm;
import bugtrap03.usersystem.Developer;
import purecollections.PList;

/**
 * @author Group 03.
 */
public abstract class AbstractSystem {

	private VersionID version;
	private String name = "";
	private String description = "";
	private PList<Subsystem> childs;

	/**
	 * This constructor is used for all elements of type AbstractSystem,
	 * although possibly indirect.
	 * 
	 * @param version The versionID (of that type) of this element.
	 * @param name The string name for this element.
	 * @param description The string description of this element.
	 * 
	 * @throws NullPointerException if the versionID is null.
	 * @throws IllegalArgumentException if one of the String arguments is
	 *             invalid.
	 *             
	 * //TODO @ see to isValid functions
	 */
	public AbstractSystem(VersionID version, String name, String description)
			throws NullPointerException, IllegalArgumentException {
		setVersionID(version);
		setName(name);
		setDescription(description);
		this.setChilds(PList.<Subsystem>empty());
		
		//TODO remove NullPointers via isValid functions and throw illegalArg in setters - Ben ;)
	}
	
	/**
	 * This constructor is used for all elements of type AbstractSystem,
	 * although possibly indirect.
	 * 
	 * @param name The string name for this element.
	 * @param description The string description of this element.
	 * 
	 * @throws IllegalArgumentException if one of the String arguments is
	 *             invalid.
	 *             
	 */
	public AbstractSystem(String name, String description) throws IllegalArgumentException {
		this(new VersionID(0, 0, 1), name, description);
	}

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
	 * @param version The versionID of the project.
	 * @throws NullPointerException When version is a null-reference.
	 */
	public void setVersionID(VersionID version) throws NullPointerException {
		if (version != null) {
			this.version = version;
		} else {
			throw new NullPointerException("Illegal version is null");
		}
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
	 * @param name The name of the project.
	 * @throws IllegalArgumentException When the given name is invalid.
         * @see #isValidName(java.lang.String) 
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (isValidName(name)) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("The name is invalid");

		}

	}

	/**
	 * This method checks the validity of the given name.
	 * 
	 * @param name The string argument to used as name.
	 * @return true if the name is not an empty string or null.
	 */
	public boolean isValidName(String name) {
		return (!"".equals(name) && name != null);
	}

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
	 * @param description The description of the project.
	 * @throws IllegalArgumentException When the description is invalid.
         * @see #isValidDescription(java.lang.String) 
	 */
	public void setDescription(String description) throws IllegalArgumentException {
		if (isValidDescription(description)) {
			this.description = description;
		} else {
			throw new IllegalArgumentException("AbstractSystem requires a valid description.");
		}
	}
        
        /**
         * Whether the given description is a valid description.
         * @param desc The description to check.
         * @return False when desc is a null reference or an empty string.
         */
        public static boolean isValidDescription(String desc) {
            if(desc == null) {
                return false;
            }
            if(desc.equals("")) {
                return false;
            }
            return true;
        }
	
	private void setChilds(PList<Subsystem> childlist) {
		this.childs = childlist;
	}
	
	/**
	 * A getter for the PList of childs.
	 * 
	 * @return an PList of childs.
	 */
	protected PList<Subsystem> getChilds() {
		return this.childs;
	}

	public Subsystem makeSubsystemChild(VersionID version, String name, String description){
		Subsystem newChild = new Subsystem(version, name, description, this);
		this.addChild(newChild);
		return newChild;
	}
	
	/**
	 * This method adds the given child to the PList of childs. A child is of
	 * type Subsystem.
	 * 
	 * @param child The given subsystem to set as child.
	 */
	private void addChild(Subsystem child) {
		this.getChilds().plus(child);
	}

	/**
	 * This is an abstract getter for the parent of the AbstractSystem.
	 * 
	 * @return The parent of an element with type Subclass or the Project.
	 */
	protected abstract AbstractSystem getParent();

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
	 * This method returns all the bug reports associated with this AbstractSystem
	 * @return the list of all bugReports
	 */
	public PList<BugReport> getAllBugReports(){
		ArrayList<BugReport> list = new ArrayList<>();
		for (Subsystem subsystem : this.getChilds()){
			list.addAll(subsystem.getAllBugReports());
		}
		return PList.<BugReport>empty().plusAll(list);
	}
	
	/**
	 * This method checks if the given developer has the requested permission for this subsystem
	 * @param dev the developer to check
	 * @param perm the requested permission
	 * @return true if the developer has the requested permission
	 */
	public abstract boolean hasPermission(Developer dev, RolePerm perm);

}
