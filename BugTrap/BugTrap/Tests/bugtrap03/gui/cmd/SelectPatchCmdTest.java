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
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertTrue;

/**
 * @author Group 03
 */
public class SelectPatchCmdTest {

    private DataModel model;
    private Developer lead;
    private Developer dev2;
    private Developer dev3;
    private Issuer issuer;
    private Administrator admin;
    private Project projectA;
    private BugReport bugRep;
    private String patch1;
    private String patch2;
    private Subsystem subsystemA2;

    private static int counter = Integer.MIN_VALUE;

    /**
     * Get the questions in the default scenario with a given BugReport where the patch selected is equal to the patch
     * that is added to the bugReport the first. (index = 0)
     *
     * @param patches The patches in the system, to chose from, should be arranged with the first element being the one
     *                that was added to the system the first.
     * @return
     */
    public static ArrayDeque<String> getDefaultQuestions(String... patches) {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Selecting patch.");
        question.add("Use the index in front of the patch to select a patch.");
        question.add("Available options:");
        for (int i = 0; i < patches.length; i++) {
            question.add(i + ". " + patches[i]);
        }
        question.add("I choose: ");
        answer.add("0");
        question.add("The selected patch is set to: " + patches[0]);

        return question;
    }

    /**
     * Get the answers in the default scenario with a given BugReport where the patch selected is equal to the patch
     * that is added to the bugReport the first. (index = 0)
     *
     * @param patches The patches in the system, to chose from, should be arranged with the first element being the one
     *                that was added to the system the first.
     * @return
     */
    public static final ArrayDeque<String> getDefaultAnswers(String... patches) {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        question.add("Selecting patch.");
        question.add("Use the index in front of the patch to select a patch.");
        question.add("Available options:");
        for (int i = 0; i < patches.length; i++) {
            question.add(i + ". " + patches[i]);
        }
        question.add("I choose: ");
        answer.add("0");
        question.add("The selected patch is set to: " + patches[0]);

        return answer;
    }

    @Before
    public void setUp() throws PermissionException {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("trolbol12-6" + counter, "Luky", "Luke");
        dev2 = model.createDeveloper("Duck12" + counter, "Truck", "Luck");
        dev3 = model.createDeveloper("Truck12" + counter, "Duck", "Luck");
        issuer = model.createIssuer("C0ws012-6" + counter, "Fly", "High");
        admin = model.createAdministrator("Adm1ral012-6" + counter, "Kwinten", "JK");

        projectA = model.createProject(new VersionID(), "ProjectTest0", "Project for testing 0", lead, 500, admin);
        model.assignToProject(projectA, lead, dev2, Role.PROGRAMMER);
        model.assignToProject(projectA, lead, dev3, Role.TESTER);

        // make subsystems
        subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        bugRep = model.createBugReport(subsystemA2, issuer, "bugRep is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(), null, 1, false);

        model.addUsersToBugReport(lead, bugRep, PList.<Developer>empty().plus(dev2));
        model.addUsersToBugReport(lead, bugRep, PList.<Developer>empty().plus(dev3));

        model.addTest(bugRep, dev3, "test over here");
        patch1 = "patch over here\npatch line 2";
        patch2 = "patchBlub over here\npatchBlub line 2";
        model.addPatch(bugRep, dev2, patch1);
        model.addPatch(bugRep, dev2, patch2);

        counter++;
    }

    /**
     * Test the exec() while there is a bugReport assigned already.
     *
     * @throws PermissionException Never
     * @throws CancelException     Never
     */
    @Test
    public void testExec_BugReportGiven() throws PermissionException, CancelException {
        ArrayDeque<String> question = SelectPatchCmdTest.getDefaultQuestions(patch1, patch2);
        ArrayDeque<String> answer = SelectPatchCmdTest.getDefaultAnswers(patch1, patch2);
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, lead);

        assertTrue(bugRep.getSelectedPatch().equals("patch over here\npatch line 2"));
    }

    /**
     * Test the exec() while there are no patches.
     *
     * @throws PermissionException Never
     * @throws CancelException     Never
     */
    @Test(expected = IllegalStateException.class)
    public void testExec_NoPatches() throws PermissionException, CancelException {
        BugReport bugRep2 = model.createBugReport(subsystemA2, issuer, "bugRep2 is too awesome",
                "CreateComment is complicated but easy to use. Is this even legal?", PList.<BugReport>empty(), null, 1, false);
        model.addUsersToBugReport(lead, bugRep2, PList.<Developer>empty().plus(dev3));
        model.addTest(bugRep2, dev3, "test over here");

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep2);
        question.add("Selecting patch.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, lead);
    }

    @Test(expected = PermissionException.class)
    public void testExec_NoPermission() throws PermissionException, CancelException {
        ArrayDeque<String> question = SelectPatchCmdTest.getDefaultQuestions(patch1, patch2);
        ArrayDeque<String> answer = SelectPatchCmdTest.getDefaultAnswers(patch1, patch2);
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, dev2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_ScanNull() throws PermissionException, CancelException {
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep);
        cmd.exec(null, model, lead);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_ModelNull() throws PermissionException, CancelException {
        ArrayDeque<String> question = SelectPatchCmdTest.getDefaultQuestions(patch1, patch2);
        ArrayDeque<String> answer = SelectPatchCmdTest.getDefaultAnswers(patch1, patch2);
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, null, lead);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_UserNull() throws PermissionException, CancelException {
        ArrayDeque<String> question = SelectPatchCmdTest.getDefaultQuestions(patch1, patch2);
        ArrayDeque<String> answer = SelectPatchCmdTest.getDefaultAnswers(patch1, patch2);
        SelectPatchCmd cmd = new SelectPatchCmd(bugRep);

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
        SelectPatchCmd cmd = new SelectPatchCmd();

        question.add("Selecting patch.");
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
        question.add("Selecting patch.");

        question.add("Use the index in front of the patch to select a patch.");
        question.add("Available options:");
        question.add("0. " + patch1);
        question.add("1. " + patch2);
        question.add("I choose: ");
        answer.add("0");
        question.add("The selected patch is set to: " + patch1);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        cmd.exec(scan, model, lead);

        assertTrue(bugRep.getSelectedPatch().equals("patch over here\npatch line 2"));
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
