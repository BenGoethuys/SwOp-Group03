package testCollection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/**
 * A {@link InputStream} used to read multiple 'lines' of Strings. This will
 * behave as reading a string from the provided queue ({@link MultiByteArrayInputStream(java.util.Queue)
 * }). When the end of a string has been reached the next read will start the
 * reading of the {@link #LINE_END} String. When this finishes as well either the
 * next element of the queue will be taken or -1 will be returned depending on
 * whether the queue is empty.
 *
 * @author Admin
 * @version %I% %G%
 */
public class MultiByteArrayInputStream extends InputStream {

    /**
     * Create a {@link MultiByteArrayInputStream} with a certain {@link Queue}
     * of Strings holding what to provide as input.
     *
     * @param queue The queue used to retrieve what to use as input. No copy
     *              will be taken, modifying this list will effect this behavior.
     * @throws IllegalArgumentException When the provided queue is null.
     */
    public MultiByteArrayInputStream(Queue<String> queue) throws IllegalArgumentException {
        if (queue == null) {
            throw new IllegalArgumentException("MultiByteArrayInputStream expects a non null-reference for the queue.");
        }

        this.queue = queue;
        if (!queue.isEmpty()) {
            this.byteSource = new ByteArrayInputStream(queue.poll().getBytes());
        }
        endLineFlag = false;
    }

    /* Marker presenting the end of a line. */
    private static final String LINE_END = System.lineSeparator();

    /* Used to read input from */
    private ByteArrayInputStream byteSource;

    /* Used to take input from, given to the byteSource. */
    private Queue<String> queue;

    /* Flag used to indicate the byteSource is currently outputting the LINE_END String. */
    private boolean endLineFlag;

    /**
     * This will behave as a ByteArrayInputStream based on the strings provided
     * by the queue of this class. (-
     * {@link #MultiByteArrayInputStream(java.util.Queue)}). When a string has
     * ended and this method is used this will start to behave as a
     * ByteArrayInputStream reading a {@link #LINE_END} after which it will to
     * behave as a ByteArrayInputStream reading the next String.
     *
     * @return -1 If the string has finished reading and there is no string left
     * to be read.
     * @throws IOException
     * @see ByteArrayInputStream#read()
     */
    @Override
    public int read() throws IOException {
        if (byteSource == null) {
            return -1;
        }
        //Read
        int nRead = byteSource.read();

        //Set endLineFlag.
        if (nRead == -1) {
            if (endLineFlag) {
                endLineFlag = false;
                if (!queue.isEmpty()) {
                    byteSource = new ByteArrayInputStream(queue.poll().getBytes());
                    return byteSource.read();
                } else {
                    byteSource = null;
                }
            } else {
                byteSource = new ByteArrayInputStream(LINE_END.getBytes());
                endLineFlag = true;
                return byteSource.read();
            }
        }
        return nRead;
    }

    /**
     * Add the given input to the queue used to read from.
     *
     * @param input The string to add to the queue.
     */
    public void addToQueue(String input) {
        this.queue.add(input);
    }

}
