package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class CreateAdminModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
    }

    /**
     * Test
     * {@link CreateAdminModelCmd#CreateAdminModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Add
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Administrator"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Administrator newAdmin = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newAdmin));
        assertTrue(cmd.toString().contains("Created Administrator"));
        assertTrue(cmd.toString().contains(newAdmin.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere14" + counter, newAdmin.getUsername());
        assertEquals("firstName", newAdmin.getFirstName());
        assertEquals("lastName", newAdmin.getLastName());
        assertEquals("", newAdmin.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newAdmin));

        counter++;
    }

    /**
     * Test
     * {@link CreateAdminModelCmd#CreateAdminModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        // 1. Add
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "middleName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Administrator"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Administrator newAdmin = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newAdmin));
        assertTrue(cmd.toString().contains("Created Administrator"));
        assertTrue(cmd.toString().contains(newAdmin.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere14" + counter, newAdmin.getUsername());
        assertEquals("firstName", newAdmin.getFirstName());
        assertEquals("lastName", newAdmin.getLastName());
        assertEquals("middleName", newAdmin.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newAdmin));
        counter++;
    }

    /**
     * Test exec() with a non unique username.
     */
    @Test
    public void testExec_IllegalInput() throws PermissionException {
        boolean worked = false;

        try {
            CreateAdminModelCmd cmd = new CreateAdminModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");
            cmd.exec();
            cmd = new CreateAdminModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");
            cmd.exec();
            worked = false;
        } catch (IllegalArgumentException ex) {
            worked = true;
        }

        counter++;
        assertTrue(worked);
        // 2. Exec()
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(model, "uniqueUsernameOvereHere14", "firstName", "lastName");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateAdminModelCmd#CreateAdminModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_ModelNull() {
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(null, "uniqueUsernameOvereHere14", "firstName", "lastName");
    }

    /**
     * Test
     * {@link CreateAdminModelCmd#CreateAdminModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_ModelNull() {
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(null, "uniqueUsernameOvereHere14", "firstName", "middleName", "lastName");
    }

}
