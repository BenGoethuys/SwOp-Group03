package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Terminal {

    public Terminal(DataController con) throws IllegalArgumentException {
        if (con == null) {
            throw new IllegalArgumentException("Terminal does not allow a null-reference for DataController.");
        }
        this.con = con;
        this.scan = new TerminalScanner(System.in);
        this.parser = new CmdParser(this);
    }

    private DataController con;
    private CmdParser parser;
    private final TerminalScanner scan;
    private User user;

    /**
     * Get the {@link User} currently logged in as.
     *
     * @return The user currently logged in as.
     */
    public User getUser() {
        return this.user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("No null-reference allowed by the Terminal for User.");
        }
        this.user = user;
    }

    /**
     * Get the {@link DataController} currently in use.
     *
     * @return The currently used controller.
     */
    public DataController getController() {
        return this.con;
    }

    /**
     * Get the {@link TerminalScanner} currently in use.
     *
     * @return The currently used scanner.
     */
    public TerminalScanner getScanner() {
        return this.scan;
    }

    public void openView() {
        do {
            try {
                //Login
                new LoginCmd(this).exec(scan, con, null);
            } catch (CancelException ex) {
                //Abort received, ignored because we really need a user.
            }
        } while(user == null);
        System.out.println("");

        //Query
        String input;
        while (true) {
            try {
                input = scan.nextLine();
                parser.performCmd(scan, con, user, input);
            } catch (CancelException ex) {
                System.out.println("Cancelled. Execute a new command.");
                // aborted current cmd, ask user for new cmd -> do nothing
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Command cancelled. Execute a new command.");
            }
        }
    }

    public final static void clearConsole() {
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
