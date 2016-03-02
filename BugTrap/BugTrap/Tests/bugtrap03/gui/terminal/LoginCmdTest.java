package bugtrap03.gui.terminal;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import java.util.ArrayDeque;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class LoginCmdTest {
    
    /**
     * 
     * @throws CancelException 
     */
    @Test
    public void testSuccessAdmin1User() throws CancelException {
        //Setup variables.
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        String username = "007";
        String firstName = "Vin";
        String lastName = "Derk";
        DataModel model = new DataModel();
        User admin = model.createAdministrator(username, firstName, lastName);
        Terminal dummyTerminal = new Terminal(model);
        LoginCmd cmd = new LoginCmd(dummyTerminal);

        //Setup scenario
        question.add("Please chose your type of login.");
        question.add("0. Administrator");
        question.add("1. Issuer");
        question.add("2. Developer");
        question.add("I chose: ");
        answer.add("0");
        question.add("Available options:");
        question.add("0. " + username);
        question.add("I chose: ");
        answer.add("0");
        question.add("Welcome " + admin.getFullName() + " (" + admin.getUsername() + ")");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        
        //Execute scenario
        User user = cmd.exec(scan, model, null);
    }

}
