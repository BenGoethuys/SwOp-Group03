package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import org.junit.BeforeClass;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Group 03
 */
public class UpdateProjectCmdTest {

    private static DataModel model;
    private static Administrator admin;
    private static Developer lead;
    private static Project proj0;
    private static Project proj1;
    Project chosen;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        model = new DataModel();
        lead = model.createDeveloper("theLeader", "the", "Leader");
        admin = model.createAdministrator("theAdmin", "Ad", "Min");
        proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExecUpdateProjectNoUpdates() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("");
        question.add("The name was not updated.");
        question.add("Give new description: (leave blank for old description)");
        answer.add("");
        question.add("Description not updated.");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("");
        question.add("Start date not updated.");
        question.add("Give new project budget estimate:");
        answer.add("");
        question.add("Budget estimate not updated.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
        assertEquals(chosen.getDetails(), proj0.getDetails());
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExecUpdateProjectUpdateName()
            throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("NewName");
        question.add("Give new description: (leave blank for old description)");
        answer.add("");
        question.add("Description not updated.");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("");
        question.add("Start date not updated.");
        question.add("Give new project budget estimate:");
        answer.add("");
        question.add("Budget estimate not updated.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
        assertEquals(proj0.getName(), "NewName");
        assertNotEquals(proj0.getName(), "ProjectTest0");
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExecUpdateProjectUpdateDescription()
            throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("");
        question.add("The name was not updated.");
        question.add("Give new description: (leave blank for old description)");
        answer.add("NewDescription");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("");
        question.add("Start date not updated.");
        question.add("Give new project budget estimate:");
        answer.add("");
        question.add("Budget estimate not updated.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
        assertEquals(proj0.getDescription(), "NewDescription");
        assertNotEquals(proj0.getDescription(), "Project for testing 0");
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecUpdateProjectUpdateDateFalse()
            throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("");
        question.add("The name was not updated.");
        question.add("Give new description: (leave blank for old description)");
        answer.add("");
        question.add("Description not updated.");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("2024");
        question.add("Invalid input. Please try again");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("2011-12-01");
        question.add("Give new project budget estimate:");
        answer.add("");
        question.add("Budget estimate not updated.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExecUpdateProjectUpdateDateTrue()
            throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("");
        question.add("The name was not updated.");
        question.add("Give new description: (leave blank for old description)");
        answer.add("");
        question.add("Description not updated.");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("2016-12-31");
        question.add("Give new project budget estimate:");
        answer.add("");
        question.add("Budget estimate not updated.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);

    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExecUpdateProjectUpdateBudget()
            throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        question.add("Give new name: (leave blank for old name)");
        answer.add("");
        question.add("The name was not updated.");
        question.add("Give new description: (leave blank for old description)");
        answer.add("");
        question.add("Description not updated.");
        question.add("Give new project starting date (YYYY-MM-DD): (leave blank for old date)");
        answer.add("");
        question.add("Start date not updated.");
        question.add("Give new project budget estimate:");
        answer.add("Lalala");
        question.add("Invalid input.");
        question.add("Give new project budget estimate:");
        answer.add("123");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
        assertEquals(proj0.getBudgetEstimate(), 123);
        assertNotEquals(proj0.getBudgetEstimate(), 500);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, PermissionException, CancelException {
        UpdateProjectCmd cmd = new UpdateProjectCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateProjectCmd cmd = new UpdateProjectCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateProjectCmd cmd = new UpdateProjectCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateProjectCmd cmd = new UpdateProjectCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }
}
