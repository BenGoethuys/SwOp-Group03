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
public class NotificationTest {

    private static Notification testNot;
    private static String message4Not;


    private static Developer dev4Not
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
                false, "triggerhappy", "stacktacktack", "error404");
        message4Not = "this is a test notification for mb";
        testNot = new Notification(message4Not, bugreport4MB, project4MB);
    }

    @Test
    public void testIsValidMessage() throws Exception {
        assertFalse(testNot.isValidMessage(null));
        assertFalse(testNot.isValidMessage(""));
        assertTrue(testNot.isValidMessage(message4Not));
    }

    @Test
    public void testIsValidBugReport() throws Exception {

    }

    @Test
    public void testIsValidSubject() throws Exception {

    }

    @Test
    public void testOpen() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }
}