package bugtrap03.bugdomain;

import java.util.Date;

import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Role;
import purecollections.PList;
import purecollections.PMap;


/**
 * This class extends AbstractSystem (versionID, name and description) and extends it with dates.
 * @author Kwinten
 *
 */
public class Project extends AbstractSystem {

	private Date creationDate;
	private Date startDate;
	private PMap<Developer, PList<Role>> projectParticipants;
	private long budgetEstimate;

	/**
	 * Creates a project with a given versionID, name, description, creationDate
	 * and startDate.
	 * 
	 * @param version The versionID of the project.
	 * @param name The name of the project.
	 * @param description The description of the project.
	 * @param creationDate The creation date of the project.
	 * @param startDate The start date of the project.
	 * @throws NullPointerException if the versionID is null.
	 * @throws IllegalArgumentException if one of the String arguments or dates
	 *             is invalid.
	 */
	public Project(VersionID version, String name, String description, Date creationDate, Date startDate, long budgetEstimate)
			throws IllegalArgumentException, NullPointerException {
		super(version, name, description);
		if (! isValidStartDate(creationDate, startDate)) {
			throw new IllegalArgumentException("The project hasn't a valid creation and start date");
		} 
		setCreationDate(creationDate);
		setStartDate(startDate);
		setBudgetEstimate(budgetEstimate);	
	}

	/**
	 * This method checks the validity of the startdate.
	 * 
	 * @param creationDate The creation date.
	 * @param startDate The start date.
	 * 
	 * @return True if creation date <= start date.
	 */
	private boolean isValidStartDate(Date creationDate, Date startDate) {
		if (creationDate == null || startDate == null) {
			throw new NullPointerException("A date can't be null");
		}
		if (creationDate.before(startDate) || creationDate.compareTo(startDate) == 0) {
			return true;
		}
		return false;
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
	 * @throws NullPointerException if the given date is equal to null.
	 */
	private void setStartDate(Date startDate) {
		if (startDate != null) {
			this.startDate = startDate;
		} else {
			throw new NullPointerException("A date can't be null.");
		}
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
	 * @throws NullPointerException if the given date is equal to null.
	 */
	private void setCreationDate(Date creationDate) {
		if (creationDate != null) {
			this.creationDate = creationDate;
		} else {
			throw new NullPointerException("A date can't be null.");
		}
	}
	
	/**
	 * 
	 * @param budgetEstimate
	 * @throws IllegalArgumentException
	 */
	private void setBudgetEstimate(long budgetEstimate) throws IllegalArgumentException{
		if (! isValidBudgetEstimate(budgetEstimate)){
			throw new IllegalArgumentException("invalid budgetestimate");
		}
		this.budgetEstimate = budgetEstimate;
	}	
	
	/**
	 * 
	 * @param budgetEstimate
	 * @return
	 */
	private boolean isValidBudgetEstimate(long budgetEstimate){
		if (budgetEstimate < 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected long getBudgetEstimate(){
		return this.budgetEstimate;
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
	
	
	
	
}
