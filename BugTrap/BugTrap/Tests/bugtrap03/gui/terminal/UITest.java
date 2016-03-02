package bugtrap03.gui.terminal;

import java.io.IOException;
import java.util.ArrayDeque;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;
import textuitester.TextUITestScriptRunner;
import textuitester.TextUITester;

/**
 *
 * @author Admin
 */
public class UITest {

    /*@Test
    public void scriptTest() throws IOException {
        TextUITestScriptRunner.runTestScript(UITest.class, "testScript1.txt");
    }*/

//    @Test
//    public void test() {
//        TextUITester tester = new TextUITester("java -cp bin bugtrap03.Main");
//        tester.sendLine("0");
//        tester.expectExit(0);
//    }
    
    public void test() {
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        
        answer.add("0");
        answer.add("0");
        
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        
    }

}
