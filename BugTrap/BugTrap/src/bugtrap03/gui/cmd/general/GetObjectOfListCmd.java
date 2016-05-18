package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This command selects a object out of an given generic list using 2 generic functions
 *
 * @author Group 03
 */
public class GetObjectOfListCmd<U extends Object> implements Cmd<U> {

    /**
     * Create a GetObjectOfListCmd that will use the given list of users as the
     * options in the select process.
     *
     * @param listOfObjects  The list of users to pick from. When null is passed an empty list will be used.
     * @param printFunction  The function used to print the objects of the list.
     * @param selectFunction The function used to filter in the list when the user provides a String input.
     *
     * @throws IllegalArgumentException If at least one of the functions is null
     *
     * @see GetObjectOfListCmd#exec(TerminalScanner, DataModel, User)
     */
    public GetObjectOfListCmd(PList<U> listOfObjects, Function<U, String> printFunction, BiFunction<U, String, Boolean> selectFunction) {
        if (listOfObjects == null) {
            this.listOfObjects = PList.<U>empty();
        } else {
            this.listOfObjects = listOfObjects;
        }
        if (printFunction == null || selectFunction == null) {
            throw new IllegalArgumentException("The given function was null");
        }
        this.printFunction = printFunction;
        this.selectFunction = selectFunction;
    }

    private final PList<U> listOfObjects;
    private final Function<U, String> printFunction;
    private final BiFunction<U, String, Boolean> selectFunction;

    /**
     * Create a scenario where the person will choose an Object from the list passed
     * to the constructor. Uses both functions passed to the constructor to print the options and filter the answers.
     * Prints 'No options found.' and returns null when the list is empty.
     *
     * @param scan   Used to interact with the person who selects the option.
     * @param dummy2 Dummy
     * @param dummy3 Dummy
     *
     * @return The selected option, can be null if the list of options was empty.
     *
     * @throws IllegalArgumentException If the given scan is null
     * @throws CancelException          When the person has indicated to abort the cmd.
     */
    @Override
    public U exec(TerminalScanner scan, DataModel dummy2, User dummy3) throws CancelException, IllegalArgumentException {
        if (scan == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
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
