package bugtrap03.gui.terminal;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.CmdParser;
import bugtrap03.gui.cmd.LoginCmd;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;

/**
 * This class represents the terminal of the system
 *
 * @author Group 03
 */
public class Terminal {

    /**
     * Create a new {@link Terminal} which can be used to start the terminal interaction.
     * @param model The data model used to access the data. Changes will be reflected.
     * @throws IllegalArgumentException When model is a null reference.
     * @see Terminal#openView()
     */
    public Terminal(DataModel model) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("Terminal does not allow a null-reference for DataController.");
        }
        this.model = model;
        this.scan = new TerminalScanner(System.in, System.out);
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
     * Set the {@link User} currently logged in.
     * 
     * @param user The new logged in user.
     * @throws IllegalArgumentException When the user given was a null reference.
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
    public DataModel getModel() {
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

    /**
     * Start the interaction with this {@link Terminal}.
     * This function does not end.
     */
    public void openView() {
        do {
            try {
                //Login
                new LoginCmd(this).exec(scan, model, null);
            } catch (CancelException ex) {
                //Abort received, ignored because we really need a user.
            }
        } while (user == null);
        scan.println("");
        scan.println("Type abort during an operation to abort.");
        scan.println("Type help to get a list of possible operations.");
        scan.println("");

        //Query
        String input;
        while (true) {
            scan.println("Give new command: (type help for a list.)");
            try {
                input = scan.nextLine().toLowerCase();
                parser.performCmd(scan, model, user, input);
            } catch (CancelException ex) {
                scan.println("Cancelled. Execute a new command.");
                // aborted current cmd, ask user for new cmd -> do nothing
            } catch (Exception ex) {
                scan.println(ex.getMessage());
                scan.println("Command cancelled. Execute a new command.");
            }
        }
    }

}
