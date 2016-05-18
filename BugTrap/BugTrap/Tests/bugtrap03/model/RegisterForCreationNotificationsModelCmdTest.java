package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * @author Group 03
 */
public class RegisterForCreationNotificationsModelCmdTest {

    private DataModel model;
    private Administrator admin;
    private Project project;
    private Developer lead;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikes1Plop" + counter, "first", "last");
        lead = model.createDeveloper("u1i" + counter, "first", "last");

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        project = model.createProject(new VersionID(), "projName", "projDesc", lead, 100, admin);
        counter++;
    }

    @Test
    public void testCons() {
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalUser() {
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalSubject() {
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(admin, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubjectTerminated() throws PermissionException {
        model.deleteProject(admin, project);
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_SubjectTerminated() throws PermissionException {
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(admin, project);
        model.deleteProject(admin, project);
        cmd.exec();
    }

    @Test
    public void testExec() throws PermissionException {
        RegisterForCreationNotificationsModelCmd cmd = new RegisterForCreationNotificationsModelCmd(admin, project);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        assertEquals("Created subscription: \nYou are subscribed to the creation of bugreports on "
                + project.getSubjectName() + " and all it's subsystems.", cmd.toString());
    }
}
