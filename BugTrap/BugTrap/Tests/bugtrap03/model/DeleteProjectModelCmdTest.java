package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Group 03
 */
public class DeleteProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob0" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere0" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);

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
        // 1. Delete
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, admin, proj);

        // test
        assertTrue(cmd.toString().contains("Deleted"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertFalse(model.getProjectList().contains(proj));
        assertTrue(cmd.toString().contains("Deleted"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertTrue(model.getProjectList().contains(proj));
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, admin, proj);

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ModelNull() {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(null, admin, proj);
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_UserNull() {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, null, proj);
    }

    /**
     * Test
     * {@link DeleteProjectModelCmd#DeleteProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.Project)}
     * with proj == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ProjNull() {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, admin, null);
    }

    /**
     * Test exec() with a dev who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, dev, proj);
        cmd.exec();
    }

    /**
     * Test constructor with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ProjectTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, admin, proj);
    }

    /**
     * Test exec() with terminated project
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ProjectTerminated() throws PermissionException {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(model, admin, proj);
        model.deleteProject(admin, proj);
        cmd.exec();
    }

}
