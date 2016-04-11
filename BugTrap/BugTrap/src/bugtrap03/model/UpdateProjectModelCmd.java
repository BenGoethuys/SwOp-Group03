package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.User;
import java.util.GregorianCalendar;

/**
 *
 * @author Group 03
 */
class UpdateProjectModelCmd extends ModelCmd {

    /**
     * Create a new {@link ModelCmd} that can update a {@link Project} when executed.
     *
     * @param proj The project to update
     * @param user The person who wants to update the project
     * @param name The new name of the given project
     * @param desc The new description of the given project
     * @param startDate The new startDate of the given project
     * @param budgetEstimate The new budget estimate of the given project
     *
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When any of the arguments passed is invalid.
     * @throws IllegalArgumentException When the given project is terminated
     */
    UpdateProjectModelCmd(Project proj, User user, String name, String desc, GregorianCalendar startDate, long budgetEstimate) {
        if (user == null) {
            throw new IllegalArgumentException("The user passed to UpdateProjectModelCmd was a null reference.");
        }
        if (proj == null){
            throw new IllegalArgumentException("The project passed to UpdateProjectModelCmd was a null reference.");
        }
        if (proj.isTerminated()){
            throw new IllegalArgumentException("The given project is terminated");
        }

        this.proj = proj;
        this.user = user;
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.budgetEstimate = budgetEstimate;
    }

    private final Project proj;
    private final User user;
    private final String name;
    private final String desc;
    private final GregorianCalendar startDate;
    private final long budgetEstimate;

    private boolean isExecuted = false;

    private String oldName;
    private String oldDesc;
    private GregorianCalendar oldStartDate;
    private long oldBudgetEstimate;

    /**
     * Update the given {@link Project} with the given name, desc, startDate and budget.
     *
     * @return The updated Project.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @throws IllegalArgumentException When any of the arguments passed is invalid.
     * @throws PermissionException When the passed user does not have permission to update the given project.
     */
    @Override
    Project exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The UpdateProjectModelCmd was already executed.");
        }

        // check needed permission
        if (!user.hasPermission(UserPerm.UPDATE_PROJ)) {
            throw new PermissionException("You don't have the needed permission to update a project!");
        }

        // Test to prevent inconsistent updating of vars
        if (!(Project.isValidName(name) && Project.isValidDescription(desc)
                && proj.isValidStartDate(startDate) && Project.isValidBudgetEstimate(budgetEstimate))) {
            throw new IllegalArgumentException("Illegal arguments passed to UpdateProjectModelCmd.");
        }

        //Save old state
        this.oldName = proj.getName();
        this.oldDesc = proj.getDescription();
        this.oldStartDate = proj.getStartDate();
        this.oldBudgetEstimate = proj.getBudgetEstimate();

        // update the vars in proj
        proj.setName(name);
        proj.setDescription(desc);
        proj.setStartDate(startDate);
        proj.setBudgetEstimate(budgetEstimate);

        isExecuted = true;
        return proj;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        //Restore variables.
        proj.setName(oldName);
        proj.setDescription(oldDesc);
        proj.setStartDate(oldStartDate);
        proj.setBudgetEstimate(oldBudgetEstimate);

        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        String strDate = (startDate != null) ? startDate.getTime().toString() : "-invalid argument-";
        
        StringBuilder string = new StringBuilder();
        string.append("Updated Project ").append(proj.getName());
        string.append(" with ").append(this.name);
        string.append(", ").append(this.desc);
        string.append(", ").append(strDate);
        string.append(", ").append(this.budgetEstimate);
        return string.toString();
    }

}
