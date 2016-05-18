package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This command provides a UI interaction to select a subsystem.
 *
 * @author Group 03
 */
public class GetSubsystemCmd implements Cmd<Subsystem> {

    /**
     * Execute the command and get a subsystem chosen by the user trough interaction.
     *
     * @param scan   The scanner used to interact with the user
     * @param model  The model used to access the system data.
     * @param dummy1 dummy
     * @return The chosen subsystem.
     * @throws CancelException          When the user aborted the cmd
     * @throws IllegalArgumentException When scan or model == null
     */
    @Override
    public Subsystem exec(TerminalScanner scan, DataModel model, User dummy1)
            throws CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan and model musn't be null.");
        }
        Subsystem selectedSub;
        do {
            Project selectedProj = new GetProjectCmd().exec(scan, model, null);
            scan.println("Select subsystem.");
            PList<Subsystem> allSubsystems = model.getAllSubsystems(selectedProj);
            selectedSub = new GetObjectOfListCmd<>(allSubsystems, (u -> u.getName()),
                    ((u, input) -> u.getName().equalsIgnoreCase(input))).exec(scan, model, null);
        } while (selectedSub == null);
        scan.println("You selected: " + selectedSub.getName());
        return selectedSub;
    }
}
