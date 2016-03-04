package testCollection;

import bugtrap03.gui.terminal.TerminalScanner;
import java.io.InputStream;
import java.util.Queue;

/**
 *
 * @author Admin
 */
public class TerminalTestScanner extends TerminalScanner {

    /**
     * Create a TerminalTestScanner that will use the given source for input,
     * System.out for output and expected as the queue to compare the output
     * with.
     *
     * @param source The InputStream used to input messages
     * @param expected The Queue consisting of the output messages to expect in
     * the order of the queue.
     * @throws IllegalArgumentException See the TerminalScanner constructor
     * @see TerminalScanner
     */
    public TerminalTestScanner(InputStream source, Queue<String> expected) throws IllegalArgumentException {
        super(source, System.out);
        this.expected = expected;
    }

    /* Queue filled with the messages we expect */
    private final Queue<String> expected;

    /**
     * Checks if the message that is supposed to be printed is the one expected
     * according to the provided queue (constructor provided).
     *
     * @param message The message to expect as output.
     * @return True if the message matches.
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
     * Behaves the same as {@link #println(java.lang.String)}.
     *
     * @param message The message to expect as output.
     * @return True if the message matches.s
     * @throws TestException When the message given does not equal the String at
     * the head of the queue of this class.
     * @see #println(String)
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
