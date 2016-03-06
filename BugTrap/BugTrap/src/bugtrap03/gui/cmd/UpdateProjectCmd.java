package bugtrap03.gui.cmd;

import java.util.GregorianCalendar;
import java.util.Scanner;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command represents the update bug report use case scenario
 * @author Ben Goethuys
 */
public class UpdateProjectCmd implements Cmd {

    /**
     * Execute the update project scenario.
     * <p>
     * <br>
     * 1. The administrator indicates he wants to update a project. <br>
     * 2. The system shows a list of all projects. <br>
     * 3. The administrator selects a project. <br>
     * 4. The system shows a form to update the project details: name,
     * description, starting date and budget estimate. <br>
     * 5. The administrator modifies the details as he sees fit. <br>
     * 6. The system updates the project.
     *
     * @param scan  The {@link Scanner} trough which to ask the questions.
     * @param model The model to use to access the model.
     * @param user  The user who wants to execute this {@link Cmd}.
     * @return The new updated project
     * @throws PermissionException When the user does not have sufficient permissions to update
     *                             a project.
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException {

        // Get project
        Project proj = new GetProjectCmd().exec(scan, model, user);

        // update name:
        String newName = null;
        scan.println("Give new name: (leave blank for old name)");
        newName = scan.nextLine();
        if (newName.equalsIgnoreCase("")) {
            newName = proj.getName();
            scan.println("The name was not updated.");
        }

        // update description:
        String newDesc = null;
        scan.println("Give new description: (leave blank for old description)");
        newDesc = scan.nextLine();
        if (newDesc.equalsIgnoreCase("")) {
            scan.println("Description not updated.");
            newDesc = proj.getDescription();
        }

        // update start date
        GregorianCalendar projStartDate = null;
        String dateStr;
        String[] projDateStr;
        do {
            scan.println("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
            dateStr = scan.nextLine();
            if (dateStr.equalsIgnoreCase("")) {
                projStartDate = proj.getStartDate();
                scan.println("Start date not updated.");
            } else {
                projDateStr = dateStr.split("-");
                try {
                    projStartDate = new GregorianCalendar(Integer.parseInt(projDateStr[0]),
                            Integer.parseInt(projDateStr[1]), Integer.parseInt(projDateStr[2]));
                    proj.setStartDate(projStartDate);
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    scan.println("Invalid input. Please try again");
                }
            }
        } while (projStartDate == null);

        // update budget estimate
        Long projBudgetEstimate = null;
        String str;
        do {
            scan.println("Give new project budget estimate:");
            str = scan.nextLine();
            if (str.equalsIgnoreCase("")) {
                projBudgetEstimate = proj.getBudgetEstimate();
                scan.println("Buget estimate not updated.");
            } else {
                try {
                    projBudgetEstimate = Long.parseLong(str);
                    proj.setBudgetEstimate(projBudgetEstimate);
                } catch (IllegalArgumentException e) {
                    scan.println("Invalid input.");
                }
            }
        } while (projBudgetEstimate == null);

        scan.println("Project updated.");
        model.updateProject(proj, user, newName, newDesc, projStartDate, projBudgetEstimate);
        return proj;
    }

}
