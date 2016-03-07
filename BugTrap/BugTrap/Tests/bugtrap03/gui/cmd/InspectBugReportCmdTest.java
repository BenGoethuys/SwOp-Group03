/**
 * 
 */
package bugtrap03.gui.cmd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.model.DataModel;

/**
 * @author Mathias
 *
 */
public class InspectBugReportCmdTest {


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        model = new DataModel();
        lead = model.createDeveloper("theLeader", "the", "Leader");
        admin = model.createAdministrator("theAdmin", "Ad", "Min");
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
     * Test method for {@link bugtrap03.gui.cmd.InspectBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testExec() {
        fail("Not yet implemented");
    }

}
