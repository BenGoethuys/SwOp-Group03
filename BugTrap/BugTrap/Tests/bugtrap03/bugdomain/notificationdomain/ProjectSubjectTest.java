package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.HealthAlgorithm;
import bugtrap03.bugdomain.HealthIndicator;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notificationdomain.mailboxes.ForkMailbox;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class ProjectSubjectTest {

    private static Project testDummy;
    private static Developer subjectDummyDev;
    private static ForkMailbox subjectDummyFMB;
    private static Project subjectDummyProject;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        subjectDummyDev = new Developer("subjectDummyDev", "first", "last");
        testDummy = new Project("sdp", "sdp", subjectDummyDev, 1000);

        subjectDummyProject = new Project("sdp", "sdp", subjectDummyDev, 1000);
    }

    @Before
    public void setUp() throws Exception {
        subjectDummyFMB = subjectDummyDev.getMailbox().forkSubscribe(testDummy);
    }

    @Test
    public void testNotifyForkSubs() throws Exception {
        assertTrue(subjectDummyFMB.getNotifications().isEmpty());
        testDummy.notifyForkSubs(subjectDummyProject);
        assertFalse(subjectDummyFMB.getNotifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCreationSubs_Invalid() {
        ForkMailbox box1 = new ForkMailbox(subjectDummyProject);
        ForkMailbox box2 = null;

        ArrayList<ForkMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addForkSub(list);
    }

    @Test
    public void testAddCreationSubs() {
        ForkMailbox box1 = new ForkMailbox(subjectDummyProject);
        ForkMailbox box2 = new ForkMailbox(subjectDummyProject);

        ArrayList<ForkMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addForkSub(list);
    }

    @Test
    public void testGetMemento() {
        ProjectSubjectDummy dummy = new ProjectSubjectDummy(subjectDummyProject, "blob", "blib");
        ProjectSubjectMemento mem = dummy.getMemento();

        assertTrue(dummy.getVersionIDSubs().equals(mem.getVersionIDSubs()));
        assertTrue(dummy.getTagSubs().equals(mem.getTagSubs()));
        assertTrue(dummy.getMilestoneSubs().equals(mem.getMilestoneSubs()));
        assertTrue(dummy.getCreationSubs().equals(mem.getCreationSubs()));
        assertTrue(dummy.getCommentSubs().equals(mem.getCommentSubs()));
        assertTrue(dummy.getForkSubs().equals(mem.getForkSubs()));

        assertEquals(dummy.getVersionID(), mem.getVersionID());
        assertEquals(dummy.getName(), mem.getName());
        assertEquals(dummy.getDescription(), mem.getDescription());
        assertEquals(dummy.getSubsystems(), mem.getChildren());
        //assertEquals(testDummy.getParent(), mem.getParent());
        assertEquals(dummy.isTerminated(), mem.getIsTerminated());
        assertEquals(dummy.getMilestone(), mem.getMilestone());
    }


    class ProjectSubjectDummy extends ProjectSubject {

        public ProjectSubjectDummy(AbstractSystem parent, String name, String description) throws IllegalArgumentException {
            super(parent, name, description);
        }

        @Override
        public double getBugImpact() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDetails() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public HealthIndicator getIndicator(HealthAlgorithm ha) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getSubjectName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
