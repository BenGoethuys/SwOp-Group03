package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class GetUserOfExactTypeCmdTest {

    /**
     * Test passing a null as classType to the constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullCons() {
        new GetUserOfExcactTypeCmd<>(null);
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
        Developer lead = model.createDeveloper("meGoodLead16", "Luky", "Luke");
        User admin = model.createAdministrator("admin16", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd<>(Developer.class);

        // Setup scenario
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

        // Execute scenario
        Developer dev = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(dev, lead);
    }

    /**
     * Test execution by index.
     *
     * @throws CancelException
     */
    @Test
    public void testExecIndex() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead20", "Luky", "Luke");
        User admin = model.createAdministrator("admin20", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd<>(Developer.class);

        // Setup scenario
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

        // Execute scenario
        Developer dev = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(dev, lead);
    }

    /**
     * Test exec() while model == null
     *
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead020", "Luky", "Luke");
        User admin = model.createAdministrator("admin020", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd<>(Developer.class);

        // Setup scenario
        question.add("Available options:");
        question.add("0. " + lead.getUsername());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Developer dev = cmd.exec(null, model, admin);

        // Test effects.
        assertEquals(dev, lead);
    }

    /**
     * Test exec() while model == null
     *
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ModelNull() throws CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        User admin = model.createAdministrator("admin0020", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetUserOfExcactTypeCmd<Developer> cmd = new GetUserOfExcactTypeCmd<>(Developer.class);

        // Setup scenario
        question.add("Available options:");
        answer.add("25");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Developer dev = cmd.exec(scan, null, admin);
    }

}
