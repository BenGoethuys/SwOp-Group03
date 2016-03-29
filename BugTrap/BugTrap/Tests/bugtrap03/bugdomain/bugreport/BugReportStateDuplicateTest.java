package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class BugReportStateDuplicateTest {

    // classes for testing
    static BugReport bugReport;
    static BugReport duplicate;
    static BugReport tempBugReport;
    static Issuer issuer;
    static Developer dev;
    static Developer lead;
    static PList<BugReport> depList;
    static Milestone milestone;
    static Project project;
    static Subsystem subsystem;
    static String test;
    static String patch;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        issuer = new Issuer("blaStateDuplicateTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateDuplicateTest1", "Jan", "Smidt");
        lead = new Developer("blaStateDuplicateTest2", "Jan", "Smidt");

        project = new Project("ANewProject", "the description of the project", lead, 0, null);
        subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project, null);

        duplicate = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");
        bugReport.setDuplicate(lead, duplicate);
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, false, null, null, null);
        tempBugReport.setDuplicate(lead, duplicate);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.DUPLICATE, bugReport.getTag());
    }

    @Test (expected = IllegalStateException.class)
    public void setTagNotABug() throws Exception {
        bugReport.getInternState().setTag(bugReport, Tag.NOT_A_BUG);
    }

    @Test
    public void isValidTag() throws Exception {
        assertFalse(bugReport.isValidTag(Tag.NOT_A_BUG));
        assertFalse(bugReport.isValidTag(Tag.NEW));
        assertFalse(bugReport.isValidTag(Tag.ASSIGNED));
        assertFalse(bugReport.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(bugReport.isValidTag(Tag.RESOLVED));
        assertFalse(bugReport.isValidTag(Tag.CLOSED));
        assertFalse(bugReport.isValidTag(Tag.DUPLICATE));
    }

    @Test (expected = IllegalStateException.class)
    public void addUser() throws Exception {
        bugReport.getInternState().addUser(bugReport, dev);
    }

    @Test (expected = IllegalStateException.class)
    public void addTest() throws Exception {
        bugReport.getInternState().addTest(bugReport, test);
    }

    @Test (expected = IllegalStateException.class)
    public void getTests() throws Exception {
        bugReport.getInternState().getTests();
    }

    @Test (expected = IllegalStateException.class)
    public void addPatch() throws Exception {
        bugReport.getInternState().addPatch(bugReport, patch);
    }

    @Test (expected = IllegalStateException.class)
    public void getPatches() throws Exception {
        bugReport.getInternState().getPatches();
    }

    @Test (expected = IllegalStateException.class)
    public void selectPatch() throws Exception {
        bugReport.getInternState().selectPatch(bugReport, patch);
    }

    @Test (expected = IllegalStateException.class)
    public void getSelectedPatch() throws Exception {
        bugReport.getInternState().getSelectedPatch();
    }

    @Test (expected = IllegalStateException.class)
    public void giveScore() throws Exception {
        bugReport.getInternState().giveScore(bugReport, 4);
    }

    @Test (expected = IllegalStateException.class)
    public void getScore() throws Exception {
        bugReport.getInternState().getScore();
    }

    @Test (expected = IllegalStateException.class)
    public void setDuplicate() throws Exception {
        bugReport.getInternState().setDuplicate(bugReport, tempBugReport);
    }

    @Test
    public void getDuplicate() throws Exception {
        assertEquals(duplicate, bugReport.getInternState().getDuplicate());
    }

    @Test
    public void isResolved() throws Exception {
        assertTrue(bugReport.isResolved());
    }

    @Test
    public void getDetails() throws Exception {
        String response = bugReport.getInternState().getDetails();

        String expected = "tag: " + Tag.DUPLICATE.name();
        assertTrue(response.contains(expected));

        expected = "is duplicate of: " + duplicate.getUniqueID();
        assertTrue(response.contains(expected));
    }
}