package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class MergeSubsystemsModelCmdTest {

    private Administrator admin;
    private Developer lead;
    private DataModel model;
    private Project projectA;
    private Subsystem subsystemA1;
    private Subsystem subsystemA2;
    private Subsystem subsystemA3;
    private Subsystem subsystemA3_1;
    private Subsystem subsystemA3_1_1;
    private Subsystem subsystemA3_2;
    private BugReport bugRep1;
    private BugReport bugRep2;

    static int counter = 0;

    @Before
    public void setUp() throws Exception {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("MergeSub_1_" + counter, "Luky", "Luke");
        admin = model.createAdministrator("MergeSub_2_" + counter, "adminT", "bie");
        Issuer issuer = model.createIssuer("MergeSub_3_" + counter, "BMW", "looks", "nice");
        projectA = model.createProject(new VersionID(), "MergeSub3", "Project for testing 0", lead, 500, admin);

        // make subsystems
        subsystemA1 = model.createSubsystem(admin, projectA, "SubsystemA1", "Description of subsystem A1");
        subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of subsystem A2");
        subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of subsystem A3");
        subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of subsystem A3.1");
        subsystemA3_1_1 = model.createSubsystem(admin, subsystemA3_1, "SubsystemA3.1.1", "Description of subsystem A3.1.1");
        subsystemA3_2 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of subsystem A3.2");

        bugRep1 = model.createBugReport(subsystemA3, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, 1, false);

        bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Second bug report", "title says it all.",
                PList.<BugReport>empty(), null, 1, false);

        counter++;
    }

    @Test
    public void testExec() throws Exception {
        String newName = "The new name";
        String newDesc = "The new description";
        MergeSubsystemsModelCmd cmd = new MergeSubsystemsModelCmd(admin, subsystemA3_1, subsystemA3,
               newName , newDesc);

        Subsystem result = cmd.exec();

        assertTrue(cmd.isExecuted());
        assertTrue(subsystemA3_1.isTerminated());

        assertEquals(newName, result.getName());
        assertEquals(newDesc, result.getDescription());

        assertTrue(result.getBugReportList().contains(bugRep1));
        assertTrue(result.getBugReportList().contains(bugRep2));

        assertTrue(result.getAllSubsystems().contains(subsystemA3_1_1));
        assertTrue(result.getAllSubsystems().contains(subsystemA3_2));
    }

    @Test
    public void testExecDifferentOrder() throws Exception {
        String newName = "The new name";
        String newDesc = "The new description";
        MergeSubsystemsModelCmd cmd = new MergeSubsystemsModelCmd(admin, subsystemA3, subsystemA3_1,
                newName , newDesc);

        Subsystem result = cmd.exec();

        assertTrue(cmd.isExecuted());
        assertTrue(subsystemA3_1.isTerminated());

        assertEquals(newName, result.getName());
        assertEquals(newDesc, result.getDescription());

        assertTrue(result.getBugReportList().contains(bugRep1));
        assertTrue(result.getBugReportList().contains(bugRep2));

        assertTrue(result.getAllSubsystems().contains(subsystemA3_1_1));
        assertTrue(result.getAllSubsystems().contains(subsystemA3_2));
    }

    @Test
    public void testUndo() throws Exception {
        String newName = "The new name";
        String newDesc = "The new description";
        MergeSubsystemsModelCmd cmd = new MergeSubsystemsModelCmd(admin, subsystemA3_1, subsystemA3,
                newName , newDesc);

        Subsystem result = cmd.exec();

        assertTrue(cmd.isExecuted());
    }

    @Test
    public void testToString() throws Exception {

    }

}