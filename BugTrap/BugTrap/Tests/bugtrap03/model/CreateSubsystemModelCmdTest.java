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
public class CreateSubsystemModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private BugReport bugRep;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob11" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere11" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, 1, false);

        counter++;
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, "name here", "desc here");

        // test
        assertTrue(cmd.toString().contains("Created subsystem"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Subsystem sub = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Created subsystem"));
        assertTrue(cmd.toString().contains("name here"));
        assertTrue(cmd.isExecuted());
        assertEquals(proj, sub.getParentProject());
        assertTrue(proj.getAllSubsystems().contains(sub));

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(proj.getAllSubsystems().contains(sub));
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, bugtrap03.bugdomain.VersionID, java.lang.String, java.lang.String)}
     * in a default scenario
     *
     * @throws PermissionException
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        // 1. Create
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, new VersionID(2, 0, 1), "name here", "desc here");

        // test
        assertTrue(cmd.toString().contains("Created subsystem"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Subsystem sub = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Created subsystem"));
        assertTrue(cmd.toString().contains("name here"));
        assertTrue(cmd.isExecuted());
        assertEquals(proj, sub.getParentProject());
        assertTrue(proj.getAllSubsystems().contains(sub));

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(proj.getAllSubsystems().contains(sub));
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, "name here", "desc here");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, java.lang.String, java.lang.String)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_UserNull() {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(null, proj, "name here", "desc here");
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, java.lang.String, java.lang.String)}
     * with abstractSystem == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_AbsNull() {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, null, "name here", "desc here");
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, bugtrap03.bugdomain.VersionID, java.lang.String, java.lang.String)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_UserNull() {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(null, proj, new VersionID(2, 0, 1), "name here", "desc here");
    }

    /**
     * Test
     * {@link CreateSubsystemModelCmd#CreateSubsystemModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.AbstractSystem, bugtrap03.bugdomain.VersionID, java.lang.String, java.lang.String)}
     * with abstractSystem == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_AbsNull() {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, null, new VersionID(2, 0, 1), "name here", "desc here");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(dev, proj, "name here", "desc here");
        cmd.exec();
    }

    /**
     * Test constructor with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_ProjectTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, "name here", "desc here");
    }

    /**
     * Test exec() with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec1_ProjectTerminated() throws PermissionException {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, "name here", "desc here");
        model.deleteProject(admin, proj);
        cmd.exec();
    }

    /**
     * Test constructor with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_ProjectTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, new VersionID(2, 0, 1), "name here", "desc here");
    }

    /**
     * Test exec() with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec2_ProjectTerminated() throws PermissionException {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(admin, proj, new VersionID(2, 0, 1), "name here", "desc here");
        model.deleteProject(admin, proj);
        cmd.exec();
    }

}
