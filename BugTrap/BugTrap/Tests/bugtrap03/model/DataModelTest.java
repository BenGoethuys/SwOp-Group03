package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class DataModelTest {

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
        Administrator admin = model.createAdministrator("adminL", "Henk", "Bass");
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
        Administrator admin = model.createAdministrator("Ja", "va", "rules");
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
        Administrator admin = model.createAdministrator("adminMyUserName", "adminMyFirstName", "adminMyMiddleName", "adminMyLastName");

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
        
    }
}
