package bugtrap03.gui.cmd.general;

import java.util.NoSuchElementException;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import purecollections.PList;

/**
 * //TODO write heading
 *
 * @author Ben Goethuys
 */
public class GetProjectCmd implements Cmd {

    /**
     * Get a Project chosen by the person by presenting him a list of all
     * projects.
     * <p>
     * <p>
     * <br> 1. The system shows a list of existing projects.
     * <br> 2. The person selects an existing project of the list.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The chosen project.
     * @throws CancelException When the users wants to abort the current cmd
     * @see Cmd#exec(TerminalScanner, DataModel, User)
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user) throws CancelException {
        // show all projects
        PList<Project> projectList = model.getProjectList();
        scan.println("Available projects:");
        for (int i = 0; i < projectList.size(); i++) {
            scan.println(i + ". " + projectList.get(i).getName());
        }

        // Retrieve & process user input.
        Project proj = null;
        do {
            scan.print("I choose: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < projectList.size()) {
                    proj = projectList.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                try {
                    proj = projectList.parallelStream().filter(u -> u.getName().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while (proj == null);
        scan.println("You have chosen:");
        scan.println(proj.getDetails());

        return proj;
    }

}
