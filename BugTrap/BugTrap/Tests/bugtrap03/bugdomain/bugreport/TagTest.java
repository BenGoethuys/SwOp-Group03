package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.bugreport.Tag;
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
    public void testGetNeededTag() {
        assertEquals(Tag.NEW.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.ASSIGNED.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.UNDER_REVIEW.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.RESOLVED.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.CLOSED.getNeededPerm(), RolePerm.SPECIAL);
        assertEquals(Tag.DUPLICATE.getNeededPerm(), RolePerm.SET_TAG_DUPLICATE);
        assertEquals(Tag.NOT_A_BUG.getNeededPerm(), RolePerm.SET_TAG_NOT_A_BUG);
    }

}
