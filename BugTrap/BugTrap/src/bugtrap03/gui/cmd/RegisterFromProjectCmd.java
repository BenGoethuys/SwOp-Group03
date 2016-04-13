package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author  Group 03
 */
public class RegisterFromProjectCmd implements Cmd<Mailbox>{
    public RegisterFromProjectCmd(){}

    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        Project selectedProject = new GetProjectCmd().exec(scan, model, user);
        Mailbox newMailbox = new RegisterFromASCmd(selectedProject).exec(scan, model, user);
        return newMailbox;
    }


}
