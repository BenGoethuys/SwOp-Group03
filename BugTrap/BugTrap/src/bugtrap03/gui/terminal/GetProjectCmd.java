/**
 * 
 */
package bugtrap03.gui.terminal;

import java.util.NoSuchElementException;

import bugtrap03.DataController;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;
import purecollections.PList;

/**
 *  //TODO write heading
 * 
 * @author Ben Goethuys
 *
 */
public class GetProjectCmd implements Cmd {

	/**
	 * 
	 */
	public GetProjectCmd() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * //TODO write heading
	 * @see bugtrap03.gui.terminal.Cmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.DataController, bugtrap03.usersystem.User)
	 */
	@Override
	public Project exec(TerminalScanner scan, DataController con, User user)
			throws PermissionException, CancelException {
		
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
        
		return proj;
	}

}
