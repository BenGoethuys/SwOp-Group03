package bugtrap03.model;

import bugtrap03.bugdomain.Project;
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
        project = model.createProject("Test", "TestProject", new GregorianCalendar(), leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectExceptionB() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha2", "Ha", "Ha");
        project2 = model.createProject("Test2", "TestProject", leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectException2A() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha3", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project = model.createProject("Test", "TestProject", new GregorianCalendar(), leadDev, 1000, creator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProjectException2B() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha4", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project2 = model.createProject("Test2", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testCreateProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha5", "Ha", "Ha");
        Administrator creator = new Administrator("Again2", "New", "Admin");
        project2 = model.createProject("Test", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testUpdateProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha6", "Ha", "Ha");
        Administrator creator = new Administrator("Again3", "New", "Admin");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
        model.updateProject(project, creator, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);

        assertEquals(project.getName(), "NewTest");
        assertEquals(project.getDescription(), "NewTestProject");
        assertEquals(project.getBudgetEstimate(), 10);
    }

    @Test(expected = PermissionException.class)
    public void testUpdateProjectException1() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha7", "Ha", "Ha");
        project = model.createProject("Test", "TestProject", leadDev, 1000, leadDev);
        model.updateProject(project, leadDev, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }

    @Test(expected = PermissionException.class)
    public void testUpdateProjectException2() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha8", "Ha", "Ha");
        Issuer creator = new Issuer("Again4", "New", "Admin");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
        model.updateProject(project, creator, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }

    @Test(expected = PermissionException.class)
    public void testDeleteProjectException() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha9", "Ha", "Ha");
        Administrator creator = new Administrator("Again9", "New", "Admin");
        issuer = new Issuer("Issuer9", "New", "Issuer");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
        model.deleteProject(leadDev, project);
    }

    @Test
    public void testDeleteProject() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha10", "Ha", "Ha");
        Administrator creator = new Administrator("Again10", "New", "Admin");
        issuer = new Issuer("Issuer10", "New", "Issuer");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
        model.deleteProject(creator, project);
    }

    @Test(expected = PermissionException.class)
    public void testDeleteProjectException2() throws IllegalArgumentException, PermissionException {
        model = new DataModel();
        Developer leadDev = new Developer("Haha11", "Ha", "Ha");
        Administrator creator = new Administrator("Again11", "New", "Admin");
        issuer = new Issuer("Issuer11", "New", "Issuer");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
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
        model.createProject("ProjectName", "This is a test Project", dev, 50, admin);

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
     * @throws PermissionException Never 
     */
    @Test
    public void testUndoLastChangesNegative() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("ABCDEF0458", "first", "last");
        assertTrue(model.undoLastChanges(admin, -5));
    }
    
    
  
}
