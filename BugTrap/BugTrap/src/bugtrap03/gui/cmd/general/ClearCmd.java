package bugtrap03.gui.cmd.general;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;

import java.io.IOException;

/**
 * Created by Ben on 29/02/2016.
 */
public class ClearCmd implements Cmd {

    /**
     * Attempt to clear the console screen.
     *
     * @param dummy1
     * @param dummy2
     * @param dummy3
     * @return null
     * @throws CancelException When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner dummy1, DataModel dummy2, User dummy3) {
        this.clearConsole();
        return null;
    }

    /**
     * Attempt to clear the console screen.
     */
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
