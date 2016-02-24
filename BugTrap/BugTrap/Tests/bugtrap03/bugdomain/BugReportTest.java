package bugtrap03.bugdomain;

import bugtrap03.bugdomain.Tag;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import purecollections.PList;
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
	static Developer dev;
	static PList<BugReport> depList;
	
	static int counter = 100;
	
	public static int getNext(){
		int temp = counter;
		counter += 1;
		return temp;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		date = new Date();
		issuer = new Issuer("blaDitGebruiktNiemandAnders", "bla", "bla");
		dev = new Developer("booDitGebruiktNiemandAnders", "Jan", "Smidt");
		depList = PList.<BugReport>empty();
		
		bugReport1 = new BugReport(issuer, 1, "NastyBug", "bla bla", date, depList);
		bugReport2 = new BugReport(issuer, 2, "FoundBug", "", depList);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
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
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreationDate() {
		new BugReport(issuer, getNext(), "Bla", "boo", null);
	}

	@Test
	public void testGetTag() {
		assertEquals(Tag.NEW, bugReport1.getTag());
		
		assertEquals(Tag.NEW, bugReport2.getTag());
	}
	
	//TODO new setTag function
	
	@Test
	public void testSetTag(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		tempBugReport.setTag(Tag.ASSIGNED);
		assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidTag(){
		bugReport1.setTag(null);
	}

	@Test
	public void testIsValidTag() {
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertFalse(tempBugReport.isValidTag(null));
		
		// For bugReport with Tag.NEW
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertTrue(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.ASSIGNED
		tempBugReport.setTag(Tag.ASSIGNED);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertTrue(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.UNDER_REVIEW
		tempBugReport.setTag(Tag.UNDER_REVIEW);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertTrue(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertTrue(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertTrue(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.RESOLVED
		tempBugReport.setTag(Tag.RESOLVED);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertTrue(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.CLOSED
		tempBugReport.setTag(Tag.CLOSED);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.Duplicate
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		tempBugReport.setTag(Tag.ASSIGNED);
		tempBugReport.setTag(Tag.UNDER_REVIEW);
		tempBugReport.setTag(Tag.DUPLICATE);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.NOT_A_BUG
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		tempBugReport.setTag(Tag.ASSIGNED);
		tempBugReport.setTag(Tag.UNDER_REVIEW);
		tempBugReport.setTag(Tag.NOT_A_BUG);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
	}
	
	//TODO move to TagTest class
	
	@Test
	public void testGetCommentList(){
		assertTrue(bugReport1.getCommentList().isEmpty());
		Comment comment = new Comment(issuer, "Bla bla bla");
		bugReport1.addComment(comment);
		assertTrue(bugReport1.getCommentList().contains(comment));
	}
	
	@Test
	public void testIsValidCommentList(){
		Comment comment = new Comment(issuer, "Bla bla bla");
		PList<Comment> validListEmpty = PList.<Comment>empty();
		PList<Comment> validList = validListEmpty.plus(comment);
		PList<Comment> nullPointer = null;
		
		assertTrue(bugReport1.isValidCommentList(validListEmpty));
		assertTrue(bugReport1.isValidCommentList(validList));
		assertFalse(bugReport1.isValidCommentList(nullPointer));
	}
	
	@Test
	public void testAddComment(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		Comment comment = new Comment(issuer, "Bla bla bla");
		tempBugReport.addComment(comment);
		assertTrue(tempBugReport.getCommentList().contains(comment));
		
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		tempBugReport.addComment(issuer, "Bla");
		assertFalse(tempBugReport.getCommentList().isEmpty());
		assertTrue(tempBugReport.getCommentList().getFirst().getCreator() == issuer);
		assertTrue(tempBugReport.getCommentList().getFirst().getText() == "Bla");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddInvalidComment(){
		bugReport1.addComment(null);
	}
	
	@Test
	public void testIsValidComment(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		Comment comment = new Comment(issuer, "Bla bla bla");
		assertTrue(tempBugReport.isValidComment(comment));
		assertFalse(tempBugReport.isValidComment(null));
		tempBugReport.addComment(comment);
		assertFalse(tempBugReport.isValidComment(comment));
	}
	
	@Test
	public void testGetCreator(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.getCreator() == issuer);
	}
	
	@Test
	public void testIsValidCreator(){
		assertTrue(bugReport1.isValidCreator(issuer));
		assertFalse(bugReport1.isValidCreator(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreator(){
		new BugReport(null, getNext(), "Bla", "boo", depList);
	}
	
	@Test
	public void testGetUserList(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.getUserList().isEmpty());
		
		tempBugReport.addUser(dev);
		assertTrue(tempBugReport.getUserList().contains(dev));
	}
	
	@Test
	public void testIsValidUserList(){
		PList<Developer> validListEmpty = PList.<Developer>empty();
		PList<Developer> validList = validListEmpty.plus(dev);
		PList<Developer> nullPointer = null;
		
		assertTrue(bugReport1.isValidUserList(validListEmpty));
		assertTrue(bugReport1.isValidUserList(validList));
		assertFalse(bugReport1.isValidUserList(nullPointer));
	}
	
	@Test
	public void testAddUser(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.getUserList().isEmpty());
		assertTrue(tempBugReport.getTag() == Tag.NEW);
		tempBugReport.addUser(dev);
		assertTrue(tempBugReport.getUserList().contains(dev));
		assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);
		
		Developer temp = new Developer("barDitGebruiktNiemandAnders", "bla", "la");
		tempBugReport.addUser(temp);
		assertTrue(tempBugReport.getUserList().contains(dev));
		assertTrue(tempBugReport.getUserList().contains(temp));
		assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddInvalidUser(){
		bugReport1.addUser(null);
	}
	
	@Test
	public void testIsValidUser(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList);
		assertTrue(tempBugReport.isValidUser(dev));
		assertFalse(tempBugReport.isValidUser(null));
		tempBugReport.addUser(dev);
		assertFalse(tempBugReport.isValidUser(dev));
	}

}
