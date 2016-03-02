package bugtrap03.gui.terminal;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Admin
 */
public class TerminalScanner {

    /**
     * Constructs a new <code>TerminalScanner</code> that produces values
     * scanned from the specified input stream. Bytes from the stream are
     * converted into characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * @param source An input stream to be scanned
     * @throws IllegalArgumentException When a null reference is passed.
     */
    public TerminalScanner(InputStream source) throws IllegalArgumentException {
        if (source == null) {
            throw new IllegalArgumentException("TerminalScanner requires a non-null reference for source.");
        }
        scan = new Scanner(source);
    }

    /**
     * Advances this scanner past the current line and returns the input that
     * was skipped.
     * <p>
     * This method returns the rest of the current line, excluding any line
     * separator at the end. The position is set to the beginning of the next
     * line.
     * <p>
     * <p>
     * Since this method continues to search through the input looking for a
     * line separator, it may buffer all of the input searching for the line to
     * skip if no line separators are present.
     *
     * @return the line that was skipped
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException  if this scanner is closed
     * @throws CancelException        If the scanner found an indication of willingness
     *                                to abort.
     * @see Scanner#nextLine()
     */
    public String nextLine() throws CancelException {
        String result = scan.nextLine();

        if (AbortCmd.ABORT_CMD.equalsIgnoreCase(result)) {
            new AbortCmd().exec(null, null, null);
        }

        return result;
    }

    /**
     * Performs next() and then nextLine() to consume the rest of the sentence.
     *
     * @return The result of performing {@link Scanner#next()}.
     * @throws CancelException When an abort indication was given.
     * @see Scanner#next()
     * @see Scanner#nextLine()
     */
    public String next() throws CancelException {
        String result = scan.next();
        scan.nextLine();
        if (AbortCmd.ABORT_CMD.equalsIgnoreCase(result)) {
            new AbortCmd().exec(null, null, null);
        }
        return result;
    }

    /**
     * @return
     * @see Scanner#hasNextInt()
     */
    public boolean hasNextInt() {
        return scan.hasNextInt();
    }

    /**
     * Execute as a {@link Scanner} except if the abort indication was given.
     *
     * @return
     * @throws CancelException        When nextLine() would throw this.
     * @throws InputMismatchException When no Integer was found.
     * @see Scanner#nextInt()
     * @see #nextLine()
     */
    public int nextInt() throws CancelException {
        if (hasNextInt()) {
            int result = this.scan.nextInt();
            this.scan.nextLine();
            return result;
        } else {
            String result = this.nextLine();
            throw new InputMismatchException("An integer was expected but not found.");
        }
    }

    /**
     * @see Scanner#close()
     */
    public void close() {
        scan.close();
    }

    private Scanner scan;

}
