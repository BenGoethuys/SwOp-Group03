package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class BugReportStateUnderReviewTest {

    // classes for testing
    static BugReport bugReport;
    static BugReport tempBugReport;
    static Issuer issuer;
    static Developer dev;
    static Developer lead;
    static Developer programer;
    static Developer tester;
    static PList<BugReport> depList;
    static PList<BugReport> depList2;
    static Milestone milestone;
    static Project project;
    static Subsystem subsystem;
    static Administrator admin;
    static String test;
    static String patch;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        issuer = new Issuer("blaStateUnderReviewTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateUnderReviewTest1", "Jan", "Smidt");
        lead = new Developer("blaStateUnderReviewTest2", "Jan", "Smidt");
        programer = new Developer("blaStateUnderReviewTest3", "Jos", "Smidt");
        tester = new Developer("blaStateUnderReviewTest4", "Jantje", "Smidt");
        admin = new Administrator("blaStateUnderReviewTest5", "bla", "hihi");

        project = new Project("ANewProject", "the description of the project", lead, 0, null);
        project.setRole(lead, programer, Role.PROGRAMMER);
        project.setRole(lead, tester, Role.TESTER);
        subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project, null);

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");

        depList2 = depList.plus(bugReport);

        // give bug reports desired state:
        bugReport.addUser(lead, tester);
        bugReport.addUser(lead, programer);
        bugReport.addTest(tester, test);
        bugReport.addPatch(programer, patch);
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, tester);
        tempBugReport.addUser(lead, programer);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.UNDER_REVIEW, bugReport.getTag());
    }

    @Test
    public void setTagNotABug() throws Exception {
        tempBugReport.getInternState().setTag(tempBugReport, Tag.NOT_A_BUG);
        assertEquals(Tag.NOT_A_BUG, tempBugReport.getTag());
    }

    @Test
    public void setTagAssigned() throws Exception {
        tempBugReport.getInternState().setTag(tempBugReport, Tag.ASSIGNED);
        assertEquals(Tag.ASSIGNED, tempBugReport.getTag());
    }

    @Test
    public void isValidTag() throws Exception {
        assertTrue(bugReport.isValidTag(Tag.NOT_A_BUG));
        assertFalse(bugReport.isValidTag(Tag.NEW));
        assertTrue(bugReport.isValidTag(Tag.ASSIGNED));
        assertFalse(bugReport.isValidTag(Tag.UNDER_REVIEW));
    }

    @Test
    public void addUser() throws Exception {
        assertFalse(tempBugReport.getUserList().contains(dev));
        tempBugReport.getInternState().addUser(tempBugReport, dev);
        assertTrue(tempBugReport.getUserList().contains(dev));
    }

    @Test
    public void addTest() throws Exception {
        tempBugReport.getInternState().addTest(tempBugReport, test);
        assertTrue(tempBugReport.getInternState().getTests().contains(test));
    }

    @Test
    public void getTests() throws Exception {
        assertTrue(bugReport.getInternState().getTests().contains(test));
    }

    @Test
    public void addPatch() throws Exception {
        tempBugReport.getInternState().addPatch(tempBugReport, patch);
        assertTrue(tempBugReport.getInternState().getPatches().contains(patch));
    }

    @Test
    public void getPatches() throws Exception {
        assertTrue(bugReport.getInternState().getPatches().contains(patch));
    }

    @Test
    public void selectPatch() throws Exception {
        tempBugReport.getInternState().selectPatch(tempBugReport, patch);
        assertEquals(patch, tempBugReport.getInternState().getSelectedPatch());
    }

    @Test (expected = IllegalArgumentException.class)
    public void selectPatchInvalidNull() throws Exception {
        tempBugReport.getInternState().selectPatch(tempBugReport, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void selectPatchInvalidNotInList() throws Exception {
        tempBugReport.getInternState().selectPatch(tempBugReport, "This patch is not in the list");
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

    @Test
    public void setDuplicate() throws Exception {
        tempBugReport.getInternState().setDuplicate(tempBugReport, bugReport);
        assertEquals(Tag.DUPLICATE, tempBugReport.getTag());
        assertEquals(bugReport, tempBugReport.getDuplicate());
    }

    @Test (expected = IllegalStateException.class)
    public void getDuplicate() throws Exception {
        bugReport.getInternState().getDuplicate();
    }

    @Test
    public void isResolved() throws Exception {
        assertFalse(bugReport.isResolved());
    }

    @Test
    public void getDetails() throws Exception {
        String response = bugReport.getInternState().getDetails();

        String expected = "tag: " + Tag.UNDER_REVIEW.name();
        assertTrue(response.contains(expected));

        expected = "tests: " + "\n \t " + test;
        assertTrue(response.contains(expected));

        expected = "patches: " + "\n \t " + patch;
        assertTrue(response.contains(expected));

    }
}