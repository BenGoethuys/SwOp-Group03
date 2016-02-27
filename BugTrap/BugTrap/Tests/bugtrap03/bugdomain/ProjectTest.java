/**
 * 
 */
package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import java.util.GregorianCalendar;

/**
 * @author Mathias
 *
 */
public class ProjectTest {

	static Project project;
	static Project projectFail;
	static GregorianCalendar startDate;
	static GregorianCalendar creationDate;
	static VersionID version;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		version = new VersionID(1, 0, 0);
		startDate = new GregorianCalendar();
		creationDate = new GregorianCalendar();

		project = new Project(version, "test", "testproject", creationDate, startDate);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionDates() {
		projectFail = new Project(version, "test", "zou moeten falen", creationDate, null);
		projectFail = new Project(version, "test", "zou moeten falen", null, startDate);
		projectFail = new Project(version, "test", "zou moeten falen", null, null);
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

		VersionID version2 = new VersionID(2, 0, 0);
		assertNotEquals(project.getVersionID(), version2);
	}

	@Test
	public void testGetName() {
		assertEquals(project.getName(), "test");

		assertNotEquals(project.getName(), "");
		assertNotEquals(project.getName(), null);
	}

	@Test
	public void testGetDescription() {
		assertEquals(project.getDescription(), "testproject");

		assertNotEquals(project.getDescription(), "");
		assertNotEquals(project.getDescription(), null);
	}

	 @Test
	 public void testGetParent() {
		 assertEquals(project.getParent(), project);
	 }

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

	@Test
	public void testEquals() {
		assertEquals(project, project);

		Project project2 = new Project(version, "test", "testproject", creationDate, startDate);
		assertNotEquals(project, project2);
		assertNotEquals(project, null);
	}

	// @Test //TODO
	// public void testToString() {
	// fail("Not yet implemented");
	// }
}
