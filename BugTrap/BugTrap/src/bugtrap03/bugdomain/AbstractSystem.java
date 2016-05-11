package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.SubjectMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
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
     * @param parent      The parent of this abstract system. (Can be null.)
     * @param version     The versionID (of that type) of this element.
     * @param name        The string name for this element.
     * @param description The string description of this element.
     * @param milestone   The milestone of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if isValidVersionID(version) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @see AbstractSystem#isValidVersionId(VersionID)
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     */
    public AbstractSystem(AbstractSystem parent, VersionID version, String name, String description,
                          Milestone milestone) throws IllegalArgumentException {
        this.setVersionID(version);
        this.setName(name);
        this.setDescription(description);
        this.setChilds(PList.<Subsystem>empty());
        this.setParent(parent);
        this.setMilestone(milestone);
        this.isTerminated = false;
    }

    /**
     * This constructor is used for all elements of type AbstractSystem, although possibly indirect.
     *
     * @param parent      The parent of this abstract system. (Can be null.)
     * @param version     The versionID (of that type) of this element.
     * @param name        The string name for this element.
     * @param description The string description of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if isValidVersionID(version) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @see AbstractSystem#isValidVersionId(VersionID)
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     */
    public AbstractSystem(AbstractSystem parent, VersionID version, String name, String description)
            throws IllegalArgumentException {
        this(parent, version, name, description, new Milestone(0));
    }

    /**
     * This constructor is used for all elements of type AbstractSystem, although possibly indirect.
     *
     * @param parent      The parent of this abstract system. (Can be null.)
     * @param name        The string name for this element.
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
    protected boolean isTerminated;

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
        if (this.version != null) {
            notifyVersionIDSubs(this);
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
     * Sets the Milestone of the project to the given Milestone. Children of this AbstractSystem will be recursively
     * updated if required
     *
     * @param milestone The milestone of the project.
     * @throws IllegalArgumentException When milestone is a invalid.
     * @see #isValidMilestone(Milestone)
     */
    protected void setMilestone(Milestone milestone) {
        // Idea:
        // Check BugReport constraint.
        // Fail = quit
        // Succes = milestone = newMilestone
        // Check Constraint from Parent Perspective
        // Fail = milestone = oldMilestone + quit
        // Succes = update Milestones Below if required (recursively)
        // required = if now higher than the highest subsystem.

        if (!isValidMilestone(milestone)) {
            throw new IllegalArgumentException("The given Milestone is not valid for this abstractSystem");
        }

        this.milestone = milestone;

        if (!this.constraintCheck()) {
            // update all children
            for (Subsystem subsystem : this.getSubsystems()) {
                subsystem.setMilestone(milestone);
            }
        }
    }

    /**
     * This method sets the Milestone of the project to the given Milestone. Children of this AbstractSystem will be
     * recursively updated if required
     *
     * @param user      The user that wants to change the milestone
     * @param milestone The new milestone
     * @throws PermissionException      If the given user doesn't have the needed permission
     * @throws IllegalArgumentException When milestone is a invalid.
     * @see #isValidMilestone(Milestone)
     */
    public void setMilestone(User user, Milestone milestone) throws PermissionException {
        if (!user.hasPermission(UserPerm.SET_MILESTONE)) {
            throw new PermissionException("The given user doesn't have the needed permission to change the milestone");
        }
        notifyMilestoneSubs(this);
        this.setMilestone(milestone);
    }

    /**
     * This method check if the given Milestone is a valid Milestone for an AbstractSystem Checks if the constraintCheck
     * is still valid for the parent of this AbstractSystem
     *
     * @param milestone the Milestone to check
     * @return true if the given Milestone is valid for an AbstractSystem.
     * @see AbstractSystem#constraintCheck()
     */
    @DomainAPI
    public boolean isValidMilestone(Milestone milestone) {
        if (milestone == null) {
            return false;
        }
        for (BugReport bugreport : this.getAllBugReports()) {
            if ((!bugreport.isResolved()) && (bugreport.getMilestone() != null)
                    && (bugreport.getMilestone().compareTo(milestone) <= 0)) {
                return false;
            }
        }

        // Check if parent milestone <= highest of subsystems if the milestone would have changed:
        Milestone oldM = this.getMilestone();
        this.milestone = milestone;
        boolean check = this.getParent().constraintCheck();
        this.milestone = oldM;
        // if (! check){
        // return false;
        // }
        // return true;
        return check; // if (check == false) return false, else true
    }

    /**
     * This method checks the constraint for this AbstractSystem specified in the assignment:
     * <p>
     * A project’s or subsystem’s achieved milestone should at all times be less than or equal to the highest achieved
     * milestone of all the subsystems it (recursively) contains.
     *
     * @return if this state adheres to the constraint
     */
    public boolean constraintCheck() {
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
     * This method sets the parent of this Abstract System to the given parent.
     * <br>
     * Should be used with care as to maintain the bidirectional relation.
     *
     * @param parent The new parent of this Abstract System
     * @throws IllegalArgumentException If the given parent was invalid
     * @see AbstractSystem#isValidParent(AbstractSystem)
     */
    protected void setParent(AbstractSystem parent) throws IllegalArgumentException {
        if (!this.isValidParent(parent)) {
            throw new IllegalArgumentException("The given parent was invalid for this Abstract System");
        }
        this.parent = parent;
    }

    /**
     * This method check if a given Abstract System is valid as a parent for this Abstract System.
     *
     * @param parent The parent to check
     * @return True if the given Abstract System is valid as a parent
     */
    @DomainAPI
    public boolean isValidParent(AbstractSystem parent) {
        if (parent == null) {
            return false;
        }
        if (parent.isTerminated()) {
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
     * A getter for the PList of children.
     *
     * @return an PList of children.
     */
    @DomainAPI
    public PList<Subsystem> getSubsystems() {
        return this.childs;
    }

    /**
     * This method adds a subsystem to this AbstractSystem
     * <br>
     * The Milestone of the subsystem will be the lowest possible while adhering to the constraints.
     *
     * @param version     The versionID of the new subsystem
     * @param name        The name of the new subsystem
     * @param description The description of the new subsystem
     * @return The new subsystems that was added to this AbstractSystem
     * @throws IllegalArgumentException When name or description is invalid.
     */
    public Subsystem addSubsystem(VersionID version, String name, String description) {
        Subsystem newChild = (this.getSubsystems().isEmpty())
                ? new Subsystem(version, name, description, this, getMilestone())
                : new Subsystem(version, name, description, this);
        // throw new IllegalArgumentException("Milestone should be bigger then the project/subsystem this belong to, "
        // + "else inconsistent state.");
        this.addChild(newChild);
        return newChild;
    }

    /**
     * Add the given subsystem as a child.
     * <br>
     * Should be used with care as to maintain bidirectional relation.
     *
     * @param subsystem The subsystem to add as a child of this.
     * @throws IllegalArgumentException When subsystem is already a subsystem in this project.
     * @throws IllegalArgumentException When the parentProject does not match, <b> implies you have to set the parent
     *                                  first.</b>
     * @throws IllegalArgumentException When subsystem == null
     */
    protected void addSubsystem(Subsystem subsystem) {
        if (subsystem == null) {
            throw new IllegalArgumentException("AbstractSystem cannot add a null Subsystem.");
        }
        if (this.getParentProject() != subsystem.getParentProject()) {
            throw new IllegalArgumentException(
                    "AbstractSystem cannot add a Subsystem that belongs to a different project.");
        }
        if (this.getParentProject().getAllSubsystems().contains(subsystem)) {
            throw new IllegalArgumentException("AbstractSystem cannot add a Subsystem which is already a child of it.");
        }
        this.addChild(subsystem);
    }

    /**
     * This method adds a subsystem to this AbstractSystem
     *
     * @param name        The name of the new subsystem
     * @param description The description of the new subsystem
     * @return The new subsystems that was added to this AbstractSystem
     * @throws IllegalArgumentException When name or description is invalid.
     */
    @Ensures("result.getVersionID.equals(new VersionID())")
    public Subsystem addSubsystem(String name, String description) {
        return addSubsystem(new VersionID(), name, description);
    }

    /**
     * This method adds the given child to the PList of childs. A child is of type Subsystem.
     *
     * @param child The given subsystem to set as child.
     */
    private void addChild(Subsystem child) {
        this.childs = this.getSubsystems().plus(child);
    }

    /**
     * This methods deletes the given child from the PList of children.
     * <br>
     * Should be used with care as to maintain bidirectional relation.
     *
     * @param child The subsystem to delete.
     * @return Whether there was a change in the data.
     */
    public boolean deleteChild(Subsystem child) {
        PList<Subsystem> oldChilds = this.getSubsystems();
        this.childs = this.getSubsystems().minus(child);
        return (oldChilds != this.childs);
    }

    /**
     * This is an abstract getter for the parent of the AbstractSystem.
     *
     * @return The parent of an element with type Subclass or the Project.
     */
    @DomainAPI
    public AbstractSystem getParent() {
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
        for (Subsystem subsystem : this.getSubsystems()) {
            list.addAll(subsystem.getAllBugReports());
        }
        return PList.<BugReport>empty().plusAll(list);
    }

    /**
     * This method returns the combined impact of all the bug reports associated with this AbstractSystem
     *
     * @return The combined impact of all bug reports associated with this AbstractSystem
     */
    @DomainAPI
    public abstract double getBugImpact();

    /**
     * This recursive method returns all the subsystems that are a child of this AbstractSystem
     *
     * @return the list of all Subsystems associated with this AbstractSystem.
     */
    @DomainAPI
    public PList<Subsystem> getAllSubsystems() {
        ArrayList<Subsystem> list = new ArrayList<>();
        for (Subsystem subsystem : this.getSubsystems()) {
            list.add(subsystem);
            list.addAll(subsystem.getAllSubsystems());
        }
        return PList.<Subsystem>empty().plusAll(list);
    }

    /**
     * This method checks if the given developer has the requested permission for this AbstractSystem
     *
     * @param dev  the developer to check
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

    /**
     * his method notifies the project it belongs toand all it's parents, to update it's mailboxes for a tag subscription.
     *
     * @param br The bugreport that has undergone a tag change.
     */
    @Override
    public void notifyTagSubs(BugReport br) {
        this.updateTagSubs(br);
        this.getParent().notifyTagSubs(br);
    }

    /**
     * his method notifies the project it belongs toand all it's parents, to update it's mailboxes for a comment subscription.
     *
     * @param br The bugreport that has been commented upon.
     */
    @Override
    public void notifyCommentSubs(BugReport br) {
        this.updateCommentSubs(br);
        this.getParent().notifyCommentSubs(br);
    }

    /**
     * his method notifies the project it belongs toand all it's parents, to update it's mailboxes for a creation subscription.
     *
     * @param br The bugreport that has been created.
     */
    @Override
    public void notifyCreationSubs(BugReport br) {
        this.updateCreationSubs(br);
        this.getParent().notifyCreationSubs(br);
    }

    /**
     * This method notifies the project it belongs to and all it's parents, to update it's mailboxes for a milestone subscription.
     *
     * @param as The abstract system of which the milestone has been updated.
     */
    @Override
    public void notifyMilestoneSubs(AbstractSystem as) {
        this.updateMilestoneSubs(as);
        this.getParent().notifyMilestoneSubs(as);
    }

    /**
     * This method notifies the project it belongs to and all it's parents, to update it's mailboxes for a versionID subscription.
     *
     * @param as The abstract system of which the milestone has been updated.
     */
    @Override
    public void notifyVersionIDSubs(AbstractSystem as) {
        this.updateVersionIDSubs(as);
        this.getParent().notifyVersionIDSubs(as);
    }

    /**
     * This method sets the isTerminated boolean of this object
     *
     * @param terminated the new value
     */
    public void setTerminated(boolean terminated) {
        this.isTerminated = terminated;
    }

    /**
     * This method check whether or not the current AbstractSystem is terminated
     *
     * @return true if the object is terminated
     */
    @DomainAPI
    @Override
    public boolean isTerminated() {
        if (this.isTerminated) {
            return true;
        } else {
            return this.getParent().isTerminated();
        }
    }

    /**
     * This method returns the {@link HealthIndicator} of the AbstractSystem
     *
     * @param ha The {@link HealthAlgorithm} to use to calculate the indicator
     * @return The health indicator of the AbstractSystem
     */
    public abstract HealthIndicator getIndicator(HealthAlgorithm ha);

    /**
     * The method returns the memento for this AbstractSystem.
     *
     * @return The memento of this system.
     */
    @Override
    public AbstractSystemMemento getMemento() {
        return new AbstractSystemMemento(this.getTagSubs(), this.getCommentSubs(), this.getCreationSubs(),
                this.getMilestoneSubs(), this.getVersionIDSubs(), this.version, this.name, this.description,
                this.childs, this.parent, this.milestone, this.isTerminated);
    }

    /**
     * Set the memento of this AbstractSystem.
     *
     * @param mem The Memento to use to set.
     * @throws IllegalArgumentException When mem == null
     * @throws IllegalArgumentException When any of the arguments stored in mem is invalid for the current state. (e.g
     *                                  milestones due to constraints)
     */
    @Override
    public void setMemento(SubjectMemento mem) throws IllegalArgumentException {
        super.setMemento(mem);

        if (mem instanceof AbstractSystemMemento) {
            AbstractSystemMemento aMem = (AbstractSystemMemento) mem;

            this.version = aMem.getVersionID();
            this.name = aMem.getName();
            this.description = aMem.getDescription();
            this.isTerminated = aMem.getIsTerminated();
            this.parent = aMem.getParent();
            this.childs = aMem.getChildren();
            aMem.restoreChildren();
            this.milestone = aMem.getMilestone();
        }
    }

}
