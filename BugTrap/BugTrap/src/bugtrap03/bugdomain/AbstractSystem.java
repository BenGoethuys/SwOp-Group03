package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.notification.AbstractSystemSubject;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import purecollections.PList;

import java.util.ArrayList;

/**
 * This class represents an abstract system with a versionID, name, description and list of subsystem associated with
 * this AbstractSystem.
 *
 * @author Group 03.
 */
@DomainAPI
@Invariant("this.constraintCheck() == True")
public abstract class AbstractSystem extends AbstractSystemSubject {

    /**
     * This constructor is used for all elements of type AbstractSystem, although possibly indirect.
     *
     * @param parent    The parent of this abstract system. (Can be null.)
     * @param version The versionID (of that type) of this element.
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @param milestone The milestone of this element.
     *
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if isValidVersionID(version) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @see AbstractSystem#isValidVersionId(VersionID)
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     */
    public AbstractSystem(AbstractSystem parent, VersionID version, String name, String description, Milestone milestone) throws IllegalArgumentException {
        this.setVersionID(version);
        this.setName(name);
        this.setDescription(description);
        this.setChilds(PList.<Subsystem>empty());
        this.parent = parent;
        this.setMilestone(milestone);
    }

    /**
     * This constructor is used for all elements of type AbstractSystem, although possibly indirect.
     *
     * @param parent    The parent of this abstract system. (Can be null.)
     * @param version The versionID (of that type) of this element.
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if isValidVersionID(version) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @see AbstractSystem#isValidVersionId(VersionID)
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     */
    public AbstractSystem(AbstractSystem parent, VersionID version, String name, String description) throws IllegalArgumentException {
        this(parent, version, name, description, new Milestone(0));
    }

    /**
     * This constructor is used for all elements of type AbstractSystem, although possibly indirect.
     *
     * @param parent    The parent of this abstract system. (Can be null.)
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String)
     */
    public AbstractSystem(AbstractSystem parent, String name, String description) throws IllegalArgumentException {
        this(parent, new VersionID(), name, description);
    }

    private VersionID version;
    private String name = "";
    private String description = "";
    private PList<Subsystem> childs;
    private Milestone milestone;
    private AbstractSystem parent;

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
     * @throws IllegalArgumentException When version is a invalid.
     * @see #isValidVersionId(VersionID)
     */
    public void setVersionID(VersionID version) throws NullPointerException {
        if (!isValidVersionId(version)) {
            throw new IllegalArgumentException("The given versionId is not valid for this abstractSystem");
        }
        this.version = version;
    }

