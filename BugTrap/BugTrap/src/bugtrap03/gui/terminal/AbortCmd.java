package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;

import java.util.Scanner;

/**
 * This class represents and abort cmd from the user
 * Created by Ben Goethuys on 28/02/2016.
 */
public class AbortCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan
     * @param controller The controller used for model access.
     * @param user       The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     */
    @Override
    public Object exec(Scanner scan, DataController controller, User user) throws PermissionException, CancelException {
        throw new CancelException("User wants to abort current cmd");
    }

}
