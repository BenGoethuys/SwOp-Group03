package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import purecollections.PList;

public class SubsystemTest {
    static Developer testDev;
    static VersionID testVersion;
    static String testName;
    static String testDescription;
    static GregorianCalendar testStartDate;
    static GregorianCalendar testCreationDate;
    static long testBudget;
    static Project testProject;
    
    static VersionID subVersion;
    static String subName;
    static String subDescription;
    static Subsystem subSysTest;
    static Subsystem subSysTest2;
    
    static PList<BugReport> emptyDep;
    static PList<BugReport> depToRep1;
    static BugReport bugreport1;
    static BugReport bugreport2;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester321", "Kwinten", "Buytaert");
        testVersion = new VersionID(1,2,4);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000,12,25);   
        testBudget = 1000;
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
        
        subVersion = new VersionID(9,8,7);
        subName = "testSub";
        subDescription = "This is a test description of a subsystem";
        subSysTest = testProject.makeSubsystemChild(subVersion, subName, subDescription);
        subSysTest2 = subSysTest.makeSubsystemChild("meh", "moeh");
        
        emptyDep = PList.<BugReport>empty();
        bugreport1 = subSysTest.addBugReport(testDev, "testBug3", "this is description of testbug 3", emptyDep);

        depToRep1 = PList.<BugReport>empty().plus(bugreport1);
        bugreport2 = subSysTest.addBugReport(testDev, "otherBug4", "i like bananas", depToRep1);
    }


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetParent() {
        assertNotEquals(null, subSysTest.getParent());
        assertNotEquals(null, subSysTest2.getParent());
        assertEquals(testProject, subSysTest.getParent());
        assertEquals(subSysTest, subSysTest2.getParent());
    }

    @Test
    public void testGetAllBugReports() {
        PList<BugReport> expectedRep = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2);
        PList<BugReport> getRep1 = subSysTest.getAllBugReports();
        PList<BugReport> getRep2 = subSysTest2.getAllBugReports();
        assertEquals(expectedRep, getRep1);
        assertEquals(emptyDep, getRep2);
        
    }

    @Test
    public void testHasPermission() {
        Developer programmer = new Developer("ladiedadieda2", "ladie2", "da2");
        assertTrue(testProject.hasPermission(testDev, RolePerm.ASSIGN_DEV_PROJECT));
        assertFalse(testProject.hasPermission(programmer, RolePerm.SET_TAG_RESOLVED));
    }

    @Test
    public void testSubsystemVersionIDStringStringAbstractSystem() {
        Subsystem tempSub = new Subsystem(testVersion, subName, testDescription, testProject);
        assertEquals(testVersion, tempSub.getVersionID());
        assertEquals(subName, tempSub.getName());
        assertEquals(testDescription, tempSub.getDescription());
        assertEquals(testProject, tempSub.getParent());
        assertEquals(emptyDep, tempSub.getBugReportList());
        
    }

    @Test
    public void testSubsystemStringStringAbstractSystem() {
        Subsystem tempSub = new Subsystem(subName, testDescription, testProject);
        assertEquals(new VersionID(), tempSub.getVersionID());
        assertEquals(subName, tempSub.getName());
        assertEquals(testDescription, tempSub.getDescription());
        assertEquals(testProject, tempSub.getParent());
        assertEquals(emptyDep, tempSub.getBugReportList());
    }

    @Test
    public void testIsValidParent() {
        assertFalse(subSysTest.isValidParent(null));
        assertFalse(subSysTest.isValidParent(subSysTest2));
        assertTrue(subSysTest2.isValidParent(subSysTest));
    }

    @Test
    public void testGetBugReportList() {
        assertEquals(expected, actual);
    }

    @Test
    public void testAddBugReport() {
        fail("Not yet implemented");
    }

    @Test
    public void testCloneSubsystem() {
        fail("Not yet implemented");
    }

}
