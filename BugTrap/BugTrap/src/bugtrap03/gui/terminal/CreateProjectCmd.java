package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.permission.UserPerm;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.User;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class CreateProjectCmd implements Cmd {

    /**
     * Execute the create project scenario.
     * <br> 0. Ask user to create a new or clone a project.
     * <br> a) Create new
     * <br> a1. Ask user the name of the project
     * <br> a2. Ask user the description of the project
     * <br> a3. Ask user the starting date of the project
     * <br> a4. Ask user the budget estimate of the project.
     * <br> a5. Ask user the lead developer by providing a list of
     * possibilities.
     * <br> a6. Show the user the details of the created project.
     * <br> b) Clone project
     * <br> b1. Ask user which project by providing a list of possibilities.
     * <br> b2. Ask user the version number
     * <br> b3. Ask user the starting date
     * <br> b4. Ask user the budget estimate
     * <br> b5. Go to step a5
     *
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param con The controller to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The user chosen by the person to login as.
     * @throws PermissionException When the user does not have sufficient
     * permissions to create/clone a project.
     */
    @Override
    public Project exec(Scanner scan, DataController con, User user) throws PermissionException {
        if (!user.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("Insufficient permissions.");
        }

        System.out.println("Create or clone a new project?");

        String answer = null;

        do {
            System.out.print("Create or clone: ");
            answer = scan.next();

            if (answer.equalsIgnoreCase("create")) {
                return createProjectScenario(scan, con, user);
            } else if (answer.equalsIgnoreCase("clone")) {
                return cloneProject(scan, con, user);
            } else {
                System.out.println("Invalid input. Use create or clone.");
                answer = null;
            }
        } while (answer == null);
        return null;
    }

    /**
     * Execute the create project scenario.
     * <br> 0. Ask user to create a new or clone a project.
     * <br> a) Create new
     * <br> a1. Ask user the name of the project
     * <br> a2. Ask user the description of the project
     * <br> a3. Ask user the starting date of the project
     * <br> a4. Ask user the budget estimate of the project.
     * <br> a5. Ask user the lead developer by providing a list of
     * possibilities.
     * <br> a6. Show the user the details of the created project.
     *
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param con The controller to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The user chosen by the person to login as.
     * @throws PermissionException When the user does not have sufficient
     * permissions to create/clone a project.
     */
    private Project createProjectScenario(Scanner scan, DataController con, User user) {
        System.out.println("");
        //TODO: Do we use isValid (static??) to check if input is correct or
        //do we just catch the error when constructing in the end and let the user 
        //be responsible for starting the cmd all over again.
    }

    private Project cloneProjectScenario(Scanner scan, DataController con, User user) {

    }

    //TODO: Do we need to check if this user can assign leads?
    private Developer askLeadDeveloper(Scanner scan, DataController con) {

    }

}
