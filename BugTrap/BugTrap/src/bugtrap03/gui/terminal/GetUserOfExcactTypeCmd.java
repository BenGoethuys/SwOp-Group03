package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.usersystem.User;
import java.util.NoSuchElementException;
import java.util.Scanner;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class GetUserOfExcactTypeCmd<U extends User> implements Cmd {

    /**
     * Create a 'chose user' scenario wherein the person can chose
     * a certain user from a list of users from a specific class.
     * @param classType The type of the users to chose from. (exclude subclasses).
     * @throws IllegalArgumentException When a non-null reference was given.
     */
    public GetUserOfExcactTypeCmd(Class<U> classType) throws IllegalArgumentException {
        if (classType == null) {
            throw new IllegalArgumentException("GetUserOfExactTypeCmd requires a non-null reference as classType.");
        }

        this.classType = classType;
    }

    private Class<U> classType;

    /**
     * Get the user the person chose. This asks the person which
     * user to chose as by presenting him a list of users of the class,
     * classType (classType, subclasses excluded).
     *
     * @param <U> extends User, The type of the users to chose from.
     *
     * @param scan The {@link Scanner} used to interact with the person.
     * @param model The model used to get access to the model.
     * @param classType The class type of the possible users to chose from.
     * (classType, excludes subclass).
     * @return The chosen user. Null if there was no option of that type.
     * 
     * @throws CancelException When the cancel operation was executed.
     */
    @Override
    public U exec(TerminalScanner scan, DataModel model, User dummy) throws CancelException {
        //Print available user options of given type
        PList<U> usersOfType = model.getUserListOfExactType(classType);

        if (usersOfType.isEmpty()) {
            System.out.println("No users of this type found.");
            return null;
        }

        System.out.println("Available options:");
        for (int i = 0; i < usersOfType.size(); i++) {
            System.out.println(i + ". " + usersOfType.get(i).getUsername());
        }

        //Retrieve & process user input.
        U user = null;
        do {
            System.out.print("I chose: ");
            if (scan.hasNextInt()) { //by index
                int index = scan.nextInt();//input
                if (index >= 0 && index < usersOfType.size()) {
                    user = usersOfType.get(index);
                } else {
                    System.out.println("Invalid input.");
                }
            } else { //by username
                String input = scan.nextLine(); //input
                try {
                    user = usersOfType.parallelStream().filter(u -> u.getUsername().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    System.out.println("Invalid input.");
                }
            }
        } while (user == null);

        return user;
    }

}
