package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class ProjectTest {
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


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester123", "Kwinten", "Buytaert");
        testVersion = new VersionID(1, 2, 3);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000, 12, 25);
        testBudget = 1000;

        subVersion = new VersionID(9, 8, 5);
        subName = "testSubAS";
        subDescription = "This is a test description of a as subsystem";
    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
        subSysTest = testProject.makeSubsystemChild(subVersion, subName, subDescription);
    }

    @Test
    public void testHasPermission() {
        Developer programmer = new Developer("ladiedadieda", "ladie", "da");
        assertTrue(testProject.hasPermission(testDev, RolePerm.ASSIGN_DEV_PROJECT));
    }

    @Test
    public void testProjectVersionIDStringStringGregorianCalendarDeveloperGregorianCalendarLong() {
        assertEquals(testVersion, testProject.getVersionID());
        assertEquals(testName, testProject.getName());
        assertEquals(testDescription, testProject.getDescription());
        assertEquals(testCreationDate, testProject.getCreationDate());
        assertEquals(testDev, testProject.getLead());
        assertEquals(testStartDate, testProject.getStartDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDevProjectVersionIDStringStringGregorianCalendarDeveloperGregorianCalendarLong() {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, null, testStartDate, testBudget);
    }

    @Test
    public void testProjectVersionIDStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 = new Project(testVersion, testName, testDescription, testDev, testStartDate, testBudget);
        assertEquals(testVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertNotEquals(null, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 = new Project(testName, testDescription, testDev, testStartDate, testBudget);
        VersionID vglVersion = new VersionID();
        assertEquals(vglVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertNotEquals(null, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperLong() {
        Project testProject2 = new Project(testName, testDescription, testDev, testBudget);
        VersionID vglVersion = new VersionID();
        assertEquals(vglVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertNotEquals(null, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertNotEquals(null, testProject2.getStartDate());
    }

    @Test
    public void testGetLead() {
        assertEquals(testDev, testProject.getLead());
    }

    @Test
    public void testIsValidLead() {
        assertTrue(Project.isValidLead(testDev));
        assertFalse(Project.isValidLead(null));
    }

    @Test
    public void testGetStartDate() {
        assertEquals(testStartDate, testProject.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        GregorianCalendar testStartDate2 = new GregorianCalendar(2888, 8, 18);
        testProject.setStartDate(testStartDate2);
        assertNotEquals(testStartDate, testProject.getStartDate());
        assertEquals(testStartDate2, testProject.getStartDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidStartDate() {
        testProject.setStartDate(new GregorianCalendar(1999, 1, 1));

    }

    @Test
    public void testIsValidStartDateGregorianCalendarGregorianCalendar() {
        assertFalse(Project.isValidStartDate(null, testStartDate));
        assertFalse(Project.isValidStartDate(testCreationDate, null));
        assertFalse(Project.isValidStartDate(testStartDate, testCreationDate));
        assertTrue(Project.isValidStartDate(testCreationDate, testStartDate));
        assertTrue(Project.isValidStartDate(testStartDate, testStartDate));

    }

    @Test
    public void testIsValidStartDateGregorianCalendar() {
        GregorianCalendar invalidStartD = new GregorianCalendar(1888, 8, 18);
        assertFalse(testProject.isValidStartDate(invalidStartD));
        assertFalse(testProject.isValidStartDate(null));
        assertTrue(testProject.isValidStartDate(testCreationDate));
        assertTrue(testProject.isValidStartDate(testStartDate));
    }

    @Test
    public void testGetCreationDate() {
        assertNotEquals(testStartDate, testProject.getCreationDate());
        assertEquals(testCreationDate, testProject.getCreationDate());
    }

    @Test
    public void testSetCreationDate() {
        GregorianCalendar testCreationDate2 = new GregorianCalendar(1888, 8, 18);
        testProject.setCreationDate(testCreationDate2);
        assertNotEquals(testCreationDate, testProject.getCreationDate());
        assertEquals(testCreationDate2, testProject.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidCreationDate() {
        testProject.setCreationDate(null);
    }

    @Test
    public void testIsValidCreationDate() {
        assertFalse(Project.isValidCreationDate(null));
        assertTrue(Project.isValidCreationDate(testCreationDate));
    }

    @Test
    public void testGetBudgetEstimate() {
        assertEquals(testBudget, testProject.getBudgetEstimate());
        assertNotEquals(13, testProject.getBudgetEstimate());
    }

    @Test
    public void testSetBudgetEstimate() {
        testProject.setBudgetEstimate(10);
        assertNotEquals(testBudget, testProject.getBudgetEstimate());
        assertEquals(10, testProject.getBudgetEstimate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidBudgetEstimate() {
        testProject.setBudgetEstimate(-10);
    }

    @Test
    public void testIsValidBudgetEstimate() {
        assertFalse(Project.isValidBudgetEstimate(-120));
        assertTrue(Project.isValidBudgetEstimate(0));
        assertTrue(Project.isValidBudgetEstimate(testBudget));
    }

    @Test
    public void testGetParent() {
        assertNotEquals(null, testProject.getParent());
        assertEquals(testProject, testProject.getParent());
    }

    @Test
    public void testGetAllRolesDev() {
        PList<Role> rolesList = PList.<Role>empty().plus(Role.LEAD);
        assertNotEquals(null, testProject.getAllRolesDev(testDev));
        assertEquals(rolesList, testProject.getAllRolesDev(testDev));
    }

    @Test
    public void testSetRole() throws IllegalArgumentException, PermissionException {
        Developer programmer = new Developer("ikGebruikDit", "Joshua", "de Smidt");
        testProject.setRole(testDev, programmer, Role.PROGRAMMER);
        testProject.setRole(testDev, testDev, Role.TESTER);
        PList<Role> programmerRoleList = PList.<Role>empty().plus(Role.PROGRAMMER);
        PList<Role> notProgrammerRoles = PList.<Role>empty().plus(Role.LEAD);
        assertNotEquals(notProgrammerRoles, testProject.getAllRolesDev(programmer));
        assertNotEquals(programmerRoleList, testProject.getAllRolesDev(testDev));
        notProgrammerRoles = notProgrammerRoles.plus(Role.TESTER);
        assertEquals(notProgrammerRoles, testProject.getAllRolesDev(testDev));
        assertEquals(programmerRoleList, testProject.getAllRolesDev(programmer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullRole() throws IllegalArgumentException, PermissionException {
        testProject.setRole(null, testDev, Role.TESTER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullUserRole() throws IllegalArgumentException, PermissionException {
        testProject.setRole(testDev, null, Role.TESTER);
    }


    @Test(expected = PermissionException.class)
    public void testSetInvalidRole() throws IllegalArgumentException, PermissionException {
        Developer programmer = new Developer("ikGebruikDitOok", "Joshua2", "de Smedt");
        testProject.setRole(testDev, programmer, Role.PROGRAMMER);
        testProject.setRole(programmer, testDev, Role.TESTER);
    }


    @Test
    public void testGetDetails() {
        String details = testProject.getDetails();
        //System.out.println(details);
        assertTrue(details.contains(testName));
        assertTrue(details.contains(testDescription));
        assertTrue(details.contains(testVersion.toString()));
        assertTrue(details.contains(testStartDate.getTime().toString()));
        assertTrue(details.contains(testCreationDate.getTime().toString()));
        assertTrue(details.contains("1000"));
        assertTrue(details.contains(testDev.getFullName()));
        assertTrue(details.contains(testDev.getUsername()));
        //System.out.println(details);
    }

    @Test
    public void testCloneProject() {
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        VersionID cversion = new VersionID(6, 6, 6);
        Developer clead = new Developer("cloneClown", "cclone", "cclown");
        GregorianCalendar cstart = new GregorianCalendar(3000, 1, 1);
        long cestimate = 4567;
        Project cloneProject = testProject.cloneProject(cversion, clead, cstart, cestimate);
        assertEquals(4567, cloneProject.getBudgetEstimate());
        assertEquals(cstart, cloneProject.getStartDate());
        assertEquals(clead, cloneProject.getLead());
        assertEquals(cversion, cloneProject.getVersionID());

        assertNotEquals(testBudget, cloneProject.getBudgetEstimate());
        assertNotEquals(testStartDate, cloneProject.getStartDate());
        assertNotEquals(testDev, cloneProject.getLead());
        assertNotEquals(testVersion, cloneProject.getVersionID());

        assertNotEquals(null, cloneProject.getCreationDate());
        assertEquals(testName, cloneProject.getName());
        assertEquals(testDescription, cloneProject.getDescription());

        assertNotEquals(null, cloneProject.getChilds());
        assertNotEquals(childList, cloneProject.getChilds());
    }

    @Test
    public void testGetVersionID() throws Exception {
        assertEquals(testVersion, testProject.getVersionID());

    }

    @Test
    public void testSetVersionID() throws Exception {
        VersionID vid = new VersionID(12, 11, 10);
        testProject.setVersionID(vid);
        assertNotEquals(testVersion, testProject.getVersionID());
        assertEquals(vid, testProject.getVersionID());
    }

    @Test
    public void testIsValidVersionId() throws Exception {
        assertTrue(Project.isValidVersionId(testVersion));
        assertFalse(Project.isValidVersionId(null));
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(testName, testProject.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String ena = "Extra test name woehoew";
        testProject.setName(ena);
        assertNotEquals(testName, testProject.getName());
        assertEquals(ena, testProject.getName());
    }

    @Test
    public void testIsValidName() throws Exception {
        assertFalse(Project.isValidName(""));
        assertFalse(Project.isValidName(null));
        assertTrue(Project.isValidName(testName));
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals(testDescription, testProject.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        String ede = "Extra test description woehoew";
        testProject.setDescription(ede);
        assertNotEquals(testDescription, testProject.getDescription());
        assertEquals(ede, testProject.getDescription());
    }

    @Test
    public void testIsValidDescription() throws Exception {
        assertTrue(Project.isValidDescription(testDescription));
    }

    @Test
    public void testGetChilds() throws Exception {
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        assertEquals(childList, testProject.getChilds());
    }

    @Test
    public void testMakeSubsystemChild() throws Exception {
        String ede = "Extra test description woehoew";
        String ena = "Extra test name woehoew";
        Subsystem esu = testProject.makeSubsystemChild(ena, ede);
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        assertNotEquals(childList, testProject.getChilds());
        childList = childList.plus(esu);
        assertEquals(childList, testProject.getChilds());
    }

    @Test
    public void testMakeSubsystemChild1() throws Exception {
        VersionID vid = new VersionID(56, 21, 22);
        String ede = "Extra test description woehoew";
        String ena = "Extra test name woehoew";
        Subsystem esu = testProject.makeSubsystemChild(vid, ena, ede);
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest);
        assertNotEquals(childList, testProject.getChilds());
        childList = childList.plus(esu);
        assertEquals(childList, testProject.getChilds());
    }

    @Test
    public void testGetParentProject() throws Exception {
        assertEquals(testProject, testProject.getParentProject());
    }

    @Test
    public void testGetAllDev() throws Exception {
        assertEquals(PList.<Developer>empty().plus(testDev), testProject.getAllDev());
    }

    @Test
    public void testGetAllBugReports() throws Exception {
        BugReport bugrep = subSysTest.addBugReport(testDev, "something", "something else", PList.<BugReport>empty());
        assertEquals(PList.<BugReport>empty().plus(bugrep), testProject.getAllBugReports());
    }

    @Test
    public void testGetAllSubsystems() throws Exception {
        Subsystem ss2 = testProject.makeSubsystemChild("uhu", "aha");
        PList<Subsystem> childList = PList.<Subsystem>empty().plus(subSysTest).plus(ss2);
        assertEquals(childList, testProject.getAllSubsystems());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidProjDescription() {
        testProject.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjDescription() {
        testProject.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjVersionID() {
        testProject.setVersionID(null);
    }


}
