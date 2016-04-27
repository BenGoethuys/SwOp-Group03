package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notification.SubjectMemento;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.User;
import com.google.java.contract.Ensures;
import purecollections.PList;

import java.util.GregorianCalendar;

/**
 * This class is a subclass of abstract system (versionID, name, description and Childs list). This type also contains a
 * bug report PList, containing the bug reports linked to this subsystem.
 *
 * @author Group 03
 */
@DomainAPI
public class Subsystem extends AbstractSystem {

    /**
     * This constructor makes an element of the class subsystem, using it's superclass, AbstractSystem, constructor.
     *
     * @param version The versionID (of that type) of this element.
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @param parent The parent (a Project or Subsystem) of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if the version id is invalid.
     * @throws IllegalArgumentException if the parent is invalid for this subsystem
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String)
     */
    protected Subsystem(VersionID version, String name, String description, AbstractSystem parent)
            throws IllegalArgumentException {
	super(parent, version, name, description);
	this.bugReportList = PList.<BugReport> empty();
    }

    /**
     * This constructor makes an element of the class subsystem, using it's superclass, AbstractSystem, constructor.
     *
     * @param version The versionID (of that type) of this element.
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @param parent The parent (a Project or Subsystem) of this element.
     * @param milestone The milestone of this element.
     *
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if the version id is invalid.
     * @throws IllegalArgumentException if the parent is invalid for this subsystem
     * @throws IllegalArgumentException If the milestone is invalid
     *
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String, Milestone)
     */
    protected Subsystem(VersionID version, String name, String description, AbstractSystem parent,
            Milestone milestone) {
	super(parent, version, name, description, milestone);
	this.bugReportList = PList.<BugReport> empty();
    }

    /**
     * This constructor makes an element of the class subsystem, using it's superclass, AbstractSystem, constructor.
     *
     * @param name The string name for this element.
     * @param description The string description of this element.
     * @param parent The parent (a Project or Subsystem) of this element.
     * @throws IllegalArgumentException if one of the String arguments is invalid.
     * @throws IllegalArgumentException if the version id is invalid.
     * @throws IllegalArgumentException if the parent is invalid for this subsystem
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String)
     */
    protected Subsystem(String name, String description, AbstractSystem parent) throws IllegalArgumentException {
	super(parent, name, description);
	this.bugReportList = PList.<BugReport> empty();
    }

    private PList<BugReport> bugReportList;

    // /**
    // * Sets the parent of the AbstractSystem to the given parent, if valid.
    // Only elements of subclass subsystems have a
    // * parent different from null.
    // *
    // * @param parent The given parent of the AbstractSystem
    // * @throws IllegalArgumentException if the given parent isn't valid for
    // this subsystem
    // * @see Subsystem#isValidParent(AbstractSystem)
    // */
    // private void setParent(AbstractSystem parent) throws
    // IllegalArgumentException {
    // if (!isValidParent(parent)) {
    // throw new IllegalArgumentException("Illegal parent for this subsystem");
    // }
    // this.parent = parent;
    // }
    //
    // /**
    // * This function checks or the given parent isn't one of subsystem's own
    // child
    // *
    // * @param parent The given parent to be checked.
    // * @return true is the given parent isn't the subsystem's own child
    // */
    // @DomainAPI
    // protected boolean isValidParent(AbstractSystem parent) {
    // if (parent == null) {
    // return false;
    // }
    // Project parentProject = parent.getParentProject();
    // AbstractSystem currentSystem = parent;
    // while (currentSystem != parentProject) {
    // if (currentSystem == this) {
    // return false;
    // }
    // currentSystem = currentSystem.getParent();
    // }
    // return true;
    // }
    // /**
    // * This is a getter for the set parent of the Subsystem;
    // *
    // * @return the parent of instance AbstractSystem.
    // */
    // protected AbstractSystem getParent() {
    // return this.parent;
    // }
    /**
     * This method returns the list of bug reports of this subsystem
     *
     * @return the list of bug reports of this subsystem
     */
    @DomainAPI
    public PList<BugReport> getBugReportList() {
	return this.bugReportList;
    }

