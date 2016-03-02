package bugtrap03.gui.cmd;

import bugtrap03.gui.cmd.LoginCmd;
import bugtrap03.DataModel;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.LoginCmd;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.Terminal;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class LoginCmdTest {

    @BeforeClass
    public static void SetUpBefore() {
        String username = "007a";
        String firstName = "James";
        String lastName = "Bond";

        String username2 = "002a";
        String username3 = "008a";

        model = new DataModel();
        admin1 = model.createAdministrator(username, firstName, lastName);
        admin2 = model.createAdministrator(username2, firstName, lastName);
        issuer = model.createIssuer(username3, firstName, lastName);
    }

    private static DataModel model;
    private static User admin1;
    private static User admin2;
    private static User issuer;

    /**
     *
     * Test a login scenario with 2 administrators, 1 Issuer.
     * <br> Person uses index 0 to select Administrator
     * <br> Person uses index 0 to select a certain Administrator on index 0 -
     * admin.
     *
     * @throws CancelException
     */
    @Test
    public void testSuccesAdmin1UserIndex() throws CancelException {
        //Setup variables.
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
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
        question.add("0. " + admin1.getUsername());
        question.add("1. " + admin2.getUsername());
        question.add("I chose: ");
        answer.add("0");
        question.add("Welcome " + admin1.getFullName() + " (" + admin1.getUsername() + ")");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        User user = cmd.exec(scan, model, null);

        //Test effects.
        assertEquals(user, dummyTerminal.getUser());
    }

    /**
     *
     * Test a login scenario with 2 administrators, 1 Issuer.
     * <br> Person uses index 0 to select Administrator
     * <br> Person uses index 0 to select a certain Administrator on index 0 -
     * admin.
     *
     * @throws CancelException
     */
    @Test
    public void testSuccesIssuer1UserName() throws CancelException {
        //Setup variables.
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        Terminal dummyTerminal = new Terminal(model);
        LoginCmd cmd = new LoginCmd(dummyTerminal);

        //Setup scenario
        question.add("Please chose your type of login.");
        question.add("0. Administrator");
        question.add("1. Issuer");
        question.add("2. Developer");
        question.add("I chose: ");
        answer.add("Wrong Input");
        question.add("Invalid input.");
        question.add("I chose: ");
        answer.add("2");
        question.add("No users of this type found.");
        question.add("Please chose your type of login.");
        question.add("0. Administrator");
        question.add("1. Issuer");
        question.add("2. Developer");
        question.add("I chose: ");
        answer.add("1");
        question.add("Available options:");
        question.add("0. " + issuer.getUsername());
        question.add("I chose: ");
        answer.add("Ben");
        question.add("Invalid input.");
        question.add("I chose: ");
        answer.add(issuer.getUsername());
        question.add("Welcome " + issuer.getFullName() + " (" + issuer.getUsername() + ")");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        User user = cmd.exec(scan, model, null);

        //Test effects.
        assertEquals(user, dummyTerminal.getUser());
    }

}
