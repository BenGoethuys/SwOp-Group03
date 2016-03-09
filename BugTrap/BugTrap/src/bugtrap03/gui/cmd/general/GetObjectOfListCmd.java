package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

import purecollections.PList;

/**
 *
 * @author Admin
 */
public class GetObjectOfListCmd<U extends Object> implements Cmd {

    /**
     * Create a GetObjectOfListCmd that will use the given list of users as the
     * options in the select process.
     *
     * @param listOfObjects The list of users to pick from. When null is passed an
     * empty list will be used.
     *
     * @throws IllegalArgumentException If the function is null
     *
     * @see GetObjectOfListCmd#exec(TerminalScanner, DataModel, User)
     */
    public GetObjectOfListCmd(PList<U> listOfObjects, Function<U, String> printFunction, BiFunction<U, String, Boolean> selectFunction) {
        if (listOfObjects == null) {
            this.listOfObjects = PList.<U>empty();
        } else {
            this.listOfObjects = listOfObjects;
        }
        if (printFunction == null || selectFunction == null){
            throw new IllegalArgumentException("The given function was null");
        }
        this.printFunction = printFunction;
        this.selectFunction = selectFunction;
    }

    private final PList<U> listOfObjects;
    private final Function<U, String> printFunction;
    private final BiFunction<U, String, Boolean> selectFunction;

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
        if (listOfObjects.isEmpty()) {
            scan.println("No options found.");
            return null;
        }

        scan.println("Available options:");
        for (int i = 0; i < listOfObjects.size(); i++) {
            scan.println(i + ". " + printFunction.apply(listOfObjects.get(i)));
        }

        //Retrieve & process object input.
        U object = null;
        do {
            scan.println("I choose: ");
            if (scan.hasNextInt()) { //by index
                int index = scan.nextInt();//input
                if (index >= 0 && index < listOfObjects.size()) {
                    object = listOfObjects.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { //by username
                String input = scan.nextLine(); //input
                try {
                    object = listOfObjects.parallelStream().filter(u -> selectFunction.apply(u, input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while (object == null);

        return object;
    }

}
