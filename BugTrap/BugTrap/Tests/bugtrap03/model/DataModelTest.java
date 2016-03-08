package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class DataModelTest {

    Administrator admin;
    Project project;
    Project project2;

    /**
     * Test if the constructors instantiates both projects and user lists as
     * empty lists.
     */
    @Test
    public void testcons() {
        DataModel model = new DataModel();
        assertNotEquals(model.getUserList(), null);
        assertNotEquals(model.getProjectList(), null);
        assertTrue(model.getUserList().isEmpty());
        assertTrue(model.getProjectList().isEmpty());
    }

    @Test
    public void testGetUserList() {
        DataModel model = new DataModel();
        admin = model.createAdministrator("adminL", "Henk", "Bass");
        Issuer issuer = model.createIssuer("issuerL", "Ha", "skell");

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
        DataModel model = new DataModel();
        admin = model.createAdministrator("Ja", "va", "rules");
        Developer dev = model.createDeveloper("de", "v", "eloper");
        Issuer issuer = model.createIssuer("iss", "uer", "007");

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
        DataModel model = new DataModel();
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
        DataModel model = new DataModel();
        Issuer iss = model.createIssuer("NewIss", "Iss", "Uer");
        Issuer iss2 = model.createIssuer("NewIss2", "Iss2", "Middle", "Uer2");

        assertTrue(model.getUserList().contains(iss));
        assertEquals(iss.getUsername(), "NewIss");
        assertEquals(iss.getFirstName(), "Iss");
        assertEquals(iss.getLastName(), "Uer");

        assertTrue(model.getUserList().contains(iss2));
        assertEquals(iss2.getUsername(), "NewIss2");
        assertEquals(iss2.getFirstName(), "Iss2");
        assertEquals(iss2.getLastName(), "Uer2");
        assertEquals(iss2.getMiddleName(), "Middle");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIssuerException() {
        DataModel model = new DataModel();
        Issuer iss = model.createIssuer(null, null, null);
    }

    @Test
    public void testCreateDeveloper() {
        DataModel model = new DataModel();
        Developer dev = model.createDeveloper("NewDev", "Devel", "Oper");
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
        DataModel model = new DataModel();
        Developer dev = model.createDeveloper(null, null, null);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectExceptionA() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha1", "Ha", "Ha");
        project = model.createProject("Test", "TestProject", new GregorianCalendar(), leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectExceptionB() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha2", "Ha", "Ha");
        project2 = model.createProject("Test2", "TestProject", leadDev, 1000, leadDev);
    }

    @Test(expected = PermissionException.class)
    public void testCreateProjectException2A() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha3", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project = model.createProject("Test", "TestProject", new GregorianCalendar(), leadDev, 1000, creator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProjectException2B() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha4", "Ha", "Ha");
        Issuer creator = new Issuer("Again", "New", "Issuer");
        project2 = model.createProject("Test2", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testCreateProjectException3() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha5", "Ha", "Ha");
        Administrator creator = new Administrator("Again2", "New", "Admin");
        project2 = model.createProject("Test", "TestProject", leadDev, 1000, creator);
    }

    @Test
    public void testUpdateProject() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
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
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha7", "Ha", "Ha");
        project = model.createProject("Test", "TestProject", leadDev, 1000, leadDev);
        model.updateProject(project, leadDev, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }

    @Test(expected = PermissionException.class)
    public void testUpdateProjectException2() throws IllegalArgumentException, PermissionException {
        DataModel model = new DataModel();
        Developer leadDev = new Developer("Haha8", "Ha", "Ha");
        Issuer creator = new Issuer("Again4", "New", "Admin");
        project = model.createProject("Test", "TestProject", leadDev, 1000, creator);
        model.updateProject(project, creator, "NewTest", "NewTestProject", new GregorianCalendar(), (long) 10);
    }
   
}
