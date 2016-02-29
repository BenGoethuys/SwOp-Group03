package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;

import java.io.IOException;

/**
 * Created by Ben on 29/02/2016.
 */
public class ClearCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     *
     * @param scan       The scanner used to interact with the person.
     * @param controller The controller used for model access.
     * @param user       The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner scan, DataController controller, User user) throws PermissionException, CancelException {
        this.clearConsole();
        return null;
    }

    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else { //ANSI & Pray :P
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException | InterruptedException e) {
            //  Handle any exceptions.
        }
    }
}
