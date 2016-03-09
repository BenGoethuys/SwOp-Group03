package testCollection;

/**
 * Used to indicate an error during testing.
 *
 * @author Admin
 */
public class TestException extends RuntimeException {

    private static final long serialVersionUID = -1969466376636941373L;

    public TestException(String message) {
        super(message);
    }

}
