package bugtrap03.usersystem;

import bugtrap03.usersystem.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class UserTest {

    public UserTest() {
    }

    /**
     * Test the constructor of usersystem.User using User(String, String,
     * String, String).
     */
    @Test
    public void testGlobalConsWGetters() {
        String unique = "Vincent045";
        String firstName = "Vincent";
        String middleName = "Mi";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);

        assertEquals(user.getUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
    }

    /**
     * Test constructor of User using User(String, String, String)
     */
    @Test
    public void testConsWithoutMiddle() {
        String unique = "Vincent045";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);

        assertEquals(user.getUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
    }

    @Test
    public void testIsValidTrue() {
        String unique = "Vincent045";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);

        assertTrue(user.isValidUsername("Vincent"));
        assertTrue(user.isValidFirstName("V"));
        assertTrue(user.isValidMiddleName(""));
        assertTrue(user.isValidMiddleName(" "));
        assertTrue(user.isValidMiddleName("t98^."));

        assertTrue(user.isValidLastName("K98L.,?"));
    }

    @Test
    public void testIsValidFalse() {
        String unique = "Vincent045";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);

        assertFalse(user.isValidUsername(""));
        assertFalse(user.isValidUsername(null));

        assertFalse(user.isValidFirstName(""));
        assertFalse(user.isValidFirstName(null));

        assertFalse(user.isValidMiddleName(null));

        assertFalse(user.isValidLastName(""));
        assertFalse(user.isValidLastName(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidUsernameNullException() {
        String unique = null;
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidFirstNameNullException() {
        String unique = "Vincent";
        String firstName = null;
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidMiddleNameNullException() {
        String unique = "Vincent";
        String firstName = "Vin";
        String middleName = null;
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidLastNameNullException() {
        String unique = "Vincent";
        String firstName = "Vin";
        String middleName = "";
        String lastName = null;

        User user = new User(unique, firstName, middleName, lastName);
    }

}
