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

public class BugReportStateNewTest {

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
        issuer = new Issuer("blaStateNewTest", "bla", "bla");
        depList = PList.<BugReport>empty();
        test = "This is a test";
        patch = "This is a patch";

        dev = new Developer("blaStateNewTest1", "Jan", "Smidt");
        lead = new Developer("blaStateNewTest2", "Jan", "Smidt");
        programer = new Developer("blaStateNewTest3", "Jos", "Smidt");
        tester = new Developer("blaStateNewTest4", "Jantje", "Smidt");
        admin = new Administrator("blaStateNewTest5", "bla", "hihi");

        project = new Project("ANewProject", "the description of the project", lead, 0);
        project.setRole(lead, programer, Role.PROGRAMMER);
        project.setRole(lead, tester, Role.TESTER);
        subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project);

        bugReport = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");

        depList2 = depList.plus(bugReport);
        bugReportDep = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList2, subsystem, milestone, false, "", "", "");

        // give bug reports desired state:
        // ok
    }

    @Before
    public void setUp() throws Exception {
        tempBugReport = new BugReport(issuer, "bla bla", "bla", new GregorianCalendar(), depList, subsystem, null, false, null, null, null);
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(Tag.NEW, bugReport.getTag());
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

        String expected = "tag: " + Tag.NEW.name();
        assertTrue(response.contains(expected));
    }
}