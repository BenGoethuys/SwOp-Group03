package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.MilestoneMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.VersionIDMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import java.util.ArrayList;
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
    
    private static CreationMailbox asssubjectDummyCrMB;
    private static MilestoneMailbox asssubjectDummyMileMB;
    private static VersionIDMailbox asssubjectDummyVerMB;

    @BeforeClass
    public static void setUpBeforeClass() throws PermissionException {
        testDummy = new AbstractSystemSubjectDummy();
        assubjectDummyDev = new Developer("ASsubjectDummyDev", "first", "last");

        assubjectDummyProject = new Project("sdp", "sdp", assubjectDummyDev, 1000);
        assubjectDummySubsystem = assubjectDummyProject.addSubsystem("This seems", "easy");
        assubjectDummyBugreport = assubjectDummySubsystem.addBugReport(assubjectDummyDev, "", "", new GregorianCalendar(),
                PList.<BugReport>empty(), new Milestone(1), 1, false, "hi", "ha", "ho");
    }

    @Before
    public void setUp() {
        asssubjectDummyCrMB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
        asssubjectDummyMileMB = assubjectDummyDev.getMailbox().milestoneSubscribe(testDummy);
        asssubjectDummyVerMB = assubjectDummyDev.getMailbox().versionIDSubscribe(testDummy);
    }

    @Test
    public void testNotifyVersionIDSubs() {
        assertTrue(asssubjectDummyVerMB.getNotifications().isEmpty());
        testDummy.notifyVersionIDSubs(assubjectDummyProject);
        assertFalse(asssubjectDummyVerMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateVersionIDSubs() {
        assertTrue(asssubjectDummyVerMB.getNotifications().isEmpty());
        testDummy.notifyVersionIDSubs(assubjectDummySubsystem);
        assertFalse(asssubjectDummyVerMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateVersionIDSub() {
        VersionIDMailbox extraCMrB = assubjectDummyDev.getMailbox().versionIDSubscribe(testDummy);
        assertTrue(extraCMrB.getNotifications().isEmpty());
        testDummy.updateVersionIDSubs(assubjectDummySubsystem);
        assertFalse(extraCMrB.getNotifications().isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddVersionIDSubNull() throws Exception {
        VersionIDMailbox box = null;
        testDummy.addVersionIDSub(box);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddVersionIDSubs_Invalid() {
        VersionIDMailbox box1 = new VersionIDMailbox(assubjectDummyProject);
        VersionIDMailbox box2 = null;

        ArrayList<VersionIDMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addVersionIDSub(list);
    }

    @Test
    public void testNotifyMilestoneSubs() {
        assertTrue(asssubjectDummyMileMB.getNotifications().isEmpty());
        testDummy.notifyMilestoneSubs(assubjectDummyProject);
        assertFalse(asssubjectDummyMileMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateMilestoneSubs() {
        assertTrue(asssubjectDummyMileMB.getNotifications().isEmpty());
        testDummy.notifyMilestoneSubs(assubjectDummySubsystem);
        assertFalse(asssubjectDummyMileMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateMilestoneSub() {
        MilestoneMailbox extraCMrB = assubjectDummyDev.getMailbox().milestoneSubscribe(testDummy);
        assertTrue(extraCMrB.getNotifications().isEmpty());
        testDummy.updateMilestoneSubs(assubjectDummySubsystem);
        assertFalse(extraCMrB.getNotifications().isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddMilestoneSubs_Invalid() {
        MilestoneMailbox box1 = new MilestoneMailbox(assubjectDummyProject);
        MilestoneMailbox box2 = null;

        ArrayList<MilestoneMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addMilestoneSub(list);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddMilestoneSubNull() throws Exception {
        MilestoneMailbox box = null;
        testDummy.addMilestoneSub(box);
    }

    @Test
    public void testNotifyCreationSubs() {
        assertTrue(asssubjectDummyCrMB.getNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateCreationSubs() {
        assertTrue(asssubjectDummyCrMB.getNotifications().isEmpty());
        testDummy.notifyCreationSubs(assubjectDummyBugreport);
        assertFalse(asssubjectDummyCrMB.getNotifications().isEmpty());
    }

    @Test
    public void testUpdateCreationSub() {
        CreationMailbox extraCMrB = assubjectDummyDev.getMailbox().creationSubscribe(testDummy);
        assertTrue(extraCMrB.getNotifications().isEmpty());
        testDummy.updateCreationSubs(assubjectDummyBugreport);
        assertFalse(extraCMrB.getNotifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCreationSubs_Invalid() {
        CreationMailbox box1 = new CreationMailbox(assubjectDummyProject);
        CreationMailbox box2 = null;

        ArrayList<CreationMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addCreationSub(list);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCreationSubNull() throws Exception {
        CreationMailbox box = null;
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
        public void notifyVersionIDSubs(AbstractSystem as) {
            this.updateVersionIDSubs(as);
        }
    }
}
