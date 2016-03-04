/**
 * 
 */
package bugtrap03.bugdomain;

import bugtrap03.bugdomain.VersionID;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

/**
 * @author Mathias
 *
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
		assertEquals(versionID.getFirstNb(), 0);
		assertEquals(versionID.getSecondNb(), 0);
		assertEquals(versionID.getThirdNb(), 0);
	}

	@Test
	public void testCreateVersionID2() {
		versionID = new VersionID(0, 0);
		assertEquals(versionID.getFirstNb(), 0);
		assertEquals(versionID.getSecondNb(), 0);
		assertEquals(versionID.getThirdNb(), 0);
	}

	@Test
	public void testCreateVersionID3() {
		versionID = new VersionID(0);
		assertEquals(versionID.getFirstNb(), 0);
		assertEquals(versionID.getSecondNb(), 0);
		assertEquals(versionID.getThirdNb(), 0);
	}

	@Test
	public void testCreateVersionID4() {
		versionID = new VersionID();
		assertEquals(versionID.getFirstNb(), 0);
		assertEquals(versionID.getSecondNb(), 0);
		assertEquals(versionID.getThirdNb(), 1);
	}

	/**
	 * Test method for {@link VersionID#getFirstNb()}.
	 */
	@Test
	public void testGetFirstNb() {
		assertEquals(versionID.getFirstNb(), 1);
	}

	/**
	 * Test method for {@link VersionID#getSecondNb()}.
	 */
	@Test
	public void testGetSecondNb() {
		assertEquals(versionID.getSecondNb(), 2);
	}

	/**
	 * Test method for {@link VersionID#getThirdNb()}.
	 */
	@Test
	public void testGetThirdNb() {
		assertEquals(versionID.getThirdNb(), 3);
	}

	/**
	 * Test method for {@link VersionID#toString()}.
	 */
	@Test
	public void testToString() {
		String test = "1.2.3";
		assertEquals(versionID.toString(), test);
	}

	/**
	 * Test method for
	 * {@link VersionID#compareTo(VersionID)}.
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

}
