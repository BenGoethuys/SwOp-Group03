/**
 * 
 */
package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.bugreport.BugReport;
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
    static BugReport bugRep;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	DataModel model = new DataModel();
	Developer lead = model.createDeveloper("healthy1", "healthy1", "healthy1");
	Developer dev = model.createDeveloper("healthy2", "healthy2", "healthy2");
	Issuer issuer = model.createIssuer("healthy3", "healthy3", "healthy3");
	Administrator admin = model.createAdministrator("healty4", "healty4", "healty4");

	Project projectA = model.createProject("ProjectTest", "Project for testing", lead, 500, admin);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);
	model.assignToProject(projectA, lead, dev, Role.TESTER);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);

	// make subsystems
	subsystem = model.createSubsystem(admin, projectA, new VersionID(), "Subsystem", "Description of susbsystem");

	bugRep = model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 5,
	        false);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isHealthy(bugtrap03.bugdomain.Subsystem)}.
     */
    @Test
    public void testIsHealthy() {

	HealthAlgorithm1 ha1 = new HealthAlgorithm1();
	HealthAlgorithm2 ha2 = new HealthAlgorithm2();
	HealthAlgorithm3 ha3 = new HealthAlgorithm3();
	System.out.println(bugRep.getBugImpact());
	System.out.println(subsystem.getIndicator(ha1));
	System.out.println(subsystem.getIndicator(ha2));
	System.out.println(subsystem.getIndicator(ha3));
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isSatisfactory(bugtrap03.bugdomain.Subsystem)}.
     */
    @Test
    public void testIsSatisfactory() {
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isStable(bugtrap03.bugdomain.Subsystem)}.
     */
    @Test
    public void testIsStable() {
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm1#isSerious(bugtrap03.bugdomain.Subsystem)}.
     */
    @Test
    public void testIsSerious() {
    }

}
