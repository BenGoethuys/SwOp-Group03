package bugtrap03.bugdomain;

import bugtrap03.bugdomain.Tag;
import bugtrap03.usersystem.Issuer;
import bugtrap03.bugdomain.BugReport;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BugReportTest {
	static BugReport bugReport1;
	static BugReport bugReport2;
	static Date date;
	static Issuer issuer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		date = new Date();
		issuer = new Issuer("blaDitGebruiktNiemandAnders", "bla", "bla");
		
		bugReport1 = new BugReport(issuer, 1, "NastyBug", "bla bla", date);
		bugReport2 = new BugReport(issuer, 2, "FoundBug", "");
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetUniqueID() {
		assertEquals(1, bugReport1.getUniqueID());
		assertEquals(2, bugReport2.getUniqueID());
	}

	@Test
	public void testIsValidUniqueID() {
		assertFalse(bugReport1.isValidUniqueID(1));
		assertFalse(bugReport1.isValidUniqueID(2));
		
		assertFalse(bugReport2.isValidUniqueID(1));
		assertFalse(bugReport2.isValidUniqueID(2));
		
		assertTrue(bugReport1.isValidUniqueID(0));
		assertTrue(bugReport2.isValidUniqueID(5));
		
		assertFalse(bugReport1.isValidUniqueID(-5));
	}

	@Test
	public void testGetTitle() {
		assertEquals("NastyBug", bugReport1.getTitle());
		assertEquals("FoundBug", bugReport2.getTitle());
	}
	
	@Test
	public void testSetTitle() {
		BugReport tempBugReport = new BugReport(issuer, 3, "bla", "bla");
		tempBugReport.setTitle("NewTitle");
		assertEquals("NewTitle", tempBugReport.getTitle());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidTitle() {
		bugReport1.setTitle(null);
	}

	@Test
	public void testIsValidTitle() {
		assertTrue(bugReport1.isValidTitle("NastyBug"));
		assertTrue(bugReport1.isValidTitle(""));
		
		assertFalse(bugReport1.isValidTitle(null));
	}

	@Test
	public void testGetDescription() {
		assertEquals("bla bla", bugReport1.getDescription());
		assertEquals("", bugReport2.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		BugReport tempBugReport = new BugReport(issuer, 4, "bla", "bla");
		tempBugReport.setDescription("NewDescription");
		assertEquals("NewDescription", tempBugReport.getDescription());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidDescription() {
		bugReport1.setDescription(null);
	}

	@Test
	public void testIsValidDescription() {
		assertTrue(bugReport1.isValidDescription("bla"));
		assertTrue(bugReport1.isValidDescription(""));
		
		assertFalse(bugReport1.isValidDescription(null));
	}

	@Test
	public void testGetCreationDate() {
		assertFalse(date == bugReport1.getCreationDate());
		
		assertEquals(date, bugReport1.getCreationDate());
	}

	@Test
	public void testIsValidCreationDate() {
		assertTrue(bugReport1.isValidCreationDate(date));
		
		assertFalse(bugReport1.isValidCreationDate(null));
	}

	@Test
	public void testGetTag() {
		assertEquals(Tag.New, bugReport1.getTag());
		
		assertEquals(Tag.New, bugReport2.getTag());
	}
	
	//TODO add test for setTag and new setTag function

	@Test
	public void testIsValidTag() {
		//TODO implement this this if code in bugReport is complete
	}
	
	//TODO add tests for comment
	
	

}
