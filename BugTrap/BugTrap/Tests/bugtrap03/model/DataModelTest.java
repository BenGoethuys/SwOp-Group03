package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.*;
import org.junit.Test;
import purecollections.PList;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class DataModelTest {

    Administrator admin;
    Issuer issuer;
    Developer dev;
    DataModel model;
    Project project;
    Project project2;

    /**
     * Test if the constructors instantiates both projects and user lists as empty lists.
     */
    @Test
    public void testcons() {
        model = new DataModel();
        assertNotEquals(model.getUserList(), null);
        assertNotEquals(model.getProjectList(), null);
        assertTrue(model.getUserList().isEmpty());
        assertTrue(model.getProjectList().isEmpty());
    }

    @Test
    public void testGetUserList() {
        model = new DataModel();
        admin = model.createAdministrator("adminL", "Henk", "Bass");
        issuer = model.createIssuer("issuerL", "Ha", "skell");

        PList<User> userList = model.getUserList();
        assertEquals(userList.size(), 2);
        assertTrue(userList.contains(admin));
        assertTrue(userList.contains(issuer));
    }

    /**
     * Test getUserListOfType(Class)
     */
    @Test
    public void testGetUserListOfType() {
        model = new DataModel();
        admin = model.createAdministrator("Ja", "va", "rules");
        dev = model.createDeveloper("de", "v", "eloper");
        issuer = model.createIssuer("iss", "uer", "007");

        PList<Issuer> results = model.getUserListOfType(Issuer.class);

        assertEquals(results.size(), 2);
        assertTrue(results.contains(dev));
        assertTrue(results.contains(issuer));

        PList<Developer> results2 = model.getUserListOfType(Developer.class);
        assertEquals(results2.size(), 1);
        assertTrue(results2.contains(dev));
    }

    @Test
    public void testCreateAdministrator_all() {
        model = new DataModel();
        admin = model.createAdministrator("adminMyUserName", "adminMyFirstName", "adminMyMiddleName",
                "adminMyLastName");

        PList<User> users = model.getUserList();
        assertEquals(admin, users.get(0));
        assertEquals(admin.getUsername(), "adminMyUserName");
        assertEquals(admin.getFirstName(), "adminMyFirstName");
        assertEquals(admin.getMiddleName(), "adminMyMiddleName");
        assertEquals(admin.getLastName(), "adminMyLastName");

        Administrator admin2 = model.createAdministrator("adminMyUserName2", "adminMyFirstName2", "adminMyLastName2");

        model.getUserList().contains(admin2);
        assertEquals(admin2.getUsername(), "adminMyUserName2");
        assertEquals(admin2.getFirstName(), "adminMyFirstName2");
        assertEquals(admin2.getMiddleName(), "");
        assertEquals(admin2.getLastName(), "adminMyLastName2");
    }

    @Test
    public void testCreateIssuer() {
        model = new DataModel();
        issuer = model.createIssuer("NewIss", "Iss", "Uer");
        Issuer issuer2 = model.createIssuer("NewIss2", "Iss2", "Middle", "Uer2");

        assertTrue(model.getUserList().contains(issuer));
        assertEquals(issuer.getUsername(), "NewIss");
        assertEquals(issuer.getFirstName(), "Iss");
        assertEquals(issuer.getLastName(), "Uer");

        assertTrue(model.getUserList().contains(issuer2));
        assertEquals(issuer2.getUsername(), "NewIss2");
        assertEquals(issuer2.getFirstName(), "Iss2");
        assertEquals(issuer2.getLastName(), "Uer2");
        assertEquals(issuer2.getMiddleName(), "Middle");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIssuerException() {
        model = new DataModel();
        issuer = model.createIssuer(null, null, null);
    }

    @Test
    public void testCreateDeveloper() {
        model = new DataModel();
        dev = model.createDeveloper("NewDev", "Devel", "Oper");
        Developer dev2 = model.createDeveloper("NewDev2", "Devel2", "Middle", "Oper2");

        assertTrue(model.getUserList().contains(dev));
        assertEquals(dev.getUsername(), "NewDev");
        assertEquals(dev.getFirstName(), "Devel");
        assertEquals(dev.getLastName(), "Oper");

        assertTrue(model.getUserList().contains(dev2));
        assertEquals(dev2.getUsername(), "NewDev2");
        assertEquals(dev2.getFirstName(), "Devel2");
        assertEquals(dev2.getLastName(), "Oper2");
        assertEquals(dev2.getMiddleName(), "Middle");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDeveloperException() {
        model = new DataModel();
        dev = model.createDeveloper(null, null, null);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectExceptionA() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha1", "Ha", "Ha");
        project = model.createProject(new VersionID(), "Test", "TestProject", new GregorianCalendar(), leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectExceptionB() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha2", "Ha", "Ha");
        project2 = model.createProject(new VersionID(), "Test2", "TestProject", leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectException2A() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha3", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project = model.createProject(new VersionID(), "Test", "TestProject", new GregorianCalendar(), leadDev, 1000, creator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProjectException2B() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha4", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project2 = model.createProject(new VersionID(), "Test2", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testCreateProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha5", "Ha", "Ha");
        Administrator creator = new Administrator("Again2", "New", "Admin");
        project2 = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testUpdateProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha6", "Ha", "Ha");
        Administrator creator = new Administrator("Again3", "New", "Admin");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
        model.updateProject(project, creator, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);

        assertEquals(project.getName(), "NewTest");
        assertEquals(project.getDescription(), "NewTestProject");
        assertEquals(project.getBudgetEstimate(), 10);
    }

    @Test(expected = PermissionException.class)
    public void testUpdateProjectException1() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha7", "Ha", "Ha");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, leadDev);
        model.updateProject(project, leadDev, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }

    @Test(expected = PermissionException.class)
    public void testUpdateProjectException2() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha8", "Ha", "Ha");
        Issuer creator = new Issuer("Again4", "New", "Admin");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
        model.updateProject(project, creator, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }

    @Test(expected = PermissionException.class)
    public void testDeleteProjectException() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha9", "Ha", "Ha");
        Administrator creator = new Administrator("Again9", "New", "Admin");
        issuer = new Issuer("Issuer9", "New", "Issuer");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
        model.deleteProject(leadDev, project);
    }

    @Test
    public void testDeleteProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha10", "Ha", "Ha");
        Administrator creator = new Administrator("Again10", "New", "Admin");
        issuer = new Issuer("Issuer10", "New", "Issuer");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
        model.deleteProject(creator, project);
    }

    @Test(expected = PermissionException.class)
    public void testDeleteProjectException2() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha11", "Ha", "Ha");
        Administrator creator = new Administrator("Again11", "New", "Admin");
        issuer = new Issuer("Issuer11", "New", "Issuer");
        project = model.createProject(new VersionID(), "Test", "TestProject", leadDev, 1000, creator);
        model.deleteProject(issuer, project);
    }

    @Test
    public void testGetAllTags() {
        model = new DataModel();
        assertTrue(model.getAllTags().contains(Tag.NEW));
        assertTrue(model.getAllTags().contains(Tag.ASSIGNED));
        assertTrue(model.getAllTags().contains(Tag.NOT_A_BUG));
        assertTrue(model.getAllTags().contains(Tag.UNDER_REVIEW));
        assertTrue(model.getAllTags().contains(Tag.CLOSED));
        assertTrue(model.getAllTags().contains(Tag.RESOLVED));
        assertTrue(model.getAllTags().contains(Tag.DUPLICATE));
        assertTrue(model.getAllTags().size() == 7);
    }

    @Test
    public void testGetAllRoles() {
        model = new DataModel();
        assertTrue(model.getAllRoles().contains(Role.LEAD));
        assertTrue(model.getAllRoles().contains(Role.TESTER));
        assertTrue(model.getAllRoles().contains(Role.PROGRAMMER));
        assertTrue(model.getAllRoles().size() == 3);
    }

    /**
     * Test {@link DataModel#getHistory(int)} x &lt 2, x &gt 2, x == 2, x &lt 0, x == 0
     */
    @Test
    public void testGetHistory() {
        model = new DataModel();

        model.createAdministrator("UniqueUserNameOverHere", "FirstName", "LastName");
        model.createIssuer("UniqueUserNameOverHere2", "FirstName2", "LastName2");

        //Test overflow: x == 5 > 2
        PList<ModelCmd> list = model.getHistory(5);
        assertEquals(2, list.size());
        assertTrue(list.get(0).toString().contains("Issuer"));
        assertTrue(list.get(1).toString().contains("Admin"));

        //Test exact: x == 2
        list = model.getHistory(2);
        assertEquals(2, list.size());
        assertTrue(list.get(0).toString().contains("Issuer"));
        assertTrue(list.get(1).toString().contains("Admin"));

        //Test less: x == 1 < 2 
        list = model.getHistory(1);
        assertEquals(1, list.size());
        assertTrue(list.get(0).toString().contains("Issuer"));

        //x == -5
        list = model.getHistory(-5);
        assertTrue(list.isEmpty());

        //x == 0
        list = model.getHistory(0);
        assertTrue(list.isEmpty());
    }

    /**
     * Test {@link DataModel#undoLastChanges(bugtrap03.bugdomain.usersystem.User, int) when the user does not have sufficient permissions.
     *
     * @throws PermissionException Always.
     */
    @Test(expected = PermissionException.class)
    public void testUndoNoPermissions() throws PermissionException {
        model = new DataModel();
        issuer = model.createIssuer("CrazyUserNameNoOneWillUse", "HelloFirst", "HelloLast");
        model.undoLastChanges(issuer, 2);
    }

    /**
     * Test {@link DataModel#undoLastChanges(bugtrap03.bugdomain.usersystem.User, int) when the user == null
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testUndoNullUser() throws PermissionException {
        model = new DataModel();
        model.undoLastChanges(null, 2);
    }

    /**
     * Test {@link DataModel#undoLastChanges(User, int) as a success scenario with every possible branch.
     *
     * @throws PermissionException  Never
     */
    @Test
    public void testUndo() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob", "FirstName", "LastName");
        dev = model.createDeveloper("RikkyTheMan", "Bob", "DrivesHome");
        model.createProject(new VersionID(), "ProjectName", "This is a test Project", dev, 50, admin);

        //Before
        assertEquals(3, model.getHistory(5).size());
        assertEquals(1, model.getProjectList().size());
        assertEquals(2, model.getUserList().size());

        model.undoLastChanges(admin, 2);

        //After
        assertEquals(1, model.getHistory(5).size());
        assertTrue(model.getProjectList().isEmpty());
        assertEquals(1, model.getUserList().size());
    }

    /**
     * Test {@link DataModel#undoLastChanges(bugtrap03.bugdomain.usersystem.User, int) with a negative value.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testUndoLastChangesNegative() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ABCDEF0458", "first", "last");
        assertTrue(model.undoLastChanges(admin, -5));
    }

    /**
     * Test {@link DataModel#undoLastChanges(bugtrap03.bugdomain.usersystem.User, int) while there are no more.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testUndoLastChangesNone() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("A0BCDEF0458", "first", "last");
        assertTrue(model.undoLastChanges(admin, 2));
    }
    
    /**
     * Test {@link DataModel#getDetails(User, BugReport)} without permissions.
     * @throws PermissionException 
     */
    @Test(expected = PermissionException.class)
    public void testGetDetailsNoPerm() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("A2BCDEF0459", "first", "last");
        dev = model.createDeveloper("A2BCDEF460", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        
        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        
        model.getDetails(admin, bugRep);
    }
    
    /**
     * Test {@link DataModel#giveScore(BugReport, User, int)}.
     * (superficial as it is tested in the ModelCmds used.
     * @throws PermissionException 
     */
    @Test
    public void testGiveScore() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("A3BCDEF0459", "first", "last");
        dev = model.createDeveloper("A3BCDEF460", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        
        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);
        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));
        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        model.selectPatch(bugRep, dev, "patch");
        
        model.giveScore(bugRep, dev, 3);
        assertEquals(3, bugRep.getScore());
    }

    @Test
    public void testGetNbClosedBRForDev() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin2", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper2", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper2_1", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);

        model.assignToProject(project1, dev, dev, Role.TESTER);
        model.assignToProject(project1, dev, dev, Role.PROGRAMMER);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");

        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep1 = model.createBugReport(subsys, other, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsys1, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        model.addUsersToBugReport(dev, bugRep2, (PList.<Developer>empty()).plus(dev));
        model.addUsersToBugReport(dev, bugRep1, (PList.<Developer>empty()).plus(other));
        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));

        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        model.selectPatch(bugRep, dev, "patch");
        model.giveScore(bugRep, dev, 4);

        model.addTest(bugRep1, dev, "test");
        model.addPatch(bugRep1, dev, "patch");
        model.selectPatch(bugRep1, dev, "patch");
        model.giveScore(bugRep1, other, 4);

        assertEquals(1, model.getNbClosedBRForDev(dev));
    }

    @Test
    public void testNbUnfinishedBRForDev() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin1", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper1", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper1_1", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);

        model.assignToProject(project1, dev, dev, Role.TESTER);
        model.assignToProject(project1, dev, dev, Role.PROGRAMMER);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");

        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep1 = model.createBugReport(subsys, other, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsys1, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        model.addUsersToBugReport(dev, bugRep2, (PList.<Developer>empty()).plus(dev));
        model.addUsersToBugReport(dev, bugRep1, (PList.<Developer>empty()).plus(other));
        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));

        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        model.selectPatch(bugRep, dev, "patch");
        model.giveScore(bugRep, dev, 4);

        assertEquals(1, model.getNbUnfinishedBRForDev(dev));
    }
    
    @Test
    public void testgetNbDuplicateBRsSubmitted() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin3", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper3", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper3_1", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);

        model.assignToProject(project1, dev, dev, Role.TESTER);
        model.assignToProject(project1, dev, dev, Role.PROGRAMMER);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");

        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep1 = model.createBugReport(subsys, other, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsys1, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        model.addUsersToBugReport(dev, bugRep2, (PList.<Developer>empty()).plus(dev));
        model.addUsersToBugReport(dev, bugRep1, (PList.<Developer>empty()).plus(other));
        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));

        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        
        assertEquals(0, model.getNbDuplicateBRsSubmitted(dev));
        assertEquals(0, model.getNbDuplicateBRsSubmitted(other));
        
        model.setDuplicate(dev, bugRep, bugRep2);
        
        assertEquals(1, model.getNbDuplicateBRsSubmitted(dev));
        assertEquals(0, model.getNbDuplicateBRsSubmitted(other));
        
        model.setDuplicate(dev, bugRep1, bugRep);
        
        assertEquals(1, model.getNbDuplicateBRsSubmitted(dev));
        assertEquals(1, model.getNbDuplicateBRsSubmitted(other));        
                
    }
    
    @Test
    public void testgetNbNotABugReportBRsSubmitted() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin4", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper4", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper4_1", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);

        model.assignToProject(project1, dev, dev, Role.TESTER);
        model.assignToProject(project1, dev, dev, Role.PROGRAMMER);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");

        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep1 = model.createBugReport(subsys, other, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsys1, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        model.addUsersToBugReport(dev, bugRep2, (PList.<Developer>empty()).plus(dev));
        model.addUsersToBugReport(dev, bugRep1, (PList.<Developer>empty()).plus(other));
        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));

        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        
        //Test both 0
        assertEquals(0, model.getNbNotABugReportBRsSubmitted(dev));
        assertEquals(0, model.getNbNotABugReportBRsSubmitted(other));
        
        model.setTag(bugRep2, Tag.NOT_A_BUG, dev);
        
        //Test 1 became 1
        assertEquals(1, model.getNbNotABugReportBRsSubmitted(dev));
        assertEquals(0, model.getNbNotABugReportBRsSubmitted(other));
        
        model.setTag(bugRep1, Tag.NOT_A_BUG, dev);

        //Test both 1.
        assertEquals(1, model.getNbNotABugReportBRsSubmitted(dev));
        assertEquals(1, model.getNbNotABugReportBRsSubmitted(other));
    }
    
    @Test
    public void testgetNbBRSubmitted() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin5", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper5", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper5_1", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        model.assignToProject(project, dev, dev, Role.TESTER);
        model.assignToProject(project, dev, dev, Role.PROGRAMMER);

        model.assignToProject(project1, dev, dev, Role.TESTER);
        model.assignToProject(project1, dev, dev, Role.PROGRAMMER);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");


        assertEquals(0, model.getNbBRSubmitted(dev));
        assertEquals(0, model.getNbBRSubmitted(other));

        BugReport bugRep = model.createBugReport(subsys, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        assertEquals(1, model.getNbBRSubmitted(dev));
        assertEquals(0, model.getNbBRSubmitted(other));

        model.addUsersToBugReport(dev, bugRep, (PList.<Developer>empty()).plus(dev));
        BugReport bugRep1 = model.createBugReport(subsys, other, "bugTitle", "bugDesc", PList.empty(), null, 1, false);
        BugReport bugRep2 = model.createBugReport(subsys1, dev, "bugTitle", "bugDesc", PList.empty(), null, 1, false);

        assertEquals(2, model.getNbBRSubmitted(dev));
        assertEquals(1, model.getNbBRSubmitted(other));

        model.addUsersToBugReport(dev, bugRep2, (PList.<Developer>empty()).plus(dev));
        model.addUsersToBugReport(dev, bugRep1, (PList.<Developer>empty()).plus(other));

        model.addTest(bugRep, dev, "test");
        model.addPatch(bugRep, dev, "patch");
        
        assertEquals(2, model.getNbBRSubmitted(dev));
        assertEquals(1, model.getNbBRSubmitted(other));
        
        //undo to creation of only 1 for each.
        model.undoLastChanges(admin, 5);
        
        assertEquals(1, model.getNbBRSubmitted(dev));
        assertEquals(1, model.getNbBRSubmitted(other));
    }
    
    
    @Test
    public void testgetAllProjectsOfLead() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin6", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper6", "first", "last");
        Developer other = model.createDeveloper("ThisIsAnotherDeveloper6_1", "first", "last");
        
        assertEquals(0, model.getAllProjectsOfLead(dev).size());
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        assertEquals(1, model.getAllProjectsOfLead(dev).size());
        assertTrue(model.getAllProjectsOfLead(dev).contains(project));
        
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project2 = model.createProject(new VersionID(), "name", "description", other, 1050, admin);
        
        assertEquals(2, model.getAllProjectsOfLead(dev).size());
        assertTrue(model.getAllProjectsOfLead(dev).contains(project));
        assertTrue(model.getAllProjectsOfLead(dev).contains(project1));
        
        model.assignToProject(project1, dev, dev, Role.TESTER);
        
        assertEquals(2, model.getAllProjectsOfLead(dev).size());
        assertTrue(model.getAllProjectsOfLead(dev).contains(project));
        assertTrue(model.getAllProjectsOfLead(dev).contains(project1));
        assertEquals(1, model.getAllProjectsOfLead(other).size());
        assertTrue(model.getAllProjectsOfLead(other).contains(project2));
    }
    
    @Test
    public void testGetAllSubsystems() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ThisIsAnotherAdmin7", "first", "last");
        dev = model.createDeveloper("ThisIsAnotherDeveloper7", "first", "last");
        project = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);
        Project project1 = model.createProject(new VersionID(), "name", "desc", dev, 50, admin);

        Subsystem subsys = model.createSubsystem(admin, project, "subName", "subDesc");
        Subsystem subsys_2 = model.createSubsystem(admin, subsys, "name", "desc");
        Subsystem subsys_1 = model.createSubsystem(admin, subsys, "name", "desc");        
        Subsystem subsys1 = model.createSubsystem(admin, project1, "subName", "subDesc");
        
        assertEquals(0, model.getAllSubsystems(null).size());
        assertEquals(1, model.getAllSubsystems(project1).size());
        PList<Subsystem> result = model.getAllSubsystems(project);
        assertEquals(3, result.size());
        assertTrue(result.contains(subsys));
        assertTrue(result.contains(subsys_2));
        assertTrue(result.contains(subsys_1));
        assertFalse(result.contains(subsys1));
    }
    
    

}
