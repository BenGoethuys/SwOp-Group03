package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.model.DataModel;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

/**
 * @author Mathias
 */
public class SelectMilestoneCmdTest {

    static DataModel model;
    static Developer lead;
    static Issuer issuer;
    static Administrator admin;

    static Project projectA;
    static Project projectB;
    static Project chosen;

    static Subsystem subsystemA1;
    static Subsystem subsystemA2;
    static Subsystem subsystemA3;
    static Subsystem subsystemA3_1;
    static Subsystem subsystemA3_2;

    static BugReport bugRep1;
    static BugReport bugRep2;
    static BugReport bugRep3;

    static Comment comment1;
    static Comment comment2;
    static Comment comment3;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("Folow1234567890987654321", "The", "Leader");
        issuer = model.createIssuer("Issu1234567890987654321", "er", "0071");
        admin = model.createAdministrator("Ad1234567890987654321", "Mi", "Ne20224");

        projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        projectB = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        subsystemA1 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA1",
                "Description of susbsystem A1");
        subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");
        subsystemA3_2 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.2",
                "Description of susbsystem A3.2");

        bugRep2 = model.createBugReport(subsystemA2, issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport>empty(), null, 1, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(), null, 1,
                false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, 1, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment");
        comment2 = model.createComment(issuer, comment1, "Inner commment");
        comment3 = model.createComment(issuer, bugRep1, "Second");
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.general.SelectMilestoneCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     *
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectMilestoneCmd cmd = new SelectMilestoneCmd();

        // Setup scenario
        question.add("Enter a new milestone: (format a.b.c...) ");
        answer.add("a");
        question.add("Invalid input. Please try again using format: a.b.c...");
        answer.add("1.2.3");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        cmd.exec(scan, model, lead);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        SelectMilestoneCmd cmd = new SelectMilestoneCmd();
        cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        SelectMilestoneCmd cmd = new SelectMilestoneCmd();
        DataModel model = new DataModel();
        cmd.exec(null, model, lead);
    }

}
