package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class AbstractSystemTest {
    private static Developer testDev;
    private static VersionID testVersion;
    private static String testName;
    private static String testDescription;
    private static GregorianCalendar testStartDate;
    private static GregorianCalendar testCreationDate;
    private static long testBudget;
    private static Project testProject;

    private static VersionID subVersion;
    private static String subName;
    private static String subDescription;
    private static Subsystem subSysTest;
    private static Subsystem subSysTest2;

    private static PList<BugReport> emptyDep;
    private static PList<BugReport> depToRep1;
    private static BugReport bugreport1;
    private static BugReport bugreport2;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester3210", "KwintenAS", "BuytaertAS");
        testVersion = new VersionID(1, 2, 4);
        testName = "testProjAS";
        testDescription = "This is an description of an AS project";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000, 12, 25);
        testBudget = 1000;

        subVersion = new VersionID(9, 8, 5);
        subName = "testSubAS";
        subDescription = "This is a test description of a as subsystem";

        emptyDep = PList.<BugReport> empty();

    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate,
                testBudget);
        subSysTest = testProject.makeSubsystemChild(subVersion, subName, subDescription);
        subSysTest2 = subSysTest.makeSubsystemChild("mehAS", "moehAS");
        bugreport1 = subSysTest.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", emptyDep);
        depToRep1 = PList.<BugReport> empty().plus(bugreport1);
        bugreport2 = subSysTest2.addBugReport(testDev, "otherBug4AS", "i like bonobos", depToRep1);
    }

    @Test
    public void testGetVersionID() {
        assertEquals(new VersionID(), subSysTest2.getVersionID());
        assertEquals(subVersion, subSysTest.getVersionID());
        assertEquals(testVersion, testProject.getVersionID());
    }

    @Test
    public void testSetVersionID() {
        testProject.setVersionID(subVersion);
        subSysTest.setVersionID(testVersion);
        assertEquals(testVersion, subSysTest.getVersionID());
        assertEquals(subVersion, testProject.getVersionID());
    }

    @Test
    public void isValidVersionID() {
        assertTrue(AbstractSystem.isValidVersionId(new VersionID(0, 0, 2)));
        assertTrue(AbstractSystem.isValidVersionId(new VersionID()));
        assertFalse(AbstractSystem.isValidVersionId(null));
    }

    @Test
    public void testGetName() {
        assertEquals(subName, subSysTest.getName());
        assertEquals(testName, testProject.getName());
    }

    @Test
    public void testSetName() {
        testProject.setName(subName);
        subSysTest.setName(testName);
        assertEquals(testName, subSysTest.getName());
        assertEquals(subName, testProject.getName());
    }

    @Test
    public void testIsValidName() {
        assertFalse(AbstractSystem.isValidName(""));
        assertFalse(AbstractSystem.isValidName(null));
        assertTrue(AbstractSystem.isValidName(subName));
        assertTrue(AbstractSystem.isValidName(testName));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidName() {
        testProject.setName("");
    }

    @Test
    public void testGetDescription() {
        assertEquals(testDescription, testProject.getDescription());
        assertEquals(subDescription, subSysTest.getDescription());
    }

    @Test
    public void testSetDescription() {
        testProject.setDescription(subDescription);
        subSysTest.setDescription(testDescription);
        assertEquals(subDescription, testProject.getDescription());
        assertEquals(testDescription, subSysTest.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidProjDescription() {
        testProject.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidSubsDescription() {
        subSysTest.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjDescription() {
        testProject.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsDescription() {
        subSysTest.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjVersionID() {
        testProject.setVersionID(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsVersionID() {
        subSysTest.setVersionID(null);
    }

    @Test
    public void testIsValidDescription() {
        assertFalse(AbstractSystem.isValidDescription(""));
        assertFalse(AbstractSystem.isValidDescription(null));
        assertTrue(AbstractSystem.isValidDescription(testDescription));
    }

    @Test
    public void testGetChilds() {
        assertEquals(PList.<Subsystem> empty().plus(subSysTest), testProject.getChilds());
        assertEquals(PList.<Subsystem> empty().plus(subSysTest2), subSysTest.getChilds());
        assertEquals(PList.<Subsystem> empty(), subSysTest2.getChilds());
    }

    @Test
    public void testMakeSubsystemChildVersionIDStringString() {
        VersionID extraVersion = new VersionID(5, 3, 7);
        String extraName = "extra naam";
        String extraDes = "extra des";
        Subsystem extraSubsys = testProject.makeSubsystemChild(extraVersion, extraName, extraDes);
        assertEquals(extraVersion, extraSubsys.getVersionID());
        assertEquals(extraName, extraSubsys.getName());
        assertEquals(extraDes, extraSubsys.getDescription());
        assertTrue(testProject.getChilds().contains(extraSubsys));
    }

    @Test
    public void testMakeSubsystemChildStringString() {
        String extraName = "extra naam2";
        String extraDes = "extra des2";
        Subsystem extraSubsys = testProject.makeSubsystemChild(extraName, extraDes);
        assertEquals(new VersionID(), extraSubsys.getVersionID());
        assertEquals(extraName, extraSubsys.getName());
        assertEquals(extraDes, extraSubsys.getDescription());
        assertTrue(testProject.getChilds().contains(extraSubsys));
    }

    @Test
    public void testGetParentProject() {
        assertEquals(testProject, subSysTest.getParentProject());
        assertEquals(testProject, subSysTest2.getParentProject());
        assertEquals(testProject, testProject.getParentProject());
    }

    @Test
    public void testGetAllBugReports() {
        PList<BugReport> buglist = PList.<BugReport> empty().plus(bugreport2);
        assertEquals(buglist, subSysTest2.getAllBugReports());
        buglist = buglist.plus(bugreport1);
        assertEquals(buglist, subSysTest.getAllBugReports());
        assertEquals(buglist, testProject.getAllBugReports());
    }

    @Test
    public void testGetAllSubsystems() {
        PList<Subsystem> sublist = PList.<Subsystem> empty();
        assertEquals(sublist, subSysTest2.getAllSubsystems());
        sublist = sublist.plus(subSysTest2);
        assertEquals(sublist, subSysTest.getAllSubsystems());
        PList<Subsystem> sublist2 = PList.<Subsystem> empty().plus(subSysTest).plus(subSysTest2);
        assertEquals(sublist2, testProject.getAllSubsystems());
    }

    @Test
    public void testGetMilestone() {
        Milestone milestone = new Milestone(0);
        assertEquals(testProject.getMilestone(), milestone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidMilestone() {
        testProject.setMilestone(null);
    }

}
