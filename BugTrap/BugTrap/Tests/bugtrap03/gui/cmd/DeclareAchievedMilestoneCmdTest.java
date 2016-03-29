package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * @author Mathias
 *
 */
public class DeclareAchievedMilestoneCmdTest {

    DataModel model;
    Developer lead;
    Issuer issuer;
    Administrator admin;

    Project projectA;
    Project projectB;
    Project chosen;

    Subsystem subsystemA1;
    Subsystem subsystemA2;
    Subsystem subsystemA3;
    Subsystem subsystemA3_1;
    Subsystem subsystemA3_2;

    BugReport bugRep1;
    BugReport bugRep2;
    BugReport bugRep3;

    Comment comment1;
    Comment comment2;
    Comment comment3;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.DeclareAchievedMilestoneCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws PermissionException
     * @throws IllegalArgumentException
     * @throws CancelException
     */
    @Test
    public void testExec() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("Folow", "The", "Leader");
        issuer = model.createIssuer("Issu", "er", "007");
        admin = model.createAdministrator("Ad", "Mi", "Ne20224");

        projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        projectB = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

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

        bugRep2 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        bugRep1 = model.createBugReport(issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport> empty(),
                subsystemA2);
        bugRep3 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        DeclareAchievedMilestoneCmd cmd = new DeclareAchievedMilestoneCmd();

        // Setup scenario
        question.add("Available options:");
        question.add("0. ProjectTest0 version: 0.0.1");
        question.add("1. ProjectTest1 version: 0.0.1");
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectA.getDetails());
        question.add("Available options:");
        question.add("0. SubsystemA1");
        question.add("1. SubsystemA2");
        question.add("2. SubsystemA3");
        question.add("3. SubsystemA3.1");
        question.add("4. SubsystemA3.2");
        question.add("I choose: ");
        answer.add("0");
        question.add("The currently achieved milestone: M0");
        question.add("Enter a new milestone: (M1.2.3)  ");
        answer.add("M1.2.3");
        // TODO Complete scenario.

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosen = cmd.exec(scan, model, issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        DeclareAchievedMilestoneCmd cmd = new DeclareAchievedMilestoneCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        DeclareAchievedMilestoneCmd cmd = new DeclareAchievedMilestoneCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        DeclareAchievedMilestoneCmd cmd = new DeclareAchievedMilestoneCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        DeclareAchievedMilestoneCmd cmd = new DeclareAchievedMilestoneCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }
}
