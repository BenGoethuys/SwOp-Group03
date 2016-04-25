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

public class BugReportStateResolvedTest {

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
        issuer = new Issuer("blaStateResolvedTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateResolvedTest1", "Jan", "Smidt");
        lead = new Developer("blaStateResolvedTest2", "Jan", "Smidt");
        programer = new Developer("blaStateResolvedTest3", "Jos", "Smidt");
        tester = new Developer("blaStateResolvedTest4", "Jantje", "Smidt");
        admin = new Administrator("blaStateResolvedTest5", "bla", "hihi");

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
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, 1, false, null, null, null);
        tempBugReport.addUser(lead, tester);
        tempBugReport.addUser(lead, programer);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        tempBugReport.selectPatch(lead, patch);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.RESOLVED, bugReport.getTag());
    }

    @Test
    public void setTagNotABug() throws Exception {
        tempBugReport.getInternState().setTag(tempBugReport, Tag.NOT_A_BUG);
        assertEquals(Tag.NOT_A_BUG, tempBugReport.getTag());
    }

    @Test
    public void isValidTag() throws Exception {
        assertTrue(bugReport.isValidTag(Tag.NOT_A_BUG));
        assertFalse(bugReport.isValidTag(Tag.NEW));
        assertFalse(bugReport.isValidTag(Tag.ASSIGNED));
        assertFalse(bugReport.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(bugReport.isValidTag(Tag.RESOLVED));
    }

    @Test
    public void addUser() throws Exception {
        assertFalse(tempBugReport.getUserList().contains(dev));
        tempBugReport.getInternState().addUser(tempBugReport, dev);
        assertTrue(tempBugReport.getUserList().contains(dev));
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

    @Test
    public void giveScore() throws Exception {
        tempBugReport.getInternState().giveScore(tempBugReport, 4);
        assertEquals(4, tempBugReport.getInternState().getScore());
    }

    @Test (expected = IllegalArgumentException.class)
    public void giveScoreInvalid(){
        bugReport.getInternState().giveScore(bugReport, -1);
    }

    @Test (expected = IllegalStateException.class)
    public void getScore() throws Exception {
        bugReport.getInternState().getScore();
    }

    @Test
    public void isValidScore(){
        for (int i = 1; i < 6; i++) {
            assertTrue(BugReportStateResolved.isValidScore(i));
        }
        assertFalse(BugReportStateResolved.isValidScore(0));
        assertFalse(BugReportStateResolved.isValidScore(-1));
        assertFalse(BugReportStateResolved.isValidScore(6));
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

        String expected = "tag: " + Tag.RESOLVED.name();
        assertTrue(response.contains(expected));

        expected = "tests: " + "\n \t " + test;
        assertTrue(response.contains(expected));

        expected = "patches: " + "\n \t " + patch;
        assertTrue(response.contains(expected));

        expected = "selected patch: " + patch;
        assertTrue(response.contains(expected));
    }
}