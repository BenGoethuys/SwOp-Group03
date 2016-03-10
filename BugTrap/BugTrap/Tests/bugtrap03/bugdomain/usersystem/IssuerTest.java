/**
 *
 */
package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.permission.UserPerm;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias
 */
public class IssuerTest {

    static Issuer issuer;
    static Issuer issuer2;
    static UserPerm up;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        issuer = new Issuer("Issuer", "Ifirst", "Ilast");
        issuer2 = new Issuer("Issuer2", "I2first", "I2middle", "I2last");
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testNames() {
        assertEquals(issuer.getFirstName(), "Ifirst");
        assertEquals(issuer.getLastName(), "Ilast");
        assertEquals(issuer.getUsername(), "Issuer");

        assertEquals(issuer2.getFirstName(), "I2first");
        assertEquals(issuer2.getLastName(), "I2last");
        assertEquals(issuer2.getMiddleName(), "I2middle");
        assertEquals(issuer2.getUsername(), "Issuer2");
    }

    @Test
    public void testPermissions() {
        issuer = new Issuer("Issuer123", "Ifirst", "Imiddle", "Ilast");
        up = UserPerm.CREATE_PROJ;
        assertFalse(issuer.hasPermission(up));
        up = UserPerm.ASSIGN_PROJ_LEAD;
        assertFalse(issuer.hasPermission(up));
        up = UserPerm.DELETE_PROJ;
        assertFalse(issuer.hasPermission(up));
        up = UserPerm.UPDATE_PROJ;
        assertFalse(issuer.hasPermission(up));
        up = UserPerm.CREATE_BUGREPORT;
        assertTrue(issuer.hasPermission(up));
        up = UserPerm.CREATE_COMMENT;
        assertTrue(issuer.hasPermission(up));
    }

}
