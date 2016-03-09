package bugtrap03.gui.cmd.general;

import bugtrap03.model.DataModel;
import purecollections.PList;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
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
public class GetProjectCmdTest {

    /**
     * Test execution of getProjectCmd exec using the index.
     *
     * @throws IllegalArgumentException Never
     * @throws PermissionException Never
     * @throws CancelException Never
     * @see GetProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner,
     *      DataModel, bugtrap03.bugdomain.usersystem.User)
     */
    @Test
    public void testExecByIndex() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead15", "Luky", "Luke");
        User admin = model.createAdministrator("admin15", "adminT", "bie");
        Project proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetProjectCmd cmd = new GetProjectCmd();

        // Setup scenario
        question.add("Available projects:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("wrongInput");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("1");
        question.add("You have chosen:");
        question.add(proj1.getDetails());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosenProj = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(chosenProj, proj1);
    }

    /**
     * Test execution of getProjectCmd exec using the project name.
     *
     * @throws IllegalArgumentException Never
     * @throws PermissionException Never
     * @throws CancelException Never
     * @see GetProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner,
     *      DataModel, bugtrap03.bugdomain.usersystem.User)
     */
    @Test
    public void testExecName() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead", "Luky", "Luke");
        User admin = model.createAdministrator("adminB", "adminT", "bie");
        Project proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetProjectCmd cmd = new GetProjectCmd();

        // Setup scenario
        question.add("Available projects:");
        question.add("0. " + proj0.getName() + " version: " + proj0.getVersionID());
        question.add("1. " + proj1.getName() + " version: " + proj1.getVersionID());
        question.add("I choose: ");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("wrongInput");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(proj1.getName() + proj1.getVersionID().toString());
        question.add("You have chosen:");
        question.add(proj1.getDetails());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosenProj = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(chosenProj, proj1);
    }

    @Test
    public void testGetProjectCmd() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead2", "Luky", "Luke");
        User admin = model.createAdministrator("adminB2", "adminT", "bie");
        Project proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        PList<Project> projectOptionList = PList.<Project> empty().plus(proj0);
        GetProjectCmd cmd = new GetProjectCmd(projectOptionList);
    }

}
