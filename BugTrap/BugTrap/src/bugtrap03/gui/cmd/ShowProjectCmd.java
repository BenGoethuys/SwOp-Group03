package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * This command represents the show project use case scenario.
 *
 * @author Group 03
 */
public class ShowProjectCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The user indicates he wants to take a look at some project.
     * <br> 2. The system shows a list of all projects.
     * <br> 3. The user selects a project.
     * <br> 4. The system shows a detailed overview of the selected project and all its subsystems.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return The project chosen.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException If scan, model or user is null
     * 
     * @see GetProjectCmd#exec(TerminalScanner, DataModel, User)
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user) throws CancelException, IllegalArgumentException {
        if(scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        //1. The user indicates he wants to take a look at some project.
        //2. Shows a list of all projects.
        //3. The user selects a project.
        Project proj = new GetProjectCmd().exec(scan, model, user);

        //4. Show a detailed overview of the selected project and all its subsystems.
        scan.println(proj.getDetails());
        return proj;
    }

}
