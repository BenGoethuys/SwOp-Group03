package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class GetUserOfExactTypeCmdTest {

    /**
     * Test passing a null as classType to the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullCons() {
        Cmd cmd = new GetUserOfExcactTypeCmd(null);
    }

    /**
     * Test execution by username.
     *
     * @throws CancelException
     */
    @Test
    public void testExecName() throws CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead", "Luky", "Luke");
        User issuer = model.createIssuer("noDev", "BadLuck", "Luke");
        User admin = model.createAdministrator("admin", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd(Developer.class);

        //Setup scenario
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
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

        //Execute scenario
        Developer dev = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(dev, lead);
    }

    /**
     * Test execution by index.
     *
     * @throws CancelException
     */
    @Test
    public void testExecIndex() throws CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead20", "Luky", "Luke");
        User issuer = model.createIssuer("noDev20", "BadLuck", "Luke");
        User admin = model.createAdministrator("admin20", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd(Developer.class);

        //Setup scenario
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
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

        //Execute scenario
        Developer dev = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(dev, lead);
    }

}