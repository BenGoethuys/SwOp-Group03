package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * @author Mathias
 *
 */
public class DeclareAchievedMilestoneCmd implements Cmd {

    /**
     * <p>
     * 1. The developer indicates that he wants to declare an achieved
     * milestone. <br>
     * 2. The system shows a list of projects. <br>
     * 3. The developer selects a project. <br>
     * 4. The system shows a list of subsystems of the selected project. <br>
     * 5. The developer selects a subsystem. <br>
     * <dd>5a. The developer indicates he wants to change the achieved milestone
     * of the entire project: The use case continues with step 6.</dd> <br>
     * 6. The system shows the currently achieved milestones and asks for a new
     * one. <br>
     * 7. The developer proposes a new achieved milestone. <br>
     * 8. The system updates the achieved milestone of the selected component.
     * If necessary, the system first recursively updates the achieved milestone
     * of all the subsystems that the component contains. <br>
     * <dd>8a. The new achieved milestone could not be assigned due to some
     * constraint: The system is restored and the use case has no effect. The
     * use case ends here.</dd>
     * 
     * @return
     */
    @Override
    public Project exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. The developer indicates that he wants to declare an achieved
        // milestone.
        // 2. The system shows a list of projects.
        // 3. The developer selects a project.
        Project proj = new GetProjectCmd().exec(scan, model, user);

        // 4. The system shows a list of subsystems of the selected project.
        // 5. The developer selects a subsystem.
        PList<Subsystem> subsysList = model.getAllSubsystems(proj);
        Subsystem subsys = new GetObjectOfListCmd<>(subsysList, (u -> u.getName()),
                ((u, input) -> u.getName().equals(input))).exec(scan, model, user);

        if (subsys == null) {
            throw new IllegalArgumentException("Cancelled command.");
        }

        // 6. The system shows the currently achieved milestones and asks for a
        // new one.
        scan.println("The currently achieved milestone: " + subsys.getMilestone().toString());
        scan.print("Enter a new milestone: (M1.2.3)  ");

        // 7. The developer proposes a new achieved milestone.
        String bugReportMilestone = scan.nextLine(); // TODO

        // 8. The system updates the achieved milestone of the selected
        // component. If necessary, the system first recursively updates the
        // achieved milestone of all the subsystems that the component contains.

        // TODO COMPLETE scenario

        return proj;
    }

}
