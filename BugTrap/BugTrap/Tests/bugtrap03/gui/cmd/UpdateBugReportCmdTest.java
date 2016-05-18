package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
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

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class UpdateBugReportCmdTest {

    BugReport chosen;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Tries to set a tag while the current tag is NEW -&gt no permissions.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecNewNewNoPermissions() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: \tNEW");
        question.add("The given Tag cannot be set");
        question.add("Command cancelled. Execute a new command.");

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

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
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
        question.add("You have selected: \tUNDER_REVIEW");
        question.add("Adding test.");
        question.add("Do you wish to submit a file? Please type yes or no.");
        answer.add("no");
        question.add("You have chosen to insert text. (Leave blank to finish the text)");
        question.add("text: ");
        answer.add("test\n");
        question.add("The given user doesn't have the permission to add a test");
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

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, lead, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plus(lead));
        model.assignToProject(projectA, lead, lead, Role.TESTER);
        model.addTest(bugRep2, lead, "test here");

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
        question.add(model.getDetails(issuer, bugRep2));
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
        question.add("You have selected: \tUNDER_REVIEW");
        question.add("Adding test.");
        question.add("Do you wish to submit a file? Please type yes or no.");
        answer.add("no");
        question.add("You have chosen to insert text. (Leave blank to finish the text)");
        question.add("text: ");
        answer.add("test\n");
        question.add("Added test.");
        question.add("The tag UNDER_REVIEW has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

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

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
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
        question.add("0. " + bugRep2.getTitle() + "\t -UniqueID: " + bugRep2.getUniqueID());
        question.add("1. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");
        answer.add("1");
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("The tag DUPLICATE has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
        assertEquals(bugRep1, updatedBR.getDuplicate());
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * . Set a tag from NEW TO resolved by creator.
     */
    @Test(expected = IllegalStateException.class)
    public void testExecNewToResolved() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying3", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous3", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General3", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, lead, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
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
        question.add("Selecting patch.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    @Test
    public void testExecNewToAssigned() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying4", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous4", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General4", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
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
        answer.add("ASSIGNED");
        question.add("You have selected: \t" + "ASSIGNED");
        question.add("Available developers: ");
        question.add("0. " + lead.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("0");
        question.add("Added " + lead.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("");
        question.add("Finished assigning.");
        question.add("The tag ASSIGNED has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    @Test
    public void testExecUnderReviewToResolved() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying5", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous5", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General5", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, lead, Role.PROGRAMMER);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, lead, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plus(lead));
        model.assignToProject(projectA, lead, lead, Role.TESTER);
        model.addTest(bugRep2, lead, "test here");
        model.addPatch(bugRep2, lead, "Test");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

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
        question.add(model.getDetails(issuer, bugRep2));
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
        answer.add("RESOLVED");
        question.add("You have selected: \tRESOLVED");
        question.add("Selecting patch.");
        question.add("Use the index in front of the patch to select a patch.");
        question.add("Available options:");
        question.add("0. Test");
        question.add("I choose: ");
        answer.add("0");
        question.add("The selected patch is set to: Test");
        question.add("The tag RESOLVED has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    @Test
    public void testExecResolvedToClosed() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying6", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous6", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General6", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, lead, Role.PROGRAMMER);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, lead, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plus(lead));
        model.assignToProject(projectA, lead, lead, Role.TESTER);
        model.addTest(bugRep2, lead, "test here");
        model.addPatch(bugRep2, lead, "Test");
        model.selectPatch(bugRep2, lead, "Test");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

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
        question.add(model.getDetails(issuer, bugRep2));
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
        answer.add("CLOSED");
        question.add("You have selected: \tCLOSED");
        question.add("Give a score between 1 and 5: ");
        question.add("Give number: ");
        answer.add("3");
        question.add("The score is set to: 3");
        question.add("The tag CLOSED has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    @Test
    public void testExecNewToNotABug() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying7", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous7", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General7", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, issuer, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, issuer, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);

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
        question.add(model.getDetails(issuer, bugRep2));
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
        answer.add("NOT_A_BUG");
        question.add("You have selected: \t" + "NOT_A_BUG");
        question.add("The tag NOT_A_BUG has been set.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    @Test
    public void testExecUnderReviewToAssigned() throws IllegalArgumentException, PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("Flying8", "Cars", "nice");
        Issuer issuer = model.createIssuer("Autonomous8", "BMW", "looks", "nice");
        Administrator admin = model.createAdministrator("General8", "Kwinten", "JK");

        Project projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, lead, Role.PROGRAMMER);
        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        BugReport bugRep1 = model.createBugReport(subsystemA2, lead, "bugRep over here",
                "createComment has an output error", PList.<BugReport>empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsystemA3_1, lead, "Used library not in repository",
                "title says it all.", PList.<BugReport>empty(), null, 1, false);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plus(lead));
        model.assignToProject(projectA, lead, lead, Role.TESTER);
        model.addTest(bugRep2, lead, "test here");
        model.addPatch(bugRep2, lead, "Test");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();

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
        question.add(model.getDetails(issuer, bugRep2));
        question.add("Available options:");
        question.add("0. NEW");
        question.add("1. ASSIGNED");
        question.add("2. NOT_A_BUG");
        question.add("3. UNDER_REVIEW");
        question.add("4. CLOSED");
        question.add("5. RESOLVED");
        question.add("6. DUPLICATE");
        question.add("I choose: ");
        answer.add("ASSIGNED");
        question.add("You have selected: \tASSIGNED");
        question.add("The tag ASSIGNED has been set.");


        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport updatedBR = cmd.exec(scan, model, lead);
        // Test effects.
    }

    // under_review => assigned
    // random => assigned : illegal

    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }
}
