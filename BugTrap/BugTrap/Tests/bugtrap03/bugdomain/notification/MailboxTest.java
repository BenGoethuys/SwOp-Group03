package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Test class for mailbox class
 * @author Group 03
 */
public class MailboxTest {

    private static Mailbox testMB;
    private static Mailbox testMB2;
    private static Notification notification4MB;


    private static Developer dev4MB;
    private static Developer dev5MB;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4MB = new Developer("dev4MB", "devviea", "mbTestera");
        dev5MB = new Developer("dev5MB", "devvieb", "mbTesterb");
        project4MB = new Project("Project4mb","a project to test the mb", dev4MB, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4MB, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1,2,3),
                false, "triggerhappy", "stacktacktack", "error404");

        testMB2 = new Mailbox();
        notification4MB = new Notification("this is a test notification for mb", bugreport4MB, project4MB);

    }

    @Before
    public void setup() throws Exception {
        testMB = new Mailbox();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        assertEquals(PList.<Notification>empty(), testMB.getAllNotifications());
    }

    @Test
    public void testAddNotification() throws Exception {
        Notification notification4MB = new Notification("This is a notification.",bugreport4MB, project4MB);
        testMB.addNotification(notification4MB);
        assertTrue(testMB.getAllNotifications().contains(notification4MB));
    }

    @Test
    public void testGetAllBoxes() throws Exception {
        assertEquals(PList.<Mailbox>empty(), testMB.getAllBoxes());
    }

    @Test
    public void testAddBox() throws Exception {
        Mailbox extraMB =  new Mailbox();
        testMB.addBox(extraMB);
        assertTrue(testMB.getAllBoxes().contains(extraMB));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddBoxSelf() throws Exception {
        testMB.addBox(testMB);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddBoxDuplicate() throws Exception {
        Mailbox extraMB =  new Mailbox();
        testMB.addBox(extraMB);
        testMB.addBox(extraMB);
    }

    @Test
    public void testIsValidSubject() throws Exception {

    }

    @Test
    public void testGetInfo() throws Exception {

    }

    @Test
    public void testTagSubscribe() throws Exception {

    }

    @Test
    public void testTagSubscribe1() throws Exception {

    }

    @Test
    public void testCommentSubscribe() throws Exception {

    }

    @Test
    public void testCreationSubscribe() throws Exception {

    }

    @Test
    public void testUnsubscribe() throws Exception {

    }

    @Test
    public void testActivate() throws Exception {

    }
}