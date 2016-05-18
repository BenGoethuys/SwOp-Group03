package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class MilestoneMailboxTest {
    private static MilestoneMailbox testTMB;
    private static MilestoneMailbox testTMB2;
    private static Notification bugReportNotification4MB2;
    private static Milestone milestone;


    private static Developer dev4TMB;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4TMB = new Developer("dev4TMB2", "devviea", "mbTestera");
        project4MB = new Project("Project4mb", "a project to test the mb", dev4TMB, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4TMB, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1, 2, 3),
                1, false, "triggerhappy", "stacktacktack", "error404");
        bugReportNotification4MB2 = new BugReportNotification("iets", bugreport4MB, project4MB);
        testTMB = dev4TMB.getMailbox().milestoneSubscribe(project4MB);
        milestone = new Milestone(5, 5, 5);
        testTMB2 = dev4TMB.getMailbox().milestoneSubscribe(project4MB, milestone);

    }

    @Test
    public void testUpdate() throws Exception {
        assertTrue(testTMB.getNotifications().isEmpty());
        assertTrue(testTMB2.getNotifications().isEmpty());
        Notification notification = testTMB.update(project4MB);
        Notification notification2 = testTMB2.update(project4MB);
        assertTrue(testTMB.getNotifications().contains(notification));
        assertEquals(null, notification2);
        assertFalse(testTMB.getNotifications().contains(bugReportNotification4MB2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUpdate() throws Exception {
        testTMB.update(null);
    }

    @Test
    public void testGetInfo() throws Exception {
        String info = testTMB.getInfo();
        String info2 = testTMB2.getInfo();
        assertTrue(info.contains(project4MB.getSubjectName()));
        assertTrue(info.contains("You are subscribed to the change of "));
        assertTrue(info.contains("a new milestone"));
        assertTrue(info2.contains("the specific milestone"));
        assertTrue(info2.contains(milestone.toString()));
        assertNotEquals(null, info);
        assertNotEquals("", info);
    }
}