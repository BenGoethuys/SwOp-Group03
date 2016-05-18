package bugtrap03.model;

import bugtrap03.bugdomain.Milestone;
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
 * Created by Kwinten on 11/05/2016.
 */
public class RegisterForMilestoneNotificationsModelCmdTest {
    private DataModel model;
    private Administrator admin;
    private Project project;
    private Developer lead;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("MathiasLikes3Plop" + counter, "first", "last");
        lead = model.createDeveloper("u1i3" + counter, "first", "last");

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        project = model.createProject(new VersionID(), "projName3", "projDesc", lead, 100, admin);
        counter++;
    }

    @Test
    public void testCons() {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalUser() {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_IllegalSubject() {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubjectTerminated() throws PermissionException {
        model.deleteProject(admin, project);
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_SubjectTerminated() throws PermissionException {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, project);
        model.deleteProject(admin, project);
        cmd.exec();
    }

    @Test
    public void testExec() throws PermissionException {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, project);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        assertEquals("Created subscription: \n" + "You are subscribed to the change of a new milestone on: " +
                project.getSubjectName(), cmd.toString());
    }

    @Test
    public void testExecSpecific() throws PermissionException {
        Milestone milestone = new Milestone(5, 5, 5);
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(admin, project, milestone);
        assertFalse(cmd.isExecuted());
        cmd.exec();
        assertTrue(cmd.isExecuted());
        assertEquals("Created subscription: \n" + "You are subscribed to the change of the specific milestone "
                + milestone.toString() + " on: " +
                project.getSubjectName(), cmd.toString());
    }
}