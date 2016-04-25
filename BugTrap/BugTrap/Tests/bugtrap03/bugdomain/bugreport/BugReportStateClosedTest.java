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

public class BugReportStateClosedTest {

    private static final double EPSILON = 1e-15;

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
        issuer = new Issuer("blaStateClosedTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateClosedTest1", "Jan", "Smidt");
        lead = new Developer("blaStateClosedTest2", "Jan", "Smidt");
        programer = new Developer("blaStateClosedTest3", "Jos", "Smidt");
        tester = new Developer("blaStateClosedTest4", "Jantje", "Smidt");
        admin = new Administrator("blaStateClosedTest5", "bla", "hihi");

        project = new Project("ANewProject", "the description of the project", lead, 0);
        project.setRole(lead, programer, Role.PROGRAMMER);
        project.setRole(lead, tester, Role.TESTER);
        subsystem = project.addSubsystem("ANewSubSystem", "the decription of the subsystem");

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, 1, false, "", "", "");

        depList2 = depList.plus(bugReport);

        // give bug reports desired state:
        bugReport.addUser(lead, tester);
        bugReport.addUser(lead, programer);
        bugReport.addTest(tester, test);
        bugReport.addPatch(programer, patch);
        bugReport.selectPatch(lead, patch);
        bugReport.giveScore(issuer, 5);
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, 1, false, null, null, null);
        tempBugReport.addUser(lead, tester);
        tempBugReport.addUser(lead, programer);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        tempBugReport.selectPatch(lead, patch);
        tempBugReport.giveScore(issuer, 4);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.CLOSED, bugReport.getTag());
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

    @Test
    public void getTests() throws Exception {
        assertTrue(bugReport.getInternState().getTests().contains(test));
    }

    @Test (expected = IllegalStateException.class)
    public void addPatch() throws Exception {
        bugReport.getInternState().addPatch(bugReport, patch);
    }

    @Test
    public void getPatches() throws Exception {
        assertTrue(bugReport.getInternState().getPatches().contains(patch));
    }

    @Test (expected = IllegalStateException.class)
    public void selectPatch() throws Exception {
        bugReport.getInternState().selectPatch(bugReport, patch);
    }

    @Test
    public void getSelectedPatch() throws Exception {
        assertEquals(patch, bugReport.getInternState().getSelectedPatch());
    }

    @Test (expected = IllegalStateException.class)
    public void giveScore() throws Exception {
        bugReport.getInternState().giveScore(bugReport, 4);
    }

    @Test
    public void getScore() throws Exception {
        assertEquals(5, bugReport.getInternState().getScore());
    }

    @Test (expected = IllegalStateException.class)
    public void setDuplicate() throws Exception {
        bugReport.getInternState().setDuplicate(bugReport, tempBugReport);
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

        String expected = "tag: " + Tag.CLOSED.name();
        assertTrue(response.contains(expected));

        expected = "tests: " + "\n \t " + test;
        assertTrue(response.contains(expected));

        expected = "patches: " + "\n \t " + patch;
        assertTrue(response.contains(expected));

        expected = "selected patch: " + patch;
        assertTrue(response.contains(expected));

        expected = "score: " + 5;
        assertTrue(response.contains(expected));
    }

    @Test
    public void getMultiplier(){
        assertEquals(0, bugReport.getMultiplier(), EPSILON);
    }
}