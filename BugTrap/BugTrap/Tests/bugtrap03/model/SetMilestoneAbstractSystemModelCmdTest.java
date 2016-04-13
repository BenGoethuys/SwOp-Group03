package bugtrap03.model;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.ExactComparisonCriteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Group 03
 */
public class SetMilestoneAbstractSystemModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;
    private Subsystem subsys;
    private Subsystem subsys1;
    private Subsystem subsys2;
    private Subsystem subsys3;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("WasHierBenAdmin" + counter, "first", "last");
        dev = model.createDeveloper("WasHierBenDev" + counter, "first", "last");
        proj = model.createProject("WasHierBenProj", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        subsys1 = model.createSubsystem(admin, proj, "fancy name 1", "fancy desc 1");
        subsys2 = model.createSubsystem(admin, subsys, "facy name 2", "fancy desc 2");
        subsys3 = model.createSubsystem(admin, subsys2, "fancy name 3", "fancy desc 3");

        counter++;
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateInvalidUser(){
        new SetMilestoneAbstractSystemModelCmd(null, proj, new Milestone(2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateInvalidProject(){
        new SetMilestoneAbstractSystemModelCmd(dev, null, new Milestone(2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateInvalidMilestone(){
        new SetMilestoneAbstractSystemModelCmd(dev, proj, null);
    }

    @Test
    public void testExec() throws PermissionException {
        // 1. create
        Milestone milsMilestone = new Milestone(6);
        SetMilestoneAbstractSystemModelCmd cmd = new SetMilestoneAbstractSystemModelCmd(dev, proj, milsMilestone);

        // test
        assertTrue(cmd.toString().contains("Updated milestone of"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());
        assertEquals(new Milestone(0), proj.getMilestone());
        assertEquals(new Milestone(0), subsys2.getMilestone());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue(cmd.isExecuted());
        assertEquals(milsMilestone, proj.getMilestone());
        assertEquals(milsMilestone, subsys2.getMilestone());

        // 3. Undo()
        assertTrue(cmd.undo());

        // test
        assertEquals(new Milestone(0), proj.getMilestone());
        assertEquals(new Milestone(0), subsys2.getMilestone());
    }

    @Test (expected = IllegalStateException.class)
    public void testExecTwice() throws PermissionException {
        // 1. create
        Milestone milsMilestone = new Milestone(6);
        SetMilestoneAbstractSystemModelCmd cmd = new SetMilestoneAbstractSystemModelCmd(dev, proj, milsMilestone);

        // 2. Exec()
        cmd.exec();

        // 2. Exec()
        cmd.exec();
    }

    @Test
    public void undoNotExec(){
        // 1. create
        Milestone milsMilestone = new Milestone(6);
        SetMilestoneAbstractSystemModelCmd cmd = new SetMilestoneAbstractSystemModelCmd(dev, proj, milsMilestone);

        // undo
        assertFalse(cmd.undo());
    }
}