    /**
     * This method returns all the bug reports associated with this Subsytem
     *
     * @return the list of all bugReports
     */
    @Override
    @DomainAPI
    public PList<BugReport> getAllBugReports() {
	PList<BugReport> list = super.getAllBugReports();
	if (this.getBugReportList() != null) {
	    // will be null during initialisation !
	    list = list.plusAll(this.getBugReportList());
	}
	return list;
    }

    /**
     * This method returns the combined impact of all the bug reports associated with this Subsystem
     *
     * @return The combined impact of all bug reports associated with this Subsystem
     */
    @Override
    @DomainAPI
    public double getBugImpact() {
	double impact = 0.0;

	PList<BugReport> bugReports = this.getBugReportList();
	for (BugReport bugReport : bugReports) {
	    impact += bugReport.getBugImpact();
	}

	PList<Subsystem> subsystems = this.getSubsystems();
	for (Subsystem subsystem : subsystems) {
	    impact += subsystem.getBugImpact();
	}

	return impact;
    }

    /**
     * This method creates and adds a bug report to the list of associated bugReports of this subsystem
     *
     * @param creator The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param impactFactor The impact factor of the new bug rport
     * @param isPrivate The boolean that says if this bug report should be
     *            private or not
     * @param trigger A trigger used to trigger the bug
     * @param stacktrace The stacktrace got when the bug was triggered
     * @param error The error got when the bug was triggered
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws IllegalArgumentException if the given impact factor is invalid
     *
     * @throws PermissionException if the given creator doesn't have the needed permission to create a bug report
     *
     * @return The create bug report
     *
     * <br>
     * <dt><b>Postconditions:</b>
     * <dd>if creationDate == null: result.getDate() == current date at the moment of initialization <br>
     * <dt><b>Postconditions:</b>
     * <dd>result.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     * @see BugReport#isValidImpactFactor(double)
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport addBugReport(User creator, String title, String description, GregorianCalendar creationDate,
            PList<BugReport> dependencies, Milestone milestone, double impactFactor, boolean isPrivate, String trigger,
            String stacktrace, String error) throws IllegalArgumentException, PermissionException {
	BugReport bugReport;
	if (creationDate == null) {
	    bugReport = new BugReport(creator, title, description, new GregorianCalendar(), dependencies, this,
	            milestone, impactFactor, isPrivate, trigger, stacktrace, error);
	} else {
	    bugReport = new BugReport(creator, title, description, creationDate, dependencies, this, milestone,
	            impactFactor, isPrivate, trigger, stacktrace, error);
	}
	this.bugReportList = this.getBugReportList().plus(bugReport);
	this.notifyCreationSubs(bugReport);
	return bugReport;
    }

    /**
     * Remove the given bugReport from the list of bugReports. <br>
     * <b>Only use with Caution</b>: No other changes will be made. (e.g no dependencies or milestones will be changed.)
     *
     * @param bugReport The bugReport to delete.
     */
    public void deleteBugReport(BugReport bugReport) {
	this.bugReportList = this.bugReportList.minus(bugReport);
    }

    /**
     * Returns a copy of the current subsystem, using the same versionID, name, description and parent, but with a
     * removal of the bug reports addressed to this subsystem.
     *
     * @return the clone of the subsystem
     * @throws IllegalArgumentException if the VersionID is invalid
     * @throws IllegalArgumentException if one of the string arguments is invalid
     * @throws IllegalArgumentException if the parent is invalid
     * @see Subsystem#Subsystem(VersionID, String, String, AbstractSystem)
     */
    public Subsystem cloneSubsystem(AbstractSystem parent) throws IllegalArgumentException {
	Subsystem clone = parent.addSubsystem(this.getVersionID().clone(), this.getName(), this.getDescription());
	for (Subsystem child : this.getSubsystems()) {
	    child.cloneSubsystem(clone);
	}
	return clone;
    }

