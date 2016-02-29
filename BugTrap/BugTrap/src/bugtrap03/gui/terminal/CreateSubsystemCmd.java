package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;
import purecollections.PList;

import java.util.NoSuchElementException;

/**
 * Created by Ben on 29/02/2016.
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
     * @param scan       The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user       The {@link User} who wants to executes this command.
     *
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     *
     * @return null if there is no result specified.
     */
    @Override
    public Subsystem exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {

        // show all projects
        PList<AbstractSystem> list = model.getAllProjectsAndSubsystems();
        System.out.println("Available projects and subsytems:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i).getName());
        }

        // Retrieve & process user input.
        AbstractSystem system = null;
        do {
            System.out.print("I chose: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < list.size()) {
                    system = list.get(index);
                } else {
                    System.out.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                try {
                    system = list.parallelStream().filter(u -> u.getName().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    System.out.println("Invalid input.");
                }
            }
        } while (system == null);
        System.out.println("You have chosen:");
        System.out.println(system.getName());

        //Project name
        System.out.print("Subsystem name:");
        String sysName = scan.nextLine();

        //Project description
        System.out.print("Subsystem description:");
        String sysDesc = scan.nextLine();

        //Create subsystem
        Subsystem subsytem = model.createSubsystem(user, system, sysName, sysDesc);
        System.out.println("Create subsystem.");
        return subsytem;
    }
}
