package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Group 03
 */
public class SetDuplicateBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Project proj2;
    private Developer dev;
    private Subsystem subsys;
    private BugReport bugRep;
    private BugReport duplicate;
    private BugReport duplicate2;

    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob19" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere19" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);
        duplicate = model.createBugReport(subsys, dev, "titleDuplicate", "descDuplicate", PList.<BugReport>empty(), null, false);
        
        proj2 = model.createProject("TestProject49", "fajfief", dev, 50, admin);
        Subsystem subsys2 = model.createSubsystem(admin, proj2, "azdazd", "ferfre");
        duplicate2 = model.createBugReport(subsys2, dev, "titleDuplicate", "descDuplicate", PList.<BugReport>empty(), null, false);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        dev2 = model.createDeveloper("Developer2OverHere19" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere19" + counter, "first", "last");

        model.assignToProject(proj, dev, dev2, Role.TESTER);
        model.assignToProject(proj, dev, dev2, Role.PROGRAMMER);

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.addUsersToBugReport(dev, bugRep, devList);
        model.addUsersToBugReport(dev, duplicate, PList.<Developer>empty().plus(dev2));

        model.addTest(bugRep, dev, "This is a test");
        model.addPatch(bugRep, dev, "another patch");

        counter++;
    }

    /**
     * Test {@link SetDuplicateBugReportModelCmd#SetDuplicateBugReportModelCmd(BugReport, BugReport, User)} in a default
     * scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, dev);

        // test
        assertTrue(cmd.toString().contains("duplicate"));
        assertTrue(cmd.toString().contains("set"));
        assertTrue(cmd.toString().contains(duplicate.getTitle()));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue(duplicate.equals(bugRep.getDuplicate()));
        assertTrue(cmd.toString().contains("duplicate"));
        assertTrue(cmd.toString().contains("set"));
        assertTrue(cmd.toString().contains(duplicate.getTitle()));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        // getSelectedPatch() shouldn't work.
        boolean shouldNotWork = false;
        try {
            bugRep.getDuplicate();
            shouldNotWork = false;
        } catch (IllegalStateException e) {
            shouldNotWork = true;
        }

        assertTrue(shouldNotWork);
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, dev);

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test {@link SetDuplicateBugReportModelCmd#SetDuplicateBugReportModelCmd(BugReport, BugReport, User)} with
     * bugReport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportNull() {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(null, duplicate, dev);
    }

    /**
     * Test {@link SetDuplicateBugReportModelCmd#SetDuplicateBugReportModelCmd(BugReport, BugReport, User)} with
     * duplicate == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_DuplicateNull() {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, null, dev);
    }

    /**
     * Test {@link SetDuplicateBugReportModelCmd#SetDuplicateBugReportModelCmd(BugReport, BugReport, User)} with user ==
     * null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_UserNull() {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, null);
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, admin);
        cmd.exec();
    }

    /**
     * Test exec() with a Developer who is not the lea and thus has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions2() throws PermissionException {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, dev2);
        cmd.exec();
    }

    /**
     * Test constructor with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, dev);
    }

    /**
     * Test exec() with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_BugReportTerminated() throws PermissionException {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate, dev);
        model.deleteProject(admin, proj);
        cmd.exec();
    }

    /**
     * Test constructor with terminated duplicate
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_DuplicateTerminated() throws PermissionException {
        model.deleteProject(admin, proj2);
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate2, dev);
    }

    /**
     * Test exec() with terminated duplicate
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_DuplicateTerminated() throws PermissionException {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugRep, duplicate2, dev);
        model.deleteProject(admin, proj2);
        cmd.exec();
    }
}
