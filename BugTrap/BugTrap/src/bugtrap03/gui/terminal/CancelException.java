package bugtrap03.gui.terminal;

/**
 * This class represents a cancel exception that is thrown if the user want to cancel something
 * Created by Ben Goethuys on 28/02/2016.
 */
public class CancelException extends Exception {

    /**
     *
     */
    public CancelException() {
    }

    /**
     * @param message
     */
    public CancelException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public CancelException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public CancelException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
