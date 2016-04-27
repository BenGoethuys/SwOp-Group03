package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;
import com.google.java.contract.Requires;
import purecollections.PList;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This class extends AbstractSystem (versionID, name and description) and extends it with dates.
 *
 * @author Kwinten Buytaert & Ben Goethuys
 */
@DomainAPI
public class Project extends AbstractSystem {

    /**
     * Creates a project with a given versionID, name, description, creationDate, lead, startDate, budgetEstimate.
     *
     * @param version The versionID of this project.
     * @param name The name of this project.
     * @param description The description of this project.
     * @param creationDate The creation date of this project.
     * @param lead The lead developer of this project
     * @param startDate The start date of this project. This will be cloned.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String)
     * @see Project#isValidCreationDate(GregorianCalendar)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(VersionID version, String name, String description, GregorianCalendar creationDate, Developer lead,
            GregorianCalendar startDate, long budgetEstimate) throws IllegalArgumentException {
        super(null, version, name, description);
        this.setCreationDate(creationDate);
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(startDate);
        this.setBudgetEstimate(budgetEstimate);

        this.isTerminated = false;
    }

    /**
     * Creates a project with a given versionID, name, description, lead, startDate, budgetEstimate.
     *
     * @param version The versionID of this project.
     * @param name The name of this project.
     * @param description The description of this project.
     * @param lead The lead developer of this project
     * @param startDate The start date of this project. This will be cloned.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(VersionID version, String name, String description, Developer lead, GregorianCalendar startDate,
            long budgetEstimate) throws IllegalArgumentException {
        this(version, name, description, new GregorianCalendar(), lead, startDate, budgetEstimate);
    }

    /**
     * Creates a project with a given versionID, name, description, lead, startDate, budgetEstimate.
     *
     * @param name The name of this project.
     * @param description The description of this project.
     * @param lead The lead developer of this project
     * @param startDate The start date of this project. This will be cloned.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(String name, String description, Developer lead, GregorianCalendar startDate, long budgetEstimate)
            throws IllegalArgumentException {
        super(null, name, description);
        this.setCreationDate(new GregorianCalendar());
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(startDate);
        this.setBudgetEstimate(budgetEstimate);

        this.isTerminated = false;
    }

    /**
     * Creates a project with a given versionID, name, description, lead, startDate, budgetEstimate.
     *
     * @param name The name of this project.
     * @param description The description of this project.
     * @param lead The lead developer of this project
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(String name, String description, Developer lead, long budgetEstimate)
            throws IllegalArgumentException {
        super(null, name, description);
        this.setCreationDate(new GregorianCalendar());
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(new GregorianCalendar());
        this.setBudgetEstimate(budgetEstimate);

        this.isTerminated = false;
    }

    private GregorianCalendar creationDate;
    private GregorianCalendar startDate;
    private HashMap<Developer, PList<Role>> projectParticipants;
    private long budgetEstimate;

    private boolean isTerminated;

    /**
     * This method check if the given Milestone is a valid Milestone for an AbstractSystem Checks if the constraintCheck
     * is still valid for the parent of this AbstractSystem
     *
     * @param milestone the Milestone to check
     *
     * @return true if the given Milestone is valid for an AbstractSystem.
     */
    @DomainAPI
    @Override
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
        return true;
    }

    /**
     * This method checks if the given parent is a valid parent for this project
     *
     * @param parent The parent to check
     *
     * @return True if the given parent is a valid parent for this project.
     */
    public boolean isValidParent(AbstractSystem parent) {
        return parent == null;
    }

    /**
     * This method returns the lead of this project
     *
     * @return the lead developer of this project, null if no exists yet
     */
    @DomainAPI
    public Developer getLead() {
        Developer lead = null;
        try {
            lead = this.projectParticipants.entrySet().parallelStream().filter(u -> u.getValue().contains(Role.LEAD))
                    .findFirst().get().getKey();
        } catch (NoSuchElementException ex) {
            // no lead -> return null;
        }
        return lead;
    }

    /**
     * This method sets the lead of this project
     *
     * @param lead The new lead of this project
     * @throws IllegalArgumentException if isValidLead(lead) fails
     * @Ensures if the lead was already set, the lead will not change
     * @see Project#isValidLead(Developer)
     */
    private void setLead(Developer lead) throws IllegalArgumentException {
        if (!Project.isValidLead(lead)) {
            throw new IllegalArgumentException("The given developer is invalid as lead for this project");
        }
        if (this.getLead() == null) {
            this.setRole(lead, Role.LEAD);
        }
    }

    /**
     * This method checks if a given lead is a valid lead for the given project
     *
     * @param lead The lead of the Project
     * @return true if the given lead is valid for this project
     */
    @DomainAPI
    public static boolean isValidLead(Developer lead) {
        if (lead == null) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the startdate variable.
     *
     * @return A clone of the start date of the project.
     */
    @DomainAPI
    public GregorianCalendar getStartDate() {
        return (GregorianCalendar) startDate.clone();
    }

    /**
     * Sets the start date of the project to the given date. A clone of this startDate will be stored.
     *
     * @param startDate The start date of the project.
     * @throws IllegalArgumentException if the given date is invalid
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     */
    public void setStartDate(GregorianCalendar startDate) throws IllegalArgumentException {
        if (!isValidStartDate(this.creationDate, startDate)) {
            throw new IllegalArgumentException("Invalid startDate/creationDate.");
        }
        this.startDate = (GregorianCalendar) startDate.clone();
    }

    /**
     * This method checks the validity of the start date.
     *
     * @param creationDate The creation date.
     * @param startDate The start date.
     * @return True if creation date <= start date.
     */
    @DomainAPI
    public static boolean isValidStartDate(GregorianCalendar creationDate, GregorianCalendar startDate) {
        if (creationDate == null || startDate == null) {
            return false;
        }
        if (creationDate.before(startDate) || creationDate.compareTo(startDate) == 0) {
            return true;
        }
        return false;
    }

    /**
     * This method checks the validity of the start date.
     *
     * @param startDate The start date.
     * @return True if creation date <= start date.
     */
    @DomainAPI
    public boolean isValidStartDate(GregorianCalendar startDate) {
        if (startDate == null) {
            return false;
        }
        if (this.creationDate.before(startDate) || this.creationDate.compareTo(startDate) == 0) {
            return true;
        }
        return false;
    }

    /**
     * This is a getter for the CreationDate variable.
     *
     * @return The creation date of the project.
     */
    @DomainAPI
    public GregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the project to the given date.
     *
     * @param creationDate The creation date of the project.
     * @throws IllegalArgumentException if the given date is invalid
     * @see Project#isValidCreationDate(GregorianCalendar)
     */
    protected void setCreationDate(GregorianCalendar creationDate) throws IllegalArgumentException {
        if (!Project.isValidCreationDate(creationDate)) {
            throw new IllegalArgumentException("The given creation date is invalid for this project");
        }
        this.creationDate = creationDate;
    }

    /**
     * This method check if the given creation date is a valid date for this project
     *
     * @param date the date to check
     * @return true if the given date is valid
     */
    @DomainAPI
    public static boolean isValidCreationDate(GregorianCalendar date) {
        if (date == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the set budget estimate
     *
     * @return the set budget estimate as a long
     */
    @DomainAPI
    public long getBudgetEstimate() {
        return this.budgetEstimate;
    }

    /**
     * This method sets the Project's budget estimate to the given long, if valid.
     *
     * @param budgetEstimate to set, as a long
     * @throws IllegalArgumentException if the given budgetEstimate is invalid.
     * @see #isValidBudgetEstimate(long)
     */
    public void setBudgetEstimate(long budgetEstimate) throws IllegalArgumentException {
        if (!isValidBudgetEstimate(budgetEstimate)) {
            throw new IllegalArgumentException("invalid budgetestimate");
        }
        this.budgetEstimate = budgetEstimate;
    }

    /**
     * Checks the validity of the given budgetEstimate. The value must be positive (or zero)
     *
     * @param budgetEstimate The budget estimate to check of type long
     * @return false if budgetEstimate is smaller than zero
     */
    @DomainAPI
    public static boolean isValidBudgetEstimate(long budgetEstimate) {
        if (budgetEstimate < 0) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the parent. Since a Project doesn't have a parent, it returns itself.
     *
     * @return this;
     */
    @Override
    protected Project getParent() {
        return this;
    }

    /**
     * This method sets the role for a given developer
     *
     * @param dev The developer to give a role
     * @param role The role the developer has in this project
     *
     * @return Whether the roles of the developer have changed
     * @throws IllegalArgumentException if the given role was invalid
     */
    @Requires("dev != null && role != null")
    private boolean setRole(Developer dev, Role role) {
        PList<Role> roleList = this.projectParticipants.get(dev);
        if (roleList == null) {
            this.projectParticipants.put(dev, PList.<Role>empty().plus(role));
            return true;
        } else if (!roleList.contains(role)) {
            this.projectParticipants.put(dev, roleList.plus(role));
            return true;
        } else {
            return false;
        }
    }

    /**
     * The given user uses this method to set the role of a given developer to the given role
     *
     * @param user The user that wants to set the given role to the given developer
     * @param dev The developer to give the new role to
     * @param role The role that will be assigned to the given developer
     *
     * @return Whether the roles of the developer have changed.
     * @throws PermissionException If the given user does not have sufficient permissions to assign the given role to
     * the given developer
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When developer == null
     * @throws IllegalArgumentException When role == null
     */
    public boolean setRole(User user, Developer dev, Role role) throws IllegalArgumentException, PermissionException {
        if (user == null || dev == null || role == null) {
            throw new IllegalArgumentException("setRole(User, Developer, Role) does not allow a null-reference for user, dev and role.");
        }
        if (!user.hasRolePermission(role.getNeededPerm(), this)) {
            throw new PermissionException("The given user doesn't have the needed permission to set the role");
        }
        return this.setRole(dev, role);
    }

    /**
     * Remove the role from the developer for this project.
     * <br><b> Caution: Only use when you can guarantee no constraints are effected.</b>
     * (e.g This does change anything about BugReports with the developer as 'assigned'.)
     * <br> This is used for undoing an 'assign role' directly after assigning that role.
     *
     * @param dev The developer to delete the role off.
     * @param role The role to remove
     * @return Whether the roles of the developer have changed. False when dev == null || role == null.
     */
    public boolean deleteRole(Developer dev, Role role) {
        if (dev == null || role == null) {
            return false;
        }

        //retrieve info
        PList<Role> roles = this.projectParticipants.get(dev);
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        int oldSize = roles.size();
        //delete
        roles = roles.minus(role);
        //clean up
        if (roles.isEmpty()) { //Change, empty now
            this.projectParticipants.remove(dev);
            return true;
        } else if (roles.size() == oldSize) { //no change
            return false;
        } else { //change
            this.projectParticipants.put(dev, roles);
            return true;
        }
    }

    /**
     * This functions returns all the developers associated with this project
     *
     * @return The list of all developers associated with this project
     */
    @DomainAPI
    @Override
    public PList<Developer> getAllDev() {
        return PList.<Developer>empty().plusAll(this.projectParticipants.keySet());
    }

    /**
     * This method returns the combined impact of all the bug reports associated with this Project
     *
     * @return The combined impact of all bug reports associated with this Project
     */
    @Override
    @DomainAPI
    public double getBugImpact() {
        double impact = 0.0;

        PList<Subsystem> subsystems = this.getSubsystems();
        for (Subsystem subsystem : subsystems) {
            impact += subsystem.getBugImpact();
        }

        return impact;
    }

    /**
     * This method returns all the roles associated with the developers of this project
     *
     * @param dev The developer to get the roles for
     * @return The roles the developer has in this project
     */
    @DomainAPI
    public PList<Role> getAllRolesDev(Developer dev) {
        PList<Role> roles = this.projectParticipants.get(dev);
        return (roles != null) ? roles : PList.<Role>empty();
    }

    /**
     * This method checks if the given developer has the requested permission for this subsystem
     *
     * @param dev the developer to check
     * @param perm the requested permission
     * @return true if the developer has the requested permission
     */
    @Override
    @DomainAPI
    public boolean hasPermission(Developer dev, RolePerm perm) {
        PList<Role> roleList = this.projectParticipants.get(dev);
        if (roleList == null) {
            return false;
        }
        return roleList.parallelStream().anyMatch(role -> role.hasPermission(perm));
    }

    /**
     * Get the Project details in string format so it is nice to print out. (Includes Project name, description, version
     * ID, budgetEstimate, startDate, Lead, creationDate)
     *
     * @return A string with on each line an attribute of the Project: name, description, versionID, budget estimate,
     * start date, lead developer and creation date
     */
    @DomainAPI
    @Override
    public String getDetails() {
        String details = "Project name:\t\t\t";
        details += this.getName();
        details += "\nProject version:\t\t";
        details += this.getVersionID().toString();
        details += "\nAchieved milestone:\t\t";
        details += this.getMilestone().toString();
        details += "\nProject description: \t\t";
        details += this.getDescription();
        details += "\nBudget estimate:\t\t" + this.getBudgetEstimate() + "\nStart date: \t\t\t";
        details += this.getStartDate().getTime();
        details += "\nLead dev. full name:\t\t";
        details += this.getLead().getFullName();
        details += "\nLead dev. user name:\t\t";
        details += this.getLead().getUsername();
        details += "\nCreation date:\t\t\t";
        details += this.getCreationDate().getTime();
        details += "\nSubsystems of this project: ";
        for (Subsystem subsys : this.getAllSubsystems()) {
            details += subsys.getDetails();
        }
        return details;
    }

    /**
     * Performs a deep clone on this Project. excluding all bugReports.
     *
     * @param version The versionID of this project.
     * @param lead The lead developer of this project
     * @param startDate The start date of this project.
     * @param budgetEstimate The budget estimate of this project
     * @return The deep-cloned project.
     * @see Subsystem#cloneSubsystem(AbstractSystem)
     */
    public Project cloneProject(VersionID version, Developer lead, GregorianCalendar startDate, long budgetEstimate) {

        Project cloneProject = new Project(version, this.getName(), this.getDescription(), lead, startDate,
                budgetEstimate);
        for (Subsystem subsystemChild : this.getSubsystems()) {
            subsystemChild.cloneSubsystem(cloneProject);
        }
        return cloneProject;

    }

    /**
     * This method returns the subject name and type as a string.
     *
     * @return the name of this subject
     */
    @Override
    @DomainAPI
    public String getSubjectName() {
        return ("Project " + this.getName());
    }

    /**
     * This method notifies the subsystem it belongs to, to update it's mailboxes for a tag subscription and to notify
     * its parent.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    @Override
    public void notifyTagSubs(BugReport br) {
        this.updateTagSubs(br);
    }

    /**
     * This method notifies the subsystem it belongs to, to update it's mailboxes for a comment subscription and to
     * notify its parent.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    @Override
    public void notifyCommentSubs(BugReport br) {
        this.updateCommentSubs(br);
    }

    /**
     * This method notifies the subsystem it belongs to, to update it's mailboxes for a creation subscription and to
     * notify its parent.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    @Override
    public void notifyCreationSubs(BugReport br) {
        this.updateCreationSubs(br);
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
     * This method check whether or not the current Project is terminated
     *
     * @return true if the object is terminated
     */
    @Override
    @DomainAPI
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * The method returns a memento for this Project.
     *
     * @return The memento of this system.
     */
    @Override
    public ProjectMemento getMemento() {
        return new ProjectMemento(getVersionID(), getName(), getDescription(), getSubsystems(), getParent(), 
                getMilestone(), getCreationDate(), getStartDate(), this.projectParticipants, this.budgetEstimate, 
                this.isTerminated);
    }

    /**
     * Set the memento of this Project.
     *
     * @param mem The Memento to use to set.
     * @throws IllegalArgumentException When mem == null
     * @throws IllegalArgumentException When any of the arguments stored in mem is invalid for the current state. (e.g
     * milestones due to constraints)
     */
    @Override
    public void setMemento(AbstractSystemMemento mem) {
        super.setMemento(mem);
        
        if(mem instanceof ProjectMemento) {
            ProjectMemento pMem = (ProjectMemento) mem;
            
            this.creationDate = pMem.getCreationDate();
            this.startDate = pMem.getStartDate();
            this.projectParticipants = pMem.getProjectParticipants();
            this.budgetEstimate = pMem.getBudgetEstimate();
            this.isTerminated = pMem.getIsTerminated();
        }
    }
}
