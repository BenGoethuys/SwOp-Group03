package testCollection;

import bugtrap03.gui.terminal.TerminalScanner;

import java.io.InputStream;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Pattern info:
 * https://docs.oracle.com/javase/tutorial/essential/regex/pre_char_classes.html
 *
 * @author Admin
 */
public class TerminalTestScanner extends TerminalScanner {

    /**
     * Create a TerminalTestScanner that will use the given source for input,
     * System.out for output and expected as the queue to compare the output
     * with. This will not activate the patterns. Meaning every String passed in
     * the expected queue is processed as the string given and not converted to
     * a pattern.
     *
     * @param source   The InputStream used to input messages
     * @param expected The Queue consisting of the output messages to expect in
     *                 the order of the queue.
     * @throws IllegalArgumentException See the TerminalScanner constructor
     * @see TerminalScanner
     */
    public TerminalTestScanner(InputStream source, Queue<String> expected) throws IllegalArgumentException {
        this(source, expected, false);
    }

    /**
     * Create a TerminalTestScanner that will use the given source for input and
     * will check the output (println,..) to the expected Strings in the queue.
     * When patterns is true each expected string will function as a pattern to
     * which the received string (println(received),..) is matched.
     *
     * @param source   The source to use input from.
     * @param expected The queue of Strings that will be used as expected
     *                 output.
     * @param patterns Whether the strings in the expected queue should be
     *                 treated as patterns.
     */
    public TerminalTestScanner(InputStream source, Queue<String> expected, boolean patterns) {
        super(source, System.out);
        this.expected = expected;
        this.patterns = patterns;
    }

    /* Queue filled with the messages we expect */
    private final Queue<String> expected;

    /* Flag whether or not to use patterns (regular string otherwise)*/
    private final boolean patterns;

    /**
     * Checks if the message that is supposed to be printed is the one expected
     * according to the provided queue (constructor provided).
     *
     * @param message The message to expect as output.
     * @return True if the message matches.
     * @throws TestException When the message given does not equal the String at
     *                       the head of the queue of this class.
     */
    @Override
    public boolean println(String message) throws TestException {
        String expect = expected.poll();
        if (expect == null) {
            return false;
        }

        if (patterns) {
            if (!Pattern.matches(expect, message)) {
                throw new TestException("TestException. Expected Pattern:" + expect + " received:" + message);
            }
        } else {
            if (!expect.equals(message)) {
                throw new TestException("TestException. Expected:" + expect + " received:" + message);
            }
        }
        return true;
    }

    /**
     * Behaves the same as {@link #println(java.lang.String)}.
     *
     * @param message The message to expect as output.
     * @return True if the message matches.s
     * @throws TestException When the message given does not equal the String at
     *                       the head of the queue of this class.
     * @see #println(String)
     */
    @Override
    public boolean print(String message) throws TestException {
        String expect = expected.poll();
        if (expect == null) {
            return false;
        }

        if (patterns) {
            if (!Pattern.matches(expect, message)) {
                throw new TestException("TestException. Expected Pattern:" + expect + " received:" + message);
            }
        } else {
            if (!expect.equals(message)) {
                throw new TestException("TestException. Expected:" + expect + " received:" + message);
            }
        }
        return true;
    }
}
