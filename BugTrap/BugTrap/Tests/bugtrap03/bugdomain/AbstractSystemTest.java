package bugtrap03.bugdomain;

import static org.junit.Assert.*;
import purecollections.PList;

import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractSystemTest {
	static AbstractSystem testSystem;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testConstructorWGetters() {
		VersionID testVersion = new VersionID(1,2,3);
		String testDescript = "This is a testdescription";
		String testName = "Swop";
		
		testSystem = new AbstractSystemDummy(testVersion, testDescript, testName);
		
		assertEquals(testSystem.getVersionID(), testVersion);
		assertEquals(testSystem.getDescription(), testDescript);
		assertEquals(testSystem.getName(), testName);
		assertEquals(testSystem.getChilds(), PList.<Subsystem>empty());
	}
	
	@Test 
	public void test

}
