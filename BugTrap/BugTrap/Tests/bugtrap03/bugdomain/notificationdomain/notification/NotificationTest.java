package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class NotificationTest {

    /**
     * Test method for {@link bugtrap03.bugdomain.notificationdomain.notification.Notification#getDate()}.
     */
    @Test
    public void testGetDate() {
        Developer dev4Not = new Developer("dev4No", "devviea", "mbTestera");
        Subject test = new Project("Project4mb", "a project to test the mb", dev4Not, 1000);
        Notification notification = new Notification("Test", test);
        assertEquals(notification.getDate(), new GregorianCalendar());
    }

}
