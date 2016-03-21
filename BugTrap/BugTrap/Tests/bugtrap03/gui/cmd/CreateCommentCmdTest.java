package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.bugreport.BugReport;
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
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

import static org.junit.Assert.assertTrue;

/**
 * @author Group 03
 */
public class CreateCommentCmdTest {

    Comment chosen;

    @Test
    public void testExecIndexTitleOnBugReport() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trollol", "Luky", "Luke");
        Issuer issuer = model.createIssuer("Cows", "Fly", "High");
        Administrator admin = model.createAdministrator("Admiral", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        Subsystem subsystemA1 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA1",
                "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.2",
                "Description of susbsystem A3.2");

        BugReport bugRep2 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport>empty(), subsystemA2);
        BugReport bugRep1 = model.createBugReport(issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                subsystemA2);
        BugReport bugRep3 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        CreateCommentCmd cmd = new CreateCommentCmd();

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
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("Please select a comment: ");
        question.add("Available comments:");
        question.add("I choose (Nr): (leave blank to create comment on the bugreport)");
        answer.add("");
        question.add("You want to create a comment on the selected bug report");
        question.add("Please enter the text of the comment: ");
        answer.add("Holy Cows = Holy beef?");
        question.add("Comment created");
        // answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Comment createdComment = cmd.exec(scan, model, issuer);
        // Test effects.
        assertTrue(createdComment.containedIn(bugRep1.getAllComments()));
    }

    @Test
    public void testExecIndexTitleOnComment() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trolbol", "Luky", "Luke");
        Issuer issuer = model.createIssuer("C0ws", "Fly", "High");
        Administrator admin = model.createAdministrator("Adm1ral", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        Subsystem subsystemA1 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA1",
                "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.2",
                "Description of susbsystem A3.2");

        BugReport bugRep2 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport>empty(), subsystemA2);
        BugReport bugRep1 = model.createBugReport(issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(),
                subsystemA2);
        BugReport bugRep3 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), subsystemA3_1);

        Comment comment1 = model.createComment(issuer, bugRep1, "First comment!!! :D");
        Comment comment2 = model.createComment(issuer, comment1, "Inner commment, Fix asap");
        Comment comment3 = model.createComment(issuer, bugRep1, "Second. On a more serious note, true story.");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        CreateCommentCmd cmd = new CreateCommentCmd();

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
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("Please select a comment: ");
        question.add("Available comments:");
        question.add("0. " + comment1.getText());
        question.add("1. " + comment2.getText());
        question.add("2. " + comment3.getText());
        question.add("I choose (Nr): (leave blank to create comment on the bugreport)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose (Nr): (leave blank to create comment on the bugreport)");
        answer.add("50");
        question.add("Invalid input.");
        question.add("I choose (Nr): (leave blank to create comment on the bugreport)");
        answer.add("wrong input");
        question.add("Invalid input.");
        question.add("I choose (Nr): (leave blank to create comment on the bugreport)");
        answer.add("1");
        question.add("You want to create a comment on the selected comment: ");
        question.add(comment2.getText());
        question.add("Please enter the text of the comment: ");
        answer.add("Holy Cows = Holy beef?");
        question.add("Comment created");
        // answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Comment createdComment = cmd.exec(scan, model, issuer);
        // Test effects.        
        assertTrue(createdComment.containedIn(bugRep1.getAllComments()));
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
        CreateCommentCmd cmd = new CreateCommentCmd();
        chosen = cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
        CreateCommentCmd cmd = new CreateCommentCmd();
        DataModel model = new DataModel();
        Administrator admin = model.createAdministrator("adminneke", "admin", "admin");
        chosen = cmd.exec(null, model, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
        CreateCommentCmd cmd = new CreateCommentCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        Administrator admin = model.createAdministrator("adminneke2", "admin", "admin");
        chosen = cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
        CreateCommentCmd cmd = new CreateCommentCmd();
        DataModel model = new DataModel();
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
                new ArrayDeque<>());
        chosen = cmd.exec(scan, model, null);
    }

}
