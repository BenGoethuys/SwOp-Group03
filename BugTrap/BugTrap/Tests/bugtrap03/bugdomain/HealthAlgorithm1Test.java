/**
 * 
 */
package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * @author Mathias
 *
 */
public class HealthAlgorithm1Test {

    static Subsystem subsystem;
    static DataModel model;
    static Issuer issuer;
    static HealthAlgorithm1 ha;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	model = new DataModel();
	Developer lead = model.createDeveloper("healthy1", "healthy1", "healthy1");
	Developer dev = model.createDeveloper("healthy2", "healthy2", "healthy2");
	issuer = model.createIssuer("healthy3", "healthy3", "healthy3");
	Administrator admin = model.createAdministrator("healty4", "healty4", "healty4");

	Project projectA = model.createProject("ProjectTest", "Project for testing", lead, 500, admin);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);
	model.assignToProject(projectA, lead, dev, Role.TESTER);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);

	// make subsystems
	subsystem = model.createSubsystem(admin, projectA, new VersionID(), "Subsystem", "Description of susbsystem");

	ha = new HealthAlgorithm1();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testHealthIndicators() {
	
    }
    
    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isHealthy(bugtrap03.bugdomain.Subsystem)}.
     * 
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testIsHealthy() throws IllegalArgumentException, PermissionException {
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	System.out.println(subsystem.getBugImpact());
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTY);
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isSatisfactory(bugtrap03.bugdomain.Subsystem)}.
     * 
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testIsSatisfactory() throws IllegalArgumentException, PermissionException {
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	System.out.println(subsystem.getBugImpact());
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTY);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isStable(bugtrap03.bugdomain.Subsystem)}.
     * @throws PermissionException 
     * @throws IllegalArgumentException 
     */
    @Test
    public void testIsStable() throws IllegalArgumentException, PermissionException {
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	System.out.println(subsystem.getBugImpact());
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isSerious(bugtrap03.bugdomain.Subsystem)}.
     */
    @Test
    public void testIsSerious() {
    }

}
