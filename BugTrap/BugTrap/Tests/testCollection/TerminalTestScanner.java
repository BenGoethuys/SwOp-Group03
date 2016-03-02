package testCollection;

import bugtrap03.gui.terminal.TerminalScanner;
import java.io.InputStream;
import java.util.Queue;

/**
 *
 * @author Admin
 */
public class TerminalTestScanner extends TerminalScanner {

    public TerminalTestScanner(InputStream source, Queue<String> expected) throws IllegalArgumentException {
        super(source, System.out);
        this.expected = expected;
    }

    private final Queue<String> expected;

    /**
     *
     * @param message
     * @return
     * @throws TestException When the message given does not equal the String at
     * the head of the queue of this class.
     */
    @Override
    public boolean println(String message) throws TestException {
        String expect = expected.poll();
        if (expect == null) {
            return false;
        }

        if (!expect.equals(message)) {
            throw new TestException("TestException. Expected:" + expect + " received:" + message);
        }
        return true;
    }

    /**
     *
     * @param message
     * @return
     * @throws TestException When the message given does not equal the String at
     * the head of the queue of this class.
     */
    @Override
    public boolean print(String message) throws TestException {
        String expect = expected.poll();
        if (expect == null) {
            return false;
        }

        if (!expect.equals(message)) {
            throw new TestException("TestException. Expected:" + expect + " received:" + message);
        }
        return true;
    }
}
