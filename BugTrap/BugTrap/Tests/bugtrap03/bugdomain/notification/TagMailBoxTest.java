package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.EnumSet;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Grout 03;
 */
public class TagMailBoxTest {

    private static TagMailBox testTMB;
    private static Notification notification4MB2;
    private static EnumSet<Tag> tagsTMB;


    private static Developer dev4TMB;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4TMB = new Developer("dev4TMB", "devviea", "mbTestera");
        project4MB = new Project("Project4mb","a project to test the mb", dev4TMB, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4TMB, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1,2,3),
                false, "triggerhappy", "stacktacktack", "error404");
        notification4MB2 = new Notification("this is a test notification for mb", bugreport4MB, project4MB);
        tagsTMB = EnumSet.of(Tag.ASSIGNED, Tag.DUPLICATE);
        testTMB = new TagMailBox(project4MB, tagsTMB);
    }

    @Test
    public void testUpdate() throws Exception {
        assertTrue(testTMB.getAllNotifications().isEmpty());
        Notification notification = testTMB.update(bugreport4MB);
        assertTrue(testTMB.getAllNotifications().contains(notification));
        assertFalse(testTMB.getAllNotifications().contains(notification4MB2));
    }

    @Test
    public void testGetInfo() throws Exception {
        String info = testTMB.getInfo();
        assertTrue(info.contains(project4MB.getSubjectName()));
        assertTrue(info.contains(tagsTMB.toString()));
        assertTrue(info.contains("You are subscribed to a change of following tags: "));
        assertTrue(info.contains("on: "));
        assertNotEquals(null, info);
        assertNotEquals("", info);
    }

    @Test
    public void testGetTagsOfInterest() throws Exception {
        assertNotEquals(null, testTMB.getTagsOfInterest());
        assertFalse(testTMB.getTagsOfInterest().isEmpty());
        assertEquals(tagsTMB, testTMB.getTagsOfInterest());
        assertFalse(testTMB.getTagsOfInterest().contains(Tag.RESOLVED));
    }
}