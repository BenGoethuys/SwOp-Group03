package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import java.util.InputMismatchException;

/**
 * This command asks an int from the user Created by Ben Goethuys on 04/03/2016.
 */
public class GetIntCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan The scanner used to interact with the person.
     * @param dummy2 Doesn't matter
     * @param dummy3 Doesn't matter
     * @return The given integer
     * @throws PermissionException Never
     * @throws CancelException When the users wants to abort the current cmd
     */
    @Override
    public Integer exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws CancelException {
        scan.print("Give number: ");
        Integer integer = null;
        do {
            try {
                integer = scan.nextInt();
            } catch (InputMismatchException ex) {
                scan.println("Invalid input, please enter a number");
            }
        } while (integer == null);
        return integer;
    }
}
