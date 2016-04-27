package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class ShowProjectCmdTest {

    Project chosen;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.ShowProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExec() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead0255", "Luky", "Luke");
        Administrator admin = model.createAdministrator("admin0255", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        ShowProjectCmd cmd = new ShowProjectCmd();

        // Setup scenario
        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add(proj0.getName() + proj0.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosen = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(chosen, proj0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException {
        ShowProjectCmd cmd = new ShowProjectCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException {
        ShowProjectCmd cmd = new ShowProjectCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException {
        ShowProjectCmd cmd = new ShowProjectCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException {
        ShowProjectCmd cmd = new ShowProjectCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }

}
