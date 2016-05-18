package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetUserOfExactTypeCmd;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This command represents the use case login, where a user logs in into the system
 *
 * @author Group 03
 */
public class LoginCmd implements Cmd<User> {

    /**
     * The construct of this command
     *
     * @param terminal The terminal of the system
     * @throws IllegalArgumentException When terminal == null
     */
    public LoginCmd(Terminal terminal) throws IllegalArgumentException {
        if (terminal == null) {
            throw new IllegalArgumentException("Terminal musn't be null.");
        }

        this.terminal = terminal;
        initLoginInfo();
    }

    private Terminal terminal;
    private ArrayList<SimpleEntry<String, Class<? extends User>>> classList;
    private HashMap<String, Class<? extends User>> optionMap;

    /**
     * Execute the login scenario.
     * <br> 1. Ask the person which type to login as.
     * <br> 2. Show the person all users of that type
     * <br> 3. Ask the person which user to login as.
     * <br> 4. Welcome the user.
     *
     * @param scan  The {@link Scanner} trough which to ask the questions.
     * @param model The model to use to access the model.
     * @param dummy Dummy, as the person isn't a specific user yet. Use
     *              whatever.
     * @return The user chosen by the person to login as.
     * @throws CancelException          When the cancel operation was executed.
     * @throws IllegalArgumentException If the scan or model is null
     */
    @Override
    public User exec(TerminalScanner scan, DataModel model, User dummy) throws CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        //Login
        User user;
        do {
            //1. Ask which type to login as.
            Class<? extends User> classType = getWantedUserType(scan);

            //2. Show the person all users of that type
            //3. Ask the person which user to login as.
            user = (new GetUserOfExactTypeCmd<>(classType)).exec(scan, model, null);
        } while (user == null);

        terminal.setUser(user);

        //4. Welcome user.
        scan.println("Welcome " + user.getFullName() + " (" + user.getUsername() + ")");
        return user;
    }

    /**
     * Initialize login information. This includes the selectable classes to
     * login as, as well as a map linking all input values to the associated
     * answer.
     */
    private void initLoginInfo() {
        //Create Entries linking what to print with a certain class.
        this.classList = new ArrayList<>();
        this.classList.add(new SimpleEntry<>("Administrator", Administrator.class));
        this.classList.add(new SimpleEntry<>("Issuer", Issuer.class));
        this.classList.add(new SimpleEntry<>("Developer", Developer.class));

        //Fill a map with acceptable input values linked to a certain class.
        this.optionMap = new HashMap<>();
        for (int i = 0; i < this.classList.size(); i++) {
            SimpleEntry<String, Class<? extends User>> entry = this.classList.get(i);
            this.optionMap.put(entry.getKey().toLowerCase(), entry.getValue());
            this.optionMap.put(Integer.toString(i), entry.getValue());
        }
        this.optionMap.put("admin", Administrator.class);
        this.optionMap.put("dev", Developer.class);
    }

    /**
     * Get the Class type the person wants to login as. This asks the person
     * which class type he wants by presenting a list of options to choose from.
     *
     * @param <U>  extends User.
     * @param scan The {@link Scanner} used to interact with the person.
     * @return The Class of the type the person wants to login as. (e.g
     * Administrator).
     */
    private <U extends User> Class<U> getWantedUserType(TerminalScanner scan) throws CancelException {
        //Ask question
        scan.println("Please choose your type of login.");

        //Print the users options.
        for (int i = 0; i < this.classList.size(); i++) {
            scan.println(i + ". " + this.classList.get(i).getKey());
        }

        //Retrieve user input.
        Class<? extends User> chosenClass;
        do {
            scan.print("I choose: ");
            if ((chosenClass = this.optionMap.get(scan.nextLine().toLowerCase())) == null) {
                scan.println("Invalid input.");
            }
        } while (chosenClass == null);

        return (Class<U>) chosenClass;
    }
}
