package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
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
        this.bugReportList = PList.<BugReport>empty();
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
        this.bugReportList = PList.<BugReport>empty();
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
        this.bugReportList = PList.<BugReport>empty();
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
     * @param isPrivate The boolean that says if this bug report should be private or not
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
            PList<BugReport> dependencies, Milestone milestone, double impactFactor, boolean isPrivate, String trigger, String stacktrace,
            String error) throws IllegalArgumentException, PermissionException {
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

    public HealthIndicator getIndicator() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * The method returns the memento for this AbstractSystem.
     *
     * @return The memento of this system.
     */
    @Override
    public SubsystemMemento getMemento() {
        return new SubsystemMemento(getVersionID(), getName(), getDescription(), this.getSubsystems(), this.getParent(), this.getMilestone(), this.bugReportList, this.isTerminated);
    }
    
    @Override
    public void setMemento(AbstractSystemMemento mem) {
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
     * @param name1 The name for this Subsystem.
     * @param desc1 The description for this Subsystem.
     * @param name2 The name for the extra Subsystem.
     * @param desc2 The description for the extra Subsystem.
     * @param subsystems1 The subsystems that will be part of this Subsystem if they are now also direct Subsystems.
     * @param bugReports1 The bugReports that will be part if this Subsystem if they are now also direct BugReports.
     * @return The extra created Subsystem.
     *
     * @throws IllegalArgumentException When subsystems1 == null
     * @throws IllegalArgumentException When bugReports1 == null
     * @throws IllegalArgumentException When any of the arguments is invalid. (such as name and description).
     */
    public Subsystem split(String name1, String desc1, String name2, String desc2, PList<Subsystem> subsystems1, PList<BugReport> bugReports1) throws IllegalArgumentException {
        if (subsystems1 == null || bugReports1 == null) {
            throw new IllegalArgumentException("Can't split a subsystem when subsystems1 or bugReports1 == null.");
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

        return resultSubsystem2;
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
