package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class UndoCmdTest {

    @Test
    public void testExec() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("UniqueNessOverrated", "Luky", "Luke");
        Administrator admin = model.createAdministrator("UniquenessOverrated", "adminT", "bie");
        Project proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UndoCmd cmd = new UndoCmd();

        // Setup scenario
        question.add("Changes starting from most recent.");
        question.add("1. .+");
        question.add("2. .+");
        question.add("3. .+");

        question.add("How many would you like to undo\\?");
        question.add("Give number: ");
        answer.add("-5");
        question.add("Please choose a positive amount");
        question.add("Give number: ");
        answer.add("2");
        question.add("Undoing successful.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question, true);

        // Execute scenario
        Boolean undone = cmd.exec(scan, model, admin);

        // Test effects.
        assertTrue(undone);
        assertEquals(model.getHistory(5).size(), 1);
        assertTrue(model.getHistory(5).get(0).toString().contains("Developer"));
    }

    /**
     * Test {@link UndoCmd#exec(TerminalScanner, DataModel, User)} with scan == null
     * @throws PermissionException Never
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecNullScan() throws PermissionException, CancelException {
        UndoCmd cmd = new UndoCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("Test_51", "Test", "Test");
        cmd.exec(null, model, admin);
    }

    /**
     * Test {@link UndoCmd#exec(TerminalScanner, DataModel, User)} with model == null
     * @throws PermissionException Never
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecNullModel() throws PermissionException, CancelException {
        UndoCmd cmd = new UndoCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("Test_52", "Test", "Test");
        cmd.exec(new TerminalScanner(System.in, System.out), null, admin);
    }

    /**
     * Test {@link UndoCmd#exec(TerminalScanner, DataModel, User)} with user == null
     * @throws PermissionException Never
     * @throws CancelException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecNullUser() throws PermissionException, CancelException {
        UndoCmd cmd = new UndoCmd();
        cmd.exec(new TerminalScanner(System.in, System.out), new DataModel(), null);
    }

}
