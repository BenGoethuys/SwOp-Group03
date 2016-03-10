package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.Scanner;

/**
 * This class represent the sub-scenario where the user selects an user of a given type
 *
 * @author Group 03
 */
public class GetUserOfExcactTypeCmd<U extends User> implements Cmd {

    /**
     * Create a 'choose user' scenario wherein the person can choose a certain user from a list of users from a specific
     * class.
     *
     * @param classType The type of the users to choose from. (exclude subclasses).
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
     * Execute the scenario wherein a person selects a user of a specified type. This asks the person which user to
     * choose as by presenting him a list of users of the class, classType (classType, subclasses excluded).
     *
     * @param scan  The {@link Scanner} used to interact with the person.
     * @param model The model used to get access to the model.
     * @param dummy Doesn't matter
     * @return The chosen user. Null if there was no option of that type.
     * @throws CancelException          When the cancel operation was executed.
     * @throws IllegalArgumentException If the given scan or model is null
     */
    @Override
    public U exec(TerminalScanner scan, DataModel model, User dummy) throws CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        //Print available user options of given type
        PList<U> usersOfType = model.getUserListOfExactType(classType);

        //Let the person select one of the options.
        return (new GetObjectOfListCmd<>(usersOfType, (u -> u.getUsername()), ((u, input) -> u.getUsername().equals(input)))).exec(scan, null, null);
    }

}
