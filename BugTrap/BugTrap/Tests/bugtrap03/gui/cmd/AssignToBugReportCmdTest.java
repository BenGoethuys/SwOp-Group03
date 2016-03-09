/**
 *
 */
package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Comment;
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
import java.util.ArrayDeque;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 *
 */
public class AssignToBugReportCmdTest {

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
     * Test method for
     * {@link bugtrap03.gui.cmd.AssignToBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testExec() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trolbol00-6", "Luky", "Luke");
        Developer dev2 = model.createDeveloper("Duck", "Truck", "Luck");
        Developer dev3 = model.createDeveloper("Truck", "Duck", "Luck");
        Developer dev4 = model.createDeveloper("Luck", "Duck", "Truck");
        Issuer issuer = model.createIssuer("C0ws00-6", "Fly", "High");
        Administrator admin = model.createAdministrator("Adm1ral00-6", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, dev2, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, dev3, Role.TESTER);
        model.assignToProject(projectA, lead, dev4, Role.PROGRAMMER);

        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        BugReport bugRep1 = model.createBugReport(issuer, "bugRep is too awesome", "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(), subsystemA2);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        AssignToBugReportCmd cmd = new AssignToBugReportCmd();

        //Setup scenario
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
        answer.add("0");
        question.add("You have selected: " + bugRep1.getTitle() + "\t -UniqueID: " + bugRep1.getUniqueID());
        question.add("Available developers: ");
        question.add("0. " + dev2.getUsername());
        question.add("1. " + lead.getUsername());
        question.add("2. " + dev3.getUsername());
        question.add("3. " + dev4.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: (leave blank when done)");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: (leave blank when done)");
        answer.add("0");
        question.add("Added " + dev2.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("1");
        question.add("Added " + lead.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("0");
        question.add("Added " + dev2.getUsername());
        question.add("I choose: (leave blank when done)");
        answer.add("");
        question.add(" ");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        BugReport chosen = cmd.exec(scan, model, lead);
        //Test effects.

    }

}
