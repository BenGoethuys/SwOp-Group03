package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class AddUsersToBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private BugReport bugRep;
    private Developer dev;
    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob1" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere1" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, 1, false);

        dev2 = model.createDeveloper("Developer2OverHere1" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere1" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        counter++;
    }

    /**
     * Test
     * {@link AddUsersToBugReportModelCmd#AddUsersToBugReportModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, purecollections.PList)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, bugRep, devList);
        int oldSize = bugRep.getUserList().size();

        // test
        assertTrue(cmd.toString().contains("Add"));
        assertTrue(cmd.toString().contains("" + devList.size()));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        assertTrue(cmd.exec());
        assertTrue(bugRep.getUserList().contains(dev2));
        assertTrue(bugRep.getUserList().contains(dev3));

        // test
        assertTrue(cmd.toString().contains("Add"));
        assertTrue(cmd.toString().contains("" + devList.size()));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(bugRep.getUserList().contains(dev2));
        assertFalse(bugRep.getUserList().contains(dev3));
        assertEquals(oldSize, bugRep.getUserList().size());
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, bugRep, devList);

        // 2. Exec()
        assertTrue(cmd.exec());

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link AddUsersToBugReportModelCmd#AddUsersToBugReportModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, purecollections.PList)}
     * with bugreport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugRepNull() {
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, null, devList);
    }

    /**
     * Test
     * {@link AddUsersToBugReportModelCmd#AddUsersToBugReportModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, purecollections.PList)}
     * with devList == null
     */
    @Test
    public void testToString_DevListNull() {
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, bugRep, null);

        assertTrue(cmd.toString().contains("0"));
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(admin, bugRep, devList);
        cmd.exec();
    }

    /**
     * Test constructor with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, bugRep, devList);
    }

    /**
     * Test exec() with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_BugReportTerminated() throws PermissionException {
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(dev, bugRep, devList);
        model.deleteProject(admin, proj);
        cmd.exec();
    }
}
