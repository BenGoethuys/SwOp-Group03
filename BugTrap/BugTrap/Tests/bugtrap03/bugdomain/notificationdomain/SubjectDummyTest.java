package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailbox;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class SubjectDummyTest {

    private static SubjectDummy testDummy;
    private static Developer subjectDummyDev;
    private static TagMailbox subjectDummyTMB;
    private static CommentMailbox subjectDummyCMB;
    private static Project subjectDummyProject;
    private static Subsystem subjectDummySubsystem;
    private static BugReport subjectDummyBugreport;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDummy = new SubjectDummy();
        subjectDummyDev = new Developer("subjectDummyDev505", "first", "last");

        subjectDummyProject = new Project("sdp", "sdp", subjectDummyDev, 1000);
        subjectDummySubsystem = subjectDummyProject.addSubsystem("This seems", "easy");
        subjectDummyBugreport = subjectDummySubsystem.addBugReport(subjectDummyDev, "", "", new GregorianCalendar(),
                PList.<BugReport>empty(), new Milestone(1), 1, false, "hi", "ha", "ho");
    }

    @Before
    public void setUp() throws Exception {
        subjectDummyTMB = subjectDummyDev.getMailbox().tagSubscribe(testDummy);
        subjectDummyCMB = subjectDummyDev.getMailbox().commentSubscribe(testDummy);
    }


    @Test
    public void testGetSubjectName() throws Exception {
        assertEquals("naam", testDummy.getSubjectName());
        assertNotEquals("", testDummy.getSubjectName());
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
        TagMailbox extraTMB = subjectDummyDev.getMailbox().tagSubscribe(testDummy, EnumSet.of(Tag.NEW));
        assertTrue(extraTMB.getNotifications().isEmpty());
        testDummy.updateTagSubs(subjectDummyBugreport);
        assertFalse(extraTMB.getNotifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTagSubs_Null() {
        TagMailbox cmb1 = new TagMailbox(subjectDummySubsystem, EnumSet.allOf(Tag.class));
        TagMailbox cmb2 = null;

        ArrayList<TagMailbox> list = new ArrayList<>();
        list.add(cmb1);
        list.add(cmb2);

        testDummy.addTagSub(list);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTagSubNull() throws Exception {
        TagMailbox box = null;
        testDummy.addTagSub(box);
    }

    @Test
    public void testUpdateCommentSubs() throws Exception {
        assertTrue(subjectDummyCMB.getNotifications().isEmpty());
        testDummy.updateCommentSubs(subjectDummyBugreport);
        assertFalse(subjectDummyCMB.getNotifications().isEmpty());
    }

    @Test
    public void testAddCommentSub() {
        CommentMailbox extraCMB = subjectDummyDev.getMailbox().commentSubscribe(testDummy);
        assertTrue(extraCMB.getNotifications().isEmpty());
        testDummy.updateCommentSubs(subjectDummyBugreport);
        assertFalse(extraCMB.getNotifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCommentSubs_Null() {
        CommentMailbox cmb1 = new CommentMailbox(subjectDummySubsystem);
        CommentMailbox cmb2 = null;

        ArrayList<CommentMailbox> list = new ArrayList<>();
        list.add(cmb1);
        list.add(cmb2);

        testDummy.addCommentSub(list);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCommentSubNull() throws Exception {
        CommentMailbox box = null;
        testDummy.addCommentSub(box);
    }

    @Test
    public void testIsValidMb() throws Exception {
        assertFalse(testDummy.isValidMb(null));
        assertTrue(testDummy.isValidMb(subjectDummyTMB));
        assertTrue(testDummy.isValidMb(subjectDummyCMB));
    }

    @Test
    public void testGetMemento() {
        SubjectMemento mem = testDummy.getMemento();

        assertTrue(testDummy.getCommentSubs().equals(mem.getCommentSubs()));
        assertTrue(testDummy.getTagSubs().equals(mem.getTagSubs()));

        testDummy.addCommentSub(new CommentMailbox(subjectDummyProject));
        testDummy.addTagSub(new TagMailbox(subjectDummyProject, EnumSet.allOf(Tag.class)));

        assertFalse(testDummy.getCommentSubs().equals(mem.getCommentSubs()));
        assertFalse(testDummy.getTagSubs().equals(mem.getTagSubs()));
    }
}
