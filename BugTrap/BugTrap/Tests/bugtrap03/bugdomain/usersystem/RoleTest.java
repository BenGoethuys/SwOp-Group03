package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.permission.RolePerm;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias
 */
public class RoleTest {

    static Role roleLead;
    static Role roleTester;
    static Role roleProgrammer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        roleLead = Role.LEAD;
        roleTester = Role.TESTER;
        roleProgrammer = Role.PROGRAMMER;
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSpecialTag() {
        assertFalse(roleLead.hasPermission(RolePerm.SPECIAL));
        assertFalse(roleTester.hasPermission(RolePerm.SPECIAL));
        assertFalse(roleProgrammer.hasPermission(RolePerm.SPECIAL));
    }

    @Test
    public void testLeadTag() {
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_NOT_A_BUG));
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_DUPLICATE));
        assertTrue(roleLead.hasPermission(RolePerm.SELECT_PATCH));
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_ASSIGNED));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_DEV_BUG_REPORT));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_PROG_ROLE));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_TEST_ROLE));

        assertFalse(roleLead.hasPermission(null));
    }

    @Test
    public void testTesterTag() {
        assertTrue(roleTester.hasPermission(RolePerm.ADD_TEST));
        assertTrue(roleTester.hasPermission(RolePerm.ASSIGN_DEV_BUG_REPORT));

        assertFalse(roleTester.hasPermission(null));
    }

    @Test
    public void testProgrammer() {
        assertTrue(roleProgrammer.hasPermission(RolePerm.ADD_PATCH));

        assertFalse(roleProgrammer.hasPermission(null));
    }

    @Test
    public void testNeededPermission() {
        assertEquals(roleLead.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(roleTester.getNeededPerm(), RolePerm.ASSIGN_TEST_ROLE);
        assertEquals(roleProgrammer.getNeededPerm(), RolePerm.ASSIGN_PROG_ROLE);
    }

}