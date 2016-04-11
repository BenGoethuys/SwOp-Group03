/**
 * 
 */
package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author group 03
 *
 */
public class UnregisterForNotificationsCmd implements Cmd{

    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        return null;
    }

}
