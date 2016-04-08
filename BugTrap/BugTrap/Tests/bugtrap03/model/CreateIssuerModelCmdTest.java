package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Issuer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Group 03
 */
public class CreateIssuerModelCmdTest {
    
    private static int counter = Integer.MIN_VALUE;

    private DataModel model;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
    }

    /**
     * Test
     * {@link CreateIssuerModelCmd#CreateIssuerModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Add
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Issuer"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Issuer newIssuer = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newIssuer));
        assertTrue(cmd.toString().contains("Created Issuer"));
        assertTrue(cmd.toString().contains(newIssuer.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere14" + counter, newIssuer.getUsername());
        assertEquals("firstName", newIssuer.getFirstName());
        assertEquals("lastName", newIssuer.getLastName());
        assertEquals("", newIssuer.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newIssuer));

        counter++;
    }

    /**
     * Test
     * {@link CreateIssuerModelCmd#CreateIssuerModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        // 1. Add
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "middleName", "lastName");

        // test
        assertTrue(cmd.toString().contains("Created Issuer"));
        assertTrue(cmd.toString().contains("-invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Issuer newIssuer = cmd.exec();

        // test
        assertTrue(model.getUserList().contains(newIssuer));
        assertTrue(cmd.toString().contains("Created Issuer"));
        assertTrue(cmd.toString().contains(newIssuer.getFullName()));
        assertTrue(cmd.isExecuted());
        assertEquals("uniqueUsernameOvereHere14" + counter, newIssuer.getUsername());
        assertEquals("firstName", newIssuer.getFirstName());
        assertEquals("lastName", newIssuer.getLastName());
        assertEquals("middleName", newIssuer.getMiddleName());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getUserList().contains(newIssuer));
        counter++;
    }

    /**
     * Test exec() with a non unique username.
     *
     */
    @Test
    public void testExec_IllegalInput() throws PermissionException {
        boolean worked = false;

        try {
            CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");
            cmd.exec();
            cmd = new CreateIssuerModelCmd(model, "uniqueUsernameOvereHere14" + counter, "firstName", "lastName");
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
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(model, "uniqueUsernameOvereHere14", "firstName", "lastName");

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateIssuerModelCmd#CreateIssuerModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_ModelNull() {
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(null, "uniqueUsernameOvereHere14", "firstName", "lastName");
    }

    /**
     * Test
     * {@link CreateIssuerModelCmd#CreateIssuerModelCmd(bugtrap03.model.DataModel, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_ModelNull() {
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(null, "uniqueUsernameOvereHere14", "firstName", "middleName", "lastName");
    }

}
