package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class RegisterForTagNotificationsModelCmdTest {

    private DataModel model;
    private Administrator admin;
    private Project project;
    private Developer lead;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikes0Plop" + counter, "first", "last");
        lead = model.createDeveloper("u0i" + counter, "first", "last");

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        project = model.createProject("projName", "projDesc", lead, 100, admin);
        counter++;
    }

    @Test
    public void testCons() {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalUser() {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalSubject() {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubjectTerminated() throws PermissionException {
        model.deleteProject(admin, project);
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_SubjectTerminated() throws PermissionException {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project);
        model.deleteProject(admin, project);
        cmd.exec();
    }

    @Test
    public void testExec_NullTag() throws PermissionException {
        EnumSet<Tag> tags = EnumSet.allOf(Tag.class);

        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());

        assertEquals("Created subscription: \nYou are subscribed to a change of following tags: "
                + tags.toString() + " on: "
                + project.getSubjectName(), cmd.toString());
    }

    @Test
    public void testExec_Tags() throws PermissionException {
        EnumSet<Tag> set = EnumSet.of(Tag.RESOLVED, Tag.ASSIGNED);
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project, set);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());

        assertEquals("Created subscription: \nYou are subscribed to a change of following tags: "
                + set.toString() + " on: "
                + project.getSubjectName(), cmd.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_EmptyTags() throws PermissionException {
        EnumSet<Tag> set = EnumSet.complementOf(EnumSet.allOf(Tag.class));
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(admin, project, set);
        
        assertFalse(cmd.isExecuted());
        cmd.exec();
    }
}
