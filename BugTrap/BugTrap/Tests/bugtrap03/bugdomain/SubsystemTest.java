package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class SubsystemTest {

    private static final double EPSILON = 1e-15;

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
    private static String subName2;
    private static String subDescription2;
    private static Subsystem subSysTest;
    private static Subsystem subSysTest2;
    private static Subsystem subSysTest_2;

    private static PList<BugReport> emptyDep;
    private static PList<BugReport> depToRep1;
    private static BugReport bugreport1;
    private static BugReport bugreport2;
    private static BugReport bugreport3;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester321", "Kwinten", "Buytaert");
        testVersion = new VersionID(1, 2, 4);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000, 12, 25);
        testBudget = 1000;

        subVersion = new VersionID(9, 8, 7);
        subName = "testSub";
        subDescription = "This is a test description of a subsystem";
        subName2 = "meh";
        subDescription2 = "moeh";
        emptyDep = PList.<BugReport>empty();
    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
        subSysTest = testProject.addSubsystem(subVersion, subName, subDescription);
        subSysTest2 = subSysTest.addSubsystem(subName2, subDescription2);
        subSysTest_2 = testProject.addSubsystem(subVersion, subName + "_2", subDescription + "_2");
        bugreport1 = subSysTest.addBugReport(testDev, "testBug3", "this is description of testbug 3", testStartDate, emptyDep, null, 5, false, null, null, null);
        depToRep1 = PList.<BugReport>empty().plus(bugreport1);
        bugreport2 = subSysTest.addBugReport(testDev, "otherBug4", "i like bananas", testStartDate, depToRep1, null, 2, false, null, null, null);
        bugreport3 = subSysTest2.addBugReport(testDev, "otherBug5", "i like bananas", testStartDate, depToRep1, null, 3, false, null, null, null);
        bugreport3.addUser(testDev, testDev);
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
        PList<BugReport> expectedRep = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2).plus(bugreport3);
        PList<BugReport> expectedRep2 = PList.<BugReport>empty().plus(bugreport3);
        PList<BugReport> getRep1 = subSysTest.getAllBugReports();
        PList<BugReport> getRep2 = subSysTest2.getAllBugReports();
        assertEquals(3, getRep1.size());
        assertTrue(getRep1.containsAll(expectedRep));
        assertEquals(1, getRep2.size());
        assertTrue(getRep2.containsAll(expectedRep2));

    }

    @Test
    public void testGetBugImpact() throws PermissionException {
        assertEquals(27.0, subSysTest.getBugImpact(), EPSILON);
    }

    @Test
    public void testHasPermission() {
        //FIXME:
//        // this doesn't do anything! ASSIGN_DEV_PROJECT is never used ...
//        assertTrue(subSysTest.hasPermission(testDev, RolePerm.ASSIGN_LEAD_ROLE));
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

//    @Test
//    public void testIsValidParent() {
//        assertFalse(subSysTest.isValidParent(null));
//        assertFalse(subSysTest.isValidParent(subSysTest2));
//        assertTrue(subSysTest2.isValidParent(subSysTest));
//    }
    @Test
    public void testGetBugReportList() {
        PList<BugReport> expectedRep = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2);

        assertEquals(2, subSysTest.getBugReportList().size());
        assertTrue(subSysTest.getBugReportList().containsAll(expectedRep));
        assertEquals(1, subSysTest2.getBugReportList().size());
        assertTrue(subSysTest2.getBugReportList().contains(bugreport3));
    }

    @Test
    public void testAddBugReport() throws IllegalArgumentException, PermissionException {
        BugReport bugreport3 = subSysTest.addBugReport(testDev, "otherBug5", "i have a love/hate relation with testing", testStartDate, emptyDep, null, 1, false, null, null, null);
        PList<BugReport> expectedRep1 = PList.<BugReport>empty().plus(bugreport1).plus(bugreport2);
        assertNotEquals(expectedRep1, subSysTest.getBugReportList());
        PList<BugReport> expectedRep2 = expectedRep1.plus(bugreport3);
        assertEquals(expectedRep2, subSysTest.getBugReportList());

        // alt method for adding BugReport
        BugReport bugReport4 = subSysTest.addBugReport(testDev, "Best title ever", "Yes you did not write this", new GregorianCalendar(2016, 3, 4), emptyDep, null, 1, false, null, null, null);
        PList<BugReport> expectedRep3 = expectedRep2.plus(bugReport4);
        assertEquals(expectedRep3, subSysTest.getBugReportList());
    }

    @Test(expected = PermissionException.class)
    public void testAddInvalidPermissionBugReport() throws IllegalArgumentException, PermissionException {
        Administrator admin = new Administrator("uniqueAdminunique", "adje", "minnie");
        @SuppressWarnings("unused")
        BugReport bugreport3 = subSysTest.addBugReport(admin, "otherBug5", "i have a love/hate relation with testing", testStartDate, emptyDep, null, 1, false, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidTitleBugReport() throws IllegalArgumentException, PermissionException {
        @SuppressWarnings("unused")
        BugReport bugreport3 = subSysTest.addBugReport(testDev, null, "i have a love/hate relation with testing", testStartDate, emptyDep, null, 1, false, null, null, null);
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
        VersionID vid = new VersionID(12, 11, 10);
        subSysTest2.setVersionID(vid);
        assertNotEquals(new VersionID(), subSysTest2.getVersionID());
        assertEquals(vid, subSysTest2.getVersionID());
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
        subSysTest2.setName(ena);
        assertNotEquals(subName2, subSysTest2.getName());
        assertEquals(ena, subSysTest2.getName());
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
        subSysTest2.setDescription(ede);
        assertNotEquals(subDescription2, subSysTest2.getDescription());
        assertEquals(ede, subSysTest2.getDescription());
    }

    @Test
    public void testIsValidDescription() throws Exception {
        assertTrue(Project.isValidDescription(subDescription));
    }

    @Test
    public void testGetChilds() throws Exception {
        assertTrue(testProject.getSubsystems().contains(subSysTest));
        assertFalse(testProject.getSubsystems().contains(subSysTest2));
        assertTrue(subSysTest.getSubsystems().contains(subSysTest2));
        assertFalse(subSysTest.getSubsystems().contains(subSysTest));
    }

    @Test
    public void testMakeSubsystemChild() throws Exception {
        String ede = "Extra test description woehoew 2";
        String ena = "Extra test name woehoew 2";
        Subsystem esu = subSysTest.addSubsystem(ena, ede);
        assertTrue(subSysTest.getSubsystems().contains(esu));
    }

    @Test
    public void testMakeSubsystemChild1() throws Exception {
        VersionID vid = new VersionID(56, 21, 20);
        String ede = "Extra test description woehoew 2";
        String ena = "Extra test name woehoew 2";
        Subsystem esu = subSysTest.addSubsystem(vid, ena, ede);
        assertTrue(subSysTest.getSubsystems().contains(esu));
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
        assertTrue(subSysTest.getAllSubsystems().contains(subSysTest2));
        Subsystem ss3 = subSysTest2.addSubsystem("uhu", "aha");
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest2).plus(ss3);
        assertTrue(subSysTest.getAllSubsystems().containsAll(childList));
        assertTrue(subSysTest2.getAllSubsystems().contains(ss3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidSubsDescription() {
        subSysTest.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsDescription() {
        subSysTest.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsVersionID() {
        subSysTest.setVersionID(null);
    }

    @Test
    public void testGetSubjectName() {
        String res = subSysTest.getSubjectName();
        assertTrue(res.contains("Subsystem " + subSysTest.getName()));
    }

    @Test
    public void testIsValidMergeSubsystem() {
        assertFalse(subSysTest.isValidMergeSubsystem(null));
        assertFalse(subSysTest.isValidMergeSubsystem(subSysTest));
        assertFalse(subSysTest_2.isValidMergeSubsystem(subSysTest2));
        assertFalse(subSysTest2.isValidMergeSubsystem(subSysTest_2));

        assertTrue(subSysTest.isValidMergeSubsystem(subSysTest2));
        assertFalse(subSysTest2.isValidMergeSubsystem(subSysTest));

        subSysTest.setTerminated(true);

        assertFalse(subSysTest.isValidMergeSubsystem(subSysTest2));
        assertFalse(subSysTest2.isValidMergeSubsystem(subSysTest));

        subSysTest.setTerminated(false);
    }

    @Test
    public void testGetCompatibleSubs() {
        PList<Subsystem> list = subSysTest.getCompatibleSubs();
        assertEquals(2, list.size());
        assertTrue(list.contains(subSysTest2));
        assertTrue(list.contains(subSysTest_2));

        list = subSysTest2.getCompatibleSubs();
        assertEquals(0, list.size());

        list = subSysTest_2.getCompatibleSubs();
        assertEquals(1, list.size());
        assertTrue(list.contains(subSysTest));
    }
}
