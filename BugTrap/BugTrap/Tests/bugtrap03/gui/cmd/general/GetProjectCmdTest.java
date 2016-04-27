package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Group 03
 */
public class GetProjectCmdTest {

    /**
     * Get the questions in the default scenario where project with index 0 is selected.
     *
     * @return The projects in the order as they would appear
     */
    public static ArrayDeque<String> getDefaultQuestions(Project... projects) {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Select a project.");
        question.add("Available options:");
        for (int i = 0; i < projects.length; i++) {
            question.add(i + ". " + projects[i].getName() + " version: " + projects[i].getVersionID());
        }
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projects[0].getDetails());
        return question;
    }

    /**
     * Get the answers in the default scenario
     *
     * @return The projects in the order as they would appear
     */
    public static ArrayDeque<String> getDefaultAnswers(Project... projects) {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Select a project.");
        question.add("Available options:");
        for (int i = 0; i < projects.length; i++) {
            question.add(i + ". " + projects[i].getName() + " version: " + projects[i].getVersionID());
        }
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projects[0].getDetails());
        return answer;
    }

    /**
     * Test execution of getProjectCmd exec using the index.
     *
     * @throws IllegalArgumentException Never
     * @throws PermissionException Never
     * @throws CancelException Never
     * @see GetProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)
     */
    @Test
    public void testExecByIndex() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead15", "Luky", "Luke");
        User admin = model.createAdministrator("admin15", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetProjectCmd cmd = new GetProjectCmd();

        // Setup scenario
        question.add("Select a project.");
        question.add("Available options:");
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
     * @see GetProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)
     */
    @Test
    public void testExecName() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead", "Luky", "Luke");
        User admin = model.createAdministrator("adminB", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetProjectCmd cmd = new GetProjectCmd();

        // Setup scenario
        question.add("Select a project.");
        question.add("Available options:");
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

    /**
     * Test the constructor using an option list.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGetProjectCmd() throws PermissionException {
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead2", "Luky", "Luke");
        User admin = model.createAdministrator("adminB2", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        PList<Project> projectOptionList = PList.<Project>empty().plus(proj0);

        GetProjectCmd cmd = new GetProjectCmd(projectOptionList);
    }

    /**
     * Test exec() while scan == null
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws PermissionException, CancelException {
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead0000", "Luky", "Luke");
        User admin = model.createAdministrator("adminB0000", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        PList<Project> projectOptionList = PList.<Project>empty().plus(proj0);

        GetProjectCmd cmd = new GetProjectCmd(projectOptionList);
        cmd.exec(null, model, admin);
    }

    /**
     * Test exec() while model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ModelNull() throws PermissionException, CancelException {
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead000", "Luky", "Luke");
        User admin = model.createAdministrator("adminB000", "adminT", "bie");
        Project proj0 = model.createProject(new VersionID(), "ProjectTest000", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject(new VersionID(), "ProjectTest100", "Project for testing 1", lead, 1000, admin);

        PList<Project> projectOptionList = PList.<Project>empty().plus(proj0);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();

        // Setup scenario
        question.add("Available options:");
        answer.add("5");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        GetProjectCmd cmd = new GetProjectCmd(projectOptionList);

        cmd.exec(scan, null, null);
    }

    /**
     * Test execution of getProjectCmd exec when proj == null
     *
     * @throws IllegalArgumentException Never
     * @throws PermissionException Never
     * @throws CancelException Never
     * @see GetProjectCmd#exec(TerminalScanner, DataModel, User)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecWithNull() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead015", "Luky", "Luke");
        User admin = model.createAdministrator("admin015", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GetProjectCmd cmd = new GetProjectCmd();

        // Setup scenario
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosenProj = cmd.exec(scan, model, admin);

        // Test effects.
        assertNull(chosenProj);
    }

}
