package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import java.util.InputMismatchException;

/**
 * This command asks a Long from the user.
 *
 * @author Group 03
 */
public class GetLongCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan The scanner used to interact with the person.
     * @param dummy2 Doesn't matter
     * @param dummy3 Doesn't matter
     * @return The long given by the user
     * @throws PermissionException When the user does not have sufficient  permissions.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan is a null reference.
     */
    @Override
    public Long exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws PermissionException, CancelException, IllegalArgumentException {
        if(scan == null) {
            throw new IllegalArgumentException("scan musn't be null.");
        }
        scan.print("Give number: ");
        Long longNb = null;
        do {
            try {
                longNb = scan.nextLong();
            } catch (InputMismatchException ex) {
                scan.println("Invalid input, please enter a number");
            }
        } while (longNb == null);
        return longNb;
    }
}
