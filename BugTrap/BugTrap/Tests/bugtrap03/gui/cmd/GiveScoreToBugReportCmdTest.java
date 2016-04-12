/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import org.junit.Before;
import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.model.DataModel;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * @author Group 03
 *
 */
public class GiveScoreToBugReportCmdTest {
    
    //TODO: Implement tests.

    private static DataModel model;
    private static Administrator admin;
    private static Developer lead;
    private static Project proj0;
    private static Project proj1;
    
    private static int counter = Integer.MIN_VALUE;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        model = new DataModel();
        lead = model.createDeveloper("the10LeaderVinnieVidiVici" + counter, "theVinnie", "LeaderVinnie");
        admin = model.createAdministrator("the10AdminVinnie" + counter, "AdVinnie", "MinVinnie");
        proj0 = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);
        counter++;
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.GiveScoreToBugReportCmd#GiveScoreToBugReportCmd()}
     * .
     * @throws CancelException 
     * @throws PermissionException 
     * @throws IllegalArgumentException 
     */
    @Test
    public void testGiveScoreToBugReportCmd() throws IllegalArgumentException, PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        GiveScoreToBugReportCmd cmd = new GiveScoreToBugReportCmd();

        
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        int number = cmd.exec(scan, model, admin);
        assertEquals(number, 0);
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.GiveScoreToBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExec() {
    }

}
