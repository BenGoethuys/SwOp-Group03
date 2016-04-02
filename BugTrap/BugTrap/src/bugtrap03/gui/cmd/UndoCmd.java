package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetIntCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import bugtrap03.model.ModelCmd;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class UndoCmd implements Cmd {

    public final static int UNDO_LIST_SIZE = 10;

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The administrator indicates he wants to undo some successfully completed use cases that changed the state
     * of BugTrap4.
     * <br> 2. The system shows a list of the last 10 completed use case instances that modified the state of BugTrap.
     * <br> 3. The administrator indicates how many use cases he wants to revert starting with the last.
     * <br> 4. The system reverts the selected use cases starting with the last completed one and, if necessary, sends
     * the required notifications if some object of interest is modified by the undoing of a use case.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     *
     * @return Whether all the wanted Commands ({@link Cmd} were undone.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException When the users wants to abort the current {@link Cmd}
     * @throws IllegalArgumentException If the given scan, model or user is null
     */
    @Override
    public Boolean exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. Indicates he wants to undo some successfully completed use cases.
        // 2. Show a list of the last 10 completed use case instances that modified the state
        scan.println("Changes starting from most recent.");
        PList<ModelCmd> undoList = model.getHistory(UNDO_LIST_SIZE);
        for (int i = 1; i <= undoList.size(); i++) {
            scan.println(i + ". " + undoList.get(i - 1).toString());
        }

        // 3. Indicates how many use cases he wants to revert starting with the last.
        int undoAmount = -1;
        scan.println("How many would you like to undo?");
        while (undoAmount < 0) {
            undoAmount = (new GetIntCmd()).exec(scan, model, user);
            if (undoAmount < 0) {
                scan.println("Please choose a positive amount");
            }
        }

        // 4. The system reverts the selected use cases starting with the last completed one and, if necessary, sends 
        //the required notifications if some object of interest is modified by the undoing of a use case.
        boolean result = model.undoLastChanges(user, undoAmount);
        if (result) {
            scan.println("Undoing successful.");
        } else {
            scan.println("An undo action failed which caused the operation to abort.");
        }
        return result;
    }

}
