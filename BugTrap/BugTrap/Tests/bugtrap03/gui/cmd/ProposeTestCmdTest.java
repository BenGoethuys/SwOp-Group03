package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 */
public class ProposeTestCmdTest {

    private DataModel model;
    private Developer lead;
    private Developer dev2;
    private Developer dev3;
    private Issuer issuer;
    private Administrator admin;
    private Project projectA;
    private BugReport bugRep;

    private static int counter = Integer.MIN_VALUE;

    /**
     * Get the questions in the default scenario with a given BugReport where the test added is equal to "test over
     * here\ntest line 2"
     *
     * @return
     */
    public static ArrayDeque<String> getDefaultQuestions() {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Adding test.");
        question.add("Do you wish to submit a file? Please type yes or no.");
        answer.add("no");
        question.add("You have chosen to insert text. (Leave blank to finish the text)");
        question.add("text: ");
        answer.add("test over here");
        answer.add("test line 2");
        answer.add("");
        question.add("Added test.");

        return question;
    }

    /**
     * Get the answers in the default scenario with a given BugReport where the test added is equal to "test over
     * here\ntest line 2"
     *
     * @return
     */
    public static final ArrayDeque<String> getDefaultAnswers() {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Adding test.");
        question.add("Do you wish to submit a file? Please type yes or no.");
        answer.add("no");
        question.add("You have chosen to insert text. (Leave blank to finish the text)");
        question.add("text: ");
        answer.add("test over here");
        answer.add("test line 2");
        answer.add("");
        question.add("Added test.");

        return answer;
    }

    @Before
    public void setUp() throws PermissionException {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("trolbol08-6" + counter, "Luky", "Luke");
        dev2 = model.createDeveloper("Duck8" + counter, "Truck", "Luck");
        dev3 = model.createDeveloper("Truck8" + counter, "Duck", "Luck");
        issuer = model.createIssuer("C0ws08-6" + counter, "Fly", "High");
        admin = model.createAdministrator("Adm1ral08-6" + counter, "Kwinten", "JK");

        projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, dev2, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, dev3, Role.TESTER);

        // make subsystems
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        bugRep = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(), null, 1, false);

        model.addUsersToBugReport(lead, bugRep, PList.<Developer>empty().plus(dev3));
        counter++;
    }

    /**
     * Test the exec() while there is a bugReport assigned already.
     *
     * @throws PermissionException Never
     * @throws CancelException Never
     */
    @Test
    public void testExec_BugReportGiven() throws PermissionException, CancelException {
        ArrayDeque<String> question = ProposeTestCmdTest.getDefaultQuestions();
        ArrayDeque<String> answer = ProposeTestCmdTest.getDefaultAnswers();
        ProposeTestCmd cmd = new ProposeTestCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, dev3);

        assertTrue(bugRep.getTests().contains("test over here\ntest line 2"));
    }
    
    @Test(expected = PermissionException.class)
    public void testExec_NoPermission() throws PermissionException, CancelException {
        ArrayDeque<String> question = ProposeTestCmdTest.getDefaultQuestions();
        ArrayDeque<String> answer = ProposeTestCmdTest.getDefaultAnswers();
        ProposeTestCmd cmd = new ProposeTestCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, dev2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws PermissionException, CancelException {
        ProposeTestCmd cmd = new ProposeTestCmd(bugRep);
        cmd.exec(null, model, dev3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_ModelNull() throws PermissionException, CancelException {
        ArrayDeque<String> question = ProposeTestCmdTest.getDefaultQuestions();
        ArrayDeque<String> answer = ProposeTestCmdTest.getDefaultAnswers();
        ProposeTestCmd cmd = new ProposeTestCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, null, dev3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_UserNull() throws PermissionException, CancelException {
        ArrayDeque<String> question = ProposeTestCmdTest.getDefaultQuestions();
        ArrayDeque<String> answer = ProposeTestCmdTest.getDefaultAnswers();
        ProposeTestCmd cmd = new ProposeTestCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, null);
    }

    /**
     * Test exec() while there is no bug report assigned, this includes the select bug report scenario.
     *
     * @throws PermissionException
     * @throws CancelException
     */
    @Test
    public void testExec_NoBugReport() throws PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        ProposeTestCmd cmd = new ProposeTestCmd();

        question.add("Adding test.");

        question.add("Select a bug report.");
        addSearchModeOptions(question);
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRep.getTitle() + "\t -UniqueID: " + bugRep.getUniqueID());
        question.add("I choose: ");
        answer.add("0");
        question.add("You have selected: " + bugRep.getTitle() + "\t -UniqueID: " + bugRep.getUniqueID());
        question.add("Adding test.");

        question.add("Do you wish to submit a file? Please type yes or no.");
        answer.add("no");
        question.add("You have chosen to insert text. (Leave blank to finish the text)");
        question.add("text: ");
        answer.add("test over here");
        answer.add("test line 2");
        answer.add("");
        question.add("Added test.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, dev3);

        assertTrue(bugRep.getTests().contains("test over here\ntest line 2"));
    }

    /**
     * Add the searchMode options + first line to question. Please select a search mode.. <b> 0.. <b> 1.. <b> ..
     *
     * @param question
     */
    private void addSearchModeOptions(ArrayDeque<String> question) {
        question.add("Please select a search mode: ");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
    }

}
