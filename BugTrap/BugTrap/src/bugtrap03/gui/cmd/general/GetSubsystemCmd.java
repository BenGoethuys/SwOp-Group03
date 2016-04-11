package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This command provides a GUI interaction to select a subsystem.
 * @author Group 03
 */
public class GetSubsystemCmd implements Cmd {
    @Override
    public Subsystem exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        Project selectedProj = new GetProjectCmd().exec(scan, model, user);
        scan.println("Select subsystem.");
        PList<Subsystem> allSubsystems = model.getAllSubsystems(selectedProj);
        Subsystem selectedSub = new GetObjectOfListCmd<>(allSubsystems, (u -> u.getName()),
                ((u, input) -> u.getName().equalsIgnoreCase(input)))
                .exec(scan, model, user);
        return selectedSub;
    }
}
