package bugtrap03.gui.cmd.general;

/**
 * This class represents a cancel exception that is thrown if the user want to cancel something
 *
 * @author Group 03
 */
public class CancelException extends Exception {
    
    /**
     * This is the constructor for a CancelException
     * @param message   The message that explains why the exception is thrown
     */
    public CancelException(String message) {
        super(message);
    }

}
