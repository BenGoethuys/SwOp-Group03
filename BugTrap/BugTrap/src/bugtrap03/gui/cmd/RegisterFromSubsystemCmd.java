package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Subject;
import bugtrap03.gui.cmd.general.*;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterFromSubsystemCmd implements Cmd {
    public RegisterFromSubsystemCmd(){
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("alltags",1);
        this.subsriptionTypes.put("specictags",2);
        this.subsriptionTypes.put("comment",3);
        this.subsriptionTypes.put("creation",4);
    }

    private HashMap<String, Integer> subsriptionTypes;

    @Override
    public Subject exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        Subsystem selectedSubsys = new GetSubsystemCmd().exec(scan, model, user);
        new RegisterFromASCmd(selectedSubsys).exec(scan, model, user);
        return selectedSubsys;
    }
}
