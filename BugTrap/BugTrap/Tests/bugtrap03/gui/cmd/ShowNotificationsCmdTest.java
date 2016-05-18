package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
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

/**
 * @author Group 03
 */
public class ShowNotificationsCmdTest {

    @BeforeClass
    public static void setUpBeforeClass() {
        index = 0;
    }


    @Before
    public void setup() throws Exception {
        index++;
        model = new DataModel();
        question = new ArrayDeque<>();
        answer = new ArrayDeque<>();

        administratorShowNotCmd = model.createAdministrator("administratorShowNotCmd" + index, "first", "last");
        developerShowNotCmd = model.createDeveloper("developerShowNotCmd" + index, "firstname", "lastname");
        projectRegisterCmd = model.createProject(new VersionID(), "projectRegisterCmd", "testdescription", developerShowNotCmd, 1000, administratorShowNotCmd);
        subsystemRegisterCmd = model.createSubsystem(administratorShowNotCmd, projectRegisterCmd, "subsystemRegisterCmd", "testdescription");
        bugReportRegisterCmd = model.createBugReport(subsystemRegisterCmd, developerShowNotCmd, "a", "b",
                PList.<BugReport>empty(), new Milestone(1, 1, 1), 1, false);

        cmd = new ShowNotificationsCmd();
    }

    private static int index;

    private static DataModel model;
    private static Administrator administratorShowNotCmd;
    private static ArrayDeque<String> question;
    private static ArrayDeque<String> answer;

    private static Project projectRegisterCmd;
    private static Subsystem subsystemRegisterCmd;
    private static BugReport bugReportRegisterCmd;
    private static Developer developerShowNotCmd;

    private static ShowNotificationsCmd cmd;

    @Test
    public void TestEmptyMailbox() throws Exception {
        question.add("No notifications in mailbox.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, model, developerShowNotCmd);
        // Test effects.
    }

    @Test
    public void TestCommentProjectNotification() throws Exception {
        //setup
        model.registerForCommentNotifications(developerShowNotCmd, projectRegisterCmd);
        model.createComment(developerShowNotCmd, bugReportRegisterCmd, "this is a comment");
        //write scenario
        //show notficiation.
        question.add("Length of inbox " + 1);
        question.add("How many notifications do you wish to see? (numeric)");
        //getIntcmd
        question.add("Give number: ");
        answer.add("k");
        question.add("Invalid input, please enter a number");
        answer.add("0");
        //show notif invalid
        question.add("Invalid input, give new number.");
        //get int
        question.add("Give number: ");
        answer.add("555");
        question.add("Your notifications, ordered by newest first:");
        //show notif list
        question.add("0. \tBugreport " + bugReportRegisterCmd.getTitle() + ", with uniqueID: " + bugReportRegisterCmd.getUniqueID() +
                " has been commented upon." + "\n" +
                "\tThis notification originated from the subscription on: " + projectRegisterCmd.getSubjectName());
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, model, developerShowNotCmd);
        // Test effects.
    }

}