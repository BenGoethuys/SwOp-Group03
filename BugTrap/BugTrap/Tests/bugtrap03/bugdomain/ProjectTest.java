package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import purecollections.PList;

public class ProjectTest {
    static Developer testDev;
    static VersionID testVersion;
    static String testName;
    static String testDescription;
    static GregorianCalendar testStartDate;
    static GregorianCalendar testCreationDate;
    static long testBudget;
    static Project testProject;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester123", "Kwinten", "Buytaert");
        testVersion = new VersionID(1,2,3);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000,12,25);   
        testBudget = 1000;
    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
    }
    
    @Test
    public void testHasPermission() {
        Developer programmer = new Developer("ladiedadieda", "ladie", "da");
        assertTrue(testProject.hasPermission(testDev, RolePerm.ASSIGN_DEV_PROJECT));
        assertFalse(testProject.hasPermission(programmer, RolePerm.SET_TAG_RESOLVED));
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
    
    @Test (expected = IllegalArgumentException.class)
    public void testNullDevProjectVersionIDStringStringGregorianCalendarDeveloperGregorianCalendarLong() {
        Project proj2 = new Project(testVersion, testName, testDescription, testCreationDate, null, testStartDate, testBudget);
    }

    @Test
    public void testProjectVersionIDStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 =  new Project(testVersion, testName, testDescription, testDev, testStartDate, testBudget);
        assertEquals(testVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertNotEquals(null, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 =  new Project(testName, testDescription, testDev, testStartDate, testBudget);
        VersionID vglVersion =  new VersionID();
        assertEquals(vglVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertNotEquals(null, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperLong() {
        Project testProject2 =  new Project(testName, testDescription, testDev, testBudget);
        VersionID vglVersion =  new VersionID();
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
    
    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalidStartDate() {
        testProject.setStartDate(new GregorianCalendar(1999,1,1));
        
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
    
    @Test (expected = IllegalArgumentException.class)
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
    
    @Test (expected = IllegalArgumentException.class)
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
    
    @Test (expected = IllegalArgumentException.class)
    public void testSetNullRole() throws IllegalArgumentException, PermissionException {
        testProject.setRole(null, testDev, Role.TESTER); 
    }
    
    @Test (expected = PermissionException.class)
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

    //TODO add subsystem and check childs
    @Test
    public void testCloneProject() {
        VersionID cversion =  new VersionID(6,6,6);
        Developer clead = new Developer("cloneClown","cclone","cclown");
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
    }
//
//    @Test
//    public void testGetVersionID() throws Exception {
//
//    }
//
//    @Test
//    public void testSetVersionID() throws Exception {
//
//    }
//
//    @Test
//    public void testIsValidVersionId() throws Exception {
//
//    }
//
//    @Test
//    public void testGetName() throws Exception {
//
//    }
//
//    @Test
//    public void testSetName() throws Exception {
//
//    }
//
//    @Test
//    public void testIsValidName() throws Exception {
//
//    }
//
//    @Test
//    public void testGetDescription() throws Exception {
//
//    }
//
//    @Test
//    public void testSetDescription() throws Exception {
//
//    }
//
//    @Test
//    public void testIsValidDescription() throws Exception {
//
//    }
//
//    @Test
//    public void testGetChilds() throws Exception {
//
//    }
//
//    @Test
//    public void testMakeSubsystemChild() throws Exception {
//
//    }
//
//    @Test
//    public void testMakeSubsystemChild1() throws Exception {
//
//    }
//
//    @Test
//    public void testGetParent() throws Exception {
//
//    }
//
//    @Test
//    public void testGetParentProject() throws Exception {
//
//    }
//
//    @Test
//    public void testGetAllDev() throws Exception {
//
//    }
//
//    @Test
//    public void testGetAllBugReports() throws Exception {
//
//    }
//
//    @Test
//    public void testGetAllSubsystems() throws Exception {
//
//    }
//
//    @Test
//    public void testHasPermission() throws Exception {
//
//    }

}
