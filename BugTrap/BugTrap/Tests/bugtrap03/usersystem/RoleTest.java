package bugtrap03.usersystem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.permission.RolePerm;

import static org.junit.Assert.*;

/**
 * 
 * @author Mathias
 *
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
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_RESOLVED));
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_CLOSED));
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_NOT_A_BUG));
        assertTrue(roleLead.hasPermission(RolePerm.SET_TAG_DUPLICATE));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_DEV_PROJECT));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_DEV_BUGREPORT));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_PROG_ROLE));
        assertTrue(roleLead.hasPermission(RolePerm.ASSIGN_TEST_ROLE));
        
        assertFalse(roleLead.hasPermission(null));
    }

    @Test
    public void testTesterTag() {
        assertTrue(roleTester.hasPermission(RolePerm.SET_TAG_UNDER_REVIEW));
        
        assertFalse(roleTester.hasPermission(null));
    }

    @Test
    public void testProgrammer() {
        assertTrue(roleProgrammer.hasPermission(RolePerm.SET_TAG_UNDER_REVIEW));
        
        assertFalse(roleProgrammer.hasPermission(null));
    }

    @Test
    public void testNeededPermission() {
        assertEquals(roleLead.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(roleTester.getNeededPerm(), RolePerm.ASSIGN_TEST_ROLE);
        assertEquals(roleProgrammer.getNeededPerm(), RolePerm.ASSIGN_PROG_ROLE);
    }

}