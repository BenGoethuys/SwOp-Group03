package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import java.util.GregorianCalendar;

/**
 *
 * @author Group 03
 */
class CreateProjectModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can create a {@link Project} when executed.
     *
     * @param model The model where the project will be created in.
     * @param name The name of the project
     * @param desc The description of the project
     * @param startDate The start date of the project
     * @param lead The lead developer of this project
     * @param budget The budget estimate for this project
     * @param creator The creator of this project.
     *
     * @throws IllegalArgumentException When model is a null reference.
     */
    CreateProjectModelCmd(DataModel model, String name, String desc, GregorianCalendar startDate, Developer lead, long budget, User creator) throws IllegalArgumentException, PermissionException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateProjectModelCmd was a null reference.");
        }

        this.model = model;
        this.name = name;
        this.desc = desc;
        this.startDate = startDate;
        this.lead = lead;
        this.budget = budget;
        this.creator = creator;
    }

    /**
     * Create a {@link ModelCmd} that can create a {@link Project} when executed.
     *
     * @param model The model to create the project in.
     * @param name The name of the project
     * @param desc The description of the project
     * @param lead The lead developer of this project
     * @param budget The budget estimate for this project
     * @param creator The creator of this project
     *
     * @return The created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient permissions
     */
    CreateProjectModelCmd(DataModel model, String name, String desc, Developer lead, long budget, User creator) {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateProjectModelCmd was a null reference.");
        }
        if(creator == null) {
            throw new IllegalArgumentException("The creator passed to the CreateProjectModelCmd was a null reference.");
        }

        this.model = model;
        this.name = name;
        this.desc = desc;
        this.startDate = null;
        this.lead = lead;
        this.budget = budget;
        this.creator = creator;
    }

    private Project project;

    private DataModel model;
    private String name;
    private String desc;
    private GregorianCalendar startDate;
    private Developer lead;
    private long budget;
    private User creator;

    private boolean isExecuted = false;

    /**
     * This method creates a new {@link Project} in the DataModel model.
     *
     * @return The created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient permissions
     * @throws IllegalStateException When this ModelCmd was already executed.
     *
     * @see Project#Project(java.lang.String, java.lang.String, bugtrap03.bugdomain.usersystem.Developer,
     * java.util.GregorianCalendar, long)
     * @see Project#Project(java.lang.String, java.lang.String, bugtrap03.bugdomain.usersystem.Developer, long)
     */
    @Override
    Project exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateIssuerModelCmd was already executed.");
        }
        
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        if (startDate == null) {
            project = new Project(name, desc, lead, budget);
        } else {
            project = new Project(name, desc, lead, startDate, budget);
        }
        model.addProject(project);
        isExecuted = true;

        return project;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        model.deleteProject(project);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        String projName = (this.project != null) ? this.project.getName() : "-invalid argument-";
        return "Created Project " + projName;
    }

}
