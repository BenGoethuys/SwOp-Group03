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

public class BugReportStateAssignedWithTestTest {

    private static final double EPSILON = 1e-15;

    // classes for testing
    static BugReport bugReport;
    static BugReport bugReportDep;
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
        issuer = new Issuer("blaStateAssignedWithTestTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateAssignedWithTestTest1", "Jan", "Smidt");
        lead = new Developer("blaStateAssignedWithTestTest2", "Jan", "Smidt");
        programer = new Developer("blaStateAssignedWithTestTest3", "Jos", "Smidt");
        tester = new Developer("blaStateAssignedWithTestTest4", "Jantje", "Smidt");
        admin = new Administrator("blaStateAssignedWithTestTest5", "bla", "hihi");

        project = new Project("ANewProject", "the description of the project", lead, 0);
        project.setRole(lead, programer, Role.PROGRAMMER);
        project.setRole(lead, tester, Role.TESTER);
        subsystem = project.addSubsystem("ANewSubSystem", "the decription of the subsystem");

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, 1, false, "", "", "");

        depList2 = depList.plus(bugReport);
        bugReportDep = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList2, subsystem, milestone, 1, false, "", "", "");

        // give bug reports desired state:
        bugReport.addUser(lead, tester);
        bugReport.addUser(lead, programer);
        bugReport.addTest(tester, test);

        bugReportDep.addUser(lead, tester);
        bugReportDep.addUser(lead, programer);
        bugReportDep.addTest(tester, test);
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, 1, false, null, null, null);
        tempBugReport.addUser(lead, tester);
        tempBugReport.addUser(lead, programer);
        tempBugReport.addTest(tester, test);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.ASSIGNED, bugReport.getTag());
    }

    @Test
    public void setTag() throws Exception {
        tempBugReport.getInternState().setTag(tempBugReport, Tag.NOT_A_BUG);
        assertEquals(Tag.NOT_A_BUG, tempBugReport.getTag());
    }

    @Test (expected = IllegalStateException.class)
    public void setTagUnresolvedDep() throws IllegalStateException {
        bugReportDep.getInternState().setTag(bugReportDep, Tag.NOT_A_BUG);
    }

    @Test
    public void isValidTag() throws Exception {
        assertTrue(bugReport.isValidTag(Tag.NOT_A_BUG));
        assertFalse(bugReport.isValidTag(Tag.NEW));
        assertFalse(bugReport.isValidTag(Tag.ASSIGNED));
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

    @Test (expected = IllegalStateException.class)
    public void addPatchUnresolvedDep() throws Exception {
        bugReportDep.getInternState().addPatch(bugReportDep, patch);
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

    @Test
    public void setDuplicate() throws Exception {
        tempBugReport.getInternState().setDuplicate(tempBugReport, bugReport);
        assertEquals(Tag.DUPLICATE, tempBugReport.getTag());
        assertEquals(bugReport, tempBugReport.getDuplicate());
    }

    @Test (expected = IllegalStateException.class)
    public void setDuplicateUnresolvedDep() throws Exception {
        bugReportDep.getInternState().setDuplicate(bugReportDep, tempBugReport);
    }

    @Test (expected = IllegalStateException.class)
    public void getDuplicate() throws Exception {
        bugReport.getInternState().getDuplicate();
    }

    @Test
    public void isResolved() throws Exception {
        assertFalse(bugReport.isResolved());
        assertFalse(bugReportDep.isResolved());
    }

    @Test
    public void getDetails() throws Exception {
        String response = bugReport.getInternState().getDetails();

        String expected = "tag: " + Tag.ASSIGNED.name();
        assertTrue(response.contains(expected));

        expected = "tests: " + "\n \t " + test;
        assertTrue(response.contains(expected));
    }

    @Test
    public void getMultiplier(){
        assertEquals(2, bugReport.getMultiplier(), EPSILON);
    }
}