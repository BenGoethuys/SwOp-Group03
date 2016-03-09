package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 *
 */
public class GetUserOfTypeCmdTest {

    /**
     * Test passing a null as classType to the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullCons() {
        Cmd cmd = new GetUserOfTypeCmd(null);
    }

    /**
     * Test execution by username.
     *
     * @throws CancelException
     */
    @Test
    public void testExecName() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLeadAX", "Luky", "Luke");
        Issuer issuer = model.createIssuer("noDevAX", "BadLuck", "Luke");
        User admin = model.createAdministrator("adminAX", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetUserOfTypeCmd<Issuer> cmd = new GetUserOfTypeCmd(Issuer.class);

        // Setup scenario
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("1. " + issuer.getUsername());
        question.add("I choose: ");
        answer.add("25");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("test");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(lead.getUsername());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Issuer chosen = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(chosen, lead);
    }

    /**
     * Test execution by username.
     *
     * @throws CancelException
     */
    @Test
    public void testExecIndex() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead50", "Luky", "Luke");
        Issuer issuer = model.createIssuer("noDev50", "BadLuck", "Luke");
        User admin = model.createAdministrator("admin50", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetUserOfTypeCmd<Issuer> cmd = new GetUserOfTypeCmd(Issuer.class);

        // Setup scenario
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("1. " + issuer.getUsername());
        question.add("I choose: ");
        answer.add("25");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("test");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Issuer chosen = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(lead, chosen);
    }

    /**
     * Test execution on empty type list.
     *
     * @throws CancelException
     */
    @Test
    public void testExecEmpty() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        User admin = model.createAdministrator("admin00", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetUserOfTypeCmd<Issuer> cmd = new GetUserOfTypeCmd(Issuer.class);

        // Setup scenario
        question.add("No users of this type found.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Issuer chosen = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(null, chosen);
    }

}
