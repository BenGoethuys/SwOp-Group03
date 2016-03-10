package bugtrap03.gui.terminal;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Admin
 */
public class TerminalTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConsNullModel() {
        Terminal term = new Terminal(null);
    }

    @Test
    public void testCons() {
        DataModel model = new DataModel();
        Terminal term = new Terminal(model);
        assertNotEquals(term.getModel(), null);
        assertNotEquals(term.getScanner(), null);
        assertEquals(term.getUser(), null);
    }

    @Test
    public void testSetUser() {
        DataModel model = new DataModel();
        Terminal term = new Terminal(model);
        User user = model.createAdministrator("Ping", "Pong", "Protocol");
        term.setUser(user);
        assertEquals(term.getUser(), user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUserNull() {
        DataModel model = new DataModel();
        Terminal term = new Terminal(model);
        term.setUser(null);
    }
}
