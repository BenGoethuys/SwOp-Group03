/**
 *
 */
package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.permission.UserPerm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mathias
 */
public class AdministratorTest {

    Administrator admin;
    Administrator admin2;
    UserPerm up;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testNames() {
        admin = new Administrator("Admin", "Afirst", "Amiddle", "Alast");
        admin2 = new Administrator("Admin2", "A2first", "A2last");

        assertEquals(admin.getFirstName(), "Afirst");
        assertEquals(admin.getMiddleName(), "Amiddle");
        assertEquals(admin.getLastName(), "Alast");
        assertEquals(admin.getUsername(), "Admin");

        assertEquals(admin2.getFirstName(), "A2first");
        assertEquals(admin2.getLastName(), "A2last");
        assertEquals(admin2.getUsername(), "Admin2");
    }

    @Test
    public void testPermissions() {
        admin = new Administrator("Admin123", "Afirst", "Amiddle", "Alast");
        up = UserPerm.CREATE_PROJ;
        assertTrue(admin.hasPermission(up));
        up = UserPerm.ASSIGN_PROJ_LEAD;
        assertTrue(admin.hasPermission(up));
        up = UserPerm.DELETE_PROJ;
        assertTrue(admin.hasPermission(up));
        up = UserPerm.UPDATE_PROJ;
        assertTrue(admin.hasPermission(up));
    }
}
