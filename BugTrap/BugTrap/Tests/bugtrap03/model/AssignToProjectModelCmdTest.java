package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
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
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob2" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere2" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);

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
        assertEquals(2, oldSize);
        assertTrue(proj.getAllRolesDev(dev).contains(Role.TESTER));
        assertTrue(cmd.toString().contains("Assigned"));
        assertTrue(cmd.toString().contains(dev.getFullName()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertEquals(oldSize - 1, proj.getAllRolesDev(dev).size());
        assertFalse(proj.getAllRolesDev(dev).contains(Role.TESTER));
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

    /**
     * Test toString() with null arguments.
     */
    @Test
    public void testToStringNullArguments() {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, null, null, null);
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.toString().contains("null"));
    }

    /**
     * Test undo when no changes have occurred, as in the developer already had the role assigned.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testUndoNoChanges() throws PermissionException {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);
        cmd.exec();
        assertTrue(proj.getAllRolesDev(dev).contains(Role.TESTER));
        int oldSize = proj.getAllRolesDev(dev).size();

        //assign once more
        cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);
        cmd.exec();
        assertTrue(proj.getAllRolesDev(dev).contains(Role.TESTER));
        assertEquals(oldSize, proj.getAllRolesDev(dev).size());

        //undo
        cmd.undo();

        //should still be there because is assigned twice so new cmd didn't do anything.
        assertTrue(proj.getAllRolesDev(dev).contains(Role.TESTER));
        assertEquals(oldSize, proj.getAllRolesDev(dev).size());
    }

    /**
     * Test constructor with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ProjectTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);
    }

    /**
     * Test exec() with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ProjectTerminated() throws PermissionException {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(proj, dev, dev, Role.TESTER);
        model.deleteProject(admin, proj);
        cmd.exec();
    }
}
