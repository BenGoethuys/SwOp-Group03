package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notification.CommentMailBox;
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
public class UnregisterFromNotificationsCmdTest {

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

        administratorUnregisterCmd = model.createAdministrator("administratorUnregisterCmd" + index,"first","last");
        developerUnregisterCmd = model.createDeveloper("developerUnregisterCmd" + index, "firstname", "lastname");
        projectRegisterCmd = model.createProject("projectRegisterCmd","testdescription", developerUnregisterCmd, 1000, administratorUnregisterCmd);
        subsystemRegisterCmd = model.createSubsystem(administratorUnregisterCmd, projectRegisterCmd, "subsystemRegisterCmd","testdescription");
        bugReportRegisterCmd = model.createBugReport(subsystemRegisterCmd, developerUnregisterCmd, "a", "b",
                PList.<BugReport>empty(), new Milestone(1,1,1), 1, false);

        cmd = new UnregisterFromNotificationsCmd();
    }

    private static int index;

    private static DataModel model;
    private static Administrator administratorUnregisterCmd;
    private static ArrayDeque<String> question;
    private static ArrayDeque<String> answer;

    private static Project projectRegisterCmd;
    private static Subsystem subsystemRegisterCmd;
    private static BugReport bugReportRegisterCmd;
    private static Developer developerUnregisterCmd;

    private static UnregisterFromNotificationsCmd cmd;

    @Test
    public void testUnregisterNoSubs()throws Exception{
        question.add("No subscriptions to show.");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, model, developerUnregisterCmd);
        // Test effects.
    }

    @Test
    public void testUnregisterCommentProject()throws Exception{
        //setup
        CommentMailBox cmb = model.registerForCommentNotifications(developerUnregisterCmd, projectRegisterCmd);
        //write scenario
        question.add("Please select a subscription from list. (use index)");
        question.add("0. " + cmb.getInfo());
        question.add("Give number: ");
        answer.add("k");
        question.add("Invalid input, please enter a number");
        answer.add("-1");
        //show notif invalid
        question.add("Invalid input, give new index.");
        //get int
        question.add("Give number: ");
        answer.add("1");
        question.add("You selected: " + cmb.getInfo());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, model, developerUnregisterCmd);
        // Test effects.
    }
}