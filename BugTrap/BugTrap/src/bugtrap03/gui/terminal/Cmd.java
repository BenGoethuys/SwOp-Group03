package bugtrap03.gui.terminal;

import bugtrap03.DataController;

/**
 *
 * @author Admin
 */
public interface Cmd {
    
    /**
     * Execute this command and possibly return a result.
     * @param controller The controller used for model access.
     * @return null if there is no result specified.
     */
    public Object exec(DataController controller);
    
}
