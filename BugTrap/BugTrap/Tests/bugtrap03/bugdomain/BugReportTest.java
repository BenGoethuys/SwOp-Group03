package bugtrap03.bugdomain;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;
import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BugReportTest {
	// classes for testing
	static BugReport bugReport1;
	static BugReport bugReport2;
	
	// classes for initialisation
	static GregorianCalendar date;
	static Issuer issuer;
	static Developer dev;
	static Developer lead;
	static Developer programer;
	static Developer tester;
	static PList<BugReport> depList;
	static Project project;
	static Subsystem subsystem;
	static Administrator admin;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		date = new GregorianCalendar();
		issuer = new Issuer("blaDitGebruiktNiemandAnders", "bla", "bla");
		dev = new Developer("booDitGebruiktNiemandAnders", "Jan", "Smidt");
		lead = new Developer("ditGebruiktNiemandAnders", "Jan", "Smidt");
		programer = new Developer("ditGebruiktNiemandAnders2", "Jos", "Smidt");
		tester = new Developer("ditGebruiktNiemandAnders3", "Jantje", "Smidt");
		depList = PList.<BugReport>empty();
		
		admin = new Administrator("ditGebruiktNiemandAnders4", "bla", "hihi");
		
		project = new Project("ANewProject", "the description of the project", lead, 0);
		project.setRole(lead, programer, Role.PROGRAMMER);
		project.setRole(lead, tester, Role.TESTER);
		subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project);
		
		bugReport1 = new BugReport(issuer, 1, "NastyBug", "bla bla", date, depList, subsystem);
		bugReport2 = new BugReport(issuer, "FoundBug", "", depList, subsystem);
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
	public void testGetNewUniqueID() throws IllegalArgumentException, PermissionException {
		long id = BugReport.getNewUniqueID();
		BugReport tempBugReport = new BugReport(issuer, "bla bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getUniqueID() == id);
		
		id = BugReport.getNewUniqueID();
		tempBugReport = new BugReport(issuer, "bla", "bla bla", depList, subsystem);
		assertTrue(BugReport.getNewUniqueID() != id);
	}

	@Test
	public void testIsValidUniqueID() {
		assertFalse(BugReport.isValidUniqueID(1));
		assertFalse(BugReport.isValidUniqueID(2));
		
		assertFalse(BugReport.isValidUniqueID(1));
		assertFalse(BugReport.isValidUniqueID(2));
		
		assertTrue(BugReport.isValidUniqueID(BugReport.getNewUniqueID()));
		
		assertFalse(BugReport.isValidUniqueID(-5));
	}

	@Test
	public void testGetTitle() {
		assertEquals("NastyBug", bugReport1.getTitle());
		assertEquals("FoundBug", bugReport2.getTitle());
	}
	
	@Test
	public void testSetTitle() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		tempBugReport.setTitle("NewTitle");
		assertEquals("NewTitle", tempBugReport.getTitle());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidTitle() {
		bugReport1.setTitle(null);
	}

	@Test
	public void testIsValidTitle() {
		assertTrue(BugReport.isValidTitle("NastyBug"));
		assertTrue(BugReport.isValidTitle(""));
		
		assertFalse(BugReport.isValidTitle(null));
	}

	@Test
	public void testGetDescription() {
		assertEquals("bla bla", bugReport1.getDescription());
		assertEquals("", bugReport2.getDescription());
	}
	
	@Test
	public void testSetDescription() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		tempBugReport.setDescription("NewDescription");
		assertEquals("NewDescription", tempBugReport.getDescription());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetInvalidDescription() {
		bugReport1.setDescription(null);
	}

	@Test
	public void testIsValidDescription() {
		assertTrue(BugReport.isValidDescription("bla"));
		assertTrue(BugReport.isValidDescription(""));
		
		assertFalse(BugReport.isValidDescription(null));
	}

	@Test
	public void testGetCreationDate() {
		assertFalse(date == bugReport1.getCreationDate());
		
		assertEquals(date, bugReport1.getCreationDate());
	}

	@Test
	public void testIsValidCreationDate() {
		assertTrue(BugReport.isValidCreationDate(date));
		
		assertFalse(BugReport.isValidCreationDate(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreationDate() throws IllegalArgumentException, PermissionException {
		new BugReport(issuer, "Bla", "boo", null, subsystem);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreationDate2() throws IllegalArgumentException, PermissionException{
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
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
	public void testBugReportInvalidTag() throws IllegalArgumentException, PermissionException{
		new BugReport(issuer, BugReport.getNewUniqueID(), "bla", "boo", date, null, depList, subsystem);
	}
	
	@Test (expected = PermissionException.class)
	public void testSetTagInvalid() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		tempBugReport.setTag(Tag.ASSIGNED, issuer);
	}
	
	@Test (expected = PermissionException.class)
	public void testSetTagInvalidNoPermission() throws IllegalArgumentException, PermissionException {
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
		tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
		tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
	public void testGetCommentList() throws PermissionException {
		assertTrue(bugReport1.getCommentList().isEmpty());
		Comment comment = new Comment(issuer, "Bla bla bla");
		bugReport1.addComment(comment);
		assertTrue(bugReport1.getCommentList().contains(comment));
	}

	@Test
	public void testGetAllComments() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "hihi", depList, subsystem);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		assertTrue(tempBugReport.getAllComments().isEmpty());
		
		Comment comment = new Comment(issuer, "bla bla");
		tempBugReport.addComment(comment);
		assertTrue(tempBugReport.getCommentList().contains(comment));
		assertTrue(tempBugReport.getAllComments().contains(comment));
		
		Comment comment2 = new Comment(issuer, "hello world");
		comment.addSubComment(comment2);
		assertFalse(tempBugReport.getCommentList().contains(comment2));
		assertTrue(tempBugReport.getAllComments().contains(comment2));
	}
	
	@Test
	public void testIsValidCommentList() throws PermissionException {
		Comment comment = new Comment(issuer, "Bla bla bla");
		PList<Comment> validListEmpty = PList.<Comment>empty();
		PList<Comment> validList = validListEmpty.plus(comment);
		PList<Comment> nullPointer = null;
		
		assertTrue(BugReport.isValidCommentList(validListEmpty));
		assertTrue(BugReport.isValidCommentList(validList));
		assertFalse(BugReport.isValidCommentList(nullPointer));
	}
	
	@Test
	public void testAddComment() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		Comment comment = new Comment(issuer, "Bla bla bla");
		tempBugReport.addComment(comment);
		assertTrue(tempBugReport.getCommentList().contains(comment));
		
		tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getCommentList().isEmpty());
		Comment returnComment = tempBugReport.addComment(issuer, "Bla");
		assertFalse(tempBugReport.getCommentList().isEmpty());
		assertTrue(tempBugReport.getCommentList().getFirst().getCreator() == issuer);
		assertTrue(tempBugReport.getCommentList().getFirst().getText() == "Bla");
		assertEquals(tempBugReport.getCommentList().getFirst(), returnComment);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddInvalidComment(){
		bugReport1.addComment(null);
	}

    @Test (expected = PermissionException.class)
    public void testAddCommentNoPermission() throws PermissionException {
        bugReport1.addComment(admin, "This is a comment");
    }
	
	@Test
	public void testIsValidComment() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		Comment comment = new Comment(issuer, "Bla bla bla");
		assertTrue(tempBugReport.isValidComment(comment));
		assertFalse(tempBugReport.isValidComment(null));
		tempBugReport.addComment(comment);
		assertFalse(tempBugReport.isValidComment(comment));
	}
	
	@Test
	public void testGetCreator() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getCreator() == issuer);
	}
	
	@Test
	public void testIsValidCreator(){
		assertTrue(BugReport.isValidCreator(issuer));
		assertFalse(BugReport.isValidCreator(null));
		assertFalse(BugReport.isValidCreator(admin));
		assertTrue(BugReport.isValidCreator(dev));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInvalidCreator() throws IllegalArgumentException, PermissionException{
		new BugReport(null, "Bla", "boo", depList, subsystem);
	}
	
	@Test (expected = PermissionException.class)
    public void testBugReportCreatorNoPermission() throws IllegalArgumentException, PermissionException{
        new BugReport(admin, "Bla", "boo", depList, subsystem);
    }
	
	@Test
	public void testGetUserList() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getUserList().isEmpty());
		
		tempBugReport.addUser(dev);
		assertTrue(tempBugReport.getUserList().contains(dev));
	}
	
	@Test
	public void testIsValidUserList(){
		PList<Developer> validListEmpty = PList.<Developer>empty();
		PList<Developer> validList = validListEmpty.plus(dev);
		PList<Developer> nullPointer = null;
		
		assertTrue(BugReport.isValidUserList(validListEmpty));
		assertTrue(BugReport.isValidUserList(validList));
		assertFalse(BugReport.isValidUserList(nullPointer));
	}
	
	@Test
	public void testAddUser() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
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
	public void testIsValidUser() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.isValidUser(dev));
		assertFalse(tempBugReport.isValidUser(null));
		tempBugReport.addUser(dev);
		assertFalse(tempBugReport.isValidUser(dev));
	}
	
	@Test
	public void testGetDependencies() throws IllegalArgumentException, PermissionException{
		BugReport tempBugReport = new BugReport(issuer, "bla", "bla", depList, subsystem);
		assertTrue(tempBugReport.getDependencies().isEmpty());
		
		PList<BugReport> newDepList = depList.plus(bugReport1);
		tempBugReport = new BugReport(issuer, "bla", "bla", newDepList, subsystem);
		assertEquals(tempBugReport.getDependencies(), newDepList);
	}
	
	@Test
	public void testIsValidDependencies(){
		PList<BugReport> validDepList = depList.plus(bugReport1);
		PList<BugReport> nullList = null;
		assertTrue(BugReport.isValidDependencies(depList));
		assertTrue(BugReport.isValidDependencies(validDepList));
		assertFalse(BugReport.isValidDependencies(nullList));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInValidDependencies() throws IllegalArgumentException, PermissionException{
		new BugReport(issuer, "bla", "ho", null, subsystem);
	}
	
	@Test
	public void testGetSubsystem(){
		assertEquals(bugReport1.getSubsystem(), subsystem);
	}
	
	@Test
	public void testIsValidSubsystem(){
		assertTrue(BugReport.isValidSubsystem(subsystem));
		assertFalse(BugReport.isValidSubsystem(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBugReportInValidSubsystem() throws IllegalArgumentException, PermissionException{
		new BugReport(issuer, "Hello", "World", depList, null);
	}

	@Test
	public void testCompareTo() throws PermissionException {

		BugReport lowerId = new BugReport(issuer, "lowerId", "This has the lower id", depList, subsystem);
		BugReport higherID = new BugReport(issuer, "higherId", "This has the higher id", depList, subsystem);

		assertTrue(lowerId.getUniqueID() < higherID.getUniqueID());

		assertEquals(lowerId.compareTo(higherID), 1);
		assertEquals(higherID.compareTo(lowerId), -1);

	}

	@Test
	public void testGetDetails() throws PermissionException {
        // For bugRep with empty depList
		long id = BugReport.getNewUniqueID();
		GregorianCalendar cal = new GregorianCalendar();
		BugReport bugRep = new BugReport(issuer, id, "This is a good title", "This is a good description", cal, depList, subsystem);

        // expected response :
		String response = "Bug report id: " + id;
		response += "\n creator: " + issuer.getFullName();
		response += "\n title: " + "This is a good title";
		response += "\n description: " + "This is a good description";
		response += "\n creation date: " + cal.getTime();
		response += "\n tag: " + bugRep.getTag().name();
		response += "\n dependencies: ";
		response += "\n subsystem: " + subsystem.getName();

		assertEquals(bugRep.getDetails(), response);

        // For bugRep with non empty depList
        long id2 = BugReport.getNewUniqueID();
        PList<BugReport> depList = PList.<BugReport>empty().plus(bugRep);
		BugReport bugRep2 = new BugReport(issuer, id2, "This is a better title", "This is a better description", cal, depList, subsystem);

        // new response:
        response = "Bug report id: " + id2;
        response += "\n creator: " + issuer.getFullName();
        response += "\n title: " + "This is a better title";
        response += "\n description: " + "This is a better description";
        response += "\n creation date: " + cal.getTime();
        response += "\n tag: " + bugRep.getTag().name();
        response += "\n dependencies: ";
        response += "\n \t id: " + id + ", title: " + "This is a good title";
        response += "\n subsystem: " + subsystem.getName();

        assertEquals(bugRep2.getDetails(), response);
	}

}
