package bugtrap03.bugdomain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mathias
 */
public class VersionIDTest {
    VersionID versionID;

    @Before
    public void setUp() throws Exception {
	versionID = new VersionID(1, 2, 3);
    }

    @Test
    public void testCreateVersionID1() {
	versionID = new VersionID(0, 0, 0);
	assertEquals(versionID.getNumbers()[0], 0);
	assertEquals(versionID.getNumbers()[1], 0);
	assertEquals(versionID.getNumbers()[2], 0);
	assertEquals(versionID.getNumbers().length, 3);
    }

    @Test
    public void testCreateVersionID2() {
	versionID = new VersionID();
	assertEquals(versionID.getNumbers()[0], 0);
	assertEquals(versionID.getNumbers().length, 1);
    }



    /**
     * Test method for {@link VersionID#toString()}.
     */
    @Test
    public void testToString3() {
	String test = "1.2.3";
	assertEquals(versionID.toString(), test);
    }

    /**
     * Test method for {@link VersionID#toString()}.
     */
    @Test
    public void testToString2() {
	String test = "1.2";
	versionID = new VersionID(1, 2);
	assertEquals(versionID.toString(), test);
    }

    /**
     * Test method for {@link VersionID#toString()}.
     */
    @Test
    public void testToString1() {
	String test = "1";
	versionID = new VersionID(1);
	assertEquals(versionID.toString(), test);
    }

    /**
     * Test method for {@link VersionID#compareTo(VersionID)}.
     */
    @Test
    public void testCompareTo() {
	VersionID versionIDcomp1 = new VersionID(2, 3, 4);

	VersionID versionIDcomp2 = new VersionID(1, 2, 2);
	VersionID versionIDcomp3 = new VersionID(1, 2, 4);

	VersionID versionIDcomp4 = new VersionID(1, 1, 3);
	VersionID versionIDcomp5 = new VersionID(1, 3, 3);

	assertEquals(versionID.compareTo(versionIDcomp1), -1);
	assertEquals(versionIDcomp1.compareTo(versionID), 1);

	assertEquals(versionID.compareTo(versionIDcomp2), 1);
	assertEquals(versionID.compareTo(versionIDcomp3), -1);

	assertEquals(versionID.compareTo(versionIDcomp4), 1);
	assertEquals(versionID.compareTo(versionIDcomp5), -1);
    }

    /**
     * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
     */
    @Test
    public void testEquals() {
	assertTrue(versionID.equals(versionID));
	VersionID versionID2 = new VersionID();
	assertFalse(versionID.equals(versionID2));
	assertFalse(versionID.equals(null));
	VersionID versionID3 = new VersionID();
	assertTrue(versionID2.equals(versionID3));

	// equals with other object
	assertFalse(versionID.equals(new Object()));
    }

    /**
     * Test method for {@link VersionID#clone()}.
     */
    @Test
    public void testClone() {
	VersionID cloneVersionID = versionID.clone();
	assertEquals(versionID.getNumbers()[0], cloneVersionID.getNumbers()[0]);
	assertEquals(versionID.getNumbers()[1], cloneVersionID.getNumbers()[1]);
	assertEquals(versionID.getNumbers()[2], cloneVersionID.getNumbers()[2]);
	assertEquals(versionID.getNumbers().length, 3);
	assertEquals(versionID.getNumbers().length, cloneVersionID.getNumbers().length);
    }

    @Test
    public void testHashcode() {
	VersionID id1 = new VersionID(1, 1, 0);
	VersionID id2 = new VersionID(1, 1);

	assertEquals(0, id1.compareTo(id2));
	System.out.println(id1.hashCode());
	System.out.println(id2.hashCode());
	assertEquals(id1.hashCode(), id2.hashCode());
    }

}
