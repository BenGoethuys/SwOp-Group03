package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

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
    static Milestone milestone;
    static String trigger;
    static String stacktrace;
    static String error;
    static Project project;
    static Subsystem subsystem;
    static Administrator admin;
    static long id1;
    static long id2;
    static String test;
    static String patch;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        date = new GregorianCalendar();
        issuer = new Issuer("blaDitGebruiktNiemandAnders", "bla", "bla");
        dev = new Developer("booDitGebruiktNiemandAnders", "Jan", "Smidt");
        lead = new Developer("ditGebruiktNiemandAnders", "Jan", "Smidt");
        programer = new Developer("ditGebruiktNiemandAnders2", "Jos", "Smidt");
        tester = new Developer("ditGebruiktNiemandAnders3", "Jantje", "Smidt");
        depList = PList.<BugReport>empty();
        milestone = new Milestone(2,3);
        trigger = "The trigger for repoducing this bug";
        stacktrace = "The stacktrace of this bug";
        error = "The error of this bug";

        test = "This is a test";
        patch = "This is a patch";

        admin = new Administrator("ditGebruiktNiemandAnders4", "bla", "hihi");

        project = new Project("ANewProject", "the description of the project", lead, 0);
        project.setRole(lead, programer, Role.PROGRAMMER);
        project.setRole(lead, tester, Role.TESTER);
        subsystem = new Subsystem("ANewSubSystem", "the decription of the subsystem", project);

        bugReport1 = new BugReport(issuer, "NastyBug", "bla bla", new GregorianCalendar(), depList, subsystem, milestone, false, "", "", "");
        bugReport2 = new BugReport(issuer, "FoundBug", "", date, depList, subsystem, null, true, trigger, stacktrace, error);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetUniqueID() {
        assertEquals(id1, bugReport1.getUniqueID());
        assertEquals(id2, bugReport2.getUniqueID());
    }

    @Test
    public void testGetNewUniqueID() throws IllegalArgumentException, PermissionException {
        long id = BugReport.getNewUniqueID();
        BugReport tempBugReport = new BugReport(issuer, "bla bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getUniqueID() == id);

        long id2 = BugReport.getNewUniqueID();
        assertTrue(BugReport.getNewUniqueID() == id2);
        assertTrue(BugReport.getNewUniqueID() != id);
    }

    @Test
    public void testIsValidUniqueID() {
        assertFalse(BugReport.isValidUniqueID(id1));
        assertFalse(BugReport.isValidUniqueID(id2));

        assertTrue(BugReport.isValidUniqueID(BugReport.getNewUniqueID()));

        assertFalse(BugReport.isValidUniqueID(-1));
    }

    @Test
    public void testGetTitle() {
        assertEquals("NastyBug", bugReport1.getTitle());
        assertEquals("FoundBug", bugReport2.getTitle());
    }

    @Test
    public void testSetTitle() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.setTitle("NewTitle");
        assertEquals("NewTitle", tempBugReport.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
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
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.setDescription("NewDescription");
        assertEquals("NewDescription", tempBugReport.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
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
        assertFalse(bugReport1.getCreationDate() == null);

        assertEquals(date, bugReport2.getCreationDate());
    }

    @Test
    public void testIsValidCreationDate() {
        assertTrue(BugReport.isValidCreationDate(date));

        assertFalse(BugReport.isValidCreationDate(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBugReportInvalidCreationDate() throws IllegalArgumentException, PermissionException {
        new BugReport(issuer, "Bla", "boo", null, depList, subsystem, null, false, null, null, null);
    }

    @Test
    public void testGetTag() {
        assertEquals(Tag.NEW, bugReport1.getTag());

        assertEquals(Tag.NEW, bugReport2.getTag());
    }

    @Test
    public void testSetTag() throws IllegalArgumentException, PermissionException {
        // Test for assigned
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertEquals(Tag.NEW, tempBugReport.getTag());
        tempBugReport.addUser(lead, dev);
        assertEquals(Tag.ASSIGNED, tempBugReport.getTag());
        assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
        tempBugReport.setTag(Tag.NOT_A_BUG, lead);
        assertEquals(Tag.NOT_A_BUG, tempBugReport.getTag());

        // Test for assigned with test
        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        assertEquals(Tag.ASSIGNED, tempBugReport.getTag());
        assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
        tempBugReport.setTag(Tag.NOT_A_BUG, lead);
        assertEquals(Tag.NOT_A_BUG, tempBugReport.getTag());

        // Test for under review
        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        assertEquals(Tag.UNDER_REVIEW, tempBugReport.getTag());
        tempBugReport.setTag(Tag.ASSIGNED, lead);

        // Test for under review
        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        assertEquals(Tag.UNDER_REVIEW, tempBugReport.getTag());
        tempBugReport.setTag(Tag.NOT_A_BUG, lead);

        // TODO
    }

    @Test(expected = PermissionException.class)
    public void testSetTagInvalid() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.setTag(Tag.NOT_A_BUG, issuer);
    }

    @Test(expected = PermissionException.class)
    public void testSetTagInvalidNoPermission() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.setTag(Tag.NOT_A_BUG, dev);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidTagNullPRogrammer() throws IllegalArgumentException, PermissionException {
        bugReport1.setTag(null, programer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidTagNullTester() throws IllegalArgumentException, PermissionException {
        bugReport1.setTag(null, tester);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidTagNullUser() throws IllegalArgumentException, PermissionException {
        bugReport1.setTag(Tag.NOT_A_BUG, null);
    }

    @Test
    public void testIsValidTag() throws PermissionException {

        //FIXME move to state classes

        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertFalse(tempBugReport.isValidTag(null));

        // For bugReport with Tag.NEW
        assertFalse(tempBugReport.isValidTag(Tag.NEW));
        assertFalse(tempBugReport.isValidTag(Tag.CLOSED));
        assertFalse(tempBugReport.isValidTag(Tag.DUPLICATE));
        assertTrue(tempBugReport.isValidTag(Tag.NOT_A_BUG));
        assertFalse(tempBugReport.isValidTag(Tag.ASSIGNED));
    }

    @Test
    public void testGetCommentList() throws PermissionException {
        assertTrue(bugReport1.getCommentList().isEmpty());
        Comment comment = new Comment(issuer, "Bla bla bla");
        bugReport1.addComment(comment);
        assertTrue(bugReport1.getCommentList().contains(comment));
    }

    @Test
    public void testGetAllComments() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "hihi", date, depList, subsystem, null, false, null, null, null);
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
    public void testAddComment() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getCommentList().isEmpty());
        Comment comment = new Comment(issuer, "Bla bla bla");
        tempBugReport.addComment(comment);
        assertTrue(tempBugReport.getCommentList().contains(comment));

        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getCommentList().isEmpty());
        Comment returnComment = tempBugReport.addComment(issuer, "Bla");
        assertFalse(tempBugReport.getCommentList().isEmpty());
        assertTrue(tempBugReport.getCommentList().getFirst().getCreator() == issuer);
        assertTrue(tempBugReport.getCommentList().getFirst().getText() == "Bla");
        assertEquals(tempBugReport.getCommentList().getFirst(), returnComment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidComment() {
        bugReport1.addComment(null);
    }

    @Test(expected = PermissionException.class)
    public void testAddCommentNoPermission() throws PermissionException {
        bugReport1.addComment(admin, "This is a comment");
    }

    @Test
    public void testIsValidComment() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        Comment comment = new Comment(issuer, "Bla bla bla");
        assertTrue(tempBugReport.isValidComment(comment));
        assertFalse(tempBugReport.isValidComment(null));
        tempBugReport.addComment(comment);
        assertFalse(tempBugReport.isValidComment(comment));
    }

    @Test
    public void testGetCreator() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getCreator() == issuer);
    }

    @Test
    public void testIsValidCreator() {
        assertTrue(BugReport.isValidCreator(issuer));
        assertFalse(BugReport.isValidCreator(null));
        assertFalse(BugReport.isValidCreator(admin));
        assertTrue(BugReport.isValidCreator(dev));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBugReportInvalidCreator() throws IllegalArgumentException, PermissionException {
        new BugReport(null, "Bla", "boo", date, depList, subsystem, null, false, null, null, null);
    }

    @Test(expected = PermissionException.class)
    public void testBugReportCreatorNoPermission() throws IllegalArgumentException, PermissionException {
        new BugReport(admin, "Bla", "boo", date, depList, subsystem, null, false, null, null, null);
    }

    @Test
    public void testGetUserList() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getUserList().isEmpty());

        tempBugReport.addUser(dev);
        assertTrue(tempBugReport.getUserList().contains(dev));
    }

    @Test
    public void testAddUser() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getUserList().isEmpty());
        assertTrue(tempBugReport.getTag() == Tag.NEW);
        tempBugReport.addUser(lead, dev);
        assertTrue(tempBugReport.getUserList().contains(dev));
        assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);

        Developer temp = new Developer("barDitGebruiktNiemandAnders", "bla", "la");
        tempBugReport.addUser(temp);
        assertTrue(tempBugReport.getUserList().contains(dev));
        assertTrue(tempBugReport.getUserList().contains(temp));
        assertTrue(tempBugReport.getTag() == Tag.ASSIGNED);

        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertFalse(tempBugReport.getUserList().contains(dev));
        tempBugReport.addUser(lead, dev);
        assertTrue(tempBugReport.getUserList().contains(dev));

        tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertFalse(tempBugReport.getUserList().contains(dev));
        tempBugReport.addUser(tester, dev);
        assertTrue(tempBugReport.getUserList().contains(dev));
    }

    @Test(expected = PermissionException.class)
    public void testAddUserNoPermProg() throws PermissionException {
        bugReport1.addUser(programer, dev);
    }

    @Test(expected = PermissionException.class)
    public void testAddUserNoPermIssuer() throws PermissionException {
        bugReport1.addUser(issuer, dev);
    }

    @Test(expected = PermissionException.class)
    public void testAddUserNoPermAdmin() throws PermissionException {
        bugReport1.addUser(admin, dev);
    }

    @Test(expected = PermissionException.class)
    public void testAddUserInvalidUser() throws PermissionException {
        bugReport1.addUser(null, dev);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserInvalidDev() throws PermissionException {
        bugReport1.addUser(lead, null);
    }

    @Test
    public void testIsValidUser() throws IllegalArgumentException, PermissionException {
        assertTrue(BugReport.isValidUser(dev));
        assertFalse(BugReport.isValidUser(null));
    }

    @Test
    public void testGetDependencies() throws IllegalArgumentException, PermissionException {
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getDependencies().isEmpty());

        PList<BugReport> newDepList = depList.plus(bugReport1);
        tempBugReport = new BugReport(issuer, "bla", "bla", date, newDepList, subsystem, null, false, null, null, null);
        assertEquals(tempBugReport.getDependencies(), newDepList);
    }

    @Test
    public void testIsValidDependencies() {
        PList<BugReport> validDepList = depList.plus(bugReport1);
        PList<BugReport> nullList = null;
        assertTrue(BugReport.isValidDependencies(depList));
        assertTrue(BugReport.isValidDependencies(validDepList));
        assertFalse(BugReport.isValidDependencies(nullList));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBugReportInValidDependencies() throws IllegalArgumentException, PermissionException {
        new BugReport(issuer, "bla", "ho", date, null, subsystem, null, false, null, null, null);
    }
    
    @Test
    public void testHasUnresolvedDependencies() throws IllegalArgumentException, PermissionException{
    	assertFalse(bugReport1.hasUnresolvedDependencies());
    	
    	// make bugReport with unresolved dependency
    	BugReport tempBugReportDep = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	PList<BugReport> newDepList = PList.<BugReport>empty().plus(tempBugReportDep);
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, newDepList, subsystem, null, false, null, null, null);
    	assertTrue(tempBugReport.hasUnresolvedDependencies());
    	
    	// resolve the dependency
    	tempBugReportDep.addUser(lead, dev);
    	tempBugReportDep.addTest(tester, test);
    	tempBugReportDep.addPatch(programer, patch);
    	tempBugReportDep.selectPatch(lead, patch);
    	tempBugReportDep.giveScore(issuer, 4);
    	assertFalse(bugReport1.hasUnresolvedDependencies());
    }

    @Test
    public void testGetSubsystem() {
        assertEquals(bugReport1.getSubsystem(), subsystem);
    }

    @Test
    public void testIsValidSubsystem() {
        assertTrue(BugReport.isValidSubsystem(subsystem));
        assertFalse(BugReport.isValidSubsystem(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBugReportInValidSubsystem() throws IllegalArgumentException, PermissionException {
        new BugReport(issuer, "Hello", "World", date, depList, null, null, false, null, null, null);
    }
    
    @Test
    public void testGetMilestone(){
    	assertEquals(milestone, bugReport1.getMilestone());
    	assertEquals(null, bugReport2.getMilestone());
    }
    
    @Test
    public void testSetMilestone() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	assertEquals(null, tempBugReport.getMilestone());
    	tempBugReport.setMilestone(milestone);
    	assertEquals(milestone, tempBugReport.getMilestone());
    	tempBugReport.setMilestone(null);
    	assertEquals(null, tempBugReport.getMilestone());
    }
    
    @Test //(expected = IllegalArgumentException.class)
    public void testSetInvalidMilestone() throws IllegalArgumentException {
    	// TODO: when milestones are fixed !
    	fail("need to implement when milestone is fixed");
    }
    
    @Test
    public void testIsValidMilestone(){
    	assertTrue(bugReport1.isValidMilestone(null));
    	//TODO: when milestones are fixed !
    	fail("need to implement when milestone is fixed");
    }
    
    @Test
    public void testIsPrivate(){
    	assertFalse(bugReport1.isPrivate());
    	assertTrue(bugReport2.isPrivate());
    }
    
    @Test
    public void testSetPrivate() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	assertEquals(false, tempBugReport.isPrivate());
    	tempBugReport.setPrivate(true);
    	assertEquals(true, tempBugReport.isPrivate());
    }
    
    @Test
    public void testGetTrigger(){
    	assertEquals("", bugReport1.getTrigger());
    	assertEquals(trigger, bugReport2.getTrigger());
    }
    
    @Test
    public void testSetTrigger() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	assertEquals("", tempBugReport.getTrigger());
    	tempBugReport.setTrigger(issuer, trigger);
    	assertEquals(trigger, tempBugReport.getTrigger());
    }
    
    @Test (expected = PermissionException.class)
    public void testSetTriggerNoPermission() throws PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	tempBugReport.setTrigger(programer, trigger);
    }
    
    @Test
    public void testGetStackTrace(){
    	assertEquals("", bugReport1.getStacktrace());
    	assertEquals(stacktrace, bugReport2.getStacktrace());
    }
    
    @Test
    public void testSetStackTrace() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	assertEquals("", tempBugReport.getStacktrace());
    	tempBugReport.setStacktrace(issuer, stacktrace);
    	assertEquals(stacktrace, tempBugReport.getStacktrace());
    }
    
    @Test (expected = PermissionException.class)
    public void testSetStackTraceNoPermission() throws PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	tempBugReport.setStacktrace(programer, stacktrace);
    }
    
    @Test
    public void testGetError(){
    	assertEquals("", bugReport1.getError());
    	assertEquals(error, bugReport2.getError());
    }
    
    @Test
    public void testSetError() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	assertEquals("", tempBugReport.getError());
    	tempBugReport.setError(issuer, error);
    	assertEquals(error, tempBugReport.getError());
    }
    
    @Test (expected = PermissionException.class)
    public void testSetErrorNoPermission() throws PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	tempBugReport.setError(programer, error);
    }

    @Test
    public void testCompareTo() throws PermissionException {

        BugReport lowerId = new BugReport(issuer, "lowerId", "This has the lower id", date, depList, subsystem, null, false, null, null, null);
        BugReport higherID = new BugReport(issuer, "higherId", "This has the higher id", date, depList, subsystem, null, false, null, null, null);

        assertTrue(lowerId.getUniqueID() < higherID.getUniqueID());

        assertEquals(lowerId.compareTo(higherID), 1);
        assertEquals(higherID.compareTo(lowerId), -1);

    }

    @Test
    public void testGetDetails() throws PermissionException {
        // For bugRep with empty depList
    	PList<BugReport> deps = PList.<BugReport>empty().plus(bugReport1);
        BugReport bugRep = new BugReport(issuer, "This is a good title", "This is a good description", date, deps, subsystem, milestone, true, trigger, stacktrace, error);
        bugRep.addComment(programer, "This is a comment");
        String details = bugRep.getDetails();
        
        // for id:
        String expected = "Bug report id: " + bugRep.getUniqueID();
        assertTrue(details.contains(expected));
        
        // for creator:
        expected = "creator: " + issuer.getFullName();
        assertTrue(details.contains(expected));
        
        // for title:
        expected = "title: " + "This is a good title";
        assertTrue(details.contains(expected));
        
        // for description:
        expected = "description: " + "This is a good description";
        assertTrue(details.contains(expected));
        
        // for creation date:
        expected = "creation date: " + date.getTime();
        assertTrue(details.contains(expected));
        
        //for private:
        expected = "is private: " + true;
        assertTrue(details.contains(expected));
        
        // for milestone:
        expected = "target milestone: " + milestone.toString();
        assertTrue(details.contains(expected));
        
        // for subsystem
        expected = "subsystem: " + subsystem.getName();
        assertTrue(details.contains(expected));
        
        // for comment:
        expected = "comments: " + "\n \t 1. " + "This is a comment";
        assertTrue(details.contains(expected));
        
        // for dependencies:
        expected = "dependencies: " + "\n \t id: " + bugReport1.getUniqueID() + ", title: "+ bugReport1.getTitle();
        assertTrue(details.contains(expected));
        
        // for trigger:
        expected = "trigger: " + trigger;
        assertTrue(details.contains(expected));
        
        // for stacktrace:
        expected = "stacktrace: " + stacktrace;
        assertTrue(details.contains(expected));
        
        // for error:
        expected = "error: " + error;
        assertTrue(details.contains(expected));
        
        bugRep = new BugReport(issuer, "This is a good title", "This is a good description", date, deps, subsystem, null, true, null, null, null);
        details = bugRep.getDetails();
        
        // for milestone:
        expected = "target milestone: " + "This bugreport has no milstone";
        assertTrue(details.contains(expected));
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSetInternalState() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, "", "", "");
    	tempBugReport.setInternState(null);
    }
    
    @Test
    public void testAddTest() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        assertTrue(tempBugReport.getTests().isEmpty());
        tempBugReport.addTest(tester, test);
        assertTrue(tempBugReport.getTests().contains(test));
    }
    
    @Test (expected = PermissionException.class)
    public void testAddTestNoPErmission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(programer, test);
    }
    
    @Test (expected = PermissionException.class)
    public void testAddTestNoPermission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(null, test);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testAddInvalidTest() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, null);
    }
    
    @Test
    public void testGetTests() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	assertTrue(tempBugReport.getTests().isEmpty());
    	tempBugReport.addUser(lead, dev);
    	assertTrue(tempBugReport.getTests().isEmpty());
    	tempBugReport.addTest(tester, test);
        assertTrue(tempBugReport.getTests().contains(test));
    }
    
    @Test
    public void testIsvalidTest(){
    	assertTrue(BugReport.isValidTest(test));
    	assertFalse(BugReport.isValidTest(null));
    	assertFalse(BugReport.isValidTest(""));
    }
    
    @Test
    public void testAddPatch() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        assertTrue(tempBugReport.getPatches().isEmpty());
        tempBugReport.addPatch(programer, patch);
        assertTrue(tempBugReport.getPatches().contains(patch));
    }
    
    @Test (expected = PermissionException.class)
    public void testAddPatchNoPErmission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(tester, patch);
    }
    
    @Test (expected = PermissionException.class)
    public void testAddPatchNoPermission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(null, patch);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testAddInvalidPatch() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, null);
    }
    
    @Test
    public void testGetPatches() throws IllegalArgumentException, PermissionException{
        BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        assertTrue(tempBugReport.getPatches().isEmpty());
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        assertTrue(tempBugReport.getPatches().isEmpty());
        tempBugReport.addPatch(programer, patch);
        assertTrue(tempBugReport.getPatches().contains(patch));
    }
    
    @Test
    public void testIsvalidPatch(){
    	assertTrue(BugReport.isValidPatch(patch));
    	assertFalse(BugReport.isValidPatch(null));
    	assertFalse(BugReport.isValidPatch(""));
    }
    
    @Test
    public void testSelectPatch() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        
        tempBugReport.selectPatch(lead, patch);
        assertEquals(patch, tempBugReport.getSelectedPatch());
    }
    
    @Test (expected = PermissionException.class)
    public void testSelectPatchNoPErmission() throws IllegalStateException, IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        
        tempBugReport.selectPatch(tester, patch);
    }
    
    @Test (expected = PermissionException.class)
    public void testSelectPatchNoPermission() throws IllegalStateException, IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
        tempBugReport.addUser(lead, dev);
        tempBugReport.addTest(tester, test);
        tempBugReport.addPatch(programer, patch);
        
        tempBugReport.selectPatch(null, patch);
    }
    
    @Test
    public void testGiveScore() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.addUser(lead, dev);
    	tempBugReport.addTest(tester, test);
    	tempBugReport.addPatch(programer, patch);
    	tempBugReport.selectPatch(lead, patch);
    	tempBugReport.giveScore(issuer, 4);
    }
    
    @Test (expected = PermissionException.class)
    public void testGiveScoreNoPermission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.addUser(lead, dev);
    	tempBugReport.addTest(tester, test);
    	tempBugReport.addPatch(programer, patch);
    	tempBugReport.selectPatch(lead, patch);
    	tempBugReport.giveScore(programer, 4);
    }
    
    @Test
    public void testGetScore() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.addUser(lead, dev);
    	tempBugReport.addTest(tester, test);
    	tempBugReport.addPatch(programer, patch);
    	tempBugReport.selectPatch(lead, patch);
    	tempBugReport.giveScore(issuer, 4);
    	
    	assertEquals(4, tempBugReport.getScore());
    }
    
    @Test
    public void testSetDuplicate() throws IllegalStateException, IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.setDuplicate(lead, bugReport1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSetDuplicateInvalidDup() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.setDuplicate(lead, tempBugReport);
    }
    
    @Test (expected = PermissionException.class)
    public void testSetDuplicateNoPermission() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.setDuplicate(issuer, bugReport1);
    }
    
    @Test (expected = PermissionException.class)
    public void testSetDuplicateNoPermissionNull() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.setDuplicate(null, bugReport1);
    }
    
    @Test
    public void testGetDuplicate() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	tempBugReport.setDuplicate(lead, bugReport1);
    	assertEquals(bugReport1, tempBugReport.getDuplicate());
    }
    
    @Test
    public void testIsValidDuplicate() throws IllegalArgumentException, PermissionException{
    	BugReport tempBugReport = new BugReport(issuer, "bla", "bla", date, depList, subsystem, null, false, null, null, null);
    	assertTrue(tempBugReport.isValidDuplicate(bugReport1));
    	assertFalse(tempBugReport.isValidDuplicate(null));
    	assertFalse(tempBugReport.isValidDuplicate(tempBugReport));
    }

}
