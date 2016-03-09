package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 *
 */
public class UpdateBugReportCmdTest {

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Tries to set a tag while the current tag is NEW -&gt no permissions.
     */
    @Test(expected = PermissionException.class)
    public void testExecNewNewNoPermissions() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        BugReport bugRep2 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

        // Setup scenario
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("5");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, issuer);
        // Test effects.

    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Tries to set a tag while the current tag is Assigned but the user is
     * not an assigned test/programmer -&gt no permissions.
     */
    @Test(expected = PermissionException.class)
    public void testExecAssignedNoPermissions() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying0", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous0", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General0", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        BugReport bugRep2 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

        // Setup scenario
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("3");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, issuer);
        // Test effects.
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Set a tag from assigned to under review.
     */
    @Test
    public void testExecAssignedToUnderReview() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying1", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous1", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General1", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(lead, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        BugReport bugRep2 = model.createBugReport(lead, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer> empty().plus(lead));
        model.assignToProject(projectA, lead, lead, Role.TESTER);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

        // Setup scenario
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("3");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    // TODO: Test the Cmd to not only assign duplicate but the pointer to the
    // duplicate BR as well.
    
    /**
     * Test method for
     * {@link UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Set a tag from NEW to Duplicate as the lead.
     */
    @Test
    public void testExecNewToDuplicate() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying2", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous2", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General2", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        BugReport bugRep2 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

        // Setup scenario
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("wrongInput");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("DUPLICATE");
        question.add("You have selected: \t" + "DUPLICATE");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }
    
    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Set a tag from NEW TO resolved by creator.
     */
    @Test(expected = CancelException.class)
    public void testExecNewToResolved() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying3", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous3", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General3", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(lead, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        BugReport bugRep2 = model.createBugReport(lead, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

        // Setup scenario
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("wrongInput");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("5");
        question.add("You have selected: \t" + "RESOLVED");
        question.add("Invalid tag, select other tag");
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("abort");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

}
