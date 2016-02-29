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
 *
 */
public class UpdateProjectCmd implements Cmd {

	/**
	 * Execute the update project scenario.
	 * 
	 * <br>
	 * 1. The administrator indicates he wants to update a project. <br>
	 * 2. The system shows a list of all projects. <br>
	 * 3. The administrator selects a project. <br>
	 * 4. The system shows a form to update the project details: name,
	 * description, starting date and budget estimate. <br>
	 * 5. The administrator modifies the details as he sees fit. <br>
	 * 6. The system updates the project.
	 * 
	 * @param scan
	 *            The {@link Scanner} trough which to ask the questions.
	 * @param con
	 *            The controller to use to access the model.
	 * @param user
	 *            The user who wants to execute this {@link Cmd}.
	 * 
	 * @return The new updated project
	 * @throws PermissionException
	 *             When the user does not have sufficient permissions to update
	 *             a project.
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

		// show all projects
		PList<Project> projectList = con.getProjectList();
		System.out.println("Available projects:");
		for (int i = 0; i < projectList.size(); i++) {
			System.out.println(i + ". " + projectList.get(i).getName());
		}

		// Retrieve & process user input.
		Project proj = null;
		do {
			System.out.print("I chose: ");
			if (scan.hasNextInt()) { // by index
				int index = scan.nextInt();// input
				if (index >= 0 && index < projectList.size()) {
					proj = projectList.get(index);
				} else {
					System.out.println("Invalid input.");
				}
			} else { // by name
				String input = scan.nextLine(); // input
				try {
					proj = projectList.parallelStream().filter(u -> u.getName().equals(input)).findFirst().get();
				} catch (NoSuchElementException ex) {
					System.out.println("Invalid input.");
				}
			}
		} while (proj == null);
		System.out.println("You have chosen:");
		System.out.println(proj.getDetails());

		boolean done = false;

		// update name:
		String newName = null;
		done = false;
		do {
			System.out.println("Give new name: (leave blank for old name)");
			newName = scan.nextLine();
			if (newName.equalsIgnoreCase("")) {
				done = true;
				System.out.println("The name was not updated.");
			} else {
				try {
					proj.setName(newName);
					done = true;
					System.out.println("The new name of the project is: " + proj.getName());
				} catch (IllegalArgumentException ex) {
					System.out.println("The given name was invalid, please try again.");
				}
			}
		} while (!done);

		// update description:
		String newDesc = null;
		done = false;
		do {
			System.out.println("Give new description: (leave blank for old description)");
			newDesc = scan.nextLine();
			if (newDesc.equalsIgnoreCase("")) {
				done = true;
				System.out.println("Description not updated.");
			} else {
				try {
					proj.setDescription(newDesc);
					done = true;
					System.out.println("The new description of the project is: " + proj.getDescription());
				} catch (IllegalArgumentException ex) {
					System.out.println("The given description was invalid, please try again.");
				}
			}
		} while (!done);

		// update start date
		GregorianCalendar projStartDate = null;
		String dateStr;
		String[] projDateStr;
		done = false;
		do {
			System.out.println("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
			dateStr = scan.nextLine();
			if (dateStr.equalsIgnoreCase("")) {
				done = true;
				System.out.println("Start date not updated.");
			} else {
				projDateStr = dateStr.split("-");
				try {
					projStartDate = new GregorianCalendar(Integer.parseInt(projDateStr[0]),
							Integer.parseInt(projDateStr[1]), Integer.parseInt(projDateStr[2]));
					proj.setCreationDate(projStartDate);
					done = true;
					//TODO find better string rep for creationdate!
					System.out.println("The new creation date of the prject: " + proj.getStartDate().toString());
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
					System.out.println("Invalid input. Please try again");
				}
			}
		} while (!done);

		// update budget estimate
		Integer projBudgetEstimate = null;
		String str;
		done = false;
		do {
			System.out.println("Give new project budget estimate:");
			str = scan.nextLine();
			if (str.equalsIgnoreCase("")) {
				done = true;
				System.out.println("Buget estimate not updated.");
			} else {
				try {
					projBudgetEstimate = Integer.parseInt(str);
					proj.setBudgetEstimate(projBudgetEstimate);
					done = true;
					System.out.println("The new budget estimate for the system: "+proj.getBudgetEstimate());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid input.");
				}
			}
		} while (!done);
		
		System.out.println("Project updated.");
		return proj;
	}

}
