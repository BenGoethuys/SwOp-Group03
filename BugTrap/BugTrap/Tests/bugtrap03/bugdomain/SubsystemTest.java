package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Administrator;
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
        testVersion = new VersionID(1, 2, 4);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000, 12, 25);
        testBudget = 1000;
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);

        subVersion = new VersionID(9, 8, 7);
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
        assertTrue(subSysTest.hasPermission(testDev, RolePerm.ASSIGN_DEV_PROJECT));
        assertFalse(subSysTest.hasPermission(programmer, RolePerm.SET_TAG_RESOLVED));
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

    @Test(expected = IllegalArgumentException.class)
    public void testSubsystemVersionIDStringStringNullAbstractSystem() {
        @SuppressWarnings("unused")
        Subsystem tempSub = new Subsystem(testVersion, subName, testDescription, null);
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
        PList<BugReport> expectedRep = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2);
        assertEquals(expectedRep, subSysTest.getBugReportList());
        assertEquals(emptyDep, subSysTest2.getBugReportList());
    }


    @Test
    public void testAddBugReport() throws IllegalArgumentException, PermissionException {
        BugReport bugreport3 = subSysTest.addBugReport(testDev, "otherBug5", "i have a love/hate relation with testing", emptyDep);
        PList<BugReport> expectedRep1 = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2);
        assertNotEquals(expectedRep1, subSysTest.getBugReportList());
        PList<BugReport> expectedRep2 = expectedRep1.plus(bugreport3);
        assertEquals(expectedRep2, subSysTest.getBugReportList());

        // alt method for adding BugReport
        BugReport bugReport4 = subSysTest.addBugReport(testDev, "Best title ever", "Yes you did not write this", new GregorianCalendar(2016, 3, 4), emptyDep);
        PList<BugReport> expectedRep3 = expectedRep2.plus(bugReport4);
        assertEquals(expectedRep3, subSysTest.getBugReportList());
    }

    @Test(expected = PermissionException.class)
    public void testAddInvalidPermissionBugReport() throws IllegalArgumentException, PermissionException {
        Administrator admin = new Administrator("uniqueAdminunique", "adje", "minnie");
        @SuppressWarnings("unused")
        BugReport bugreport3 = subSysTest.addBugReport(admin, "otherBug5", "i have a love/hate relation with testing", emptyDep);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidTitleBugReport() throws IllegalArgumentException, PermissionException {
        @SuppressWarnings("unused")
        BugReport bugreport3 = subSysTest.addBugReport(testDev, null, "i have a love/hate relation with testing", emptyDep);
    }

    @Test
    public void testCloneSubsystem() {
        Project cloneProj = new Project("cloneProjTest", "this project is supposed to be a clone", testDev, 99);
        Subsystem cloneSub = subSysTest.cloneSubsystem(cloneProj);
        assertEquals(subSysTest.getVersionID(), cloneSub.getVersionID());
        assertEquals(subSysTest.getName(), cloneSub.getName());
        assertEquals(subSysTest.getDescription(), cloneSub.getDescription());
        assertNotEquals(subSysTest.getParent(), cloneSub.getParent());
        assertEquals(cloneProj, cloneSub.getParent());
    }

    @Test
    public void testGetVersionID() throws Exception {
        assertEquals(subVersion, subSysTest.getVersionID());

    }

    @Test
    public void testSetVersionID() throws Exception {
        VersionID vid = new VersionID(12,11,10);
        subSysTest.setVersionID(vid);
        assertNotEquals(subVersion, subSysTest.getVersionID());
        assertEquals(vid, subSysTest.getVersionID());
    }

    @Test
    public void testIsValidVersionId() throws Exception {
        assertTrue(Project.isValidVersionId(subVersion));
        assertFalse(Project.isValidVersionId(null));
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(subName, subSysTest.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String ena = "Extra test name woehoew";
        subSysTest.setName(ena);
        assertNotEquals(subName, subSysTest.getName());
        assertEquals(ena, subSysTest.getName());
    }

    @Test
    public void testIsValidName() throws Exception {
        assertFalse(Project.isValidName(""));
        assertFalse(Project.isValidName(null));
        assertTrue(Project.isValidName(subName));
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals(subDescription, subSysTest.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        String ede = "Extra test description woehoew";
        subSysTest.setDescription(ede);
        assertNotEquals(subDescription, subSysTest.getDescription());
        assertEquals(ede, subSysTest.getDescription());
    }

    @Test
    public void testIsValidDescription() throws Exception {
        assertTrue(Project.isValidDescription(subDescription));
    }

    @Test
    public void testGetChilds() throws Exception {
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest).plus(subSysTest2);
        assertEquals(childList, testProject.getChilds());
    }

    @Test
    public void testMakeSubsystemChild() throws Exception {
        String ede = "Extra test description woehoew 2";
        String ena = "Extra test name woehoew 2";
        Subsystem esu = subSysTest2.makeSubsystemChild(ena, ede);
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        assertNotEquals(childList, subSysTest2.getChilds());
        childList = childList.plus(esu);
        assertEquals(childList,subSysTest2.getChilds());
    }

    @Test
    public void testMakeSubsystemChild1() throws Exception {
        VersionID vid = new VersionID(56,21,20);
        String ede = "Extra test description woehoew 2";
        String ena = "Extra test name woehoew 2";
        Subsystem esu = subSysTest2.makeSubsystemChild(vid, ena, ede);
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        assertNotEquals(childList, subSysTest2.getChilds());
        childList = childList.plus(esu);
        assertEquals(childList,subSysTest2.getChilds());
    }

    @Test
    public void testGetParentProject() throws Exception {
        assertEquals(testProject, subSysTest.getParentProject());
        assertEquals(testProject, subSysTest2.getParentProject());
    }

    @Test
    public void testGetAllDev() throws Exception {
        assertEquals(PList.<Developer>empty().plus(testDev), subSysTest2.getAllDev());
        assertEquals(PList.<Developer>empty().plus(testDev), subSysTest.getAllDev());
    }

    @Test
    public void testGetAllSubsystems() throws Exception {
        Subsystem ss3 = subSysTest2.makeSubsystemChild("uhu", "aha");
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest).plus(ss3);
        assertEquals(childList, subSysTest2.getAllSubsystems());
        PList<Subsystem> childList2 = PList.<Subsystem>empty().plus(subSysTest2).plus(subSysTest).plus(ss3);
        assertEquals(childList2, subSysTest.getAllSubsystems());
    }



    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalidSubsDescription() {
        subSysTest.setDescription("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetNullSubsDescription() {
        subSysTest.setDescription(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetNullSubsVersionID() {
        subSysTest.setVersionID(null);
    }
}
