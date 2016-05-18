package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
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

/**
 * @author Mathias
 */
public class ShowPerformanceMetricsCmdTest {

    static Administrator admin;
    static DataModel model;
    static Developer lead;
    static Project projectA;
    static Project projectB;
    static Subsystem subsystemA1;
    static Subsystem subsystemA2;
    static Subsystem subsystemA3;
    static Subsystem subsystemA3_1;
    static Subsystem subsystemA3_2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("ShowPerform1", "Luky", "Luke");
        admin = model.createAdministrator("ShowPerform2", "adminT", "bie");
        projectA = model.createProject(new VersionID(), "ShowPerform3", "Project for testing 0", lead, 500, admin);
        projectB = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        subsystemA1 = model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
        subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
        subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
        subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of susbsystem A3.1");
        subsystemA3_2 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.ShowPerformanceMetricsCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     *
     * @throws CancelException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec() throws IllegalArgumentException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        ShowPerformanceMetricsCmd cmd = new ShowPerformanceMetricsCmd();

        // Setup scenario
        question.add("Please select a developer to show the performance metrics of.");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("I choose: ");
        answer.add("0");
        question.add("--Developer details--");
        question.add("name: " + lead.getFullName());
        question.add("--Performance Metrics--");
        question.add("- Reporting -");
        question.add("Duplicate bug reports submitted: 0");
        question.add("NotABug bug reports submitted: 0");
        question.add("Bug reports submitted: 0");
        question.add("- Leadership -");
        question.add("ShowPerform3:\n" + "\tAlgorithm 1: HEALTHY\n" + "\tAlgorithm 2: HEALTHY\n"
                + "\tAlgorithm 3: HEALTHY\n" + "ProjectTest1:\n" + "\tAlgorithm 1: HEALTHY\n"
                + "\tAlgorithm 2: HEALTHY\n" + "\tAlgorithm 3: HEALTHY\n");
        question.add("- Test Skills -");
        question.add("Average lines each test: 0.0");
        question.add("Tests submitted: 0");
        question.add("- Problem Solving -");
        question.add("Assigned to closed bug reports: 0");
        question.add("Assigned to unfinished bug reports: 0");
        question.add("Average lines each patch: 0.0");
        question.add("Patches submitted: 0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Object chosen = cmd.exec(scan, model, admin);

        // Test effects.
        assertEquals(chosen, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        ShowPerformanceMetricsCmd cmd = new ShowPerformanceMetricsCmd();
        cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        ShowPerformanceMetricsCmd cmd = new ShowPerformanceMetricsCmd();
        DataModel model1 = new DataModel();
        cmd.exec(null, model1, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        ShowPerformanceMetricsCmd cmd = new ShowPerformanceMetricsCmd();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        cmd.exec(scan, null, admin);
    }

    @Test(expected = CancelException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        ShowPerformanceMetricsCmd cmd = new ShowPerformanceMetricsCmd();
        DataModel model2 = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        cmd.exec(scan, model2, null);
    }

}
