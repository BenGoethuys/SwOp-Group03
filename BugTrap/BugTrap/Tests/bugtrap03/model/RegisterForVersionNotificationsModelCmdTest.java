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
public class RegisterForVersionNotificationsModelCmdTest {
    private DataModel model;
    private Administrator admin;
    private Project project;
    private Developer lead;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikes4Plop" + counter, "first", "last");
        lead = model.createDeveloper("u1i4" + counter, "first", "last");

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        project = model.createProject(new VersionID(), "projName4", "projDesc", lead, 100, admin);
        counter++;
    }

    @Test
    public void testCons() {
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalUser() {
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalSubject() {
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(admin, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubjectTerminated() throws PermissionException {
        model.deleteProject(admin, project);
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_SubjectTerminated() throws PermissionException {
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(admin, project);
        model.deleteProject(admin, project);
        cmd.exec();
    }

    @Test
    public void testExec() throws PermissionException {
        RegisterForVersionNotificationsModelCmd cmd = new RegisterForVersionNotificationsModelCmd(admin, project);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        assertEquals("Created subscription: \n"+ "You are subscribed to the change of VersionIDs on " +
                project.getSubjectName() + " and all it's subsystems.", cmd.toString());
    }
}