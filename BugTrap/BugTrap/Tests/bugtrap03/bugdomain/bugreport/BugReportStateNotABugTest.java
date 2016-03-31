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

public class BugReportStateNotABugTest {

    // classes for testing
    static BugReport bugReport;
    static BugReport duplicate;
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
        issuer = new Issuer("blaStateNotABugTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateNotABugTest1", "Jan", "Smidt");
        lead = new Developer("blaStateNotABugTest2", "Jan", "Smidt");

        project = new Project("ANewProject", "the description of the project", lead, 0);
        subsystem = project.makeSubsystemChild("ANewSubSystem", "the decription of the subsystem");

        duplicate = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");
        bugReport.setTag(Tag.NOT_A_BUG, lead);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.NOT_A_BUG, bugReport.getTag());
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
        bugReport.getInternState().setDuplicate(bugReport, duplicate);
    }

    @Test (expected = IllegalStateException.class)
    public void getDuplicate() throws Exception {
        bugReport.getInternState().getDuplicate();
    }

    @Test
    public void isResolved() throws Exception {
        assertTrue(bugReport.isResolved());
    }

    @Test
    public void getDetails() throws Exception {
        String response = bugReport.getInternState().getDetails();

        String expected = "tag: " + Tag.NOT_A_BUG.name();
        assertTrue(response.contains(expected));
    }
}