package bugtrap03.bugdomain;

import bugtrap03.bugdomain.permission.RolePerm;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsValidTag() {
        // For bugReport with Tag.NEW
        assertFalse(Tag.NEW.isValidTag(null));
        assertFalse(Tag.NEW.isValidTag(Tag.NEW));
        assertFalse(Tag.NEW.isValidTag(Tag.CLOSED));
        assertTrue(Tag.NEW.isValidTag(Tag.DUPLICATE));
        assertTrue(Tag.NEW.isValidTag(Tag.NOT_A_BUG));

        assertTrue(Tag.NEW.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.NEW.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.NEW.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.ASSIGNED
        assertFalse(Tag.ASSIGNED.isValidTag(null));
        assertFalse(Tag.ASSIGNED.isValidTag(Tag.NEW));
        assertFalse(Tag.ASSIGNED.isValidTag(Tag.CLOSED));
        assertTrue(Tag.ASSIGNED.isValidTag(Tag.DUPLICATE));
        assertTrue(Tag.ASSIGNED.isValidTag(Tag.NOT_A_BUG));

        assertFalse(Tag.ASSIGNED.isValidTag(Tag.ASSIGNED));
        assertTrue(Tag.ASSIGNED.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.ASSIGNED.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.UNDER_REVIEW
        assertFalse(Tag.UNDER_REVIEW.isValidTag(null));
        assertFalse(Tag.UNDER_REVIEW.isValidTag(Tag.NEW));
        assertTrue(Tag.UNDER_REVIEW.isValidTag(Tag.CLOSED));
        assertTrue(Tag.UNDER_REVIEW.isValidTag(Tag.DUPLICATE));
        assertTrue(Tag.UNDER_REVIEW.isValidTag(Tag.NOT_A_BUG));

        assertTrue(Tag.UNDER_REVIEW.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.UNDER_REVIEW.isValidTag(Tag.UNDER_REVIEW));
        assertTrue(Tag.UNDER_REVIEW.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.RESOLVED
        assertFalse(Tag.RESOLVED.isValidTag(null));
        assertFalse(Tag.RESOLVED.isValidTag(Tag.NEW));
        assertTrue(Tag.RESOLVED.isValidTag(Tag.CLOSED));
        assertTrue(Tag.RESOLVED.isValidTag(Tag.DUPLICATE));
        assertTrue(Tag.RESOLVED.isValidTag(Tag.NOT_A_BUG));

        assertFalse(Tag.RESOLVED.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.RESOLVED.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.RESOLVED.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.CLOSED
        assertFalse(Tag.CLOSED.isValidTag(null));
        assertFalse(Tag.CLOSED.isValidTag(Tag.NEW));
        assertFalse(Tag.CLOSED.isValidTag(Tag.CLOSED));
        assertFalse(Tag.CLOSED.isValidTag(Tag.DUPLICATE));
        assertFalse(Tag.CLOSED.isValidTag(Tag.NOT_A_BUG));

        assertFalse(Tag.CLOSED.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.CLOSED.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.CLOSED.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.Duplicate
        assertFalse(Tag.DUPLICATE.isValidTag(null));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.NEW));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.CLOSED));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.DUPLICATE));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.NOT_A_BUG));

        assertFalse(Tag.DUPLICATE.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.DUPLICATE.isValidTag(Tag.RESOLVED));

        // For bugReport with Tag.NOT_A_BUG
        assertFalse(Tag.NOT_A_BUG.isValidTag(null));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.NEW));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.CLOSED));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.DUPLICATE));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.NOT_A_BUG));

        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.ASSIGNED));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.UNDER_REVIEW));
        assertFalse(Tag.NOT_A_BUG.isValidTag(Tag.RESOLVED));
    }

    @Test
    public void testGetNeededTag() {
        assertEquals(Tag.NEW.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.ASSIGNED.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.UNDER_REVIEW.getNeededPerm(), RolePerm.SET_TAG_UNDER_REVIEW);
        assertEquals(Tag.RESOLVED.getNeededPerm(), RolePerm.SET_TAG_RESOLVED);
        assertEquals(Tag.CLOSED.getNeededPerm(), RolePerm.SET_TAG_CLOSED);
        assertEquals(Tag.DUPLICATE.getNeededPerm(), RolePerm.SET_TAG_DUPLICATE);
        assertEquals(Tag.NOT_A_BUG.getNeededPerm(), RolePerm.SET_TAG_NOT_A_BUG);
    }

}