    /**
     * This method check if the given VersionId is a valid versionId for an AbstractSystem
     *
     * @param versionID the versionId to check
     * @return true if the given versionId is a valid for an AbstractSystem
     */
    @DomainAPI
    public static boolean isValidVersionId(VersionID versionID) {
        if (versionID == null) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the Milestone.
     *
     * @return The milestone of the project.
     */
    @DomainAPI
    public Milestone getMilestone() {
        return this.milestone;
    }

    /**
     * Sets the Milestone of the project to the given Milestone.
     * Children of this AbstractSystem will be recursively updated if required
     *
     * @param milestone The milestone of the project.
     * @throws IllegalArgumentException When milestone is a invalid.
     * @see #isValidMilestone(Milestone)
     */
    public void setMilestone(Milestone milestone) throws NullPointerException {
        //Idea:
        //Check BugReport constraint. 
        //Fail = quit
        //Succes = milestone = newMilestone
        //Check Constraint from Parent Perspective
        //Fail = milestone = oldMilestone + quit
        //Succes = update Milestones Below if required (recursively)
        //required = if now higher than the highest subsystem.
                
        if (!isValidMilestone(milestone)) {
            throw new IllegalArgumentException("The given Milestone is not valid for this abstractSystem");
        }

        this.milestone = milestone;

        if (! this.constraintCheck()){
            // update all children
            for (Subsystem subsystem : this.getChilds()){
                subsystem.setMilestone(milestone);
            }
        }
    }

    /**
     * This method check if the given Milestone is a valid Milestone for an AbstractSystem
     * Checks if the constraintCheck is still valid for the parent of this AbstractSystem
     *
     * @param milestone the Milestone to check
     * @return true if the given Milestone is valid for an AbstractSystem.
     *
     * @see AbstractSystem#constraintCheck()
     */
    @DomainAPI
    public boolean isValidMilestone(Milestone milestone) {
        if (milestone == null) {
            return false;
        }
        for (BugReport bugreport : this.getAllBugReports()) {
            if ((!bugreport.isResolved()) && (bugreport.getMilestone().compareTo(milestone) <= 0)) {
                return false;
            }
        }

        // Check if parent milestone <= highest of subsystems if the milestone would have changed:
        Milestone oldM = this.getMilestone();
        this.milestone = milestone;
        boolean check = this.getParent().constraintCheck();
        this.milestone = oldM;
        if (! check){
            return false;
        }
        return true;
    }

    /**
     * This method checks the constraint for this AbstractSystem specified in the assignment:
     *
     * A project’s or subsystem’s achieved milestone should at all times be less
     * than or equal to the highest achieved milestone of all the subsystems it
     * (recursively) contains.
     *
     * @return if this state adheres to the constraint
     */
    public boolean constraintCheck(){
        if (this.getAllSubsystems().isEmpty()) {
            return true;
        }

        // Check if this milestone <= highest of subsystems.
        Milestone high = new Milestone(0, 0, 0);
        for (Subsystem subs : this.getAllSubsystems()) {
            if (subs.getMilestone().compareTo(high) == 1) {
                high = subs.getMilestone();
            }
        }

        if (milestone.compareTo(high) <= 0) {
            return true;
        }
        return false;
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
     * <br> The Milestone of the subsystem will be the lowest possible while adhering to the constraints.
     *
     * @param version The versionID of the new subsystem
     * @param name The name of the new subsystem
     * @param description The description of the new subsystem
     *
     * @return The new subsystems that was added to this AbstractSystem
     * @throws IllegalArgumentException When name or description is invalid.
     */
    public Subsystem makeSubsystemChild(VersionID version, String name, String description) {
        Subsystem newChild = (this.getChilds().isEmpty()) ? new Subsystem(version, name, description, this, getMilestone()) : new Subsystem(version, name, description, this);
        // throw new IllegalArgumentException("Milestone should be bigger then the project/subsystem this belong to, "
        //+ "else inconsistent state.");
        this.addChild(newChild);
        return newChild;
    }

    /**
     * This method adds a subsystem to this AbstractSystem
     *
     * @param name The name of the new subsystem
     * @param description The description of the new subsystem
     * @return The new subsystems that was added to this AbstractSystem
     *
     * @throws IllegalArgumentException When name or description is invalid.
     */
    @Ensures("result.getVersionID.equals(new VersionID())")
    public Subsystem makeSubsystemChild(String name, String description) {
        return makeSubsystemChild(new VersionID(), name, description);
    }

    /**
     * This method adds the given child to the PList of childs. A child is of type Subsystem.
     *
     * @param child The given subsystem to set as child.
     */
    private void addChild(Subsystem child) {
        this.childs = this.getChilds().plus(child);
    }

    /**
     * This methods deletes the given child from the PList of childs.
     *
     * @param child The subsystem to delete.
     * @return Whether there was a change in the data.
     */
    public boolean deleteChild(Subsystem child) {
        PList<Subsystem> oldChilds = this.getChilds();
        this.childs = this.getChilds().minus(child);
        return (oldChilds != this.childs);
    }

    /**
     * This is an abstract getter for the parent of the AbstractSystem.
     *
     * @return The parent of an element with type Subclass or the Project.
     */
    protected AbstractSystem getParent(){
        return this.parent;
    }

    /**
     * This method returns the head of the subsystem tree structure. This is an element from the type Project and can be
     * recognized by his self reference in getParent().
     *
     * @return the Project to which all the subsystems are linked.
     */
    @DomainAPI
    public Project getParentProject() {
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
     * This functions returns all the developers associated with this project
     *
     * @return The list of all developers associated with this project
     */
    @DomainAPI
    public PList<Developer> getAllDev() {
        return this.getParentProject().getAllDev();
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
     * This recursive method returns all the subsystems that are a child of this AbstractSystem
     *
     * @return the list of all Subsystems associated with this AbstractSystem.
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
     * This method checks if the given developer has the requested permission for this AbstractSystem
     *
     * @param dev the developer to check
     * @param perm the requested permission
     * @return true if the developer has the requested permission
     */
    @DomainAPI
    public boolean hasPermission(Developer dev, RolePerm perm) {
        return this.getParentProject().hasPermission(dev, perm);
    }

    /**
     * This method stub represents the different getDetails of the subclasses.
     *
     * @return the details as String
     */
    @DomainAPI
    public abstract String getDetails();

    @Override
    public void notifyTagSubs(BugReport br) {
        this.getParent().notifyTagSubs(br);
        this.updateTagSubs(br);
    }

    @Override
    public void notifyCommentSubs(BugReport br) {
        this.getParent().notifyCommentSubs(br);
        this.updateCommentSubs(br);
    }

    @Override
    public void notifyCreationSubs(BugReport br) {
        this.getParent().notifyCreationSubs(br);
        this.updateCreationSubs(br);
    }

    //TODO ADD COMMENTARY
}
