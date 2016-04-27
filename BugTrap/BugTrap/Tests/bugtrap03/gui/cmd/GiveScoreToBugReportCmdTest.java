/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import bugtrap03.bugdomain.VersionID;
import org.junit.Before;
import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * @author Group 03
 *
 */
public class GiveScoreToBugReportCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private BugReport bugRep;
    private BugReport bugRepWrongState;
    private Developer dev;
    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("Ploperdeplop001" + counter, "first", "last");
        dev = model.createDeveloper("Ploperdeplop002" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "Ploperdeplop003", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport> empty(), null, 1, false);
        bugRepWrongState = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport> empty(), null, 1, false);

        dev2 = model.createDeveloper("Ploperdeplop004" + counter, "first", "last");
        dev3 = model.createDeveloper("Ploperdeplop005" + counter, "first", "last");

        devList = PList.<Developer> empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);
        model.addUsersToBugReport(dev, bugRep, devList);
        model.addTest(bugRep, dev, "This is a test");
        model.addPatch(bugRep, dev, "This is a patch");
        model.selectPatch(bugRep, dev, "This is a patch");

        counter++;
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.GiveScoreToBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec1() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GiveScoreToBugReportCmd cmd = new GiveScoreToBugReportCmd();

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
        answer.add("");
        question.add("Please select a bug report: ");
        question.add("Available options:");
        question.add("0. " + bugRepWrongState.getTitle() + "\t -UniqueID: " + bugRepWrongState.getUniqueID());
        question.add("1. " + bugRep.getTitle() + "\t -UniqueID: " + bugRep.getUniqueID());
        question.add("I choose: ");
        answer.add("1");
        question.add("You have selected: " + bugRep.getTitle() + "\t -UniqueID: " + bugRep.getUniqueID());
        question.add("Give a score between 1 and 5: ");
        question.add("Give number: ");
        answer.add("2");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Integer score = cmd.exec(scan, model, dev);

        assertTrue(score == 2);
    }
    
    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.GiveScoreToBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws CancelException
     * @throws PermissionException
     * @throws IllegalArgumentException
     */
    @Test
    public void testExec2() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GiveScoreToBugReportCmd cmd = new GiveScoreToBugReportCmd(bugRep);

        question.add("Give a score between 1 and 5: ");
        question.add("Give number: ");
        answer.add("2");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        Integer score = cmd.exec(scan, model, dev);

        assertTrue(score == 2);
    }

}
