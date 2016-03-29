package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
class AssignToProjectModelCmd extends ModelCmd {

    /**
     * Creates a {@link ModelCmd} that when executed can assign a developer a role on a certain project.
     *
     * @param project The project in which the user will be assigned
     * @param user The user that assigns the role to the developer
     * @param developer The developer that gets a role assigned
     * @param role The role that will be assigned
     *
     * @throws IllegalArgumentException When project == null
     */
    AssignToProjectModelCmd(Project project, User user, Developer developer, Role role) throws IllegalArgumentException {
        if (project == null) {
            throw new IllegalArgumentException("The project passed to AssignToProjectModelCmd was a null reference.");
        }

        this.project = project;
        this.user = user;
        this.developer = developer;
        this.role = role;
    }

    private final Project project;
    private final User user;
    private final Developer developer;
    private final Role role;

    private boolean isExecuted;

    private PList<Role> oldRoles;

    /**
     * This method let's a user assign a role to a developer in a given project .
     *
     * @returns Whether the roles were changed.
     * @throws PermissionException if the user doesn't have the needed permission.
     * @throws IllegalArgumentException When role == null
     * @throws IllegalArgumentException When developer == null
     * @throws IllegalArgumentException When user == null
     */
    @Override
    Boolean exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The AssignToProjectModelCmd was already executed.");
        }

        oldRoles = project.getAllRolesDev(developer);
        project.setRole(user, developer, role);
        isExecuted = true;
        return oldRoles != project.getAllRolesDev(developer);
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        //If there was a change, undo it.
        if (oldRoles != project.getAllRolesDev(developer)) {
            project.deleteRoles(developer); //delete current roles
            for (Role r : oldRoles) { //re-add old roles
                try {
                    project.setRole(user, developer, r);
                } catch (IllegalArgumentException | PermissionException ex) {
                    Logger.getLogger(AssignToProjectModelCmd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

}
