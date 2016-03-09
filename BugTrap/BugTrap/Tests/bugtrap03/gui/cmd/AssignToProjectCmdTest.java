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

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link bugtrap03.gui.cmd.AssignToProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testExec() throws PermissionException, CancelException {
        // Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("BlackPowerRanger","Black","Power Ranger");
        Developer dev2 = model.createDeveloper("PinkPowerRanger","Pink","Power Ranger");
        Developer dev3 = model.createDeveloper("YellowPowerRanger","Yellow","Power Ranger");
        Issuer issuer = model.createIssuer("RedPowerRanger","Red","Power Ranger");
        Administrator admin = model.createAdministrator("GreenPowerRanger","Green","Power Ranger");

        Project projectA = model.createProject("PowerRangerProject", "Project for testing with power rangers", lead, 999, admin);
        model.assignToProject(projectA, lead, dev2, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, dev3, Role.TESTER);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        AssignToProjectCmd cmd = new AssignToProjectCmd();

        // Setup scenario
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("bugRep");
        question.add("Please select a bug report: ");
        question.add("Available bugReports:");
        question.add("0. " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("I choose: ");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        // Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        // Test effects
    }

}
