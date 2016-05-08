package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailBox;
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

        assubjectDummyProject = new Project("sdp", "sdp", assubjectDummyDev, 1000);
        assubjectDummySubsystem = assubjectDummyProject.addSubsystem("This seems", "easy");
        assubjectDummyBugreport = assubjectDummySubsystem.addBugReport(assubjectDummyDev, "", "", new GregorianCalendar(),
                PList.<BugReport>empty(), new Milestone(1), 1, false, "hi", "ha", "ho");
    }

    @Before
    public void setUp() throws Exception {
        asssubjectDummyCrMB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
    }

    @Test
    public void testNotifyCreationSubs() throws Exception {
        assertTrue(asssubjectDummyCrMB.getNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateCreationSubs() throws Exception {
        assertTrue(asssubjectDummyCrMB.getNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getNotifications().isEmpty());
    }

    @Test
    public void testAddCreationSub() throws Exception {
        CreationMailBox extraCMrB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
        assertTrue(extraCMrB.getNotifications().isEmpty());
        testDummy.updateCreationSubs(assubjectDummyBugreport);
        assertFalse(extraCMrB.getNotifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCreationSubNull() throws Exception {
        CreationMailBox box = null;
        testDummy.addCreationSub(box);
    }

    static class AbstractSystemSubjectDummy extends AbstractSystemSubject {

        @Override
        public void notifyCreationSubs(BugReport br) {
            this.updateCreationSubs(br);
        }

        @Override
        public String getSubjectName() {
            return "naam";
        }

        @Override
        public void notifyTagSubs(BugReport br) {
            this.updateTagSubs(br);
        }

        @Override
        public void notifyCommentSubs(BugReport br) {
            this.updateCommentSubs(br);
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public void notifyMilestoneSubs(AbstractSystem as) {
            this.updateMilestoneSubs(as);
        }

        @Override
        public void notifyVersionIDsubs(AbstractSystem as) {
            this.updateVersionIDSubs(as);
        }
    }
}
