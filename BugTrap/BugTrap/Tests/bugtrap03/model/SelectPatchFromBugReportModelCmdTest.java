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
public class SelectPatchFromBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;
    private Subsystem subsys;
    private BugReport bugRep;

    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob12" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere12" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        dev2 = model.createDeveloper("Developer2OverHere12" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere12" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.addUsersToBugReport(dev, bugRep, devList);
        model.addTest(bugRep, dev, "This is a test");
        model.addPatch(bugRep, dev, "another patch");

        counter++;
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        model.addPatch(bugRep, dev, "patch text here");

        // 1. Delete
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(bugRep, dev, "patch text here");

        // test
        assertTrue(cmd.toString().contains("Selected a patch for"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue("patch text here".equals(bugRep.getSelectedPatch()));
        assertTrue(cmd.toString().contains("Selected a patch for"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        // getSelectedPatch() shouldn't work.
        boolean shouldNotWork = false;
        try {
            bugRep.getSelectedPatch();
            shouldNotWork = false;
        } catch (IllegalStateException e) {
            shouldNotWork = true;
        }

        assertTrue(shouldNotWork);
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * when the patch is not added beforehand.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_IllegalPatch() throws PermissionException {

        // 1. Delete
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(bugRep, dev, "patch text here");

        // 2. Exec()
        cmd.exec();

        // 3. undo()
        assertTrue(cmd.undo());
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(bugRep, dev, "another patch");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link SelectPatchFromBugReportModelCmd#SelectPatchFromBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String)}
     * with bugReport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportNull() {
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(null, dev, "patch");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(bugRep, admin, "patch");
        cmd.exec();
    }
}
