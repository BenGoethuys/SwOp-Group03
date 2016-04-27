package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias
 */
public class UserTest {

    static User user;
    static User user2;

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

        user = new UserTestDummy(unique, firstName, middleName, lastName);

        assertEquals(user.getUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
        assertNotNull(user.getMailbox());
    }

    /**
     * Test constructor of User using User(String, String, String)
     */
    @Test
    public void testConsWithoutMiddle() {
        String unique = "Vincent046";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);

        assertEquals(user.getUsername(), unique);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getMiddleName(), middleName);
        assertEquals(user.getLastName(), lastName);
    }

    /**
     * Test valid username, firstname, middlename, lastname.
     */
    @Test
    public void testIsValidTrue() {
        String unique = "Vincent047";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);

        assertTrue(user.isValidUsername("Vincent048"));
        assertTrue(user.isValidFirstName("V"));
        assertTrue(user.isValidMiddleName(""));
        assertTrue(user.isValidMiddleName(" "));
        assertTrue(user.isValidMiddleName("t98^."));

        assertTrue(user.isValidLastName("K98L.,?"));
    }

    /**
     * Test invalid username/firstname/middlename/lastname.
     */
    @Test
    public void testIsValidFalse() {
        String unique = "Vincent049";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);

        assertFalse(user.isValidUsername(unique));
        assertFalse(user.isValidUsername(""));
        assertFalse(user.isValidUsername(null));

        assertFalse(user.isValidFirstName(""));
        assertFalse(user.isValidFirstName(null));

        assertFalse(user.isValidMiddleName(null));

        assertFalse(user.isValidLastName(""));
        assertFalse(user.isValidLastName(null));
    }

    /**
     * Test username == "" should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConsUsernameEmptyException() {
        String unique = "";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test firstName == "" should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConsFirstNameEmptyException() {
        String unique = "VBA1";
        String firstName = "";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test lastName == "" should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConsLastNameEmptyException() {
        String unique = "VBA2";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test username not unique should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsValidUsernameAlreadyUsedException() {
        String unique = "VincentT";
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
        user2 = new UserTestDummy(unique, "L", "A", "B");
    }

    /**
     * Test username == null should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsValidUsernameNullException() {
        String unique = null;
        String firstName = "Vincent";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test firstname == null should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsValidFirstNameNullException() {
        String unique = "Vincent";
        String firstName = null;
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test middle == null should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConcMiddleNameNullException() {
        String unique = "VincentXD";
        String firstName = "Vin";
        String middleName = null;
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test lastName == null should throw error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConcLastNameNullException() {
        String unique = "Vincent";
        String firstName = "Vin";
        String middleName = "";
        String lastName = null;

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    /**
     * Test middle == null should throw error.
     */
    @Test
    public void testConcMiddleNameValid() {
        String unique = "VDA";
        String firstName = "Vin";
        String middleName = "";
        String lastName = "Derk";

        user = new UserTestDummy(unique, firstName, middleName, lastName);
    }

    @Test
    public void testGetFullName() {
        user = new UserTestDummy("User1", "First", "Last");
        String str = "First Last";
        assertEquals(user.getFullName(), str);

        user2 = new UserTestDummy("User2", "First", "Middle", "Last");
        String str2 = "First Middle Last";
        assertEquals(user2.getFullName(), str2);
    }

    @Test
    public void testHasPermission() {
        user = new UserTestDummy("AlwaysFalse", "First", "Last");
        assertFalse(user.hasPermission(UserPerm.ASSIGN_PROJ_LEAD));
        assertFalse(user.hasPermission(UserPerm.CREATE_PROJ));
        assertFalse(user.hasPermission(UserPerm.DELETE_PROJ));
        assertFalse(user.hasPermission(UserPerm.UPDATE_PROJ));
    }

    /**
     * Test isValidMailbox
     */
    @Test
    public void testIsValidMailBox() {
        user = new UserTestDummy("AlwaysFalse1", "First", "Last");
        assertFalse(user.isValidMailbox(null));
        assertTrue(user.isValidMailbox(new Mailbox()));
    }
    
    @Test
    public void testEquals() {
        user = new UserTestDummy("D", "um", "my");
        assertFalse(user.equals(null));
        int getal = 0;
        assertFalse(user.equals(getal));
    }
}
