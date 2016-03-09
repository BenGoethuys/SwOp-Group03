package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.cmd.general.GetUserOfTypeCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This class represents the assign to project scenario
 *
 * @author Group 03
 */
public class AssignToProjectCmd implements Cmd {

    /**
     * <p>
     * <br> 1. The developer indicates he wants to assign another developer.
     * <br> 2. The system shows a list of the projects in which the logged in
     * user is assigned as lead developer.
     * <br> 2.a The logged in developer is not assigned a lead role in any
     * project: The use case ends here.
     * <br> 3. The lead developer selects one of his projects.
     * <br> 4. The system shows a list of other developers to assign.
     * <br> 5. The lead developer selects one of these other developers.
     * <br> 6. The system shows a list of possible (i.e. not yet assigned) roles
     * for the selected developer.
     * <br> 7. The lead developer selects a role.
     * <br> 8. The systems assigns the selected role to the selected developer.
     *
     * @param scan The {@link TerminalScanner} used to interact with the person.
     * @param model The {@link DataModel} used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return The project to which the selected developer is assigned the
     * selected role. Null if the user does not lead any projects.
     * @throws PermissionException When the user does not have sufficient
     * permissions.
     * @throws CancelException When the user has indicated to abort the cmd.
     * @throws IllegalArgumentException When scan, model,user is null
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        //1. The developer indicates he wants to assign another developer.

        //Retrieve projects with user as lead.
        PList<Project> projectList = model.getProjectList();
        for (Project proj : projectList) {
            if (!proj.getLead().equals(user)) {
                projectList = projectList.minus(proj);
            }
        }

        //2.a user is not assigned a lead role in any project: The use case ends here.
        if (projectList.isEmpty()) {
            scan.println("You don't lead any projects.");
            return null;
        }

        //2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
        //3. The lead developer selects one of his projects.
        Project selectedProj = (new GetProjectCmd(projectList)).exec(scan, model, user);

        //4. The system shows a list of other developers to assign.
        //5. The lead developer selects one of these other developers.
        Developer devToSet = new GetUserOfTypeCmd<>(Developer.class).exec(scan, model, user);

        PList<Role> currentRolesList = selectedProj.getAllRolesDev(devToSet);
        PList<Role> roleList = model.getAllRoles();
        for (Role role : currentRolesList) {
            roleList.minus(role);
        }

        //6. Shows a list of possible (not yet assigned) roles for the selected dev.
        //7. The lead developer selects a role.
        Role selectedRole = new GetObjectOfListCmd<>(roleList, (u -> u.name()), ((u, input) -> u.name().equals(input)))
                .exec(scan, model, user);

        scan.println("Selected role: " + selectedRole.name());

        //8. The systems assigns the selected role to the selected developer.
        model.assignToProject(selectedProj, user, devToSet, selectedRole);
        scan.println(devToSet.getFullName() + " assigned to project: " + selectedProj.getName() + ", with role: " + selectedRole.name());

        return selectedProj;
    }
}
