package bugtrap03.gui.cmd.general;

/**
 * This class represents a cancel exception that is thrown if the user want to cancel something
 * Created by Ben Goethuys on 28/02/2016.
 */
public class CancelException extends Exception {
    
    /**
     * @param message
     */
    public CancelException(String message) {
        super(message);
    }

}
