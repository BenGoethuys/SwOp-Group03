package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterFromSubsystemCmd implements Cmd<Mailbox> {
    public RegisterFromSubsystemCmd(){
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("alltags",1);
        this.subsriptionTypes.put("specictags",2);
        this.subsriptionTypes.put("comment",3);
        this.subsriptionTypes.put("creation",4);
    }

    private HashMap<String, Integer> subsriptionTypes;

    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        Subsystem selectedSubsys = new GetSubsystemCmd().exec(scan, model, user);
        Mailbox newMailbox = new RegisterFromASCmd(selectedSubsys).exec(scan, model, user);
        return newMailbox;
    }
}
