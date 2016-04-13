/**
 * 
 */
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
public class SetDuplicateBugReportCmdTest {

    Subsystem subsystemA1;
    Subsystem subsystemA2;
    Subsystem subsystemA3;
    Subsystem subsystemA3_1;
    Subsystem subsystemA3_2;
    BugReport bugRep1;
    BugReport bugRep2;
    BugReport bugRep3;
    BugReport chosen;
    Comment comment1;
    Comment comment2;
    Comment comment3;
    Project proj1;
    DataModel model;
    Developer lead;
    Issuer issuer;
    Administrator admin;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.SetDuplicateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec() throws IllegalArgumentException, PermissionException, CancelException {
        model = new DataModel();
        lead = model.createDeveloper("Ploperdeplop010", "Luky", "Luke");
        issuer = model.createIssuer("Ploperdeplop011", "Fly", "High");
        admin = model.createAdministrator("Ploperdeplop012", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

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
                PList.<BugReport> empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport> empty(), null,
                false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SetDuplicateBugReportCmd cmd = new SetDuplicateBugReportCmd();

        // Setup scenario
        question.add("Please select the bug report that will be given the Tag DUPLICATE: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("Please select the duplicate bug report: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        // Test effects.
        assertEquals(chosen, bugRep1);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.SetDuplicateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec2() throws IllegalArgumentException, PermissionException, CancelException {
        model = new DataModel();
        lead = model.createDeveloper("Ploperdeplop013", "Luky", "Luke");
        issuer = model.createIssuer("Ploperdeplop014", "Fly", "High");
        admin = model.createAdministrator("Ploperdeplop015", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

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
                PList.<BugReport> empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport> empty(), null,
                false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SetDuplicateBugReportCmd cmd = new SetDuplicateBugReportCmd();

        // Setup scenario
        question.add("Please select the bug report that will be given the Tag DUPLICATE: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("Please select the duplicate bug report: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("1");
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        // Test effects.
        assertEquals(chosen, bugRep1);
        assertNotEquals(chosen, bugRep2);
        assertNotEquals(chosen, bugRep3);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.SetDuplicateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec3() throws IllegalArgumentException, PermissionException, CancelException {
        model = new DataModel();
        lead = model.createDeveloper("Ploperdeplop016", "Luky", "Luke");
        issuer = model.createIssuer("Ploperdeplop017", "Fly", "High");
        admin = model.createAdministrator("Ploperdeplop018", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

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
                PList.<BugReport> empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport> empty(), null,
                false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SetDuplicateBugReportCmd cmd = new SetDuplicateBugReportCmd(bugRep1);

        // Setup scenario
        question.add("Please select the duplicate bug report: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("1");
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        // Test effects.
        assertEquals(chosen, bugRep1);
        assertNotEquals(chosen, bugRep2);
        assertNotEquals(chosen, bugRep3);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.SetDuplicateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec4() throws IllegalArgumentException, PermissionException, CancelException {
        model = new DataModel();
        lead = model.createDeveloper("Ploperdeplop019", "Luky", "Luke");
        issuer = model.createIssuer("Ploperdeplop020", "Fly", "High");
        admin = model.createAdministrator("Ploperdeplop021", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

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
                PList.<BugReport> empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport> empty(), null,
                false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SetDuplicateBugReportCmd cmd = new SetDuplicateBugReportCmd(bugRep3);

        // Setup scenario
        question.add("Please select the duplicate bug report: ");
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("2. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("1");
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        // Test effects.
        assertEquals(chosen, bugRep1);
        assertNotEquals(chosen, bugRep2);
        assertNotEquals(chosen, bugRep3);
    }

}
