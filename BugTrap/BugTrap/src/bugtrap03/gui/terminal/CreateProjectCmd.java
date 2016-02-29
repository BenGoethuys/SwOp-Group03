package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.User;
import java.util.GregorianCalendar;
import java.util.Scanner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
     * <br> b) Clone project (without BugReports)
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
    public Project exec(TerminalScanner scan, DataController con, User user) throws PermissionException, CancelException {
        System.out.println("Create or clone a new project?");
        String answer = null;
        do {
            System.out.print("Create or clone: ");
            answer = scan.nextLine();

            if (answer.equalsIgnoreCase("create")) {
                return createProjectScenario(scan, con, user);
            } else if (answer.equalsIgnoreCase("clone")) {
                return cloneProjectScenario(scan, con, user);
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
    private Project createProjectScenario(TerminalScanner scan, DataController con, User user) throws CancelException, PermissionException {
        //Project name
        System.out.print("Project name:");
        String projName = scan.nextLine();

        //Project description
        System.out.print("Project description:");
        String projDesc = scan.nextLine();

        //Project start date
        GregorianCalendar projStartDate = null;
        do {
            System.out.print("Project starting date (YYYY-MM-DD):");
            String[] projDateStr = scan.nextLine().split("-");
            try {
                projStartDate = new GregorianCalendar(Integer.parseInt(projDateStr[0]), Integer.parseInt(projDateStr[1]), Integer.parseInt(projDateStr[2]));
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        } while (projStartDate == null);

        //Project budget estimate
        Integer projBudgetEstimate = null;
        do {
            System.out.print("Project budget estimate:");
            try {
                projBudgetEstimate = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        } while (projBudgetEstimate == null);

        //Project lead developer
        System.out.println("Chose a lead developer.");
        Developer lead = (new GetUserOfExcactTypeCmd<>(Developer.class)).exec(scan, con, user);

        //Create Project
        Project proj = con.createProject(projName, projDesc, projStartDate, lead, projBudgetEstimate, user);

        //Print created project details
        System.out.println(proj.getDetails());

        return proj;
    }

    /**
     * Execute the create project scenario.
     * <br> b) Clone project (without BugReports)
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
    private Project cloneProjectScenario(TerminalScanner scan, DataController con, User user) {
        
    }
}
