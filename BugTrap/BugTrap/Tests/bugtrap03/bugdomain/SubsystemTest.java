package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.usersystem.Developer;

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
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester", "Kwinten", "Buytaert");
        testVersion = new VersionID(1,2,3);
        testName = "testProj";
        testDescription = "This is an description";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000,12,25);   
        testBudget = 1000;
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate, testBudget);
        
        
    }


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetParent() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllBugReports() {
        fail("Not yet implemented");
    }

    @Test
    public void testHasPermission() {
        fail("Not yet implemented");
    }

    @Test
    public void testSubsystemVersionIDStringStringAbstractSystem() {
        fail("Not yet implemented");
    }

    @Test
    public void testSubsystemStringStringAbstractSystem() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidParent() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetBugReportList() {
        fail("Not yet implemented");
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
