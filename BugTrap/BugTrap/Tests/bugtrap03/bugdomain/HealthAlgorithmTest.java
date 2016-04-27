package bugtrap03.bugdomain;

import static org.junit.Assert.*;

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
public class HealthAlgorithmTest {

    static Subsystem subsystemA;
    static Subsystem subsystemA1;
    static DataModel model;
    static Issuer issuer;
    static HealthAlgorithm1 ha;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	model = new DataModel();
	Developer lead = model.createDeveloper("healthy13", "healthy1", "healthy1");
	Developer dev = model.createDeveloper("healthy14", "healthy2", "healthy2");
	issuer = model.createIssuer("healthy15", "healthy15", "healthy3");
	Administrator admin = model.createAdministrator("healty16", "healty4", "healty4");

	Project projectA = model.createProject("ProjectTest", "Project for testing", lead, 500, admin);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);
	model.assignToProject(projectA, lead, dev, Role.TESTER);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);

	// make subsystems
	subsystemA = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA", "Description of subsystemA");
	subsystemA1 = model.createSubsystem(admin, subsystemA, new VersionID(), "SubsystemA1",
	        "Description of subsystemA1");
	ha = new HealthAlgorithm1();
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.HealthAlgorithm#checkSubsystem(bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.HealthIndicator, int)}.
     * 
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testCheckSubsystem() throws IllegalArgumentException, PermissionException {
	model.createBugReport(subsystemA, issuer, "First", "First", PList.<BugReport> empty(), null, 10, false);
	model.createBugReport(subsystemA1, issuer, "Second", "Second", PList.<BugReport> empty(), null, 10, false);
	assertFalse(ha.checkSubsystem(subsystemA, HealthIndicator.HEALTY, 50));
	assertTrue(ha.checkSubsystem(subsystemA1, HealthIndicator.HEALTY, 50));
	assertTrue(ha.checkSubsystem(subsystemA, HealthIndicator.SATISFACTORY, 100));
	model.createBugReport(subsystemA1, issuer, "Third", "Third", PList.<BugReport> empty(), null, 10, false);
	assertFalse(ha.checkSubsystem(subsystemA, HealthIndicator.HEALTY, 200));
    }

}
