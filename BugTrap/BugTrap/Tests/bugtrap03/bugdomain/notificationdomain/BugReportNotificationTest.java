package bugtrap03.bugdomain.notificationdomain;

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
public class BugReportNotificationTest {

    private static BugReportNotification testNot;
    private static String message4Not;

    private static Developer dev4Not;

    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4Not = new Developer("dev4Not", "devviea", "mbTestera");
        project4MB = new Project("Project4mb","a project to test the mb", dev4Not, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4Not, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1,2,3),
                1, true, "triggerhappy", "stacktacktack", "error404");
        message4Not = "this is a test notificationdomain for mb";
        testNot = new BugReportNotification(message4Not, bugreport4MB, project4MB);
    }

    @Test
    public void testIsValidMessage() throws Exception {
        assertFalse(testNot.isValidMessage(null));
        assertFalse(testNot.isValidMessage(""));
        assertTrue(testNot.isValidMessage(message4Not));
    }

    @Test
    public void testIsValidBugReport() throws Exception {
        assertTrue(testNot.isValidBugReport(bugreport4MB));
        assertFalse(testNot.isValidBugReport(null));
    }

    @Test
    public void testIsValidSubject() throws Exception {
        assertTrue(testNot.isValidSubject(project4MB));
        assertFalse(testNot.isValidSubject(null));
    }

    @Test
    public void testOpen() throws Exception {
        String message = testNot.open(dev4Not);
        assertTrue(message.contains("This notificationdomain originated from the subscription on: "));
        assertTrue(message.contains(message4Not));
        assertTrue(message.contains(project4MB.getSubjectName()));
        assertTrue(message.contains(bugreport4MB.getTitle()));
        assertFalse(message.isEmpty());
        assertNotEquals(null, message);

        Developer dev5Not = new Developer("dev5Not", "devviea", "mbTestera");
        assertTrue(testNot.open(dev5Not).contains("This notificationdomain is closed for you at the moment."));
    }

    @Test
    public void testEquals() throws Exception {
        assertFalse(testNot.equals(project4MB));
        assertFalse(testNot.equals(null));
        BugReportNotification testNot1 = new BugReportNotification(message4Not, bugreport4MB, project4MB);
        assertTrue(testNot.equals(testNot1));
        BugReportNotification testNot2 = new BugReportNotification("something else", bugreport4MB, project4MB);
        assertFalse(testNot.equals(testNot2));
        BugReportNotification testNot3 = new BugReportNotification(message4Not, new BugReport(dev4Not, "a", "b", new GregorianCalendar(),
                PList.<BugReport>empty(), subsystem4MB, new Milestone(1,2,4), 1, false, "d","e","f"), project4MB);
        assertFalse(testNot.equals(testNot3));
        BugReportNotification testNot4 = new BugReportNotification(message4Not, bugreport4MB, new Project("g","h",dev4Not,123));
        assertFalse(testNot.equals(testNot4));
    }

    @Test
    public void testHashCode() throws Exception {
        int value = testNot.hashCode();
        int exepected = ((((bugreport4MB.hashCode() * 113) + project4MB.hashCode()) * 31) + message4Not.hashCode());
        assertEquals(exepected,value);
        assertNotEquals(0,value);
        assertNotEquals(null, value);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotificationNullDesc() throws Exception {
        BugReportNotification notif = new BugReportNotification(null,bugreport4MB,project4MB);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotificationEmptyDesc() throws Exception {
        BugReportNotification notif = new BugReportNotification("",bugreport4MB,project4MB);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotificationNullBugRep() throws Exception {
        BugReportNotification notif = new BugReportNotification("haha",null,project4MB);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNotificationNullSubject() throws Exception {
        BugReportNotification notif = new BugReportNotification("haha",bugreport4MB,null);
    }
}