/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

/**
 * 
 * @author Group 03
 *
 */
public class AssignToProjectCmdTest {

    private static DataModel model;
    private static Developer dev1;
    private static Developer dev2;
    private static Developer dev3;


    private static Project projectA;
    private static Project projectB;
    private static TerminalTestScanner scan;

    private static  ArrayDeque<String> question;
    private static  ArrayDeque<String> answer;
    private static  AssignToProjectCmd cmd;
    private static Administrator admin;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        model = new DataModel();
        dev1 = model.createDeveloper("BlackPowerRanger","Black","Power Ranger");
        dev2 = model.createDeveloper("PinkPowerRanger","Pink","Power Ranger");
        dev3 = model.createDeveloper("YellowPowerRanger","Yellow","Power Ranger");
        Administrator admin = model.createAdministrator("Adm1ral00-6", "Kwinten", "JK");

        projectA = model.createProject("PowerRangerProject", "Project for testing with power rangers", dev1, 999, admin);
        model.assignToProject(projectA, dev1, dev3, Role.PROGRAMMER);
        model.assignToProject(projectA, dev1, dev3, Role.TESTER);

        projectB = model.createProject("PowerRangerProject2", "Project for testing with power rangers2", dev1, 999, admin);
        model.assignToProject(projectB, dev1, dev2, Role.PROGRAMMER);
        model.assignToProject(projectB, dev1, dev3, Role.TESTER);

        cmd = new AssignToProjectCmd();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        question = new ArrayDeque<>();
        answer = new ArrayDeque<>();
    }

    /**
     * Test method for {@link bugtrap03.gui.cmd.AssignToProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testNormalIntExec() throws PermissionException, CancelException {
        // Setup scenario
        question.add("Please select a project you lead.");
        question.add( "Available options:");
        question.add("0. PowerRangerProject version: " + new VersionID().toString());
        question.add("1. PowerRangerProject2 version: " + new VersionID().toString());
        question.add("I choose: ");
        answer.add("11");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("1");
        question.add("You have chosen:");
        question.add(projectB.getDetails());
        question.add("Please select a developer to assign.");
        question.add( "Available options:");
        question.add("0. BlackPowerRanger");
        question.add("1. PinkPowerRanger");
        question.add("2. YellowPowerRanger");
        question.add("I choose: ");
        answer.add("11");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("1");
        question.add("You have chosen: PinkPowerRanger");
        question.add("Please select a role to assign.");
        question.add( "Available options:");
        question.add("0. " + Role.LEAD.name());
        question.add("1. " + Role.TESTER.name());
        answer.add("11");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("1");
        question.add("You have chosen:" + Role.TESTER.name());
        question.add("PinkPowerRanger assigned to project: PowerRangerProject2 version: " + new VersionID().toString() + ", with role: " + Role.TESTER.name());

        // init scanner
        scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosen = cmd.exec(scan, model, dev1);
        // Test effects
        assertTrue(chosen.getAllRolesDev(dev2).contains(Role.TESTER));
    }

    @Test
    public void testNormalStringNoSpaceExec() throws PermissionException, CancelException {
        // Setup scenario
        question.add("Please select a project you lead.");
        question.add( "Available options:");
        question.add("0. PowerRangerProject version: " + new VersionID().toString());
        question.add("1. PowerRangerProject2 version: " + new VersionID().toString());
        question.add("I choose: ");
        answer.add("lala");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("PowerRangerProject20.0.1");
        question.add("You have chosen:");
        question.add(projectB.getDetails());
        question.add("Please select a developer to assign.");
        question.add( "Available options:");
        question.add("0. BlackPowerRanger");
        question.add("1. PinkPowerRanger");
        question.add("2. YellowPowerRanger");
        question.add("I choose: ");
        answer.add("Blackkie");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("BlackPowerRanger");
        question.add("You have chosen: BlackPowerRanger");
        question.add("Please select a role to assign.");
        question.add( "Available options:");
        question.add("0. " + Role.LEAD.name());
        question.add("1. " + Role.PROGRAMMER.name());
        answer.add("Testiebestie");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("TESTER");
        question.add("You have chosen:" + Role.TESTER.name());
        question.add("PinkPowerRanger assigned to project: PowerRangerProject2 version: " + new VersionID().toString() + ", with role: " + Role.TESTER.name());

        // init scanner
        scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosen = cmd.exec(scan, model, dev1);
        // Test effects
        assertTrue(chosen.getAllRolesDev(dev1).contains(Role.TESTER));
    }

    @Test
    public void testNormalStringWithSpaceExec() throws PermissionException, CancelException {
        // Setup scenario
        question.add("Please select a project you lead.");
        question.add( "Available options:");
        question.add("0. PowerRangerProject version: " + new VersionID().toString());
        question.add("1. PowerRangerProject2 version: " + new VersionID().toString());
        question.add("I choose: ");
        answer.add("lala");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("PowerRangerProject2 0.0.1");
        question.add("You have chosen:");
        question.add(projectB.getDetails());
        question.add("Please select a developer to assign.");
        question.add( "Available options:");
        question.add("0. BlackPowerRanger");
        question.add("1. PinkPowerRanger");
        question.add("2. YellowPowerRanger");
        question.add("I choose: ");
        answer.add("Blackkie");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("YellowPowerRanger");
        question.add("You have chosen: YellowPowerRanger");
        question.add("Please select a role to assign.");
        question.add( "Available options:");
        question.add("0. " + Role.LEAD.name());
        question.add("1. " + Role.PROGRAMMER.name());
        answer.add("Testiebestie");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("PROGRAMMER");
        question.add("You have chosen:" + Role.PROGRAMMER.name());
        question.add("PinkPowerRanger assigned to project: PowerRangerProject2 version: " + new VersionID().toString() + ", with role: " + Role.PROGRAMMER.name());

        // init scanner
        scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        Project chosen = cmd.exec(scan, model, dev1);
        // Test effects
        assertTrue(chosen.getAllRolesDev(dev3).contains(Role.PROGRAMMER));
    }

    @Test
    public void testNoLeadExec() throws PermissionException, CancelException {
        // Setup scenario
        question.add("You don't lead any projects.");
        // Execute scenario
        scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Project chosen = cmd.exec(scan, model, dev3);
        // Test effects
        assertEquals(null, chosen);
    }



}
