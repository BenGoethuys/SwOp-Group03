package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.usersystem.User;
import java.util.Scanner;

/**
 *
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
    public Object exec(Scanner sdummy, DataController cdummy, User udummy) {
        System.out.println("Invalid command.");
        return null;
    }
    
}
