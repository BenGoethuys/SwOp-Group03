package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.function.Predicate;
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
    
    @Test
    public void test() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        String username = "007";
        String firstName = "Vin";
        String lastName = "Derk";
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        DataModel model = new DataModel();
        User admin = model.createAdministrator(username, firstName, lastName);
        Terminal dummyTerminal = new Terminal(model);
        LoginCmd cmd = new LoginCmd(dummyTerminal);
        
        question.add("Please chose your type of login.");
        question.add("0. Administrator");
        question.add("1. Issuer");
        question.add("2. Developer");
        question.add("I chose: ");
        answer.add("0");
        question.add("I chose: ");
        answer.add("0");
        question.add("Welcome " + admin.getFullName() + " (" + admin.getUsername() + ")");
        
        User user = cmd.exec(scan, model, null);
        System.out.println(user.getFullName());
        
        
    }

}
