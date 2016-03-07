/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO
 * 
 * @author Group 03
 *
 */
public class UpdateProjectCmdTest {
    private static DataModel model;
    private static Issuer issuer;
    private static Project proj0;
    private static Project proj1;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        model = new DataModel();
        Developer lead = model.createDeveloper("theLeader", "the", "Leader");
        issuer = model.createIssuer("theIssuer", "Iss", "Uer");
        Administrator admin = model.createAdministrator("theAdmin", "Ad", "Min");
        proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.UpdateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     * 
     * @throws PermissionException
     * @throws IllegalArgumentException
     * @throws CancelException
     */
    @Test
    public void testExecUpdateProject() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        UpdateProjectCmd cmd = new UpdateProjectCmd();

        question.add("Available projects:");
        question.add("0. " + proj0.getName());
        question.add("1. " + proj1.getName());
        question.add("I choose: ");
        answer.add(proj0.getName());
        question.add("You have chosen:");
        question.add(proj0.getDetails());
        answer.add("");
        answer.add("");
        answer.add("");
        answer.add("");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        Project chosen = cmd.exec(scan, model, admin);

        assertEquals(chosen, proj0);
        assertEquals(chosen.getDetails(), proj0.getDetails());
    }

}
