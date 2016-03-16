package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 *
 */
public class MilestoneTest {

    private Milestone milestone1;
    private Milestone milestone2;
    private Milestone milestone3;
    private String string1;
    private String string2;
    private String string3;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        milestone1 = new Milestone(1);
        milestone2 = new Milestone(1, 2);
        milestone3 = new Milestone(1, 2, 3);
        string1 = "M1";
        string2 = "M1.2";
        string3 = "M1.2.3";
    }

    /**
     * Test method for {@link bugtrap03.bugdomain.Milestone#toString()}.
     */
    @Test
    public void testToString() {
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

}
