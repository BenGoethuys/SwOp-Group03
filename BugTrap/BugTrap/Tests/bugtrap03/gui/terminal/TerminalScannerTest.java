package bugtrap03.gui.terminal;

import bugtrap03.gui.cmd.general.CancelException;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;

/**
 *
 * @author Admin
 */
public class TerminalScannerTest {

    /**
     * Test constructor with null reference for output stream.
     *
     * @throws IllegalArgumentException always
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInNullCons() {
        TerminalScanner scan = new TerminalScanner(System.in, null);
    }

    /**
     * Test constructor with null reference for input stream.
     *
     * @throws IllegalArgumentException always
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullOutCons() {
        TerminalScanner scan = new TerminalScanner(null, System.out);
    }

    /**
     * Test proper constructor, nextLine() and next()
     *
     * @throws CancelException never
     */
    @Test
    public void testCons_NextLine_Next() throws CancelException {
        String testStr = "test Input S5423";
        String testLine = "32DV Blub";
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(testStr);
        answer.add(testLine);
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        assertEquals(testStr, scan.nextLine());
        assertEquals(testLine.split(" ")[0], scan.next());
    }

    /**
     * Test throwing a CancelException due to typing abort for nextLine().
     *
     * @throws CancelException always
     */
    @Test(expected = CancelException.class)
    public void testCancelNextLine() throws CancelException {
        String abortStr = "abort";
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(abortStr);
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        scan.nextLine();
    }

    /**
     * Test throwing a CancelException due to typing abort for next().
     *
     * @throws CancelException always
     */
    @Test(expected = CancelException.class)
    public void testCancelNext() throws CancelException {
        String abortStr = "abort";
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(abortStr);
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        scan.next();
    }

    /**
     * Test throwing a CancelException due to typing abort for nextInt().
     *
     * @throws CancelException always
     */
    @Test(expected = CancelException.class)
    public void testNextIntException() throws CancelException {
        String abortStr = "abort";
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(abortStr);
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        scan.nextInt();
    }

    /**
     * Test throwing an InputMismatchException when doing nextInt expecting an
     * Int giving a String.
     *
     * @throws CancelException never
     */
    @Test(expected = InputMismatchException.class)
    public void testNextIntMisMatch() throws CancelException {
        String abortStr = "hello";
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(abortStr);
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        int number = scan.nextInt();
    }

    /**
     * Execute print(String), println(String) & close(). No testing is really
     * performed as it is just printed to System.out. The class
     * TerminalTestScanner was created to test these outputs but this is a class
     * it cannot be used for.
     */
    @Test
    public void testPrints() {
        ArrayDeque<String> answer = new ArrayDeque<>();
        answer.add(" empty");
        TerminalScanner scan = new TerminalScanner(new MultiByteArrayInputStream(answer), System.out);
        scan.print("This tests");
        scan.println(" the TerminalScanner class.");

        scan.close();
    }

}
