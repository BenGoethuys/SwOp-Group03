package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class GiveScoreToBugReportModelCmdTest {

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
        admin = model.createAdministrator("BlubBlabBlob3" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere3" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);

        dev2 = model.createDeveloper("Developer2OverHere3" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere3" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        counter++;
    }

    /**
     * Test
     * {@link GiveScoreToBugReportModelCmd#GiveScoreToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, int)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);
        model.addUsersToBugReport(dev, bugRep, devList);
        model.addTest(bugRep, dev, "This is a test");
        model.addPatch(bugRep, dev, "This is a patch");
        model.selectPatch(bugRep, dev, "This is a patch");
        GiveScoreToBugReportModelCmd cmd = new GiveScoreToBugReportModelCmd(bugRep, dev, 2);

        // test
        assertTrue(cmd.toString().contains("score"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        assertTrue(cmd.exec());
        assertEquals(2, bugRep.getScore());

        // test
        assertTrue(cmd.toString().contains("score"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        // getScore() shouldn't work.
        boolean shouldNotWork = false;
        try {
            bugRep.getScore();
            shouldNotWork = false;
        } catch(IllegalStateException e) {
            shouldNotWork = true;
        }
        
        assertTrue(shouldNotWork);
    }

    /**
     * Test {@link GiveScoreToBugReportModelCmd#exec()} with a BugReport that is not in a valid state for this.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testExec_IllegalState() throws PermissionException {
        // 1. Create
        GiveScoreToBugReportModelCmd cmd = new GiveScoreToBugReportModelCmd(bugRep, dev, 2);

        // 2. Exec()
        assertTrue(cmd.exec());
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
     * {@link GiveScoreToBugReportModelCmd#GiveScoreToBugReportModelCmd(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, int)}
     * with bugreport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugRepNull() {
        GiveScoreToBugReportModelCmd cmd = new GiveScoreToBugReportModelCmd(null, dev, 2);
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        GiveScoreToBugReportModelCmd cmd = new GiveScoreToBugReportModelCmd(bugRep, admin, 2);
        cmd.exec();
    }
}
