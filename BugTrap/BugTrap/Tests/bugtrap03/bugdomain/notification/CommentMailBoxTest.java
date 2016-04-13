package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class CommentMailBoxTest {
    private static CommentMailBox testCMB;
    private static Notification notification4MB2;


    private static Developer dev4CMB1;
    private static Project project4MB;
    private static Subsystem subsystem4MB;
    private static BugReport bugreport4MB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        dev4CMB1 = new Developer("dev4CMB1", "devviea", "mbTestera");
        project4MB = new Project("Project4mb","a project to test the mb", dev4CMB1, 1000);
        subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
        bugreport4MB = subsystem4MB.addBugReport(dev4CMB1, "bugreport4MB1", "A bugreport to test the mb",
                new GregorianCalendar(), PList.<BugReport>empty(), new Milestone(1,2,3),
                false, "triggerhappy", "stacktacktack", "error404");
        notification4MB2 = new Notification("this is a test notification for mb", bugreport4MB, project4MB);
        testCMB = new CommentMailBox(project4MB);
    }

    @Test
    public void testUpdate() throws Exception {
        assertTrue(testCMB.getAllNotifications().isEmpty());
        Notification notification = testCMB.update(bugreport4MB);
        assertTrue(testCMB.getAllNotifications().contains(notification));
        assertFalse(testCMB.getAllNotifications().contains(notification4MB2));
    }

    @Test
    public void testGetInfo() throws Exception {
        String info = testCMB.getInfo();
        assertTrue(info.contains(project4MB.getSubjectName()));
        assertTrue(info.contains("You are subscribed to the creation of comments on "));
        assertNotEquals(null, info);
        assertNotEquals("", info);
    }
}