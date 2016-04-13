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
 * @author Group 03
 */
public class AbstractSystemSubjectDummyTest {

    private static AbstractSystemSubjectDummy testDummy;
    private static Developer assubjectDummyDev;
    private static Project assubjectDummyProject;
    private static Subsystem assubjectDummySubsystem;
    private static BugReport assubjectDummyBugreport;
    private static CreationMailBox asssubjectDummyCrMB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDummy = new AbstractSystemSubjectDummy();
        assubjectDummyDev = new Developer("ASsubjectDummyDev", "first", "last");

        assubjectDummyProject = new Project("sdp","sdp",assubjectDummyDev,1000);
        assubjectDummySubsystem = assubjectDummyProject.addSubsystem("This seems","easy");
        assubjectDummyBugreport = assubjectDummySubsystem.addBugReport(assubjectDummyDev, "", "", new GregorianCalendar(),
                PList.<BugReport>empty(), new Milestone(1), false, "hi","ha","ho");
    }

    @Before
    public void setUp() throws Exception{
        asssubjectDummyCrMB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
    }

    @Test
    public void testNotifyCreationSubs() throws Exception {
        assertTrue(asssubjectDummyCrMB.getAllNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getAllNotifications().isEmpty());
    }

    @Test
    public void testUpdateCreationSubs() throws Exception {
        assertTrue(asssubjectDummyCrMB.getAllNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getAllNotifications().isEmpty());
    }

    @Test
    public void testAddCreationSub() throws Exception {
        CreationMailBox extraCMrB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
        assertTrue(extraCMrB.getAllNotifications().isEmpty());
        testDummy.updateCreationSubs(assubjectDummyBugreport);
        assertFalse(extraCMrB.getAllNotifications().isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCreationSubNull() throws Exception {
        testDummy.addCreationSub(null);
    }
}