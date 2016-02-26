package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.usersystem.Administrator;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import bugtrap03.usersystem.User;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class LoginCmd implements Cmd {

    public LoginCmd() {
        initLoginInfo();
    }

    private ArrayList<SimpleEntry<String, Class<? extends User>>> classList;
    private HashMap<String, Class<? extends User>> optionMap;

    /**
     * Execute the login scenario.
     * <br> 1. Ask the person which type to login as.
     * <br> 2. Show the person all users of that type
     * <br> 3. Ask the person which user to login as.
     * <br> 4. Welcome the user.
     *
     * @param scan The {@link Scanner} trough which to ask the questions.
     * @param controller The controller to use to access the model.
     * @param dummy Dummy, as the person isn't a specific user yet. Use
     * whatever.
     * @return The user chosen by the person to login as.
     */
    @Override
    public Object exec(Scanner scan, DataController controller, User dummy) {
        //Ask which type to login as.
        Class<? extends User> classType = getWantedUserType(scan);
        //Ask which user to login as.
        User user = getWantedUserOfType(scan, controller, classType);

        //Welcome user.
        System.out.println("Welcome " + user.getFullName() + " (" + user.getUsername() + ")");
        return user;
    }

    /**
     * Initialize login information. This includes the selectable classes to
     * login as, as well as a map linking all input values to the associated
     * answer.
     */
    private void initLoginInfo() {
        //Create Entries linking what to print with a certain class.
        this.classList = new ArrayList();
        this.classList.add(new SimpleEntry("Administrator", Administrator.class));
        this.classList.add(new SimpleEntry("Issuer", Issuer.class));
        this.classList.add(new SimpleEntry("Developer", Developer.class));

        //Fill a map with acceptable input values linked to a certain class.
        this.optionMap = new HashMap();
        for (int i = 0; i < this.classList.size(); i++) {
            SimpleEntry<String, Class<? extends User>> entry = this.classList.get(i);
            this.optionMap.put(entry.getKey().toLowerCase(), entry.getValue());
            this.optionMap.put(Integer.toString(i), entry.getValue());
        }
        this.optionMap.put("admin", Administrator.class);
        this.optionMap.put("issuer", Issuer.class);
        this.optionMap.put("dev", Developer.class);
    }

    /**
     * Get the Class type the person wants to login as. This asks the person
     * which class type he wants by presenting a list of options to chose from.
     *
     * @param <U> extends User.
     * @param scan The {@link Scanner} used to interact with the person.
     * @return The Class of the type the person wants to login as. (e.g
     * Administrator).
     */
    private <U extends User> Class<U> getWantedUserType(Scanner scan) {
        //Ask question
        System.out.println("Please chose your type of login.");

        //Print the users options.
        for (int i = 0; i < this.classList.size(); i++) {
            System.out.println(i + ". " + this.classList.get(i).getKey());
        }

        //Retrieve user input.
        Class<? extends User> chosenClass;
        do {
            System.out.print("I chose: ");
            if ((chosenClass = this.optionMap.get(scan.next().toLowerCase())) == null) {
                System.out.println("invalid input.");
            }
        } while (chosenClass == null);

        return (Class<U>) chosenClass;
    }

    /**
     * Get the user the person wants to login as.
     * This asks the person which user to login as by presenting him a list of
     * users of the class, classType (classType, subclasses excluded).
     * @param <U> extends User, The type of the user to login as.
     * 
     * @param scan The {@link Scanner} used to interact with the person.
     * @param con The controller used to get access to the model.
     * @param classType The class type of the possible users to login as. (classType, excludes subclass).
     * @return The user to login as.
     */
    private <U extends User> U getWantedUserOfType(Scanner scan, DataController con, Class<U> classType) {
        //Print available user options of given type
        PList<U> usersOfType = con.getUsersOfExactType(classType);
        System.out.println("Available of chosen type:");
        for (int i = 0; i < usersOfType.size(); i++) {
            System.out.println(i + ". " + usersOfType.get(i).getUsername());
        }

        //Retrieve & process user input.
        U user = null;
        do {
            System.out.print("I chose: ");
            if (scan.hasNextInt()) { //by index
                int index = scan.nextInt(); //input
                if (index >= 0 && index < usersOfType.size()) {
                    user = usersOfType.get(index);
                } else {
                    System.out.println("Invalid input.");
                }
            } else { //by username
                String input = scan.nextLine(); //input
                try {
                    user = usersOfType.parallelStream().filter(u -> u.getUsername().equalsIgnoreCase(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    System.out.println("Invalid input.");
                }
            }
        } while (user == null);

        return user;
    }

}
