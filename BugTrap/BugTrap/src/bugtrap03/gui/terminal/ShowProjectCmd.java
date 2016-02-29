package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;

/**
 * Created by Ben Goethuys on 29/02/2016.
 */
public class ShowProjectCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The user indicates he wants to take a look at some project.
     * <br> 2. The system shows a list of all projects.
     * <br> 3. The user selects a project.
     * <br> 4. The system shows a detailed overview of the selected project and all
     * its subsystems.
     *
     * @param scan       The scanner used to interact with the person.
     * @param controller The controller used for model access.
     * @param user       The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner scan, DataController controller, User user) throws CancelException {
        Project proj = new GetProjectCmd().exec(scan, controller, user);
        System.out.println(proj.getDetails());
        return null;
    }

}
