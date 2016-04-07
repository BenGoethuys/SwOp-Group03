package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class AssignToProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob2" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere2" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");

        counter++;
    }

    /**
     * Test
     * {@link AssignToProjectModelCmd#AssignToProjectModelCmd(bugtrap03.bugdomain.Project, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.usersystem.Developer, bugtrap03.bugdomain.usersystem.Role)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);

        // test
        assertTrue(cmd.toString().contains("Assigned"));
        assertTrue(cmd.toString().contains(dev.getFullName()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        assertTrue(cmd.exec());
        int oldSize = proj.getAllRolesDev(dev).size();

        // test
        assertTrue(cmd.toString().contains("Assigned"));
        assertTrue(cmd.toString().contains(dev.getFullName()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertEquals(oldSize - 1, proj.getAllRolesDev(dev).size());
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);

        // 2. Exec()
        assertTrue(cmd.exec());

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link AssignToProjectModelCmd#AssignToProjectModelCmd(bugtrap03.bugdomain.Project, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.usersystem.Developer, bugtrap03.bugdomain.usersystem.Role)}
     * with subsystem == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ProjNull() {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(null, dev, dev, Role.TESTER);
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, admin, dev, Role.TESTER);
        cmd.exec();
    }
}