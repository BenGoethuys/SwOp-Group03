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
    @BeforeClass
    public static void setUpBeforeClass(){
        index = 0;
    }


    @Before
    public void setup() throws Exception{
        index++;
        model = new DataModel();
        question = new ArrayDeque<>();
        answer = new ArrayDeque<>();

        administratorRegisterCmd = model.createAdministrator("administratorRegisterCmd" + index,"first","last");
        developerRegisterCmd =  new Developer("developerRegisterCmd" + index, "firstname", "lastname");
        projectRegisterCmd = model.createProject("projectRegisterCmd","testdescription", developerRegisterCmd, 1000, administratorRegisterCmd);
        subsystemRegisterCmd = model.createSubsystem(administratorRegisterCmd, projectRegisterCmd, "subsystemRegisterCmd","testdescription");
        bugReportRegisterCmd = model.createBugReport(subsystemRegisterCmd, developerRegisterCmd, "", "",
                PList.<BugReport>empty(), new Milestone(1,1,1), false);

        cmd = new RegisterForNotificationsCmd();

    }

    private static int index;

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
        question.add("Tag " + Tag.ASSIGNED.toString() + " added to subscription.");
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
        answer.add("2");
        question.add("Tag " + Tag.NOT_A_BUG.toString() + " added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("trololol");
        question.add("Invalid input, selecting of tags considered complete.");
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
        //register from subsystem
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
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("2");
        //register from subsystem
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
    public void testSubsystemAllTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("2");
        //register from subsystem
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
    public void testSubsystemSpecificTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("2");
        //register from subsystem
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
    public void testBugReportComment() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("0");
        //register from bugreport
        //select bugreport
        this.addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("4");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(bugReportRegisterCmd.getUniqueID() + "");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. newtag");
        question.add("2. specifictags");
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
    public void testBugReportAllTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("0");
        //register from bugreport
        //select bugreport
        this.addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("4");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(bugReportRegisterCmd.getUniqueID() + "");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. newtag");
        question.add("2. specifictags");
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
    public void testBugReportSpecificTags() throws Exception {
        //set up scenario
        //register for notifications cmd
        question.add("Select subject type.");
        question.add("Available options:");
        question.add("0. bugreport");
        question.add("1. project");
        question.add("2. subsystem");
        question.add("I choose: ");
        answer.add("0");
        //register from bugreport
        //select bugreport
        this.addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("4");
        question.add("Please enter the required search term ...");
        question.add("Give number: ");
        answer.add(bugReportRegisterCmd.getUniqueID() + "");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugReportRegisterCmd.getTitle() + "\t -UniqueID: " + bugReportRegisterCmd.getUniqueID());
        //register from as
        question.add("Select subscription type.");
        question.add("Available options:");
        question.add("0. comment");
        question.add("1. newtag");
        question.add("2. specifictags");
        question.add("I choose: ");
        answer.add("2");
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
        answer.add("N");
        // end register for notifications
        question.add("Registration for notifications complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        Mailbox newMB = cmd.exec(scan, model, developerRegisterCmd);
        // Test effects.
        assertTrue(developerRegisterCmd.getMailbox().getAllBoxes().contains(newMB));
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


}