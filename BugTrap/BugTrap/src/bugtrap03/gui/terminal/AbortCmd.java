package bugtrap03.gui.terminal;

import bugtrap03.DataController;
import bugtrap03.usersystem.User;


/**
 * This class represents and abort cmd from the user
 * Created by Ben Goethuys on 28/02/2016.
 */
public class AbortCmd implements Cmd {

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
    public Object exec(TerminalScanner dummy1, DataController dummy2, User dummy3) throws CancelException {
        throw new CancelException("User wants to abort current cmd");
    }
    
    public static final String ABORT_CMD = "abort";

}
