package bugtrap03.bugdomain;

import java.util.Date;
import java.util.HashMap;

import bugtrap03.permission.PermissionException;
import bugtrap03.permission.RolePerm;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Role;
import bugtrap03.usersystem.User;
import purecollections.PList;

/**
 * This class extends AbstractSystem (versionID, name and description) and
 * extends it with dates.
 * 
 * @author Kwinten Buytaert & Ben Goethuys
 *
 */
public class Project extends AbstractSystem {
	
	//TODO isValid functions are always public - Ben ;)

	/**
	 * Creates a project with a given versionID, name, description, creationDate, lead,
	 * startDate, budgetEstimate.
	 * 
	 * @param version The versionID of this project.
	 * @param name The name of this project.
	 * @param description The description of this project.
	 * @param creationDate The creation date of this project.
	 * @param lead The lead developer of this project
	 * @param startDate The start date of this project.
	 * @param budgetEstimate The budget estimate of this project
	 * 
	 * @throws IllegalArgumentException if any of the arguments is invalid
	 * 
	 * @see AbstractSystem#AbstractSystem(VersionID, String, String)
	 * @see Project#isValidCreationDate(Date)
	 * @see Project#isValidLead(Developer)
	 * @see Project#isValidStartDate(Date, Date)
	 * @see Project#isValidBudgetEstimate(long)
	 * 
	 */
	public Project(VersionID version, String name, String description, Date creationDate, Developer lead, Date startDate,
			long budgetEstimate) throws IllegalArgumentException {
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
	 * @param version The versionID of this project.
	 * @param name The name of this project.
	 * @param description The description of this project.
	 * @param lead The lead developer of this project
	 * @param startDate The start date of this project.
	 * @param budgetEstimate The budget estimate of this project
	 * 
	 * @throws IllegalArgumentException if any of the arguments is invalid
	 * 
	 * @see AbstractSystem#AbstractSystem(VersionID, String, String)
	 * @see Project#isValidLead(Developer)
	 * @see Project#isValidStartDate(Date, Date)
	 * @see Project#isValidBudgetEstimate(long)
	 * 
	 */
	public Project(VersionID version, String name, String description, Developer lead, Date startDate,
			long budgetEstimate) throws IllegalArgumentException {
		this(version, name, description, new Date(), lead, startDate, budgetEstimate);
	}
	
	/**
	 * Creates a project with a given versionID, name, description, lead,
	 * startDate, budgetEstimate.
	 * 
	 * @param name The name of this project.
	 * @param description The description of this project.
	 * @param lead The lead developer of this project
	 * @param startDate The start date of this project.
	 * @param budgetEstimate The budget estimate of this project
	 * 
	 * @throws IllegalArgumentException if any of the arguments is invalid
	 * 
	 * @see AbstractSystem#AbstractSystem(String, String)
	 * @see Project#isValidLead(Developer)
	 * @see Project#isValidStartDate(Date, Date)
	 * @see Project#isValidBudgetEstimate(long)
	 * 
	 */
	public Project(String name, String description, Developer lead, Date startDate,
			long budgetEstimate) throws IllegalArgumentException {
		super(name, description);
		this.setCreationDate(new Date());
		this.projectParticipants = new HashMap<>();
		this.setLead(lead);
		this.setStartDate(startDate);
		this.setBudgetEstimate(budgetEstimate);
	}
	
	/**
	 * Creates a project with a given versionID, name, description, lead,
	 * startDate, budgetEstimate.
	 * 
	 * @param name The name of this project.
	 * @param description The description of this project.
	 * @param lead The lead developer of this project
	 * @param budgetEstimate The budget estimate of this project
	 * 
	 * @throws IllegalArgumentException if any of the arguments is invalid
	 * 
	 * @see AbstractSystem#AbstractSystem(String, String)
	 * @see Project#isValidLead(Developer)
	 * @see Project#isValidBudgetEstimate(long)
	 * 
	 */
	public Project(String name, String description, Developer lead, long budgetEstimate) 
			throws IllegalArgumentException {
		super(name, description);
		this.setCreationDate(new Date());
		this.projectParticipants = new HashMap<>();
		this.setLead(lead);
		this.setStartDate(new Date());
		this.setBudgetEstimate(budgetEstimate);
	}
	
	private Developer lead;
	private Date creationDate;
	private Date startDate;
	private HashMap<Developer, PList<Role>> projectParticipants;
	private long budgetEstimate;
	
	/**
	 * This method returns the lead of this project
	 * @return the lead developer of this project
	 */
	public Developer getLead(){
		return this.lead;
	}
	
	/**
	 * This method sets the lead of this project
	 * @param lead The new lead of this project
	 * 
	 * @throws IllegalArgumentException if isValidLead(lead) fails
	 * 
	 * @see Project#isValidLead(Developer)
	 * 
	 * @Ensures if the lead was already set, the lead will not change
	 */
	private void setLead(Developer lead) throws IllegalArgumentException {
		if (! this.isValidLead(lead)){
			throw new IllegalArgumentException("The given developer is invalid as lead for this project");
		}
		if (this.lead == null){
			this.lead = lead;
			this.setRole(lead, Role.LEAD);
		}
	}
	
	/**
	 * This method checks if a given lead is a valid lead for the given project
	 * @param lead The lead of the Project
	 * @return true if the given lead is valid for this project
	 */
	public boolean isValidLead(Developer lead){
		if (lead == null){
			return false;
		}
		return true;
	}
	
	/**
	 * This is a getter for the startdate variable.
	 * 
	 * @return The start date of the project.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the project to the given date.
	 * 
	 * @param startDate The start date of the project.
	 * 
	 * @throws IllegalArgumentException if the given date is invalid
	 * 
	 * @see Project#isValidStartDate(Date, Date)
	 */
	private void setStartDate(Date startDate) throws IllegalArgumentException {
		if (!isValidStartDate(this.creationDate, startDate)) {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		}
		this.startDate = startDate;
	}
	
	/**
	 * This method checks the validity of the start date.
	 * 
	 * @param creationDate The creation date.
	 * @param startDate The start date.
	 * 
	 * @return True if creation date <= start date.
	 */
	public boolean isValidStartDate(Date creationDate, Date startDate) {
		if (creationDate == null || startDate == null) {
			return false;
		}
		if (creationDate.before(startDate) || creationDate.compareTo(startDate) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This is a getter for the CreationDate variable.
	 * 
	 * @return The creation date of the project.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date of the project to the given date.
	 * 
	 * @param creationDate The creation date of the project.
	 * 
	 * @throws IllegalArgumentException if the given date is invalid
	 * 
	 * @see Project#isValidCreationDate(Date)
	 */
	private void setCreationDate(Date creationDate) throws IllegalArgumentException {
		if (! this.isValidCreationDate(creationDate)){
			throw new IllegalArgumentException("The given creation date is invalid for this project");
		}
		this.creationDate = creationDate;
	}
	
	/**
	 * This method check if the given creation date is a valid date for this project
	 * @param date the date to check
	 * @return true if the given date is valid
	 */
	public boolean isValidCreationDate(Date date){
		if (date == null){
			return false;
		}
		return true;
	}
	
	/**
	 * //TODO
	 * @return
	 */
	protected long getBudgetEstimate() {
		return this.budgetEstimate;
	}

	/**
	 * //TODO
	 * @param budgetEstimate
	 * @throws IllegalArgumentException
	 */
	private void setBudgetEstimate(long budgetEstimate) throws IllegalArgumentException {
		if (!isValidBudgetEstimate(budgetEstimate)) {
			throw new IllegalArgumentException("invalid budgetestimate");
		}
		this.budgetEstimate = budgetEstimate;
	}

	/**
	 * //TODO
	 * @param budgetEstimate
	 * @return
	 */
	public boolean isValidBudgetEstimate(long budgetEstimate) {
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
	 * @param dev The developer to give a role
	 * @param role The role the developer has in this project
	 */
	private void setRole(Developer dev, Role role) {
		PList<Role> roleList = this.projectParticipants.get(dev);
		if (roleList == null) {
			this.projectParticipants.put(dev, PList.<Role> empty().plus(role));
		} else {
			if (! roleList.contains(role)){
				this.projectParticipants.put(dev, roleList.plus(role));
			}
			// else already has that role
		}
	}
	
	/**
	 * //TODO heading
	 * @param user
	 * @param dev
	 * @param role
	 * @throws PermissionException
	 */
	public void setRole(User user, Developer dev, Role role) throws PermissionException {
		if (! this.hasPermission(user, role.getNeededPerm())){
			throw new PermissionException("The given user doesn't have the needed permission to set the role");
		}
		this.setRole(dev, role);
	}
	
	/**
	 * This method returns all the roles associated with the developers of this project
	 * @param dev The developer to get the roles for
	 * 
	 * @return The roles the developer has in this project
	 */
	public PList<Role> getAllRolesDev(Developer dev) {
		return this.projectParticipants.get(dev);
	}
	
	/**
	 * This method checks if the given user has the requested permission for this subsystem
	 * @param user the user to check
	 * @param perm the requested permission
	 * 
	 * @return true if the user has the requested permission
	 */
	public boolean hasPermission(User user, RolePerm perm) {
		if (user == null){
			return false;
		}
		if (! (user instanceof Developer)){
			return false;
		}
		if (! this.hasPermission((Developer) user, perm)){
			return false;
		}
		return true;
	}
	
	/**
	 * This method checks if the given developer has the requested permission for this subsystem
	 * @param dev the developer to check
	 * @param perm the requested permission
	 * 
	 * @return true if the developer has the requested permission
	 */
	public boolean hasPermission(Developer dev, RolePerm perm){
		PList<Role> roleList = this.projectParticipants.get(dev);
		if (roleList == null){
			return false;
		}
		return roleList.parallelStream().anyMatch(role -> role.hasPermission(perm));
	}
}
