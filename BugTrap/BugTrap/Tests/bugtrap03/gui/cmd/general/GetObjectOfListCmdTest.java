package bugtrap03.gui.cmd.general;

import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertNull;

/**
 * Includes tests of border cases. The mainly used scenarios are tested by usage in other tests.
 *
 * @author Group 03
 */
public class GetObjectOfListCmdTest {

    private BiFunction biFunctionDummy = new BiFunction() {
        @Override
        public Object apply(Object t, Object u) {
            return "dummy";
        }
    };

    private Function functionDummy = new Function() {
        @Override
        public Object apply(Object t) {
            return "dummy";
        }
    };

    /**
     * Test Constructor with printFunction == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_PrintFunctionNull() {
        GetObjectOfListCmd cmd = new GetObjectOfListCmd(PList.<Object>empty(), null, biFunctionDummy);
    }

    /**
     * Test Constructor with selectFunction == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_SelectFunctionNull() {
        GetObjectOfListCmd cmd = new GetObjectOfListCmd(PList.<Object>empty(), functionDummy, null);
    }

    /**
     * Test exec() while scan == null
     *
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws CancelException {
        GetObjectOfListCmd cmd = new GetObjectOfListCmd(PList.<Object>empty(), functionDummy, biFunctionDummy);
        cmd.exec(null, null, null);
    }

    /**
     * Test the constructor with optionList == null, this should result in behaving like it was an empty list passed.
     *
     * @throws CancelException Never
     */
    @Test
    public void testCons_OptionsNull() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();

        // Setup scenario
        question.add("No options found.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        GetObjectOfListCmd cmd = new GetObjectOfListCmd(null, functionDummy, biFunctionDummy);
        assertNull(cmd.exec(scan, null, null));
    }
}
