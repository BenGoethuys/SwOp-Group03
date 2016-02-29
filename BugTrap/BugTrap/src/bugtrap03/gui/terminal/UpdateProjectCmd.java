/**
 *
 */
package bugtrap03.gui.terminal;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bugtrap03.DataController;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.permission.UserPerm;
import bugtrap03.usersystem.User;
import purecollections.PList;

/**
 * @author Ben
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
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param con  The controller to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The new updated project
     * @throws PermissionException When the user does not have sufficient permissions to update
     *                             a project.
     */
    @Override
    public Project exec(TerminalScanner scan, DataController con, User user)
            throws PermissionException, CancelException {
        //TODO: Sure? Maybe we should create a method in model to updateProject(...para..)
        //then this method checks for permissions and updates everything at once.
        //It would be more in line with CreateProjectCmd.
        // check needed permission
        if (!user.hasPermission(UserPerm.UPDATE_PROJ)) {
            throw new PermissionException("You dont have the needed permission to update a project!");
        }

        // Get project
        Project proj = new GetProjectCmd().exec(scan, con, user);

        // update name:
        String newName = null;
        System.out.println("Give new name: (leave blank for old name)");
        newName = scan.nextLine();
        if (newName.equalsIgnoreCase("")) {
            newName = proj.getName();
            System.out.println("The name was not updated.");
        }

        // update description:
        String newDesc = null;
        System.out.println("Give new description: (leave blank for old description)");
        newDesc = scan.nextLine();
        if (newDesc.equalsIgnoreCase("")) {
            System.out.println("Description not updated.");
            newDesc = proj.getDescription();
        }

        // update start date
        GregorianCalendar projStartDate;
        String dateStr;
        String[] projDateStr;
        boolean done = false;
        done = false;
        do {
            System.out.println("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
            dateStr = scan.nextLine();
            if (dateStr.equalsIgnoreCase("")) {
                projStartDate = proj.getStartDate();
                done = true;
                System.out.println("Start date not updated.");
            } else {
                projDateStr = dateStr.split("-");
                try {
                    projStartDate = new GregorianCalendar(Integer.parseInt(projDateStr[0]),
                            Integer.parseInt(projDateStr[1]), Integer.parseInt(projDateStr[2]));
                    proj.setStartDate(projStartDate);
                    done = true;
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    System.out.println("Invalid input. Please try again");
                }
            }
        } while (!done);

        // update budget estimate
        Long projBudgetEstimate;
        String str;
        done = false;
        do {
            System.out.println("Give new project budget estimate:");
            str = scan.nextLine();
            if (str.equalsIgnoreCase("")) {
                projBudgetEstimate = proj.getBudgetEstimate();
                done = true;
                System.out.println("Buget estimate not updated.");
            } else {
                try {
                    projBudgetEstimate = Long.parseLong(str);
                    proj.setBudgetEstimate(projBudgetEstimate);
                    done = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input.");
                }
            }
        } while (!done);

        System.out.println("Project updated.");
        return proj;
    }

}
