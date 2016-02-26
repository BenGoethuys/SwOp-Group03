package bugtrap03.bugdomain;

import bugtrap03.bugdomain.Tag;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import bugtrap03.usersystem.Role;
import purecollections.PList;
import bugtrap03.bugdomain.BugReport;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BugReportTest {
	// classes for testing
	static BugReport bugReport1;
	static BugReport bugReport2;
	
	// classes for initialisation
	static Date date;
	static Issuer issuer;
	static Developer dev;
	static Developer lead;
	static Developer programer;
	static Developer tester;
	static PList<BugReport> depList;
	static Project project;
	static Subsystem subsystem;
	
	// static counter, for older tests that don't use getUniqueId yet
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
		lead = new Developer("ditGebruiktNiemandAnders", "Jan", "Smidt");
		programer = new Developer("ditGebruiktNiemandAnders2", "Jos", "Smidt");
		tester = new Developer("ditGebruiktNiemandAnders3", "Jantje", "Smidt");
		depList = PList.<BugReport>empty();
		
		project = new Project("ANewProject", "the description of the project", lead, 0);
		project.setRole(lead, programer, Role.PROGRAMMER);
		project.setRole(lead, tester, Role.TESTER);
		subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project);
		
		bugReport1 = new BugReport(issuer, 1, "NastyBug", "bla bla", date, depList, subsystem);
		bugReport2 = new BugReport(issuer, 2, "FoundBug", "", depList, subsystem);
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
	public void testGetNewUniqueID() {
		long id = BugReport.getNewUniqueID();
		BugReport tempBugReport = new BugReport(issuer, "bla bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getUniqueID() == id);
		
		id = BugReport.getNewUniqueID();
		tempBugReport = new BugReport(issuer, id, "bla", "bla bla", depList, subsystem);
		assertTrue(BugReport.getNewUniqueID() != id);
	}

	@Test
	public void testIsValidUniqueID() {
		assertFalse(bugReport1.isValidUniqueID(1));
		assertFalse(bugReport1.isValidUniqueID(2));
		
		assertFalse(bugReport2.isValidUniqueID(1));
		assertFalse(bugReport2.isValidUniqueID(2));
		
		assertTrue(bugReport1.isValidUniqueID(BugReport.getNewUniqueID()));
		
		assertFalse(bugReport1.isValidUniqueID(-5));
	}

	@Test
	public void testGetTitle() {
		assertEquals("NastyBug", bugReport1.getTitle());
		assertEquals("FoundBug", bugReport2.getTitle());
	}
	
	@Test
	public void testSetTitle() {
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		new BugReport(issuer, getNext(), "Bla", "boo", null, subsystem);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreationDate2(){
		new BugReport(issuer, BugReport.getNewUniqueID(), "bla", "hihi", null, depList, subsystem);
	}

	@Test
	public void testGetTag() {
		assertEquals(Tag.NEW, bugReport1.getTag());
		
		assertEquals(Tag.NEW, bugReport2.getTag());
	}
	
	@Test
	public void testSetTag() throws IllegalArgumentException, PermissionException{
		// Test for normal situation
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getTag() == Tag.NEW);
		tempBugReport.addUser(dev);
		assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);
		tempBugReport.setTag(Tag.UNDER_REVIEW, programer);
		assertTrue(tempBugReport.getTag() == Tag.UNDER_REVIEW);
		tempBugReport.setTag(Tag.ASSIGNED, issuer);
		assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);
		tempBugReport.setTag(Tag.UNDER_REVIEW, tester);
		assertTrue(tempBugReport.getTag() == Tag.UNDER_REVIEW);
		tempBugReport.setTag(Tag.RESOLVED, issuer);
		assertTrue(tempBugReport.getTag() == Tag.RESOLVED);
		tempBugReport.setTag(Tag.CLOSED, lead);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidTag(){
		new BugReport(issuer, BugReport.getNewUniqueID(), "bla", "boo", date, null, depList, subsystem);
	}
	
	@Test (expected = PermissionException.class)
	public void testSetTagInvalid() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		tempBugReport.setTag(Tag.ASSIGNED, issuer);
	}
	
	@Test (expected = PermissionException.class)
	public void testSetTagInvalidNoPermission() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		tempBugReport.setTag(Tag.ASSIGNED, dev);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidTagNullPRogrammer() throws IllegalArgumentException, PermissionException{
		bugReport1.setTag(null, programer);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidTagNullTester() throws IllegalArgumentException, PermissionException{
		bugReport1.setTag(null, tester);
	}

	@Test
	public void testIsValidTag() throws PermissionException {
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		tempBugReport.addUser(dev);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertTrue(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.UNDER_REVIEW
		tempBugReport.setTag(Tag.UNDER_REVIEW, programer);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertTrue(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertTrue(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertTrue(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.RESOLVED
		tempBugReport.setTag(Tag.RESOLVED, issuer);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertTrue(tempBugReport.isValidTag(Tag.CLOSED));
		assertTrue(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.CLOSED
		tempBugReport.setTag(Tag.CLOSED, lead);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.Duplicate
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		tempBugReport.addUser(dev);
		tempBugReport.setTag(Tag.UNDER_REVIEW, tester);
		tempBugReport.setTag(Tag.DUPLICATE, lead);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
		
		// For bugReport with Tag.NOT_A_BUG
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		tempBugReport.addUser(dev);
		tempBugReport.setTag(Tag.UNDER_REVIEW, programer);
		tempBugReport.setTag(Tag.NOT_A_BUG, lead);
		assertFalse(tempBugReport.isValidTag(Tag.NEW));
		assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
		assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
		assertFalse(tempBugReport.isValidTag(Tag.NOT_A_BUG));
		
		assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
		assertFalse(tempBugReport.isValidTag(Tag.UNDER_REVIEW));
		assertFalse(tempBugReport.isValidTag(Tag.RESOLVED));
	}
	
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		Comment comment = new Comment(issuer, "Bla bla bla");
		tempBugReport.addComment(comment);
		assertTrue(tempBugReport.getCommentList().contains(comment));
		
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		Comment comment = new Comment(issuer, "Bla bla bla");
		assertTrue(tempBugReport.isValidComment(comment));
		assertFalse(tempBugReport.isValidComment(null));
		tempBugReport.addComment(comment);
		assertFalse(tempBugReport.isValidComment(comment));
	}
	
	@Test
	public void testGetCreator(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getCreator() == issuer);
	}
	
	@Test
	public void testIsValidCreator(){
		assertTrue(bugReport1.isValidCreator(issuer));
		assertFalse(bugReport1.isValidCreator(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreator(){
		new BugReport(null, getNext(), "Bla", "boo", depList, subsystem);
	}
	
	@Test
	public void testGetUserList(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
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
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.isValidUser(dev));
		assertFalse(tempBugReport.isValidUser(null));
		tempBugReport.addUser(dev);
		assertFalse(tempBugReport.isValidUser(dev));
	}
	
	@Test
	public void testGetDependencies(){
		BugReport tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getDependencies().isEmpty());
		
		PList<BugReport> newDepList = depList.plus(bugReport1);
		tempBugReport = new BugReport(issuer, getNext(), "bla", "bla", newDepList, subsystem);
		assertEquals(tempBugReport.getDependencies(), newDepList);
	}
	
	@Test
	public void testIsValidDependencies(){
		PList<BugReport> validDepList = depList.plus(bugReport1);
		PList<BugReport> nullList = null;
		assertTrue(bugReport1.isValidDependencies(depList));
		assertTrue(bugReport1.isValidDependencies(validDepList));
		assertFalse(bugReport1.isValidDependencies(nullList));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInValidDependencies(){
		new BugReport(issuer, "bla", "ho", null, subsystem);
	}
	
	@Test
	public void testGetSubsystem(){
		assertEquals(bugReport1.getSubsystem(), subsystem);
	}
	
	@Test
	public void testIsValidSubsystem(){
		assertTrue(bugReport1.isValidSubsystem(subsystem));
		assertFalse(bugReport1.isValidSubsystem(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInValidSubsystem(){
		new BugReport(issuer, "Hello", "World", depList, null);
	}

}
