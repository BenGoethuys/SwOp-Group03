package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;

import purecollections.PList;

import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractSystemTest {
	static AbstractSystem testSystem1;
	static VersionID testVersion1; 
	static String testDescript1; 
	static String testName1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testVersion1 = new VersionID(1,2,3);
		testDescript1 = "This is a testdescription";
		testName1 = "Swop";		
	}

	@Before
	public void setUp(){
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
		assertTrue(testSystem1.isValidName(testName1));
	}
	
	@Test
	public void testMakeSubsystemChild(){
		testSystem1.makeSubsystemChild(testVersion1, testName1, testDescript1);
		
		assertEquals(testSystem1.getChilds().size(), 1);
		assertEquals(testSystem1.getChilds().get(0).getVersionID(), testVersion1);
		assertEquals(testSystem1.getChilds().get(0).getName(), testName1);
		assertEquals(testSystem1.getChilds().get(0).getDescription(), testDescript1);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetParentProject(){
		
	}

}
