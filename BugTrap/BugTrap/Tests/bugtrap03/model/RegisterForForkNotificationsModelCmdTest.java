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
public class RegisterForForkNotificationsModelCmdTest {

    private DataModel model;
    private Administrator admin;
    private Project project;
    private Developer lead;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikes2Plop" + counter, "first", "last");
        lead = model.createDeveloper("u1i2" + counter, "first", "last");

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        project = model.createProject(new VersionID(), "projName2", "projDesc", lead, 100, admin);
        counter++;
    }

    @Test
    public void testCons() {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalUser() {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalSubject() {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(admin, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubjectTerminated() throws PermissionException {
        model.deleteProject(admin, project);
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_SubjectTerminated() throws PermissionException {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(admin, project);
        model.deleteProject(admin, project);
        cmd.exec();
    }

    @Test
    public void testExec() throws PermissionException {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(admin, project);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        assertEquals("Created subscription: \n" + "You are subscribed to the forking of " +
                project.getSubjectName(), cmd.toString());
    }
}