package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.GetSubsystemCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This command represents the use case for merging 2 subsystems
 *
 * @author Group 03
 */
public class MergeSubsystemsCmd implements Cmd<Subsystem> {

    /**
     * Execute use case 4.8, merge Subsystem and return the result.
     *
     * 4.8 Use Case: Merge Subsystems
     * <p>
     * <br>1. The administrator indicates he wants to merge two subsystems.
     * <br>2. The system shows a list of projects.
     * <br>3. The administrator selects a project.
     * <br>4. The system shows a list of subsystems of the selected project.
     * <br>5. The administrator selects a first subsystem.
     * <br>6. The system shows a list of subsystems compatible with the first one.
     * A compatible subsystem is a child, a parent or a sibling of the first
     * selected subsystem in the tree of subsystems of the selected project.
     * <br>7. The administrator selects a second subsystem.
     * <br>8. The system asks for a name and description for the new subsystem.
     * <br>9. The administrator enters a name and description.
     * <br>10. The system creates a new subsystem with the lowest milestone of the
     * two original subsystems. The bug reports and subsystems that are part
     * of the original two subsystems are migrated to the new subsystem. The
     * two original subsystems are removed.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     *
     * @return  The merged subsystem
     *
     * @throws PermissionException      When the user does not have sufficient permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalStateException    When the subject is in an illegal state to perform this cmd.
     * @throws IllegalArgumentException When any of the arguments is null and
     *                                  shouldn't be or when in the scenario a chosen option conflicted.
     */
    @Override
    public Subsystem exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException,
            IllegalArgumentException, IllegalStateException {

        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user passed to SplitSubsystemCmd shouldn't be a null reference.");
        }
        //1. The administrator indicates he wants to merge two subsystems.
        scan.println("Merging subsystems.");
        //2. The system shows a list of projects.
        //3. The administrator selects a project.
        //4. The system shows a list of subsystems of the selected project.
        //5. The administrator selects a first subsystem.
        GetSubsystemCmd cmd = new GetSubsystemCmd();
        Subsystem sub1 = cmd.exec(scan, model, null);

        // 6. The system shows a list of subsystems compatible with the first one.
        // A compatible subsystem is a child, a parent or a sibling of the first
        // selected subsystem in the tree of subsystems of the selected project.
        // 7. The administrator selects a second subsystem.
        Subsystem sub2 = this.getCompatibleMergeCandidate(sub1, scan, model);

        // 8. The system asks for a name and description for the new subsystem.
        // 9. The administrator enters a name and description.
        scan.println("Please enter the new name:");
        String name = scan.nextLine();
        scan.print("Please enter the new description:");
        String description = scan.nextLine();

        // 10. The system creates a new subsystem with the lowest milestone of the
        // two original subsystems. The bug reports and subsystems that are part
        // of the original two subsystems are migrated to the new subsystem. The
        // two original subsystems are removed.
        Subsystem result = model.mergeSubsystems(user, sub1, sub2, name, description);

        // print result
        scan.println(result.getDetails());

        return result;
    }

    /**
     * This method asks the user to select a compatible subsystem
     *
     * @param subsystem The first selected subsystem
     * @param scanner   The scanner of this cmd
     *
     * @return  The selected compatible subsystem
     */
    private Subsystem getCompatibleMergeCandidate(Subsystem subsystem, TerminalScanner scanner, DataModel model)
            throws CancelException {
        PList<Subsystem> compatList = subsystem.getCompatibleMergeSubs();
        scanner.println("Please select a subsystem to merge with the first");
        return new GetObjectOfListCmd<>(compatList, (u -> u.getName()),
                ((u, input) -> u.getName().equalsIgnoreCase(input))).exec(scanner, model, null);
    }
}
