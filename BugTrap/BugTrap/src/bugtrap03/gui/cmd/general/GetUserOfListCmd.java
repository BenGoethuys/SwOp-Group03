package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.NoSuchElementException;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class GetUserOfListCmd<U extends User> implements Cmd {

    /**
     * Create a GetUserOfListCmd that will use the given list of users as the
     * options in the select process.
     *
     * @param listOfUsers The list of users to pick from. When null is passed an
     * empty list will be used.
     *
     * @see GetUserOfListCmd#exec(TerminalScanner, DataModel, User)
     */
    public GetUserOfListCmd(PList<U> listOfUsers) {
        if (listOfUsers == null) {
            this.listOfUsers = PList.<U>empty();
        } else {
            this.listOfUsers = listOfUsers;
        }
    }

    private final PList<U> listOfUsers;

    /**
     * Create a scenario where the person will chose a User from the list passed
     * to the constructor.
     *
     * @param scan Used to interact with the person who selects the option.
     * @param dummy2 Dummy
     * @param dummy3 Dummy
     * @return The selected option, can be null if the list of options was empty.
     * @throws CancelException When the person has indicated to abort the cmd.
     */
    @Override
    public U exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws CancelException {
        if (listOfUsers.isEmpty()) {
            scan.println("No users of this type found.");
            return null;
        }

        scan.println("Available options:");
        for (int i = 0; i < listOfUsers.size(); i++) {
            scan.println(i + ". " + listOfUsers.get(i).getUsername());
        }

        //Retrieve & process user input.
        U user = null;
        do {
            scan.println("I choose: ");
            if (scan.hasNextInt()) { //by index
                int index = scan.nextInt();//input
                if (index >= 0 && index < listOfUsers.size()) {
                    user = listOfUsers.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { //by username
                String input = scan.nextLine(); //input
                try {
                    user = listOfUsers.parallelStream().filter(u -> u.getUsername().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while (user == null);

        return user;
    }

}
