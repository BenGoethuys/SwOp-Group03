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
public class AddTestToBugReportModelCmdTest {

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
        admin = model.createAdministrator("BlubBlabBlob13" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere13" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, 1, false);
        bugRepWrongState = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, 1, false);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        dev2 = model.createDeveloper("Developer2OverHere13" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere13" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.addUsersToBugReport(dev, bugRep, devList);

        counter++;
    }

    /**
     * Test
     * {@link AddTestToBugReportModelCmd#AddTestToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Add
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRep, dev, "test here");

        // test
        assertTrue(cmd.toString().contains("Added a test for"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue(bugRep.getTests().contains("test here"));
        assertTrue(cmd.toString().contains("Added a test for"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(bugRep.getTests().contains("test here"));
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * when the bugreport is not in the right state.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testExec_IllegalState() throws PermissionException {

        // 1. Add
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRepWrongState, dev, "test here");

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
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRep, dev, "test here");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link AddTestToBugReportModelCmd#AddTestToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * with bugReport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportNull() {
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(null, dev, "test here");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRep, admin, "test here");
        cmd.exec();
    }

    /**
     * Test constructor with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRep, dev, "test here");
    }

    /**
     * Test exec() with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_BugReportTerminated() throws PermissionException {
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugRep, dev, "test here");
        model.deleteProject(admin, proj);
        cmd.exec();
    }
}
