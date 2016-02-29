package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.usersystem.User;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class Terminal {

    public Terminal(DataModel model) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("Terminal does not allow a null-reference for DataController.");
        }
        this.model = model;
        this.scan = new TerminalScanner(System.in);
        this.parser = new CmdParser(this);
    }

    private DataModel model;
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
     * Get the {@link DataModel} currently in use.
     *
     * @return The currently used model.
     */
    public DataModel getController() {
        return this.model;
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
                new LoginCmd(this).exec(scan, model, null);
            } catch (CancelException ex) {
                //Abort received, ignored because we really need a user.
            }
        } while(user == null);
        System.out.println("");

        //Query
        String input;
        while (true) {
        	System.out.println("Give new command");
            try {
                input = scan.nextLine().toLowerCase();
                parser.performCmd(scan, model, user, input);
            } catch (CancelException ex) {
                System.out.println("Cancelled. Execute a new command.");
                // aborted current cmd, ask user for new cmd -> do nothing
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Command cancelled. Execute a new command.");
            }
        }
    }

}
