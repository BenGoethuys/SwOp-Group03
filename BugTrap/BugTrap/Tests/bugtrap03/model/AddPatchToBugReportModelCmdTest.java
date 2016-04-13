package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class AddPatchToBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;
    private Subsystem subsys;
    private BugReport bugRep;
    private BugReport bugRepWrongState;

    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob17" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere17" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);
        bugRepWrongState = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        dev2 = model.createDeveloper("Developer2OverHere17" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere17" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.addUsersToBugReport(dev, bugRep, devList);
        model.addTest(bugRep, dev, "test here");

        counter++;
    }

    /**
     * Test
     * {@link AddPatchToBugReportModelCmd#AddPatchToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons() throws PermissionException {
        // 1. Add
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRep, dev, "patch here");

        // test
        assertTrue(cmd.toString().contains("Added a patch to"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue(bugRep.getPatches().contains("patch here"));
        assertTrue(cmd.toString().contains("Added a patch to"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(bugRep.getPatches().contains("patch here"));
    }

    /**
     * Test
     * {@link AddPatchToBugReportModelCmd#AddPatchToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * when the bugReport is not in the right state
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testExec_IllegalState() throws PermissionException {

        // 1. Add
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRepWrongState, dev, "patch here");

        // 2. Exec()
        cmd.exec();
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRep, dev, "patch here");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link AddPatchToBugReportModelCmd#AddPatchToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * with bugReport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportNull() {
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(null, dev, "patch here");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRep, admin, "patch here");
        cmd.exec();
    }

    /**
     * Test constructor with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRep, dev, "patch here");
    }

    /**
     * Test exec() with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_BugReportTerminated() throws PermissionException {
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugRep, dev, "patch here");
        model.deleteProject(admin, proj);
        cmd.exec();
    }
}
