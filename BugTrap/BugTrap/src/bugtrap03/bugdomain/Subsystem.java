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
    protected Subsystem(VersionID version, String name, String description, AbstractSystem parent, Milestone milestone) {
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
    protected Subsystem(String name, String description, AbstractSystem parent)
            throws IllegalArgumentException {
        super(parent, name, description);
        this.bugReportList = PList.<BugReport> empty();
    }

    private PList<BugReport> bugReportList;

//    /**
//     * Sets the parent of the AbstractSystem to the given parent, if valid. Only elements of subclass subsystems have a
//     * parent different from null.
//     *
//     * @param parent The given parent of the AbstractSystem
//     * @throws IllegalArgumentException if the given parent isn't valid for this subsystem
//     * @see Subsystem#isValidParent(AbstractSystem)
//     */
//    private void setParent(AbstractSystem parent) throws IllegalArgumentException {
//        if (!isValidParent(parent)) {
//            throw new IllegalArgumentException("Illegal parent for this subsystem");
//        }
//        this.parent = parent;
//    }
//
//    /**
//     * This function checks or the given parent isn't one of subsystem's own child
//     *
//     * @param parent The given parent to be checked.
//     * @return true is the given parent isn't the subsystem's own child
//     */
//    @DomainAPI
//    protected boolean isValidParent(AbstractSystem parent) {
//        if (parent == null) {
//            return false;
//        }
//        Project parentProject = parent.getParentProject();
//        AbstractSystem currentSystem = parent;
//        while (currentSystem != parentProject) {
//            if (currentSystem == this) {
//                return false;
//            }
//            currentSystem = currentSystem.getParent();
//        }
//        return true;
//    }

//    /**
//     * This is a getter for the set parent of the Subsystem;
//     *
//     * @return the parent of instance AbstractSystem.
//     */
//    protected AbstractSystem getParent() {
//        return this.parent;
//    }

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
     * This method creates and adds a bug report to the list of associated bugReports of this subsystem
     *
     * @param creator The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param isPrivate The boolean that says if this bug report should be private or not
     * @param trigger A trigger used to trigger the bug
     * @param stacktrace The stacktrace got when the bug was triggered
     * @param error The error got when the bug was triggered
     *
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException if the given creator doesn't have the needed permission to create a bug report
     *
     * @return The create bug report
     *
     * <br><dt><b>Postconditions:</b><dd> if creationDate == null: result.getDate() == current date at the moment of
     * initialization
     * <br><dt><b>Postconditions:</b><dd> result.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    public BugReport addBugReport(User creator, String title, String description, GregorianCalendar creationDate,
            PList<BugReport> dependencies, Milestone milestone, boolean isPrivate,
            String trigger, String stacktrace, String error)
            throws IllegalArgumentException, PermissionException {
        BugReport bugReport;
        if (creationDate == null) {
            bugReport = new BugReport(creator, title, description, new GregorianCalendar(), dependencies, this,
                    milestone, isPrivate, trigger, stacktrace, error);
        } else {
            bugReport = new BugReport(creator, title, description, creationDate, dependencies, this,
                    milestone, isPrivate, trigger, stacktrace, error);
        }
        this.bugReportList = this.getBugReportList().plus(bugReport);
        this.notifyCreationSubs(bugReport);
        return bugReport;
    }

    /**
     * Remove the given bugReport from the list of bugReports.
     * <br> <b>Only use with Caution</b>: No other changes will be made. (e.g no dependencies or milestones will be
     * changed.)
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
        Subsystem clone = parent.makeSubsystemChild(this.getVersionID().clone(), this.getName(), this.getDescription());
        for (Subsystem child : this.getChilds()) {
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
        String details = "\n\n\tSubsystem name:\t\t \t";
        details += this.getName();
        details += "\n\tSubsystem version:\t\t";
        details += this.getVersionID().toString();
        details += "\nAchieved milestone:\t\t";
        details += this.getMilestone().toString();
        details += "\n\tSubsystem description: \t";
        details += this.getDescription();
        details += "\n\tSubsystem parent: \t\t";
        details += this.getParent().getName();
        details += "\n\tSubsystem from project: ";
        details += this.getParentProject().getName();
        return details;
    }

    /**
     * This method returns the subject name and type as a string.
     *
     * @return the name of this subject
     */
    @Override
    public String getSubjectName() {
        return ("Subsystem " + this.getName());
    }


    /**
     * This function checks the validity of the given name, in combination with
     * its parent.
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
    // for (Subsystem child : parent.getChilds()) {
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
