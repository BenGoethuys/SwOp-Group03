package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Admin
 */
public class InvalidCmd implements Cmd {

    /**
     * Inform the user of the invalid command.
     *
     * @param sdummy
     * @param cdummy
     * @param udummy
     * @return A null reference.
     */
    @Override
    public Object exec(TerminalScanner sdummy, DataModel cdummy, User udummy) {
        System.out.println("Invalid command.");
        return null;
    }

}
