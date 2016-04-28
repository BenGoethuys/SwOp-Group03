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
public class HealthAlgorithm3Test {

    static Subsystem subsystem;
    static DataModel model;
    static Issuer issuer;
    static HealthAlgorithm3 ha;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	model = new DataModel();
	Developer lead = model.createDeveloper("healthy9", "healthy1", "healthy1");
	Developer dev = model.createDeveloper("healthy10", "healthy2", "healthy2");
	issuer = model.createIssuer("healthy11", "healthy3", "healthy3");
	Administrator admin = model.createAdministrator("healty12", "healty4", "healty4");

	Project projectA = model.createProject(new VersionID(), "ProjectTest", "Project for testing", lead, 500, admin);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);
	model.assignToProject(projectA, lead, dev, Role.TESTER);
	model.assignToProject(projectA, lead, dev, Role.PROGRAMMER);

	// make subsystems
	subsystem = model.createSubsystem(admin, projectA, new VersionID(), "Subsystem", "Description of susbsystem");

	ha = new HealthAlgorithm3();
    }

    @Test
    public void testHealthIndicators() throws IllegalArgumentException, PermissionException {
	// HEALTHY
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 3, false);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTHY);

	// SATISFACTORY
	model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTHY);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);

	// STABLE
	for (int i = 1; i < 4; i++) {
	    model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	}
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTHY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.STABLE);

	// SERIOUS
	for (int i = 1; i < 13; i++) {
	    model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	}
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTHY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.STABLE);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.SERIOUS);

	// CRITICAL
	for (int i = 1; i < 2; i++) {
	    model.createBugReport(subsystem, issuer, "bugrep", "descr", PList.<BugReport> empty(), null, 10, false);
	}
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.HEALTHY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.SATISFACTORY);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.STABLE);
	assertNotEquals(subsystem.getIndicator(ha), HealthIndicator.SERIOUS);
	assertEquals(subsystem.getIndicator(ha), HealthIndicator.CRITICAL);
    }

}