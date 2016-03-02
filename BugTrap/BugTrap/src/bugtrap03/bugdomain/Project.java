package bugtrap03.bugdomain;

import java.util.ArrayList;
import java.util.HashMap;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import purecollections.PList;

/**
 * This class extends AbstractSystem (versionID, name and description) and
 * extends it with dates.
 *
 * @author Kwinten Buytaert & Ben Goethuys
 */
public class Project extends AbstractSystem {

    /**
     * Creates a project with a given versionID, name, description,
     * creationDate, lead, startDate, budgetEstimate.
     *
     * @param version        The versionID of this project.
     * @param name           The name of this project.
     * @param description    The description of this project.
     * @param creationDate   The creation date of this project.
     * @param lead           The lead developer of this project
     * @param startDate      The start date of this project.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(VersionID, String, String)
     * @see Project#isValidCreationDate(GregorianCalendar)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(VersionID version, String name, String description, GregorianCalendar creationDate, Developer lead,
                   GregorianCalendar startDate, long budgetEstimate) throws IllegalArgumentException {
        super(version, name, description);
        this.setCreationDate(creationDate);
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(startDate);
        this.setBudgetEstimate(budgetEstimate);
    }

    /**
     * Creates a project with a given versionID, name, description, lead,
     * startDate, budgetEstimate.
     *
     * @param version        The versionID of this project.
     * @param name           The name of this project.
     * @param description    The description of this project.
     * @param lead           The lead developer of this project
     * @param startDate      The start date of this project.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(VersionID, String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(VersionID version, String name, String description, Developer lead, GregorianCalendar startDate,
                   long budgetEstimate) throws IllegalArgumentException {
        this(version, name, description, new GregorianCalendar(), lead, startDate, budgetEstimate);
    }

    /**
     * Creates a project with a given versionID, name, description, lead,
     * startDate, budgetEstimate.
     *
     * @param name           The name of this project.
     * @param description    The description of this project.
     * @param lead           The lead developer of this project
     * @param startDate      The start date of this project.
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(String name, String description, Developer lead, GregorianCalendar startDate, long budgetEstimate)
            throws IllegalArgumentException {
        super(name, description);
        this.setCreationDate(new GregorianCalendar());
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(startDate);
        this.setBudgetEstimate(budgetEstimate);
    }

    /**
     * Creates a project with a given versionID, name, description, lead,
     * startDate, budgetEstimate.
     *
     * @param name           The name of this project.
     * @param description    The description of this project.
     * @param lead           The lead developer of this project
     * @param budgetEstimate The budget estimate of this project
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(String, String)
     * @see Project#isValidLead(Developer)
     * @see Project#isValidBudgetEstimate(long)
     */
    public Project(String name, String description, Developer lead, long budgetEstimate)
            throws IllegalArgumentException {
        super(name, description);
        this.setCreationDate(new GregorianCalendar());
        this.projectParticipants = new HashMap<>();
        this.setLead(lead);
        this.setStartDate(new GregorianCalendar());
        this.setBudgetEstimate(budgetEstimate);
    }

    private GregorianCalendar creationDate;
    private GregorianCalendar startDate;
    private HashMap<Developer, PList<Role>> projectParticipants;
    private long budgetEstimate;

