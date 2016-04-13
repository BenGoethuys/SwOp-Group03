package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;


/**
 * This class represents and abort cmd from the user
 *
 * @author Group 03
 */
public class AbortCmd implements Cmd<Object> {

    /**
     * Throws a {@link CancelException} representing an indication of abort.
     *
     * @param dummy1 Does not matter.
     * @param dummy2 Does not matter.
     * @param dummy3 Does not matter.
     * @return Returns never.
     * @throws CancelException Always.
     */
    @Override
    public Object exec(TerminalScanner dummy1, DataModel dummy2, User dummy3) throws CancelException {
        throw new CancelException("User wants to abort current cmd");
    }

    public static final String ABORT_CMD = "abort";

}
