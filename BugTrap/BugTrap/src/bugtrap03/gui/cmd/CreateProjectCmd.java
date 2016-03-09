package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.cmd.general.GetUserOfExcactTypeCmd;
import bugtrap03.gui.terminal.TerminalScanner;

import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * This command represents the use case for creating a project in the system
 *
 * @author Group 03
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
     * @param model The model to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The user chosen by the person to login as.
     * @throws PermissionException When the user does not have sufficient
     * permissions to create/clone a project.
     * @throws CancelException When the user has indicated that he/she wants to
     * abort the cmd.
     * @throws IllegalArgumentException When scan, model or user is a null
     * reference.
     *
     * @see DataModel#createProject(String, String, GregorianCalendar, Developer, long, User)
     * @see DataModel#cloneProject(Project, VersionID, Developer, GregorianCalendar, long)
     * @see GetProjectCmd#exec(TerminalScanner, DataModel, User)
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 0. Ask user to create a new or clone a project.
        scan.println("Create or clone a new project?");
        Project result = null;
        do {
            scan.print("Create or clone: ");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("create")) {
                // a)
                result = createProjectScenario(scan, model, user);
            } else if (answer.equalsIgnoreCase("clone")) {
                // b)
                result = cloneProjectScenario(scan, model, user);
            } else {
                scan.println("Invalid input. Use create or clone.");
            }
        } while (result == null);
        return result;
    }

    /**
     * Execute the create project scenario.
     * <br> 0. Ask user to create a new or clone a project.
     * <br> a) Create new
     * <br> a1. Ask user the name of the project
     * <br> a2. Ask user the description of the project
     * <br> a3. Ask user the starting date of the project
     * <br> a4. Ask user the budget estimate of the project.
     * <br> a5. Ask user the lead developer by providing a list of possibilities.
     * <br> a6. Show the user the details of the created project.
     *
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param model The model to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The user chosen by the person to login as.
     * @throws CancelException When the user has indicated to abort the cmd.
     * @throws PermissionException When the user does not have sufficient permissions to create/clone a project.
     * @throws IllegalArgumentException When there is no option for a lead developer.
     * 
     * @see DataModel#createProject(String, String, GregorianCalendar, Developer, long, User) 
     */
    private Project createProjectScenario(TerminalScanner scan, DataModel model, User user) throws CancelException, PermissionException, IllegalArgumentException {
        // Project name
        // a1. Ask user the name of the project
        scan.print("Project name:");
        String projName = scan.nextLine();

        // Project description
        // a2. Ask user the description of the project
        scan.print("Project description:");
        String projDesc = scan.nextLine();

        // Project start date
        // a3. Ask user the starting date of the project
        GregorianCalendar projStartDate = null;
        do {
            scan.print("Project starting date (YYYY-MM-DD):");
            String[] projDateStr = scan.nextLine().split("-");
            try {
                projStartDate = new GregorianCalendar(Integer.parseInt(projDateStr[0]), Integer.parseInt(projDateStr[1]) - 1, Integer.parseInt(projDateStr[2]));
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                scan.println("Invalid input.");
            }
        } while (projStartDate == null);

        // Project budget estimate
        // a4. Ask user the budget estimate of the project.
        Integer projBudgetEstimate = null;
        do {
            scan.print("Project budget estimate:");
            try {
                projBudgetEstimate = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                scan.println("Invalid input.");
            }
        } while (projBudgetEstimate == null);

        // Project lead developer
        // a5. Ask user the lead developer by providing a list of possibilities.
        scan.println("choose a lead developer.");
        Developer lead = (new GetUserOfExcactTypeCmd<>(Developer.class)).exec(scan, model, user);

        if (lead == null) {
            throw new IllegalArgumentException("Cancelled cmd.");
        }

        // Create Project
        Project proj = model.createProject(projName, projDesc, projStartDate, lead, projBudgetEstimate, user);

        // Print created project details
        // a6. Show the user the details of the created project.
        scan.println(proj.getDetails());

        return proj;
    }

    /**
     * Execute the clone project scenario.
     * <br> b) Clone project (without BugReports)
     * <br> b1. Ask user which project by providing a list of possibilities.
     * <br> b2. Ask user the version number
     * <br> b3. Ask user the starting date
     * <br> b4. Ask user the budget estimate
     * <br> b5. Go to step a5
     *
     * @param scan The {@link TerminalScanner} trough which to ask the questions.
     * @param model The {@link DataModel} to use to access the model.
     * @param user The {@link User} who wants to execute this {@link Cmd}.
     * @return The user chosen by the person to login as.
     * @throws CancelException When the user has indicated to abort the cmd.
     * @throws PermissionException When the user does not have sufficient permissions to create/clone a project.
     * @throws IllegalArgumentException Check @see.
     * 
     * @see DataModel#cloneProject(Project, VersionID, Developer, GregorianCalendar, long)
     * @see GetProjectCmd#exec(TerminalScanner, DataModel, User)
     */
    private Project cloneProjectScenario(TerminalScanner scan, DataModel model, User user) throws CancelException, PermissionException, IllegalArgumentException {
        // b1. Ask user which project by providing a list of possibilities.
        Project project = (new GetProjectCmd()).exec(scan, model, user);

        // Update versionID
        // b2. Ask user the version number
        VersionID versionID = null;
        do {
            scan.print("new VersionID (format=a.b.c):");
            String input = scan.nextLine();
            String[] versionIDStr = input.split("\\.");

            int nb1, nb2, nb3;
            try {
                nb1 = Integer.parseInt(versionIDStr[0]);
                nb2 = Integer.parseInt(versionIDStr[1]);
                nb3 = Integer.parseInt(versionIDStr[2]);

                versionID = new VersionID(nb1, nb2, nb3);
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                scan.println("Invalid input. Please try again using format: a.b.c");
            }
        } while (versionID == null);

        // Start Date
        // b3. Ask user the starting date
        GregorianCalendar startDate = null;
        do {
            scan.print("New starting date (format=YYYY-MM-DD):");
            String[] startDateStr = scan.nextLine().split("-");
            try {
                startDate = new GregorianCalendar(Integer.parseInt(startDateStr[0]),
                        Integer.parseInt(startDateStr[1]), Integer.parseInt(startDateStr[2]));
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                scan.println("Invalid input. Please try again using format YYYY-MM-DD");
            }
        } while (startDate == null);

        // Budget estimate
        // b4. Ask user the budget estimate
        Long budgetEstimate = null;
        do {
            scan.print("New budget Estimate:");
            String budgetEstimateStr = scan.nextLine();
            try {
                budgetEstimate = Long.parseLong(budgetEstimateStr);
            } catch (NumberFormatException e) {
                scan.println("Invalid input. Please try again.");
            }
        } while (budgetEstimate == null);

        // Lead developer
        // b5. Go to step a5
        // a5.
        scan.println("choose a lead developer.");
        Developer lead = (new GetUserOfExcactTypeCmd<>(Developer.class)).exec(scan, model, user);

        // Clone Project
        Project newProject = model.cloneProject(project, versionID, lead, startDate, budgetEstimate);

        // Print created project details
        scan.println("Project details:");
        scan.println(newProject.getDetails());

        return newProject;
    }
}
