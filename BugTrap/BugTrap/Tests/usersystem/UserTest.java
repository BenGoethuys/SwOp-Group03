package usersystem;

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

        assertEquals(user.getUniqueUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
    }

    /**
     * Test constructor of User with using 
     */
    public void testConsWithoutMiddle() {
        String unique = "Vincent045";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        User user = new User(unique, firstName, middleName, lastName);

        assertEquals(user.getUniqueUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
    }
}
