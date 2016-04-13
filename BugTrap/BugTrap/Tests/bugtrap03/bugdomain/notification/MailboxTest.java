package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.EnumSet;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Test class for mailbox class
 * @author Group 03
 */
public class MailboxTest {

    private static Mailbox testMB;
    private static Mailbox testMB2;
    private static CreationMailBox testCMB2;
    private static Notification notification4MB2;


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


        notification4MB2 = new Notification("this is a test notification for mb", bugreport4MB, project4MB);
        testMB2 = new Mailbox();
        testCMB2= testMB2.creationSubscribe(project4MB);
        testCMB2.addNotification(notification4MB2);

    }

    @Before
    public void setup() throws Exception {
        testMB = new Mailbox();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        assertTrue(testMB.getAllNotifications().isEmpty());
        assertTrue(testMB2.getAllNotifications().contains(notification4MB2));
    }

    @Test
    public void testAddNotification() throws Exception {
        Notification notification4MB = new Notification("This is a notification.",bugreport4MB, project4MB);
        testMB.addNotification(notification4MB);
        assertTrue(testMB.getAllNotifications().contains(notification4MB));
    }

    @Test
    public void testGetAllBoxes() throws Exception {
        assertTrue(testMB.getAllBoxes().isEmpty());
        assertTrue(testMB2.getAllBoxes().contains(testCMB2));
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
        assertTrue(testMB.isValidSubject(project4MB));
        assertTrue(testMB.isValidSubject(subsystem4MB));
        assertTrue(testMB.isValidSubject(bugreport4MB));
        assertFalse(testMB.isValidSubject(null));
    }

    @Test
    public void testGetInfo() throws Exception {
        assertNotEquals("", testMB.getInfo());
        assertNotEquals(null, testMB.getInfo());
        assertTrue(testCMB2.getInfo().contains(project4MB.getSubjectName()));
    }

    @Test
    public void testTagSubscribe() throws Exception {
        EnumSet<Tag> tags = EnumSet.of(Tag.ASSIGNED, Tag.UNDER_REVIEW);
        TagMailBox tmb = testMB.tagSubscribe(project4MB, tags);
        assertTrue(testMB.getAllBoxes().contains(tmb));
        assertEquals(tags, tmb.getTagsOfInterest());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTagSubscribeNullSubject() throws Exception {
        testMB.tagSubscribe(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTagSubscribeNullSubject1() throws Exception {
        EnumSet<Tag> tags = EnumSet.of(Tag.ASSIGNED, Tag.UNDER_REVIEW);
        testMB.tagSubscribe(null, tags);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTagSubscribeNullTags() throws Exception {
        testMB.tagSubscribe(project4MB, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTagSubscribeEmptyTags() throws Exception {
        testMB.tagSubscribe(project4MB, EnumSet.noneOf(Tag.class));
    }

    @Test
    public void testTagSubscribe1() throws Exception {
        EnumSet<Tag> tags = EnumSet.allOf(Tag.class);
        TagMailBox tmb = testMB.tagSubscribe(project4MB);
        assertTrue(testMB.getAllBoxes().contains(tmb));
        assertEquals(tags, tmb.getTagsOfInterest());
    }

    @Test
    public void testCommentSubscribe() throws Exception {
        CommentMailBox cmb = testMB.commentSubscribe(subsystem4MB);
        assertTrue(testMB.getAllBoxes().contains(cmb));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCommentSubscribeNull() throws Exception {
        testMB.commentSubscribe(null);
    }

    @Test
    public void testCreationSubscribe() throws Exception {
        CreationMailBox cmb = testMB.creationSubscribe(subsystem4MB);
        assertTrue(testMB.getAllBoxes().contains(cmb));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreationSubscribeNull() throws Exception {
        testMB.creationSubscribe(null);
    }

    @Test
    public void testUnsubscribe() throws Exception {
        CreationMailBox cmb = testMB.creationSubscribe(subsystem4MB);
        assertTrue(testMB.getAllBoxes().contains(cmb));
        CommentMailBox cmb2 = cmb.commentSubscribe(subsystem4MB);
        assertTrue(testMB.unsubscribe(cmb2));
        assertFalse(testMB.getAllBoxes().contains(cmb2));
        assertFalse(testMB2.getAllBoxes().contains(cmb2));
        assertTrue(testMB.unsubscribe(cmb));
        assertFalse(testMB.unsubscribe(testCMB2));
        assertFalse(testMB.getAllBoxes().contains(cmb));
    }

    @Test
    public void testActivate() throws Exception {
        CreationMailBox cmb = testMB.creationSubscribe(subsystem4MB);
        testMB.unsubscribe(cmb);
        cmb.addNotification(notification4MB2);
        assertFalse(cmb.getAllNotifications().contains(notification4MB2));
        cmb.activate();
        cmb.addNotification(notification4MB2);
        assertTrue(cmb.getAllNotifications().contains(notification4MB2));
    }
}