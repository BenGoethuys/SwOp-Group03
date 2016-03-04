package bugtrap03.gui.cmd;

import java.util.Scanner;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;

/**
 * @author group 03
 */
public class DeleteProjectCmd implements Cmd {

    /**
     * Execute the update project scenario.
     * <p>
     * <br>
     * 1. The administrator indicates he wants to delete a project. <br>
     * 2. The system shows a list of all projects. <br>
     * 3. The administrator selects a project. <br>
     * 4. The system deletes a project and recursively all subsystems that are
     * part of the project. All bug reports fore those subsystem are also
     * removed from BugTrap.
     *
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param model The model to use to access the model.
     * @param user The user who wants to execute this {@link Cmd}.
     * @return The deleted project
     * @throws PermissionException When the user does not have sufficient
     *             permissions to update a project.
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException {

        // Get project
        Project proj = new GetProjectCmd().exec(scan, model, user);

        // Delete the project
        model.deleteProject(user, proj);

        scan.println("Project deleted.");
        return proj;
    }
}