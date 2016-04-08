package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Group 03
 */
public class CreateDeveloperModelCmdTest {
        
    private static int counter = Integer.MIN_VALUE;

    private DataModel model;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
    }

    /**
     * Test
     * {@link CreateDeveloperModelCmd#CreateDeveloperModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Add
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(model, "uniqueUsernameOvereHere16" + counter, "firstName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Developer"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Developer newDeveloper = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newDeveloper));
        assertTrue(cmd.toString().contains("Created Developer"));
        assertTrue(cmd.toString().contains(newDeveloper.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere16" + counter, newDeveloper.getUsername());
        assertEquals("firstName", newDeveloper.getFirstName());
        assertEquals("lastName", newDeveloper.getLastName());
        assertEquals("", newDeveloper.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newDeveloper));

        counter++;
    }

    /**
     * Test
     * {@link CreateDeveloperModelCmd#CreateDeveloperModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        // 1. Add
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(model, "uniqueUsernameOvereHere16" + counter, "firstName", "middleName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Developer"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Developer newDeveloper = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newDeveloper));
        assertTrue(cmd.toString().contains("Created Developer"));
        assertTrue(cmd.toString().contains(newDeveloper.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere16" + counter, newDeveloper.getUsername());
        assertEquals("firstName", newDeveloper.getFirstName());
        assertEquals("lastName", newDeveloper.getLastName());
        assertEquals("middleName", newDeveloper.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newDeveloper));
        counter++;
    }

    /**
     * Test exec() with a non unique username.
     *
     */
    @Test
    public void testExec_IllegalInput() throws PermissionException {
        boolean worked;

        try {
            CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(model, "uniqueUsernameOvereHere16" + counter, "firstName", "lastName");
            cmd.exec();
            cmd = new CreateDeveloperModelCmd(model, "uniqueUsernameOvereHere16" + counter, "firstName", "lastName");
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
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(model, "uniqueUsernameOvereHere16", "firstName", "lastName");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateDeveloperModelCmd#CreateDeveloperModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_ModelNull() {
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(null, "uniqueUsernameOvereHere16", "firstName", "lastName");
    }

    /**
     * Test
     * {@link CreateDeveloperModelCmd#CreateDeveloperModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_ModelNull() {
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(null, "uniqueUsernameOvereHere16", "firstName", "middleName", "lastName");
    }
}
