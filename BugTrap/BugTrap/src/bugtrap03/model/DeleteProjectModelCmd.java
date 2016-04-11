package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class DeleteProjectModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can delete a given {@link Project} when executed.
     *
     * @param model The DataModel to delete the given project in.
     * @param user The user who wants to delete the project.
     * @param project The project to delete.
     * 
     * @throws IllegalArgumentException When model == null
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When the given project is null or terminated
     */
    DeleteProjectModelCmd(DataModel model, User user, Project project) {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the DeleteProjectModelCmd was a null reference.");
        }
        if(user == null) {
            throw new IllegalArgumentException("The user passed to the DeleteProjectModelCmd was a null reference.");
        }
        if (project ==  null){
            throw new IllegalArgumentException("The given project was null and thus cannot be deleted");
        }
        if (project.isTerminated()){
            throw new IllegalArgumentException("The given project is terminated");
        }

        this.model = model;
        this.user = user;
        this.project = project;
    }

    private final DataModel model;
    private final User user;
    private final Project project;

    private boolean isExecuted = false;

    /**
     * Delete the given {@link Project} from the given {@link DataModel}
     *
     * @return The deleted Project
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @throws PermissionException When the passed user does not have permission to delete the given project.
     */
    @Override
    Project exec() throws PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The UpdateProjectModelCmd was already executed.");
        }

        if (!user.hasPermission(UserPerm.DELETE_PROJ)) {
            throw new PermissionException("You dont have the needed permission to delete a project");
        }

        model.deleteProject(project);
        isExecuted = true;
        return project;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        project.setTerminated(false);
        model.addProject(project);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        String projName = (this.project != null) ? this.project.getName() : "-invalid argument-";
        return "Deleted Project " + projName;
    }

}
