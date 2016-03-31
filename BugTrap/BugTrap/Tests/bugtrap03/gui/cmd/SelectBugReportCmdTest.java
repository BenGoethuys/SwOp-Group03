package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class SelectBugReportCmdTest {

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

    /**
     *
     */
    @Test
    public void testTitleFilterIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trolbol007", "Luky", "Luke");
        Issuer issuer = model.createIssuer("C0ws007", "Fly", "High");
        Administrator admin = model.createAdministrator("Adm1ral007", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("tester");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("title");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("bugRep");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep1);
    }

    /**
     *
     */
    @Test
    public void testTitleFilterName() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trolbol00", "Luky", "Luke");
        Issuer issuer = model.createIssuer("C0ws00", "Fly", "High");
        Administrator admin = model.createAdministrator("Adm1ral00", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("tester");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("title");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("bugRep");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("25");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("wrong input");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        answer.add(bugRep1.getTitle());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep1);
    }

    /**
     *
     */
    @Test
    public void testTitleDescriptionIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD0", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL0", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED0", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("description");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("tester");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("description");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("createcomment");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep1);
    }

    @Test
    public void testTitleDescIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("desc");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("tesTer");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("desc");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("createCOmment");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep1);
    }

    @Test
    public void testTitleCreatorIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD1", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL1", "Fly", "High");
        Issuer noBugIssuer = model.createIssuer("MHmm", "Okeee", "PO");
        Administrator admin = model.createAdministrator("ABCED1", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("creator");
        question.add("Please enter the required search term ...");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("1. " + issuer.getUsername());
        question.add("2. " + noBugIssuer.getUsername());
        question.add("I choose: ");
        answer.add("tester");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("2");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("creator");
        question.add("Please enter the required search term ...");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("1. " + issuer.getUsername());
        question.add("2. " + noBugIssuer.getUsername());
        question.add("I choose: ");
        answer.add("1");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep3);
    }

    @Test
    public void testTitleUniqueIDIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD6", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL6", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED6", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("uniqueId");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add("tester");
        question.add("Invalid input, please enter a number");
        answer.add("128700549");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("uniqueId");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(Long.toString(bugRep2.getUniqueID()));
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep2);
    }

    @Test
    public void testTitleIDIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD7", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL7", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED7", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("id");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add("tester");
        question.add("Invalid input, please enter a number");
        answer.add("128700549");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("id");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(Long.toString(bugRep2.getUniqueID()));

        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep2);
    }

    @Test
    public void testTitleAssignedIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD8", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL8", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED8", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        model.addUsersToBugReport(lead, bugRep3, PList.<Developer>empty().plus(lead));
        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("assigned");
        question.add("Please enter the required search term ...");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("I choose: ");
        answer.add("tester");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep3.getTitle() + "\t -UniqueID: " + bugRep3.getUniqueID());
        question.add("I choose: ");
        answer.add("0");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep3);
    }

    @Test
    public void testTitleUniqueIDName() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("HMXD9", "Luky", "Luke");
        Issuer issuer = model.createIssuer("CFDL9", "Fly", "High");
        Administrator admin = model.createAdministrator("ABCED9", "Kwinten", "JK");

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
                PList.<BugReport>empty(), null, false);
        bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                null, false);
        bugRep3 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, false);

        comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectBugReportCmd cmd = new SelectBugReportCmd();

        // Setup scenario
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("uniqueId");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add("tester");
        question.add("Invalid input, please enter a number");
        answer.add("128700549");
        question.add("Please select a bug report: ");
        question.add("No options found.");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("uniqueId");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(Long.toString(bugRep2.getUniqueID()));
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("I choose: ");
        answer.add(bugRep2.getTitle());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, issuer);
        // Test effects.
        assertEquals(chosen, bugRep2);
    }

    /**
     * Add the searchMode options + first line to question. Please select a
     * search mode.. <b> 0.. <b> 1.. <b> ..
     *
     * @param question
     */
    private void addSearchModeOptions(ArrayDeque<String> question) {
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        SelectBugReportCmd cmd = new SelectBugReportCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        SelectBugReportCmd cmd = new SelectBugReportCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        SelectBugReportCmd cmd = new SelectBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        SelectBugReportCmd cmd = new SelectBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }
}
