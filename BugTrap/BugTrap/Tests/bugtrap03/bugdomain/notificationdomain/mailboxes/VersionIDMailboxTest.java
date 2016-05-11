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
public class VersionIDMailboxTest {
    private static VersionIDMailbox testTMB;
    private static Notification bugReportNotification4MB2;


    private static Developer dev4TMB;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4TMB = new Developer("dev4TMB2", "devviea", "mbTestera");
        project4MB = new Project("Project4mb","a project to test the mb", dev4TMB, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4TMB, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1,2,3),
                1, false, "triggerhappy", "stacktacktack", "error404");
        bugReportNotification4MB2 = new BugReportNotification("iets", bugreport4MB, project4MB);
        testTMB = dev4TMB.getMailbox().versionIDSubscribe(project4MB);
    }

    @Test
    public void testUpdate() throws Exception {
        assertTrue(testTMB.getNotifications().isEmpty());
        Notification notification = testTMB.update(project4MB);
        assertTrue(testTMB.getNotifications().contains(notification));
        assertFalse(testTMB.getNotifications().contains(bugReportNotification4MB2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullUpdate() throws Exception {
        testTMB.update(null);
    }

    @Test
    public void testGetInfo() throws Exception {
        String info = testTMB.getInfo();
        assertTrue(info.contains(project4MB.getSubjectName()));
        assertTrue(info.contains("You are subscribed to the change of VersionIDs on "));
        assertNotEquals(null, info);
        assertNotEquals("", info);
    }
}