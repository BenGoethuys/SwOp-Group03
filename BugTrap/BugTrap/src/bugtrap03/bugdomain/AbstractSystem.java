package bugtrap03.bugdomain;

import java.util.ArrayList;

import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import purecollections.PList;

/**
 * This class represents a system
 * @author Group 03.
 */
@DomainAPI
public abstract class AbstractSystem {

    private VersionID version;
    private String name = "";
    private String description = "";
    private PList<Subsystem> childs;

    /**
     * This constructor is used for all elements of type AbstractSystem,
     * although possibly indirect.
     *
     * @param version     The versionID (of that type) of this element.
     * @param name        The string name for this element.
     * @param description The string description of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if isValidVerionID(version) fails
     * @see AbstractSystem#isValidVersionId(VersionID)
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     */
    public AbstractSystem(VersionID version, String name, String description)
            throws IllegalArgumentException {
        setVersionID(version);
        setName(name);
        setDescription(description);
        this.setChilds(PList.<Subsystem>empty());
    }

    /**
     * This constructor is used for all elements of type AbstractSystem,
     * although possibly indirect.
     *
     * @param name        The string name for this element.
     * @param description The string description of this element.
     * @throws IllegalArgumentException if one of the String arguments is
     *                                  invalid.
     */
    public AbstractSystem(String name, String description) throws IllegalArgumentException {
        this(new VersionID(), name, description);
    }

    /**
     * This is a getter for the version variable.
     *
     * @return The versionID of the project.
     */
    @DomainAPI
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
        if (! isValidVersionId(version)) {
            throw new IllegalArgumentException("The given versionId is not valid for this abstractSystem");
        }
        this.version = version;
    }

    /**
     * This method check if the given VersionId is a valid verionId for an AbstractSystem
     * @param versionID the versionId to check
     * @return true if the given versionId is a valid i for an AbstractSystem
     */
    @DomainAPI
    public static boolean isValidVersionId(VersionID versionID){
        if (versionID == null){
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the name variable.
     *
     * @return The name of the project.
     */
    @DomainAPI
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
    @DomainAPI
    public static boolean isValidName(String name) {
        return (!"".equals(name) && name != null);
    }

    /**
     * This is a getter for the description variable.
     *
     * @return The description of the project.
     */
    @DomainAPI
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
     *
     * @param desc The description to check.
     * @return False when desc is a null reference or an empty string.
     */
    @DomainAPI
    public static boolean isValidDescription(String desc) {
        if (desc == null) {
            return false;
        }
        if (desc.equals("")) {
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

    /**
     * This method adds a subsystem to this AbstractSystem
     *
     * @param version       The versionID of the new subsystem
     * @param name          The name of te new subsystem
     * @param description   The description of the new subsystem
     * @return  The new subsytems that was added to this AbstractSystem
     */
    public Subsystem makeSubsystemChild(VersionID version, String name, String description) {
        Subsystem newChild = new Subsystem(version, name, description, this);
        this.addChild(newChild);
        return newChild;
    }

    /**
     * This method adds a subsystem to this AbstractSystem
     *
     * @param name          The name of te new subsystem
     * @param description   The description of the new subsystem
     * @return  The new subsytems that was added to this AbstractSystem
     * @Ensures The new versionID will be equal to "new VersionID()"
     */
    public Subsystem makeSubsystemChild(String name, String description) {
        Subsystem newChild = new Subsystem(name, description, this);
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
        this.childs = this.getChilds().plus(child);
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
     *
     * @return the list of all bugReports
     */
    @DomainAPI
    public PList<BugReport> getAllBugReports() {
        ArrayList<BugReport> list = new ArrayList<>();
        for (Subsystem subsystem : this.getChilds()) {
            list.addAll(subsystem.getAllBugReports());
        }
        return PList.<BugReport>empty().plusAll(list);
    }
    
    

    /**
     * This recursive method returns all the subsystems that are a child of this AbstracSystem
     *
     * @return the list of all Subsystem childs.
     */
    @DomainAPI
    public PList<Subsystem> getAllSubsystems() {
        ArrayList<Subsystem> list = new ArrayList<>();
        for (Subsystem subsystem : this.getChilds()) {
            list.add(subsystem);
            list.addAll(subsystem.getAllSubsystems());
        }
        return PList.<Subsystem>empty().plusAll(list);
    }

    /**
     * This method checks if the given developer has the requested permission
     * for this AbstractSystem
     *
     * @param dev the developer to check
     * @param perm the requested permission
     * @return true if the developer has the requested permission
     */
    @DomainAPI
    public boolean hasPermission(Developer dev, RolePerm perm) {
        return this.getParentProject().hasPermission(dev, perm);
    }
    

}
