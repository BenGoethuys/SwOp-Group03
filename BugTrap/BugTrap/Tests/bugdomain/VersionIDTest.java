/**
 * 
 */
package bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 *
 */
public class VersionIDTest {
	VersionID versionID;
	VersionID versionIDcomp;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		versionID = new VersionID(1, 2, 3);
		versionIDcomp = new VersionID(2, 3, 4);
	}

	/**
	 * Test method for {@link bugdomain.VersionID#VersionID(int, int, int)}.
	 */
	@Test
	public void testVersionID() {
		System.out.println(versionID);
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
//
//	/**
//	 * Test method for {@link bugdomain.VersionID#compareTo(bugdomain.VersionID)}.
//	 */
//	@Test
//	public void testCompareTo() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#Object()}.
//	 */
//	@Test
//	public void testObject() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#getClass()}.
//	 */
//	@Test
//	public void testGetClass() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#hashCode()}.
//	 */
//	@Test
//	public void testHashCode() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
//	 */
//	@Test
//	public void testEquals() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#clone()}.
//	 */
//	@Test
//	public void testClone() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#toString()}.
//	 */
//	@Test
//	public void testToString1() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#notify()}.
//	 */
//	@Test
//	public void testNotify() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#notifyAll()}.
//	 */
//	@Test
//	public void testNotifyAll() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#wait(long)}.
//	 */
//	@Test
//	public void testWaitLong() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#wait(long, int)}.
//	 */
//	@Test
//	public void testWaitLongInt() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#wait()}.
//	 */
//	@Test
//	public void testWait() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link java.lang.Object#finalize()}.
//	 */
//	@Test
//	public void testFinalize() {
//		fail("Not yet implemented");
//	}

}
