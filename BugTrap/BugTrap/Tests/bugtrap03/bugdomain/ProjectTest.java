package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.javafx.image.impl.General;

import bugtrap03.usersystem.Developer;

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
        testDev = new Developer("subsysTester", "Kwinten", "Buytaert");
        testVersion = new VersionID(1,2,3);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(2016, 01, 01);
        testCreationDate = new GregorianCalendar(2000,12,25);   
        testBudget = 1000;
    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
    }
    
    @Test
    public void testHasPermission() {
        fail("Not yet implemented");
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

    @Test
    public void testProjectVersionIDStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 =  new Project(testVersion, testName, testDescription, testDev, testStartDate, testBudget);
        GregorianCalendar vglGreg = new GregorianCalendar();
        assertEquals(testVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertEquals(vglGreg, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperGregorianCalendarLong() {
        Project testProject2 =  new Project(testName, testDescription, testDev, testStartDate, testBudget);
        VersionID vglVersion =  new VersionID();
        GregorianCalendar vglGreg = new GregorianCalendar();
        assertEquals(vglVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertEquals(vglGreg, testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(testStartDate, testProject2.getStartDate());
    }

    @Test
    public void testProjectStringStringDeveloperLong() {
        Project testProject2 =  new Project(testVersion, testName, testDescription, testDev, testStartDate, testBudget);
        VersionID vglVersion =  new VersionID();
        GregorianCalendar vglGreg1 = new GregorianCalendar();
        GregorianCalendar vglGreg2 = new GregorianCalendar();
        assertEquals(testVersion, testProject2.getVersionID());
        assertEquals(testName, testProject2.getName());
        assertEquals(testDescription, testProject2.getDescription());
        assertEquals(vglGreg1., testProject2.getCreationDate());
        assertEquals(testDev, testProject2.getLead());
        assertEquals(vglGreg2, testProject2.getStartDate());
    }

    @Test
    public void testGetLead() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidLead() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStartDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetStartDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidStartDateGregorianCalendarGregorianCalendar() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidStartDateGregorianCalendar() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCreationDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetCreationDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidCreationDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetBudgetEstimate() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetBudgetEstimate() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidBudgetEstimate() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetParent() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetRole() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllRolesDev() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDetails() {
        fail("Not yet implemented");
    }

    @Test
    public void testCloneProject() {
        fail("Not yet implemented");
    }

}
