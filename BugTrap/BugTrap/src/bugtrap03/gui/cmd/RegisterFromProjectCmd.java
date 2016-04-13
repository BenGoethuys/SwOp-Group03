package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author  Group 03
 */
public class RegisterFromProjectCmd implements Cmd{
    public RegisterFromProjectCmd(){}

    @Override
    public Subject exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        Project selectedProject = new GetProjectCmd().exec(scan, model, user);
        new RegisterFromASCmd(selectedProject).exec(scan, model, user);
        return selectedProject;
    }


}
