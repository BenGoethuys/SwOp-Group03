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
 * @author Groupt 03
 */
public class SubjectDummyTest {

    private static SubjectDummy testDummy;
    private static Developer subjectDummyDev;
    private static TagMailBox subjectDummyTMB;
    private static CommentMailBox subjectDummyCMB;
    private static Project subjectDummyProject;
    private static Subsystem subjectDummySubsystem;
    private static BugReport subjectDummyBugreport;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDummy = new SubjectDummy();
        subjectDummyDev = new Developer("subjectDummyDev", "first", "last");

        subjectDummyProject = new Project("sdp","sdp",subjectDummyDev,1000);
        subjectDummySubsystem = subjectDummyProject.addSubsystem("This seems","easy");
        subjectDummyBugreport = subjectDummySubsystem.addBugReport(subjectDummyDev, "", "", new GregorianCalendar(),
                PList.<BugReport>empty(), new Milestone(1), 1, false, "hi","ha","ho");
    }

    @Before
    public void setUp() throws Exception{
        subjectDummyTMB = subjectDummyDev.getMailbox().tagSubscribe(testDummy);
        subjectDummyCMB = subjectDummyDev.getMailbox().commentSubscribe(testDummy);
    }


    @Test
    public void testGetSubjectName() throws Exception {
        assertEquals("naam", testDummy.getSubjectName());
        assertNotEquals("",testDummy.getSubjectName());
        assertNotEquals(null, testDummy.getSubjectName());
    }

    @Test
    public void testNotifyTagSubs() throws Exception {
        assertTrue(subjectDummyTMB.getNotifications().isEmpty());
        testDummy.notifyTagSubs(subjectDummyBugreport);
        assertFalse(subjectDummyTMB.getNotifications().isEmpty());
    }

    @Test
    public void testNotifyCommentSubs() throws Exception {
        assertTrue(subjectDummyCMB.getNotifications().isEmpty());
        testDummy.notifyCommentSubs(subjectDummyBugreport);
        assertFalse(subjectDummyCMB.getNotifications().isEmpty());
    }

    @Test
    public void testIsTerminated() throws Exception {
        assertFalse(testDummy.isTerminated());
    }

    @Test
    public void testUpdateTagSubs() throws Exception {
        assertTrue(subjectDummyTMB.getNotifications().isEmpty());
        testDummy.updateTagSubs(subjectDummyBugreport);
        assertFalse(subjectDummyTMB.getNotifications().isEmpty());
    }

    @Test
    public void testAddTagSub() throws Exception {
        TagMailBox extraTMB = subjectDummyDev.getMailbox().tagSubscribe(testDummy, EnumSet.of(Tag.NOT_A_BUG));
        assertTrue(extraTMB.getNotifications().isEmpty());
        testDummy.updateTagSubs(subjectDummyBugreport);
        assertFalse(extraTMB.getNotifications().isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddTagSubNull() throws Exception {
        testDummy.addTagSub(null);
    }

    @Test
    public void testUpdateCommentSubs() throws Exception {
        assertTrue(subjectDummyCMB.getNotifications().isEmpty());
        testDummy.updateCommentSubs(subjectDummyBugreport);
        assertFalse(subjectDummyCMB.getNotifications().isEmpty());
    }

    @Test
    public void testAddCommentSub() throws Exception {
        CommentMailBox extraCMB = subjectDummyDev.getMailbox().commentSubscribe(testDummy);
        assertTrue(extraCMB.getNotifications().isEmpty());
        testDummy.updateCommentSubs(subjectDummyBugreport);
        assertFalse(extraCMB.getNotifications().isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCommentSubNull() throws Exception {
        testDummy.addCommentSub(null);
    }

    @Test
    public void testIsValidMb() throws Exception {
        assertFalse(testDummy.isValidMb(null));
        assertTrue(testDummy.isValidMb(subjectDummyTMB));
        assertTrue(testDummy.isValidMb(subjectDummyCMB));

    }
}