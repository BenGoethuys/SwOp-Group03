package testCollection;

/**
 * Used to indicate an error during testing.
 *
 * @author Admin
 */
public class TestException extends RuntimeException {

    public TestException(String message) {
        super(message);
    }

}
