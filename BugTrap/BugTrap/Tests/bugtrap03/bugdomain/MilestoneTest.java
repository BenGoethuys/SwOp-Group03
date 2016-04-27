package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 *
 */
public class MilestoneTest {

    private Milestone milestone0;
    private Milestone milestone1;
    private Milestone milestone2;
    private Milestone milestone3;
    private String string0;
    private String string1;
    private String string2;
    private String string3;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	milestone0 = new Milestone();
        milestone1 = new Milestone(1);
        milestone2 = new Milestone(1, 2);
        milestone3 = new Milestone(1, 2, 3);
        string0 = "M0";
        string1 = "M1";
        string2 = "M1.2";
        string3 = "M1.2.3";
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.Milestone#toString()}.
     */
    @Test
    public void testToString() {
	assertEquals(string0, milestone0.toString());
	
        assertEquals(string1, milestone1.toString());
        assertNotEquals("M1.0", milestone1.toString());
        assertNotEquals("M1.0.0", milestone1.toString());

        assertEquals(string2, milestone2.toString());
        assertNotEquals("M1", milestone2.toString());
        assertNotEquals("M1.2.0", milestone2.toString());

        assertEquals(string3, milestone3.toString());
        assertNotEquals("M1", milestone3.toString());
        assertNotEquals("M1.2.0", milestone3.toString());
    }

    /**
     * Test method for
     * {@link bugtrap03.bugdomain.Milestone#compareTo(Milestone)}.
     */
    @Test
    public void testCompareTo() {
        Milestone milestonecomp1 = new Milestone(2, 3, 4);

        Milestone milestonecomp2 = new Milestone(1, 2, 2);
        Milestone milestonecomp3 = new Milestone(1, 2, 4);

        Milestone milestonecomp4 = new Milestone(1, 1, 3);
        Milestone milestonecomp5 = new Milestone(1, 3, 3);

        assertEquals(milestone3.compareTo(milestonecomp1), -1);
        assertEquals(milestonecomp1.compareTo(milestone3), 1);

        assertEquals(milestone3.compareTo(milestonecomp2), 1);
        assertEquals(milestone3.compareTo(milestonecomp3), -1);

        assertEquals(milestone3.compareTo(milestonecomp4), 1);
        assertEquals(milestone3.compareTo(milestonecomp5), -1);
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.Milestone#equals(Object)}.
     */
    @Test
    public void testEquals() {
        assertTrue(milestone3.equals(milestone3));
        Milestone milestonecomp2 = new Milestone(0);
        assertFalse(milestone3.equals(milestonecomp2));
        assertFalse(milestone3.equals(null));
        Milestone milestonecomp3 = new Milestone(0);
        assertTrue(milestonecomp2.equals(milestonecomp3));

        // equals with other object
        assertFalse(milestone3.equals(new Object()));
    }

}
