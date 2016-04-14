package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 0.3
 */
public class GetSubsystemCmdTest {

    private DataModel model;
    private Administrator admin;
    private Developer lead;
    private Project project1;
    private Project project2;
    private Subsystem subsys1;
    private Subsystem subsys2;
    private Subsystem subsys11;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("Jammer123" + counter, "first", "last");
        lead = model.createDeveloper("Jamme1r123" + counter, "first", "last");
        project1 = model.createProject("Project1", "ProjDesc", lead, 100, admin);
        project2 = model.createProject("Project2", "projDesc", lead, 100, admin);
        subsys1 = model.createSubsystem(admin, project1, "subsys1", "desc");
        subsys2 = model.createSubsystem(admin, project1, "subsys2", "desc");
        model.createSubsystem(admin, project2, "testExtra", "desc");
        subsys11 = model.createSubsystem(admin, subsys1, "subsys11", "desc");

        counter++;
    }

    @Test
    public void testExec() throws PermissionException, CancelException {
        ArrayDeque<String> question = GetProjectCmdTest.getDefaultQuestions(project1, project2);
        ArrayDeque<String> answer = GetProjectCmdTest.getDefaultAnswers(project1, project2);
        GetSubsystemCmd cmd = new GetSubsystemCmd();

        // Setup scenario
        question.add("Select subsystem.");
        question.add("Available options:");
        question.add("0. " + subsys1.getName());
        question.add("1. " + subsys11.getName());
        question.add("2. " + subsys2.getName());
        question.add("I choose: ");
        answer.add("1");
        question.add("You selected: " + subsys11.getName());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Subsystem subsys = cmd.exec(scan, model, null);

        assertEquals(subsys11, subsys);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_NullScan() throws PermissionException, CancelException {
        GetSubsystemCmd cmd = new GetSubsystemCmd();
        cmd.exec(null, model, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_NullModel() throws PermissionException, CancelException {
        ArrayDeque<String> question = GetProjectCmdTest.getDefaultQuestions(project1, project2);
        ArrayDeque<String> answer = GetProjectCmdTest.getDefaultAnswers(project1, project2);
        GetSubsystemCmd cmd = new GetSubsystemCmd();

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Subsystem subsys = cmd.exec(scan, null, null);
    }
    
    
    @Test
    public void testExec_ByName() throws PermissionException, CancelException {
        ArrayDeque<String> question = GetProjectCmdTest.getDefaultQuestions(project1, project2);
        ArrayDeque<String> answer = GetProjectCmdTest.getDefaultAnswers(project1, project2);
        GetSubsystemCmd cmd = new GetSubsystemCmd();

        // Setup scenario
        question.add("Select subsystem.");
        question.add("Available options:");
        question.add("0. " + subsys1.getName());
        question.add("1. " + subsys11.getName());
        question.add("2. " + subsys2.getName());
        question.add("I choose: ");
        answer.add(subsys11.getName());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Subsystem subsys = cmd.exec(scan, model, null);

        assertEquals(subsys11, subsys);
    }

}
