package bugtrap03.bugdomain;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.SubjectMemento;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.VersionIDMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class AbstractSystemTest {

    private static Developer testDev;
    private static Issuer testIss;
    private static VersionID testVersion;
    private static String testName;
    private static String testDescription;
    private static GregorianCalendar testStartDate;
    private static GregorianCalendar testCreationDate;
    private static long testBudget;
    private static Project testProject;

    private static VersionID subVersion;
    private static String subName;
    private static String subDescription;
    private static Subsystem subSysTest;
    private static Subsystem subSysTest2;

    private static PList<BugReport> emptyDep;
    private static PList<BugReport> depToRep1;
    private static BugReport bugreport1;
    private static BugReport bugreport2;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testDev = new Developer("subsysTester3210", "KwintenAS", "BuytaertAS");
        testIss = new Issuer("subsysTester3211", "KwintenAS", "BuytaertAS");
        testVersion = new VersionID(1, 2, 4);
        testName = "testProjAS";
        testDescription = "This is an description of an AS project";
        testStartDate = new GregorianCalendar(3016, 1, 1);
        testCreationDate = new GregorianCalendar(2000, 12, 25);
        testBudget = 1000;

        subVersion = new VersionID(9, 8, 5);
        subName = "testSubAS";
        subDescription = "This is a test description of a as subsystem";

        emptyDep = PList.<BugReport>empty();

    }

    @Before
    public void setUp() throws Exception {
        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate,
                testBudget);
        subSysTest = testProject.addSubsystem(subVersion, subName, subDescription);
        subSysTest2 = subSysTest.addSubsystem("mehAS", "moehAS");
        //bugreport1 = subSysTest.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", emptyDep);
        bugreport1 = subSysTest.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", new GregorianCalendar(), emptyDep, null, 1, false, null, null, null);
        depToRep1 = PList.<BugReport>empty().plus(bugreport1);
        //        bugreport2 = subSysTest2.addBugReport(testDev, "otherBug4AS", "i like bonobos", depToRep1);
        bugreport2 = subSysTest2.addBugReport(testDev, "otherBug4AS", "i like bonobos", new GregorianCalendar(), depToRep1, null, 1, false, null, null, null);
    }

    @Test
    public void testGetVersionID() {
        assertEquals(new VersionID(), subSysTest2.getVersionID());
        assertEquals(subVersion, subSysTest.getVersionID());
        assertEquals(testVersion, testProject.getVersionID());
    }

    @Test
    public void testSetVersionID() {
        testProject.setVersionID(subVersion);
        subSysTest.setVersionID(testVersion);
        assertEquals(testVersion, subSysTest.getVersionID());
        assertEquals(subVersion, testProject.getVersionID());
    }

    @Test
    public void isValidVersionID() {
        assertTrue(AbstractSystem.isValidVersionId(new VersionID(0, 0, 2)));
        assertTrue(AbstractSystem.isValidVersionId(new VersionID()));
        assertFalse(AbstractSystem.isValidVersionId(null));
    }

    @Test
    public void isValidMilestone() throws PermissionException {
        Project project = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate,
                testBudget);
        Milestone projMil = new Milestone(2, 4);
        project.setMilestone(projMil);
        Subsystem subsystem = project.addSubsystem("Subsys 1", "Description subsys 1");
        subsystem.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", new GregorianCalendar(),
                emptyDep, null, 1, false, null, null, null);
        subsystem.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", new GregorianCalendar(),
                emptyDep, new Milestone(5, 6), 1, false, null, null, null);

        assertFalse(subsystem.isValidMilestone(null));
        assertTrue(subsystem.isValidMilestone(new Milestone(3)));
        assertFalse(subsystem.isValidMilestone(new Milestone(6)));

    }

    @Test
    public void isValidParent() {
        Project project = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate,
                testBudget);
        project.setTerminated(true);
        assertFalse(subSysTest.isValidParent(null));
        assertFalse(subSysTest.isValidParent(project));
        assertTrue(subSysTest.isValidParent(testProject));
    }

    @Test
    public void testGetName() {
        assertEquals(subName, subSysTest.getName());
        assertEquals(testName, testProject.getName());
    }

    @Test
    public void testSetName() {
        testProject.setName(subName);
        subSysTest.setName(testName);
        assertEquals(testName, subSysTest.getName());
        assertEquals(subName, testProject.getName());
    }

    @Test
    public void testIsValidName() {
        assertFalse(AbstractSystem.isValidName(""));
        assertFalse(AbstractSystem.isValidName(null));
        assertTrue(AbstractSystem.isValidName(subName));
        assertTrue(AbstractSystem.isValidName(testName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidName() {
        testProject.setName("");
    }

    @Test
    public void testGetDescription() {
        assertEquals(testDescription, testProject.getDescription());
        assertEquals(subDescription, subSysTest.getDescription());
    }

    @Test
    public void testSetDescription() {
        testProject.setDescription(subDescription);
        subSysTest.setDescription(testDescription);
        assertEquals(subDescription, testProject.getDescription());
        assertEquals(testDescription, subSysTest.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidProjDescription() {
        testProject.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidSubsDescription() {
        subSysTest.setDescription("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjDescription() {
        testProject.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsDescription() {
        subSysTest.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProjVersionID() {
        testProject.setVersionID(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullSubsVersionID() {
        subSysTest.setVersionID(null);
    }

    @Test
    public void testIsValidDescription() {
        assertFalse(AbstractSystem.isValidDescription(""));
        assertFalse(AbstractSystem.isValidDescription(null));
        assertTrue(AbstractSystem.isValidDescription(testDescription));
    }

    @Test
    public void testGetChilds() {
        assertEquals(PList.<Subsystem>empty().plus(subSysTest), testProject.getSubsystems());
        assertEquals(PList.<Subsystem>empty().plus(subSysTest2), subSysTest.getSubsystems());
        assertEquals(PList.<Subsystem>empty(), subSysTest2.getSubsystems());
    }

    @Test
    public void testMakeSubsystemChildVersionIDStringString() {
        VersionID extraVersion = new VersionID(5, 3, 7);
        String extraName = "extra naam";
        String extraDes = "extra des";
        Subsystem extraSubsys = testProject.addSubsystem(extraVersion, extraName, extraDes);
        assertEquals(extraVersion, extraSubsys.getVersionID());
        assertEquals(extraName, extraSubsys.getName());
        assertEquals(extraDes, extraSubsys.getDescription());
        assertTrue(testProject.getSubsystems().contains(extraSubsys));
    }

    @Test
    public void testMakeSubsystemChildStringString() {
        String extraName = "extra naam2";
        String extraDes = "extra des2";
        Subsystem extraSubsys = testProject.addSubsystem(extraName, extraDes);
        assertEquals(new VersionID(), extraSubsys.getVersionID());
        assertEquals(extraName, extraSubsys.getName());
        assertEquals(extraDes, extraSubsys.getDescription());
        assertTrue(testProject.getSubsystems().contains(extraSubsys));
    }

    @Test
    public void testGetParentProject() {
        assertEquals(testProject, subSysTest.getParentProject());
        assertEquals(testProject, subSysTest2.getParentProject());
        assertEquals(testProject, testProject.getParentProject());
    }

    @Test
    public void testGetAllBugReports() {
        PList<BugReport> buglist = PList.<BugReport>empty().plus(bugreport2);
        assertEquals(buglist, subSysTest2.getAllBugReports());
        buglist = buglist.plus(bugreport1);
        assertEquals(buglist, subSysTest.getAllBugReports());
        assertEquals(buglist, testProject.getAllBugReports());
    }

    @Test
    public void testGetAllSubsystems() {
        PList<Subsystem> sublist = PList.<Subsystem>empty();
        assertEquals(sublist, subSysTest2.getAllSubsystems());
        sublist = sublist.plus(subSysTest2);
        assertEquals(sublist, subSysTest.getAllSubsystems());
        PList<Subsystem> sublist2 = PList.<Subsystem>empty().plus(subSysTest).plus(subSysTest2);
        assertEquals(sublist2, testProject.getAllSubsystems());
    }

    @Test
    public void testGetMilestone() {
        Milestone milestone = new Milestone(0);
        assertEquals(testProject.getMilestone(), milestone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidMilestone() {
        testProject.setMilestone(null);
    }

    @Test
    public void testSetMilestone() {
        testProject.addSubsystem("Another test subsystem", "the description");

        Milestone newM = new Milestone(10, 5, 3);
        testProject.setMilestone(newM);
        assertEquals(newM, testProject.getMilestone());

        for (Subsystem subsystem : testProject.getSubsystems()) {
            assertEquals(newM, subsystem.getMilestone());
        }
    }

    @Test
    public void testGetMemento() {
        AbstractSystemMemento mem = testProject.getMemento();

        assertEquals(testProject.getVersionID(), mem.getVersionID());
        assertEquals(testProject.getName(), mem.getName());
        assertEquals(testProject.getDescription(), mem.getDescription());
        assertEquals(testProject.getSubsystems(), mem.getChildren());
        assertEquals(testProject.getParent(), mem.getParent());
        assertEquals(testProject.isTerminated(), mem.getIsTerminated());
        assertEquals(testProject.getMilestone(), mem.getMilestone());
        
        //subs
        assertEquals(testProject.getCommentSubs(), mem.getCommentSubs());
        assertEquals(testProject.getCreationSubs(), mem.getCreationSubs());
        assertEquals(testProject.getTagSubs(), mem.getTagSubs());
        assertEquals(testProject.getVersionIDSubs(), mem.getVersionIDSubs());
        assertEquals(testProject.getMilestoneSubs(), mem.getMilestoneSubs());
    }

    @Test
    public void testSetMemento() {
        //Store 
        VersionID oldVersionID = testProject.getVersionID();
        String oldName = testProject.getName();
        String oldDesc = testProject.getDescription();
        PList<Subsystem> oldChildren = testProject.getSubsystems();
        AbstractSystem oldParent = testProject.getParent();
        boolean oldIsTerminated = testProject.isTerminated();
        Milestone oldMilestone = testProject.getMilestone();
        PList<CommentMailBox> oldCommentSubs = testProject.getCommentSubs();
        PList<CreationMailBox> oldCreationSubs = testProject.getCreationSubs();
        PList<TagMailBox> oldTagSubs = testProject.getTagSubs();
        PList<VersionIDMailbox> oldVersionIDSubs = testProject.getVersionIDSubs();
        testProject.getMilestoneSubs();
        
        String oldSubName = subSysTest.getName();
        
        
        

        AbstractSystemMemento mem = testProject.getMemento();

        // Change
        testProject.setDescription("New description here");
        testProject.setName("New name here");
        testProject.addSubsystem("SubNew", "SubNewDesc");
        testProject.setVersionID(new VersionID(5, 2, 1));
        testProject.setMilestone(new Milestone(3, 2, 1));
        subSysTest.setName("Blub new subsys name.");

        //Revert
        testProject.setMemento(mem);

        assertEquals(oldVersionID, testProject.getVersionID());
        assertEquals(oldName, testProject.getName());
        assertEquals(oldDesc, testProject.getDescription());
        assertEquals(oldChildren, testProject.getSubsystems());
        assertEquals(oldParent, testProject.getParent());
        assertEquals(oldIsTerminated, testProject.isTerminated());
        assertEquals(oldMilestone, testProject.getMilestone());
        assertEquals(oldSubName, subSysTest.getName());
    }

    @Test
    public void testSetMemento_SubjectMemento() {
        PList<CommentMailBox> oldCSubs = subSysTest.getCommentSubs();
        //Store 
        SubjectMemento mem = subSysTest.getMemento();

        // Change
        testProject.addCommentSub(new CommentMailBox(testProject));

        //Revert
        testProject.setMemento(mem);
        
        //TODO: Vincent Test the getCommentSubs;
        //assertEquals()
    }
    
    @Test(expected=PermissionException.class)
    public void testSetMilestoneNoPermission() throws PermissionException {
	testProject.setMilestone(testIss, new Milestone(0));
    }
    
    @Test
    public void testHasPermission() throws IllegalArgumentException, PermissionException {
	testProject.setRole(testDev, testDev, Role.TESTER);
	assertFalse(subSysTest.hasPermission(testDev, RolePerm.SPECIAL));
	assertTrue(subSysTest.hasPermission(testDev, RolePerm.ADD_TEST));
    }

}
