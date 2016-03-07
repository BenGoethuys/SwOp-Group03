package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.cmd.general.GetUserOfTypeCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.NoSuchElementException;

/**
 * Created by Kwinten on 07/03/2016.
 */
public class AssignToProjectCmd implements Cmd{
    /**
     * <p>
     * <br> 1. The developer indicates he wants to assign another developer.
     * <br> 2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
     * <br> 2.a The logged in developer is not assigned a lead role in any project: The use case ends here.
     * <br> 3. The lead developer selects one of his projects.
     * <br> 4. The system shows a list of other developers to assign.
     * <br> 5. The lead developer selects one of these other developers.
     * <br> 6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
     * <br> 7. The lead developer selects a role.
     * <br> 8. The systems assigns the selected role to the selected developer.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return the project to which the selected developer is assigned the selected role
     * @throws PermissionException
     * @throws CancelException
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {

        PList<Project> projectList = model.getProjectList();
        for (Project proj: projectList){
            if (! proj.getLead().equals(user)){
                projectList = projectList.minus(proj);
            }
        }
        if (projectList.isEmpty()){
            scan.println("You don't lead any projects.");
            return null;
        }
        scan.println("Projects you lead: ");
        for (int i=0; i<projectList.size(); i++){
            scan.println(i + ". " + projectList.get(i).getName() + projectList.get(i).getVersionID());
        }
        Project selectedProj = null;
        do{
            scan.print("I choose project: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < projectList.size()) {
                    selectedProj = projectList.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                try {
                    selectedProj = projectList.parallelStream().filter(u -> u.getName().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
                try {
                    selectedProj = projectList.parallelStream().filter(u -> (u.getName()+u.getVersionID()).equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while (selectedProj ==  null);

        Developer devToSet = new GetUserOfTypeCmd<Developer>(Developer.class).exec(scan,  model, user);
        PList<Role> currentRolesList = selectedProj.getAllRolesDev(devToSet);
        PList<Role> roleList = model.getAllRoles();
        for (Role role: currentRolesList){
            roleList.minus(role);
        }
        scan.println("Possible roles to assign: ");
        for (int i=0; i<roleList.size();i++){
            scan.println(i + ". " + roleList.get(i).name());
        }
        Role selectedRole =  null;
        do{
            scan.print("I choose role: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < roleList.size()) {
                    selectedRole = roleList.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                try {
                    selectedRole = roleList.parallelStream().filter(u -> u.name().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while(selectedRole == null);
        scan.println("Selected role: " + selectedRole.name());
        model.assignToProject(selectedProj, user, devToSet, selectedRole);
        scan.println(devToSet.getFullName() + " assigned to project: " + selectedProj.getName() + ", with role: " + selectedRole.name());
        return selectedProj;
    }
}
