/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * @author Mathias
 *
 */
public class MergeSubsystemsCmdTest {

    static Administrator admin;
    static DataModel model;
    static Developer lead;
    static Project projectA;
    static Project projectB;
    static Subsystem subsystemA1;
    static Subsystem subsystemA2;
    static Subsystem subsystemA3;
    static Subsystem subsystemA3_1;
    static Subsystem subsystemA3_2;
    static BugReport bugRep1;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// Setup variables.
	model = new DataModel();
	lead = model.createDeveloper("SplitSub4", "Luky", "Luke");
	admin = model.createAdministrator("SplitSub5", "adminT", "bie");
	Issuer issuer = model.createIssuer("SplitSub6", "BMW", "looks", "nice");
	projectA = model.createProject(new VersionID(), "SplitSub6", "Project for testing 0", lead, 500, admin);
	projectB = model.createProject(new VersionID(), "ProjectTest1", "Project for testing 1", lead, 1000, admin);

	// make subsystems
	subsystemA1 = model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
	subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
	subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
	subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of susbsystem A3.1");
	subsystemA3_2 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");

	bugRep1 = model.createBugReport(subsystemA3, issuer, "Used library not in repository", "title says it all.",
	        PList.<BugReport> empty(), null, 1, false);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.SplitSubsystemCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec() throws IllegalArgumentException, PermissionException, CancelException {
	ArrayDeque<String> question = new ArrayDeque<>();
	ArrayDeque<String> answer = new ArrayDeque<>();
	MergeSubsystemsCmd cmd = new MergeSubsystemsCmd();

	// Setup scenario
	question.add("Merging subsystems.");
	question.add("Select a project.");
	question.add("Available options:");
	question.add("0. " + projectA.getName() + " version: " + projectA.getVersionID());
	question.add("1. " + projectB.getName() + " version: " + projectB.getVersionID());
	question.add("I choose: ");
	answer.add("0");
	question.add("You have chosen:");
	question.add(projectA.getDetails());
	question.add("Select subsystem.");
	question.add("Available options:");
	question.add("0. " + subsystemA1.getName());
	question.add("1. " + subsystemA2.getName());
	question.add("2. " + subsystemA3.getName());
	question.add("3. " + subsystemA3_1.getName());
	question.add("4. " + subsystemA3_2.getName());
	question.add("I choose: ");
	answer.add("0");
	question.add("You selected: " + subsystemA1.getName());
	question.add("Please select a subsystem to merge with the first");
	question.add("Available options:");
	question.add("0. " + subsystemA2.getName());
	question.add("1. " + subsystemA3.getName());
	question.add("I choose: ");
	answer.add("0");
	question.add("Please enter the new name:");
	answer.add("NEW1");
	question.add("Please enter the new description:");
	answer.add("DescriptionNew1");

	TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

	// Execute scenario
	Subsystem chosen = cmd.exec(scan, model, admin);

	// Test effects.
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testException() throws IllegalArgumentException, CancelException, PermissionException {
	MergeSubsystemsCmd cmd = new MergeSubsystemsCmd();
	cmd.exec(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() throws IllegalArgumentException, CancelException, PermissionException {
	MergeSubsystemsCmd cmd = new MergeSubsystemsCmd();
	DataModel model1 = new DataModel();
	cmd.exec(null, model1, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() throws IllegalArgumentException, CancelException, PermissionException {
	MergeSubsystemsCmd cmd = new MergeSubsystemsCmd();
	TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
	        new ArrayDeque<>());
	cmd.exec(scan, null, admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException4() throws IllegalArgumentException, CancelException, PermissionException {
	MergeSubsystemsCmd cmd = new MergeSubsystemsCmd();
	DataModel model2 = new DataModel();
	TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(new ArrayDeque<>()),
	        new ArrayDeque<>());
	cmd.exec(scan, model2, null);
    }

}
