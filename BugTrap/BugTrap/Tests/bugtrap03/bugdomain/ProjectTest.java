/**
 * 
 */
package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;

/**
 * @author Mathias
 *
 */
public class ProjectTest {

	static Project project;
	static Date startDate;
	static Date creationDate;
	static VersionID version;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		version = new VersionID(1, 0, 0);
		startDate = new Date();
		creationDate = new Date();

		project = new Project(version, "test", "testproject", creationDate, startDate);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetStartDate() {
		assertEquals(project.getStartDate(), startDate);
	}

	@Test
	public void testGetCreationDate() {
		assertEquals(project.getCreationDate(), creationDate);
	}

	@Test
	public void testGetVersionID() {
		assertEquals(project.getVersionID(), version);
	}

	@Test
	public void testGetName() {
		assertEquals(project.getName(), "test");
	}

	@Test
	public void testGetDescription() {
		assertEquals(project.getDescription(), "testproject");

	}
	
	// @Test //TODO
	// public void testGetParent() {
	// fail("Not yet implemented");
	// }

	// @Test //TODO
	// public void testGetParentProject() {
	// fail("Not yet implemented");
	// }

	// @Test /TODO
	// public void testGetChilds() {
	// fail("Not yet implemented");
	// }

	// @Test //TODO
	// public void testAddChild() {
	// fail("Not yet implemented");
	// }
	//

	// @Test //TODO
	// public void testObject() {
	// fail("Not yet implemented");
	// }
	
	// @Test //TODO
	// public void testGetClass() {
	// fail("Not yet implemented");
	// }
	
	// @Test //TODO
	// public void testHashCode() {
	// fail("Not yet implemented");
	// }

	// @Test //TODO
	// public void testEquals() {
	// fail("Not yet implemented");
	// }
	
	// @Test //TODO
	// public void testToString() {
	// fail("Not yet implemented");
	// }
}
