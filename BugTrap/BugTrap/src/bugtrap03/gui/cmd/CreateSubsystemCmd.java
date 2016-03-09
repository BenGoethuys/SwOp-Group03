package bugtrap03.gui.cmd;

import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import purecollections.PList;

/**
 * This command represents the use case for creating a subsystem in the system
 *
 * @author Group 03
 */
public class CreateSubsystemCmd implements Cmd {
    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The administrator indicates he wants to create a new subsystem.
     * <br> 2. The system shows a list of projects and subsystems.
     * <br> 3. The administrator selects the project or subsystem that the new subsystem will be part of.
     * <br> 4. The system shows the subsystem creation form.
     * <br> 5. The administrator enters the subsystem details: name and description
     * <br> 6. The system creates the subsystem.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The newly created subsystem.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Subsystem exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        // show all projects
        PList<AbstractSystem> list = model.getAllProjectsAndSubsystems();

        AbstractSystem system = new GetObjectOfListCmd<>(list, (u -> u.getName()), ((u, input) -> u.getName().equals(input)))
                .exec(scan, model, user);

        scan.println("You have chosen:");
        scan.println(system.getName());

        //Project name
        scan.print("Subsystem name:");
        String sysName = scan.nextLine();

        //Project description
        scan.print("Subsystem description:");
        String sysDesc = scan.nextLine();

        //Create subsystem
        Subsystem subsystem = model.createSubsystem(user, system, sysName, sysDesc);
        scan.println("Created subsystem " + subsystem.getName());
        return subsystem;
    }
}
