package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.HealthAlgorithm;
import bugtrap03.bugdomain.HealthIndicator;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.ForkMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailbox;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class ProjectSubjectTest {
    
    private static Project testDummy;
    private static Developer subjectDummyDev;
    private static ForkMailbox subjectDummyFMB;
    private static Project subjectDummyProject;
    private static Subsystem subjectDummySubsystem;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        subjectDummyDev = new Developer("subjectDummyDev", "first", "last");
        testDummy = new Project("sdp", "sdp", subjectDummyDev, 1000);

        subjectDummyProject = new Project("sdp","sdp",subjectDummyDev,1000);
        subjectDummySubsystem = subjectDummyProject.addSubsystem("This seems","easy");
    }

    @Before
    public void setUp() throws Exception{
        subjectDummyFMB = subjectDummyDev.getMailbox().forkSubscribe(testDummy);
    }

//    private static Developer testDev;
//    private static Issuer testIss;
//    private static VersionID testVersion;
//    private static String testName;
//    private static String testDescription;
//    private static GregorianCalendar testStartDate;
//    private static GregorianCalendar testCreationDate;
//    private static long testBudget;
//    private static Project testProject;
//
//    private static VersionID subVersion;
//    private static String subName;
//    private static String subDescription;
//    private static Subsystem subSysTest;
//    private static Subsystem subSysTest2;
//
//    private static PList<BugReport> emptyDep;
//    private static PList<BugReport> depToRep1;
//    private static BugReport bugreport1;
//    private static BugReport bugreport2;
//
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//        testDev = new Developer("subsysTester3210", "KwintenAS", "BuytaertAS");
//        testIss = new Issuer("subsysTester3211", "KwintenAS", "BuytaertAS");
//        testVersion = new VersionID(1, 2, 4);
//        testName = "testProjAS";
//        testDescription = "This is an description of an AS project";
//        testStartDate = new GregorianCalendar(3016, 1, 1);
//        testCreationDate = new GregorianCalendar(2000, 12, 25);
//        testBudget = 1000;
//
//        subVersion = new VersionID(9, 8, 5);
//        subName = "testSubAS";
//        subDescription = "This is a test description of a as subsystem";
//
//        emptyDep = PList.<BugReport>empty();
//
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        testProject = new Project(testVersion, testName, testDescription, testCreationDate, testDev, testStartDate,
//                testBudget);
//        subSysTest = testProject.addSubsystem(subVersion, subName, subDescription);
//        subSysTest2 = subSysTest.addSubsystem("mehAS", "moehAS");
//        //bugreport1 = subSysTest.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", emptyDep);
//        bugreport1 = subSysTest.addBugReport(testDev, "testBug3AS", "this is description of testbug 3AS", new GregorianCalendar(), emptyDep, null, 1, false, null, null, null);
//        depToRep1 = PList.<BugReport>empty().plus(bugreport1);
//        //        bugreport2 = subSysTest2.addBugReport(testDev, "otherBug4AS", "i like bonobos", depToRep1);
//        bugreport2 = subSysTest2.addBugReport(testDev, "otherBug4AS", "i like bonobos", new GregorianCalendar(), depToRep1, null, 1, false, null, null, null);
//    }
    
    @Test
    public void testNotifyForkSubs() throws Exception {
        assertTrue(subjectDummyFMB.getNotifications().isEmpty());
        testDummy.notifyForkSubs(subjectDummyProject);
        assertFalse(subjectDummyFMB.getNotifications().isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddCreationSubs_Invalid() {
        ForkMailbox box1 = new ForkMailbox(subjectDummyProject);
        ForkMailbox box2 = null;

        ArrayList<ForkMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addForkSub(list);
    }
    
    @Test
    public void testAddCreationSubs() {
        ForkMailbox box1 = new ForkMailbox(subjectDummyProject);
        ForkMailbox box2 = new ForkMailbox(subjectDummyProject);

        ArrayList<ForkMailbox> list = new ArrayList<>();
        list.add(box1);
        list.add(box2);

        testDummy.addForkSub(list);
    }
    
    @Test
    public void testGetMemento() {
        ProjectSubjectDummy dummy = new ProjectSubjectDummy(subjectDummyProject, "blob", "blib");
        ProjectSubjectMemento mem = dummy.getMemento();
        
        assertTrue(dummy.getVersionIDSubs().equals(mem.getVersionIDSubs()));
        assertTrue(dummy.getTagSubs().equals(mem.getTagSubs()));
        assertTrue(dummy.getMilestoneSubs().equals(mem.getMilestoneSubs()));
        assertTrue(dummy.getCreationSubs().equals(mem.getCreationSubs()));
        assertTrue(dummy.getCommentSubs().equals(mem.getCommentSubs()));
        assertTrue(dummy.getForkSubs().equals(mem.getForkSubs()));
                
        assertEquals(dummy.getVersionID(), mem.getVersionID());
        assertEquals(dummy.getName(), mem.getName());
        assertEquals(dummy.getDescription(), mem.getDescription());
        assertEquals(dummy.getSubsystems(), mem.getChildren());
        //assertEquals(testDummy.getParent(), mem.getParent());
        assertEquals(dummy.isTerminated(), mem.getIsTerminated());
        assertEquals(dummy.getMilestone(), mem.getMilestone());
    }
    
    
    
    class ProjectSubjectDummy extends ProjectSubject {

        public ProjectSubjectDummy(AbstractSystem parent, String name, String description) throws IllegalArgumentException {
            super(parent, name, description);
        }

        @Override
        public double getBugImpact() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDetails() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public HealthIndicator getIndicator(HealthAlgorithm ha) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getSubjectName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

}