    /**
     * This method returns the lead of this project
     *
     * @return the lead developer of this project, null if no exists yet
     */
    public Developer getLead() {
        Developer lead = null;
        try {
            lead = this.projectParticipants.entrySet().parallelStream()
                    .filter(u -> u.getValue().contains(Role.LEAD))
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
    public static boolean isValidLead(Developer lead) {
        if (lead == null) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the startdate variable.
     *
     * @return The start date of the project.
     */
    public GregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the project to the given date.
     *
     * @param startDate The start date of the project.
     * @throws IllegalArgumentException if the given date is invalid
     * @see Project#isValidStartDate(GregorianCalendar, GregorianCalendar)
     */
    public void setStartDate(GregorianCalendar startDate) throws IllegalArgumentException {
        if (!isValidStartDate(this.creationDate, startDate)) {
            throw new IllegalArgumentException("Invalid startDate/creationDate.");
        }
        this.startDate = startDate;
    }

    /**
     * This method checks the validity of the start date.
     *
     * @param creationDate The creation date.
     * @param startDate    The start date.
     * @return True if creation date <= start date.
     */
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
     * This method check if the given creation date is a valid date for this
     * project
     *
     * @param date the date to check
     * @return true if the given date is valid
     */
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
    public long getBudgetEstimate() {
        return this.budgetEstimate;
    }

    /**
     * This method sets the Project's budget estimate to the given long,
     * if valid.
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
     * Checks the validity of the given budgetEstimate.
     * The value must be positive (or zero)
     *
     * @param budgetEstimate The budget estimate to check of type long
     * @return false if budgetEstimate is smaller than zero
     */
    public static boolean isValidBudgetEstimate(long budgetEstimate) {
        if (budgetEstimate < 0) {
            return false;
        }
        return true;
    }

    /**
     * This is a getter for the parent. Since a Project doesn't have a parent,
     * it returns itself.
     *
     * @return this;
     */
    protected Project getParent() {
        return this;
    }

    /**
     * This method sets the role for a given developer
     *
     * @param dev  The developer to give a role
     * @param role The role the developer has in this project
     */
    private void setRole(Developer dev, Role role) {
        PList<Role> roleList = this.projectParticipants.get(dev);
        if (roleList == null) {
            this.projectParticipants.put(dev, PList.<Role>empty().plus(role));
        } else {
            if (!roleList.contains(role)) {
                this.projectParticipants.put(dev, roleList.plus(role));
            }
        }
    }

    /**
     * //TODO heading
     *
     * @param user
     * @param dev
     * @param role
     * @throws PermissionException
     */
    public void setRole(User user, Developer dev, Role role) throws IllegalArgumentException, PermissionException {
        if (user == null) {
            throw new IllegalArgumentException(
                    "setRole(User, Developer, Role) does not allow a null-reference for user.");
        }
        if (! user.hasRolePermission(role.getNeededPerm(), this)) {
            throw new PermissionException("The given user doesn't have the needed permission to set the role");
        }
        this.setRole(dev, role);
    }

    /**
     * This method returns all the roles associated with the developers of this
     * project
     *
     * @param dev The developer to get the roles for
     * @return The roles the developer has in this project
     */
    public PList<Role> getAllRolesDev(Developer dev) {
        return this.projectParticipants.get(dev);
    }

    /**
     * This method checks if the given developer has the requested permission
     * for this subsystem
     *
     * @param dev  the developer to check
     * @param perm the requested permission
     * @return true if the developer has the requested permission
     */
    @Override
    public boolean hasPermission(Developer dev, RolePerm perm) {
        PList<Role> roleList = this.projectParticipants.get(dev);
        if (roleList == null) {
            return false;
        }
        return roleList.parallelStream().anyMatch(role -> role.hasPermission(perm));
    }

    /**
     * Get the Project details in string format so it is nice to print out.
     * (Includes Project name, description, version ID, budgetEstimate, startDate, Lead,
     * creationDate)
     *
     * @return A string with on each line an attribute of the Project:
     * name, description, versionID, budget estimate, start date,
     * lead developer and creation date
     */
    public String getDetails() {
        String details = "Project name:\t \t";
        details += this.getName();
        details += "\nProject version:\t";
        details += this.getVersionID().toString();
        details += "\nProject description: \t";
        details += this.getDescription();
        details += "\nBudget estimate:\t" + this.getBudgetEstimate() + "\nStart date: \t \t";
        details += this.getStartDate().getTime();
        details += "\nLead dev. full name:\t";
        details += this.getLead().getFullName();
        details += "\nLead dev. user name:\t";
        details += this.getLead().getUsername();
        details += "\nCreation date:\t \t";
        details += this.getCreationDate().getTime();
        details += "\n";
        return details;
    }

    /**
     * Performs a deep clone on this Project. excluding all bugReports.
     *
     * @param version        The versionID of this project.
     * @param lead           The lead developer of this project
     * @param startDate      The start date of this project.
     * @param budgetEstimate The budget estimate of this project
     * @return The deep-cloned project.
     * @see Subsystem#cloneSubsystem()
     */
    public Project cloneProject(VersionID version, Developer lead, GregorianCalendar startDate, long budgetEstimate) {
 
        Project cloneProject = new Project(version, this.getName(), this.getDescription(), lead, startDate,
                budgetEstimate);
        ArrayList<Subsystem> cloneChilds = new ArrayList<>();
        for (Subsystem subsystemChild : this.getChilds()) {
            cloneChilds.add(subsystemChild.cloneSubsystem());
        }
        return cloneProject;

    }
}
