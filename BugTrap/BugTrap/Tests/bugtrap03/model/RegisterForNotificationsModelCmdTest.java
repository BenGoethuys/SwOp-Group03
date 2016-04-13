package bugtrap03.model;

import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class RegisterForNotificationsModelCmdTest {

    private DataModel model;
    private Administrator admin;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikesBlub" + counter, "first", "last");
        counter++;
    }

    @Test
    public void testCons() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        assertEquals(admin.getMailbox(), cmd.getMailbox());
        assertFalse(cmd.isExecuted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_InvalidUser() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(null);
    }

    @Test
    public void testIsValidUser() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);

        assertTrue(cmd.isValidUser(admin));

        assertFalse(cmd.isValidUser(null));
    }

    @Test
    public void testSetExecuted() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);

        assertFalse(cmd.isExecuted());
        cmd.setExecuted();
        assertTrue(cmd.isExecuted());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetExecuted_IllegalState() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        cmd.setExecuted();
        cmd.setExecuted();
    }

    @Test
    public void testUndo_NotExecuted() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        assertFalse(cmd.undo());
    }

    /**
     * Test undo() while newMailbox == null because no subscribe happened.
     */
    @Test
    public void testUndo_unsubNull() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        cmd.setExecuted();
        assertTrue(cmd.undo());
    }

    @Test(expected = IllegalStateException.class)
    public void testToString_IllegalState() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        cmd.setExecuted();
        cmd.toString();
    }

    @Test
    public void testToString_NotExecuted() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        assertEquals("Subscription not yet created.", cmd.toString());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetNewMailbox_IllegalMB() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        cmd.setNewMailbox(null);
    }
    
    @Test
    public void testSetNewMailBox_toString() {
        RegNotModelCmdDummy cmd = new RegNotModelCmdDummy(admin);
        Mailbox box = new Mailbox();
        cmd.setNewMailbox(box);
        assertEquals("Created subscription: \n" + box.getInfo(), cmd.toString());
    }

    /**
     * Dummy class to test methods.
     */
    class RegNotModelCmdDummy extends RegisterForNotificationsModelCmd {

        public RegNotModelCmdDummy(User user) {
            super(user);
        }

        @Override
        Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
