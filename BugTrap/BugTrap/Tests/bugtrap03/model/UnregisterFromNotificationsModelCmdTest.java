package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class UnregisterFromNotificationsModelCmdTest {

    private DataModel model;
    private Project project;
    private Administrator admin;
    private Developer dev;
    private CommentMailbox cmb;
    UnregisterFromNotificationsModelCmd cmd;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikesBluuub" + counter, "furst", "lust");
        dev = model.createDeveloper("MathiasLikesHimself" + counter, "hellow", "miauw");
        project = model.createProject(new VersionID(), "blub","welp",dev, 1000, admin);
        cmb = model.registerForCommentNotifications(admin, project);
        cmd = new UnregisterFromNotificationsModelCmd(admin, cmb);
        counter++;
    }

    @Test
    public void testIsValidUser() throws Exception {
        assertFalse(cmd.isValidUser(null));
        assertTrue(cmd.isValidUser(dev));
    }

    @Test
    public void testIsValidMailbox() throws Exception {
        assertFalse(cmd.isValidMailbox(null, dev));
        assertFalse(cmd.isValidMailbox(admin.getMailbox(), admin));
        assertTrue(cmd.isValidMailbox(cmb, admin));
    }

    @Test
    public void testExec() throws Exception {
        assertTrue(admin.getMailbox().getAllBoxes().contains(cmb));
        cmd.exec();
        assertFalse(admin.getMailbox().getAllBoxes().contains(cmb));
    }

    @Test
    public void testUndo() throws Exception {
        assertTrue(admin.getMailbox().getAllBoxes().contains(cmb));
        cmd.exec();
        assertFalse(admin.getMailbox().getAllBoxes().contains(cmb));
        cmd.undo();
        assertTrue(admin.getMailbox().getAllBoxes().contains(cmb));
    }

    @Test
    public void testIsExecuted() throws Exception {
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        cmd.undo();
        assertTrue(cmd.isExecuted());
    }

    @Test
    public void testToString() throws Exception {
        String message2 = cmd.toString();
        assertTrue(message2.contains("This unregistration has not yet been executed."));
        cmd.exec();
        String message = cmd.toString();
        assertTrue(message.contains(" has unregistered from notifications by deleting subscription: "));
        assertTrue(message.contains(cmb.getInfo()));
        assertTrue(message.contains(admin.getFullName()));
    }
}