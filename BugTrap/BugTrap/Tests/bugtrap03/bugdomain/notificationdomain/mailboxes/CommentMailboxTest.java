package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class CommentMailboxTest {
    private static CommentMailbox testCMB;
    private static BugReportNotification bugReportNotification4MB2;


    private static Developer dev4CMB1;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4CMB1 = new Developer("dev4CMB1", "devviea", "mbTestera");
        project4MB = new Project("Project4mb", "a project to test the mb", dev4CMB1, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4CMB1, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1, 2, 3),
                1, false, "triggerhappy", "stacktacktack", "error404");
        bugReportNotification4MB2 = new BugReportNotification("this is a test notification for mb", bugreport4MB, project4MB);
        testCMB = new CommentMailbox(project4MB);
    }

    @Test
    public void testUpdate() throws Exception {
        assertTrue(testCMB.getNotifications().isEmpty());
        BugReportNotification bugReportNotification = testCMB.update(bugreport4MB);
        assertTrue(testCMB.getNotifications().contains(bugReportNotification));
        assertFalse(testCMB.getNotifications().contains(bugReportNotification4MB2));
    }

    @Test
    public void testGetInfo() throws Exception {
        String info = testCMB.getInfo();
        assertTrue(info.contains(project4MB.getSubjectName()));
        assertTrue(info.contains("You are subscribed to the creation of comments on "));
        assertNotEquals(null, info);
        assertNotEquals("", info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUpdate() throws Exception {
        testCMB.update(null);
    }
}