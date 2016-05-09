package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class CreateBugReportCmdTest {

    BugReport chosen;

    @Test
    public void testExecByIndex() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();

        Developer lead = model.createDeveloper("meGoodLead125", "Luky", "Luke");
        Developer maria = model.createDeveloper("Mae146", "Maria", "developer");
        Issuer charlie = model.createIssuer("noDev125", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin125", "adminT", "bie");
        // create projectA
        Project projectA = model.createProject(new VersionID(), "ProjectA", "Description of projectA", lead, 10000, admin);
        // add asked roles
        model.assignToProject(projectA, lead, lead, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, maria, Role.TESTER);
        // make subsystems
        model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1",
                "Description of susbsystem A3.1");
        model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");
        // make bug report 2
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, charlie, "Crash while processing user input",
                "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15),
                PList.<BugReport>empty(), null, 1, false, null, null, null);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plusAll(Arrays.asList(lead, maria)));
        // mak bug report 3
        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "SubsystemA2 feezes",
                "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4),
                PList.<BugReport>empty(), null, 1, false, null, null, null);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        CreateBugReportCmd cmd = new CreateBugReportCmd();

        // Setup scenario
        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. ProjectA version: " + projectA.getVersionID());
        question.add("I choose: ");
        answer.add("test wrong input");
        question.add("Invalid input.");
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
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("3");
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("BugReport title:");
        answer.add("BR Title");
        question.add("BugReport description:");
        answer.add("tester description");
        question.add("- Additional attributes - Leave blank when not applicable.");
        question.add("How to reproduce the bug: ");
        answer.add("");
        question.add("Stacktrace: ");
        answer.add("");
        question.add("Error message: ");
        answer.add("");
        question.add("Should this be private? (leave blank for no, anything for yes.)");
        answer.add("");
        question.add("Choose a dependency.");
        question.add("Available bugReports:");
        question.add("0. " + bugRep1.getTitle() + " " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + " " + bugRep2.getUniqueID());
        question.add("I choose: (leave blank if done)");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("wrong input here");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("0");
        question.add("Added dependency: " + bugRep1.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add(bugRep2.getTitle());
        question.add("Added dependency: " + bugRep2.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add("");
        question.add("Ended selection.");
        question.add("Give the impact factor I of the bug report: (0 < I <= 10)");
        question.add("Give number: ");
        answer.add("3");

        // answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport bugReport = cmd.exec(scan, model, maria);

        // Test effects.
    }

    @Test
    public void testExecByName() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();

        Developer lead = model.createDeveloper("meGoodLead146", "Luky", "Luke");
        Developer maria = model.createDeveloper("Mae46", "Maria", "developer");
        Issuer charlie = model.createIssuer("noDev146", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin146", "adminT", "bie");
        // create projectA
        Project projectA = model.createProject(new VersionID(), "ProjectA", "Description of projectA", lead, 10000, admin);
        // add asked roles
        model.assignToProject(projectA, lead, lead, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, maria, Role.TESTER);
        // make subsystems
        model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1",
                "Description of susbsystem A3.1");
        model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");
        // make bug report 2
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, charlie, "Crash while processing user input",
                "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15),
                PList.<BugReport>empty(), null, 1, false, null, null, null);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plusAll(Arrays.asList(lead, maria)));
        // mak bug report 3
        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "SubsystemA2 feezes",
                "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4),
                PList.<BugReport>empty(), null, 1, false, null, null, null);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        CreateBugReportCmd cmd = new CreateBugReportCmd();

        // Setup scenario
        question.add("Select a project.");
        question.add("Available options:");
        question.add("0. ProjectA version: " + projectA.getVersionID());
        question.add("I choose: ");
        answer.add(" test wrong input");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(projectA.getName() + projectA.getVersionID().toString());
        question.add("You have chosen:");
        question.add(projectA.getDetails());
        question.add("Available options:");
        question.add("0. SubsystemA1");
        question.add("1. SubsystemA2");
        question.add("2. SubsystemA3");
        question.add("3. SubsystemA3.1");
        question.add("4. SubsystemA3.2");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(subsystemA3_1.getName());
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("BugReport title:");
        answer.add("BR Title");
        question.add("BugReport description:");
        answer.add("tester description");
        question.add("- Additional attributes - Leave blank when not applicable.");
        question.add("How to reproduce the bug: ");
        answer.add("");
        question.add("Stacktrace: ");
        answer.add("");
        question.add("Error message: ");
        answer.add("");
        question.add("Should this be private? (leave blank for no, anything for yes.)");
        answer.add("");
        question.add("Choose a dependency.");
        question.add("Available bugReports:");
        question.add("0. " + bugRep1.getTitle() + " " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + " " + bugRep2.getUniqueID());
        question.add("I choose: (leave blank if done)");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("wrong input here");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("0");
        question.add("Added dependency: " + bugRep1.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add(bugRep2.getTitle());
        question.add("Added dependency: " + bugRep2.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add("");
        question.add("Ended selection.");
        question.add("Give the impact factor I of the bug report: (0 < I <= 10)");
        question.add("Give number: ");
        answer.add("0");
        question.add("Give the impact factor I of the bug report: (0 < I <= 10)");
        question.add("Give number: ");
        answer.add("11");
        question.add("Give the impact factor I of the bug report: (0 < I <= 10)");
        question.add("Give number: ");
        answer.add("3");

        // answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport bugReport = cmd.exec(scan, model, maria);

        // Test effects.
        assertEquals(bugReport.getTitle(), "BR Title");
        assertEquals(bugReport.getDescription(), "tester description");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        CreateBugReportCmd cmd = new CreateBugReportCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        CreateBugReportCmd cmd = new CreateBugReportCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        CreateBugReportCmd cmd = new CreateBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        CreateBugReportCmd cmd = new CreateBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }
}