    /**
     * this method implements the abstract method from abstract system to return a string of details belonging to this
     * subsystem.
     *
     * @return a string of details
     */
    @DomainAPI
    @Override
    public String getDetails() {
	String details = "\n\n\tSubsystem name:\t\t\t";
	details += this.getName();
	details += "\n\tSubsystem version:\t\t";
	details += this.getVersionID().toString();
	details += "\n\tAchieved milestone:\t\t";
	details += this.getMilestone().toString();
	details += "\n\tSubsystem description: \t\t";
	details += this.getDescription();
	details += "\n\tSubsystem parent: \t\t";
	details += this.getParent().getName();
	details += "\n\tSubsystem from project: \t";
	details += this.getParentProject().getName();
	details += "\n\tHealth indicator Algorithm 1: \t" + this.getIndicator(new HealthAlgorithm1());
	details += "\n\tHealth indicator Algorithm 2: \t" + this.getIndicator(new HealthAlgorithm2());
	details += "\n\tHealth indicator Algorithm 3: \t" + this.getIndicator(new HealthAlgorithm3());
	return details;
    }

    /**
     * This method returns the subject name and type as a string.
     *
     * @return the name of this subject
     */
    @Override
    @DomainAPI
    public String getSubjectName() {
	return ("Subsystem " + this.getName());
    }

    @Override
    public HealthIndicator getIndicator(HealthAlgorithm ha) {
	return ha.getIndicator(this);
    }

    /**
     * The method returns the memento for this AbstractSystem.
     *
     * @return The memento of this system.
     */
    @Override
    public SubsystemMemento getMemento() {
        return new SubsystemMemento(getTagSubs(), getCommentSubs(), getCreationSubs(), getVersionID(), getName(), getDescription(), this.getSubsystems(), this.getParent(), this.getMilestone(), this.bugReportList, this.isTerminated);
    }
    
    @Override
    public void setMemento(SubjectMemento mem) {
        super.setMemento(mem);
        
        if(mem instanceof SubsystemMemento) {
            SubsystemMemento sMem = (SubsystemMemento) mem;
    
            this.bugReportList = sMem.getBugReportList();
            sMem.restoreBugReports();
        }
    }

    /**
     * This will split this Subsystem into this and another Subsystem.
     * <br>This Subsystem will keep all its direct Subsystems and BugReports that are also in subsystems1 and
     * bugReports1 respectively. The rest of the direct Subsystems and BugReports will go to the extra Subsystem.
     * 
     * <p>
     * <b> Warning: A invalid argument may cause a partially executed split. e.g Invalid argument for the 2nd subsystem
     * may still cause the name and description for subsystem 1 to be set before the error. </b>
     *
     * @param name1 The name for this Subsystem.
     * @param desc1 The description for this Subsystem.
     * @param name2 The name for the extra Subsystem.
     * @param desc2 The description for the extra Subsystem.
     * @param subsystems1 The subsystems that will be part of this Subsystem if they are now also direct Subsystems.
     * @param bugReports1 The bugReports that will be part if this Subsystem if they are now also direct BugReports.
     * @param user The user who wants to split the subsystem.
     * @return The extra created Subsystem.
     *
     * @throws PermissionException When user does not have sufficient permissions.
     * @throws IllegalArgumentException When subsystems1 == null
     * @throws IllegalArgumentException When bugReports1 == null
     * @throws IllegalArgumentException When any of the arguments is invalid. (such as name and description).
     */
    public Subsystem split(String name1, String desc1, String name2, String desc2, PList<Subsystem> subsystems1, PList<BugReport> bugReports1, User user) throws PermissionException, IllegalArgumentException {
        if (subsystems1 == null || bugReports1 == null || user == null) {
            throw new IllegalArgumentException("Can't split a subsystem when subsystems1, bugReports1 or user == null.");
        }

        //Check perms
        if (!user.hasPermission(UserPerm.SPLIT_SUBSYS)) {
            throw new PermissionException("You do not have sufficient permissions to split a subsystem.");
        }

        //TODO: Kwinten add the notification list to the other subsystem as well.
        //Set current subsystem
        this.setName(name1);
        this.setDescription(desc1);

        //create another subsystems        
        Subsystem resultSubsystem2 = getParent().addSubsystem(getVersionID(), name2, desc2);

        //Set milestones
        resultSubsystem2.setMilestone(this.getMilestone());

        //set childs of sub2 by removing every direct subsystem that is not in subsystems1.
        for (Subsystem subsystem : this.getSubsystems()) {
            if (!subsystems1.contains(subsystem)) {
                this.deleteChild(subsystem);
                subsystem.setParent(resultSubsystem2);
                resultSubsystem2.addSubsystem(subsystem);
            }
        }

        //Set BugReports of sub2 by removing every direct bugReport that is not in bugReports1.
        for (BugReport bugReport : this.getBugReportList()) {
            if (!bugReports1.contains(bugReport)) {
                //If notification is required use a method and add notify.
                this.bugReportList = this.bugReportList.minus(bugReport);
                bugReport.setSubsystem(resultSubsystem2);
                resultSubsystem2.bugReportList = resultSubsystem2.bugReportList.plus(bugReport);
            }
        }
        
        //Set subscribers
        resultSubsystem2.addTagSub(this.getTagSubs());
        resultSubsystem2.addCommentSub(this.getCommentSubs());
        resultSubsystem2.addCreationSub(this.getCreationSubs());
        
        return resultSubsystem2;
    }

