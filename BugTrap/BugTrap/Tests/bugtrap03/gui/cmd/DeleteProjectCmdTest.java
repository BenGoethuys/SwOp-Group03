package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import java.util.ArrayDeque;
import static org.junit.Assert.*;

import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Group 03
 *
 */
public class DeleteProjectCmdTest {

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.CreateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testExec() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead08", "Luky", "Luke");
        Issuer issuer = model.createIssuer("noDev08", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin08", "adminT", "bie");
        Project proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        DeleteProjectCmd cmd = new DeleteProjectCmd();

        //Setup scenario
        question.add("Available projects:");
        question.add("0. " + proj0.getName());
        question.add("1. " + proj1.getName());
        answer.add("0");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        Project chosen = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(chosen, proj0);
        assertEquals(model.getProjectList().size(), 1);
        assertTrue(model.getProjectList().contains(proj1));
    }

}
