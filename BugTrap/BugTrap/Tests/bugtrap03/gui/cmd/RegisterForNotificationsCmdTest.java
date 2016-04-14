package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.model.DataModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class RegisterForNotificationsCmdTest {

    @Before
    public void setup() throws Exception{
        model = new DataModel();
        question = new ArrayDeque<>();
        answer = new ArrayDeque<>();

        administratorRegisterCmd = model.createAdministrator("administratorRegisterCmd","first","last");
        developerRegisterCmd =  new Developer("developerRegisterCmd", "firstname", "lastname");
        projectRegisterCmd = model.createProject("projectRegisterCmd","testdescription", developerRegisterCmd, 1000, administratorRegisterCmd);
        subsystemRegisterCmd = model.createSubsystem(administratorRegisterCmd, projectRegisterCmd, "subsystemRegisterCmd","testdescription");
        bugReportRegisterCmd = model.createBugReport(subsystemRegisterCmd, developerRegisterCmd, "", "",
                PList.<BugReport>empty(), new Milestone(1,1,1), false);

        cmd = new RegisterForNotificationsCmd();

    }

    private static DataModel model;
    private static Administrator administratorRegisterCmd;
    private static ArrayDeque<String> question;
    private static ArrayDeque<String> answer;

    private static Project projectRegisterCmd;
    private static Subsystem subsystemRegisterCmd;
    private static BugReport bugReportRegisterCmd;
    private static Developer developerRegisterCmd;

    private static RegisterForNotificationsCmd cmd;

    @Test
    public void testProjectCreation() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("1");
        //register from project
        //select project
        question.add("Available options:");
        question.add("0. " + projectRegisterCmd.getName() + " version: " + projectRegisterCmd.getVersionID().toString());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectRegisterCmd.getDetails());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. creation");
        question.add("2. newtag");
        question.add("3. specifictags");
        question.add("I choose: ");
        answer.add("1");
        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
    }

    @Test
    public void testProjectComment() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("1");
        //register from project
        //select project
        question.add("Available options:");
        question.add("0. " + projectRegisterCmd.getName() + " version: " + projectRegisterCmd.getVersionID().toString());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectRegisterCmd.getDetails());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. creation");
        question.add("2. newtag");
        question.add("3. specifictags");
        question.add("I choose: ");
        answer.add("0");
        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
    }

    @Test
    public void testProjectAllTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("1");
        //register from project
        //select project
        question.add("Available options:");
        question.add("0. " + projectRegisterCmd.getName() + " version: " + projectRegisterCmd.getVersionID().toString());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectRegisterCmd.getDetails());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. creation");
        question.add("2. newtag");
        question.add("3. specifictags");
        question.add("I choose: ");
        answer.add("2");
        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
    }

    @Test
    public void testProjectSpecificTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("1");
        //register from project
        //select project
        question.add("Available options:");
        question.add("0. " + projectRegisterCmd.getName() + " version: " + projectRegisterCmd.getVersionID().toString());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectRegisterCmd.getDetails());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. creation");
        question.add("2. newtag");
        question.add("3. specifictags");
        question.add("I choose: ");
        answer.add("3");
        // select tags
        question.add("Please select tag.");
        question.add("Available options:");
        PList<Tag> taglist = model.getAllTags();
        int i = 0;
        for(Tag tag: taglist){
            question.add(i + ". " + tag.name());
            i++;
        }
        question.add("I choose: ");
        answer.add("0");
        question.add("Tag " + Tag.NEW.toString() + " added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("Y");

        question.add("Please select tag.");
        question.add("Available options:");
        i = 0;
        for(Tag tag: taglist){
            question.add(i + ". " + tag.name());
            i++;
        }
        question.add("I choose: ");
        answer.add("1");
        question.add("Tag " + Tag.ASSIGNED + " added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("N");

        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
    }

    @Test
    public void testSubsystemCreation() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("2");
        //register from project
        //select subsystem cmd
        //select project
        question.add("Available options:");
        question.add("0. " + projectRegisterCmd.getName() + " version: " + projectRegisterCmd.getVersionID().toString());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectRegisterCmd.getDetails());
        //select subsystem itself
        question.add("Select subsystem.");
        question.add("Available options:");
        question.add("0. " + subsystemRegisterCmd.getName());
        question.add("I choose: ");
        answer.add("0");
        question.add("You selected: " + subsystemRegisterCmd.getName());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. creation");
        question.add("2. newtag");
        question.add("3. specifictags");
        question.add("I choose: ");
        answer.add("1");
        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
    }

    @Test
    public void testSubsystemComment() throws Exception {

    }

    @Test
    public void testSubsystemAllTags() throws Exception {

    }

    @Test
    public void testSubsystemSpecificTags() throws Exception {

    }

    @Test
    public void testBugReportComment() throws Exception {

    }

    @Test
    public void testBugReportAllTags() throws Exception {

    }

    @Test
    public void testBugReportSpecificTags() throws Exception {

    }

    @Test (expected = IllegalArgumentException.class)
    public void testScanNull() throws Exception {
        // Execute scenario
        Mailbox chosen = cmd.exec(null, model, developerRegisterCmd);
        // Test effects.
    }

    @Test (expected = IllegalArgumentException.class)
    public void testModelNull() throws Exception {
        //set up scenario
        answer.add("dummy");
        question.add("dummy");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox chosen = cmd.exec(scan, null, developerRegisterCmd);
        // Test effects.
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUserNull() throws Exception {
        //set up scenario
        answer.add("dummy");
        question.add("dummy");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox chosen = cmd.exec(scan, model, null);
        // Test effects.
    }


}