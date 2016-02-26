package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.usersystem.User;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public interface Cmd {
    
    /**
     * Execute this command and possibly return a result.
     * @param controller The controller used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     */
    public Object exec(Scanner scan, DataController controller, User user);
    
}
