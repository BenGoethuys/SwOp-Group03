package bugtrap03.bugdomain;

import static org.junit.Assert.*;



import purecollections.PList;

import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractSystemTest {
	static AbstractSystem testSystem1;
	static Subsystem testChild1;
	static Subsystem testChild2;
	static VersionID testVersion1; 
	static String testDescript1; 
	static String testName1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testVersion1 = new VersionID(1,2,3);
		testDescript1 = "This is a testdescription";
		testName1 = "Swop";		
		testSystem1 = new AbstractSystemDummy(testVersion1, testName1, testDescript1);
	}

	@Test
	public void testConstructorWGetters() {
		assertEquals(testSystem1.getVersionID(), testVersion1);
		assertEquals(testSystem1.getDescription(), testDescript1);
		assertEquals(testSystem1.getName(), testName1);
		assertEquals(testSystem1.getChilds(), PList.<Subsystem>empty());
	}
	
	@Test 
	public void testisValidName(){
		assertFalse(testSystem1.isValidName(""));
		assertTrue(testSystem1.isValidName("kwinten"));
	}

}
