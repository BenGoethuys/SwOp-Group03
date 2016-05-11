package bugtrap03.bugdomain.notificationdomain.notification;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.Developer;
import purecollections.PList;

/**
 * @author Mathias
 *
 */
public class NotificationTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// dev4Not = new Developer("dev4Not", "devviea", "mbTestera");
	// project4MB = new Project("Project4mb", "a project to test the mb", dev4Not, 1000);
	// subsystem4MB = project4MB.addSubsystem("subsystem4MB", "A susbsystem to test the mb");
	// bugreport4MB = subsystem4MB.addBugReport(dev4Not, "bugreport4MB1", "A bugreport to test the mb",
	// new GregorianCalendar(), PList.<BugReport> empty(), new Milestone(1, 2, 3), 1, true, "triggerhappy",
	// "stacktacktack", "error404");
	// message4Not = "this is a test notification for mb";
	// testNot = new BugReportNotification(message4Not, bugreport4MB, project4MB);
    }

    @Test
    public void test() {
	Developer dev4Not = new Developer("dev4Not", "devviea", "mbTestera");
	Subject test = new Project("Project4mb", "a project to test the mb", dev4Not, 1000);
	Notification notification = new Notification("Test", test);
	
    }

}
