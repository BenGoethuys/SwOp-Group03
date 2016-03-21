package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author Mathias
 *
 */
public class DeclareAchievedMilestoneCmd implements Cmd {

    /**
     * <p>
     * <br>
     * 1. The developer indicates that he wants to declare an achieved
     * milestone. <br>
     * 2. The system shows a list of projects. <br>
     * 3. The developer selects a project. <br>
     * 4. The system shows a list of subsystems of the selected project. <br>
     * 5. The developer selects a subsystem. <br>
     * 5a. The developer indicates he wants to change the achieved milestone of
     * the entire project: The use case continues with step 6. <br>
     * 6. The system shows the currently achieved milestones and asks for a new
     * one. <br>
     * 7. The developer proposes a new achieved milestone. <br>
     * 8. The system updates the achieved milestone of the selected component.
     * If necessary, the system first recursively updates the achieved milestone
     * of all the subsystems that the component contains. <br>
     * 8a. The new achieved milestone could not be assigned due to some
     * constraint: The system is restored and the use case has no effect. The
     * use case ends here.
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {

        return null;
    }

}
