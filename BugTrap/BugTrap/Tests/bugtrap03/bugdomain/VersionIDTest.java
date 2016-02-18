/**
 * 
 */
package bugtrap03.bugdomain;

import bugtrap03.bugdomain.VersionID;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 *
 */
public class VersionIDTest {
	VersionID versionID;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		versionID = new VersionID(1, 2, 3);
	}

	/**
	 * Test method for {@link bugdomain.VersionID#VersionID(int, int, int)}.
	 */
	@Test
	public void testVersionID() {
		System.out.println(versionID);
	}

	@Test
	public void testIllegalArgument() {
		try {
			@SuppressWarnings("unused")
			VersionID test = new VersionID(0, 0, 0);
			fail("There must be an exception");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link bugdomain.VersionID#getFirstNb()}.
	 */
	@Test
	public void testGetFirstNb() {
		assertEquals(versionID.getFirstNb(), 1);
	}

	/**
	 * Test method for {@link bugdomain.VersionID#getSecondNb()}.
	 */
	@Test
	public void testGetSecondNb() {
		assertEquals(versionID.getSecondNb(), 2);
	}

	/**
	 * Test method for {@link bugdomain.VersionID#getThirdNb()}.
	 */
	@Test
	public void testGetThirdNb() {
		assertEquals(versionID.getThirdNb(), 3);
	}

	/**
	 * Test method for {@link bugdomain.VersionID#toString()}.
	 */
	@Test
	public void testToString() {
		String test = "1.2.3";
		assertEquals(versionID.toString(), test);
	}

	/**
	 * Test method for
	 * {@link bugdomain.VersionID#compareTo(bugdomain.VersionID)}.
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
		assertEquals(versionID.compareTo(versionID), 0);

		VersionID versionID2 = new VersionID(1, 2, 3);
		assertEquals(versionID.compareTo(versionID2), 0);
	}

}