    /**
     * WARNING: This method doesn't guarantee a consistent state of the subsystem if it fails!
     *
     * This method merges a given subsystem with this subsystem and renames this subsystem and redefines the description
     *
     * @param user          The user that wants to merge the subsystems
     * @param subsystem     The subsystem to merge with
     * @param name          The new name of this subsystem
     * @param description   The new description of this subsystem
     *
     * @return  The merged subsystem
     *
     * @throws PermissionException      If the given user doesn't have the needed permission to merge subsystems
     * @throws IllegalArgumentException If one of the given arguments is invalid
     *
     * @see AbstractSystem#isValidName(String)
     * @see AbstractSystem#isValidDescription(String)
     * @see #isValidMergeSubsystem(Subsystem)
     */
    public Subsystem mergeWithSubsystem(User user, Subsystem subsystem, String name, String description)
            throws PermissionException, IllegalArgumentException {

        if (user == null){
            throw new IllegalArgumentException("The given arguments cannot be null");
        }

        if (! user.hasPermission(UserPerm.MERGE_SUBSYS)){
            throw new PermissionException("The given user doesn't have the permission to merge subsystems");
        }

        if (! this.isValidMergeSubsystem(subsystem)){
            throw new IllegalArgumentException("Cannot merge with the given subsystem");
        }

        // set params
        this.setName(name);
        this.setDescription(description);

        // add childs to this subsystem
        for (Subsystem temp : subsystem.getSubsystems()){
            temp.setParent(this);
            this.addSubsystem(temp);
        }

        // find lowest milestone and replace if necessary
        if (subsystem.getMilestone().compareTo(this.getMilestone()) < 0) {
            this.setMilestone(subsystem.getMilestone());
        }

        //TODO: possible merge subscribers?

        // terminate subsystem
        subsystem.setTerminated(true);

        return this;
    }

    /**
     * This methdo check whether or not the given subsystem can be merged with this subsystem
     *
     * @param subsystem The subsystem to merge
     *
     * @return  The given subsystem must be a sibling OR the parent of the given subsystem must be this
     */
    @DomainAPI
    public boolean isValidMergeSubsystem(Subsystem subsystem){
        if (subsystem == null){
            return false;
        }
        if (subsystem == this){
            return false;
        }
        if (subsystem.getParent() == this){
            return true;
        }
        if (subsystem.getParent() == this.getParent()){
            return true;
        }
        return false;
    }

    /**
     * This function checks the validity of the given name, in combination with its parent.
     *
     * @param name The string argument to be used as name.
     * @param parent The parent of the element to be named.
     * @return true if no of the other child of the projectParent has the same name.
     */
    // protected boolean isValidName(String name, AbstractSystem parent) {
    // if (!this.isValidName(name)) {
    // return false;
    // }
    // Project parentProject = parent.getParentProject();
    // if (name == parentProject.getName()) {
    // return false;
    // }
    // return childNamesNotEqual(name, parentProject);
    // }
    /**
     * This function checks of none of the other subsystems (childs) of the AbstracSystem (parent) have the same name.
     * This is done recursively. To be correctly used, the function should be called the first time with the Project of
     * the three.
     *
     * @param name The name to check.
     * @param parent The given parent.
     * @return True if the name is unique.
     */
    // private boolean childNamesNotEqual(String name, AbstractSystem parent) {
    // for (Subsystem child : parent.getSubsystems()) {
    // if (child.getName() == name) {
    // return false;
    // }
    // if (!childNamesNotEqual(name, child)) {
    // return false;
    // }
    // }
    // return true;
    // }
}
