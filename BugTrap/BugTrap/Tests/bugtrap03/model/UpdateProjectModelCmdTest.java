package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Group 03
 */
public class UpdateProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private Developer dev;
    private GregorianCalendar startDate;

    @Before
    public void setUp() throws PermissionException {
        startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob9" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere9" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");

        counter++;
    }

    /**
     * Test
     * {@link UpdateProjectModelCmd#UpdateProjectModelCmd(bugtrap03.bugdomain.Project, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, long)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        String oldName = proj.getName();
        String oldDesc = proj.getDescription();
        GregorianCalendar oldStartDate = proj.getStartDate();
        long oldBudget = proj.getBudgetEstimate();

        // 1. Create
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, admin, "new name", "new desc", startDate, 100);

        // test
        assertTrue(cmd.toString().contains("Updated") || cmd.toString().contains("updated"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertTrue(cmd.toString().contains("new name"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Updated") || cmd.toString().contains("updated"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertTrue(cmd.toString().contains("new name"));
        assertTrue(cmd.isExecuted());
        assertEquals("new name", proj.getName());
        assertEquals("new desc", proj.getDescription());
        assertEquals(startDate, proj.getStartDate());
        assertEquals(100, proj.getBudgetEstimate());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertTrue(cmd.toString().contains("Updated") || cmd.toString().contains("updated"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertTrue(cmd.toString().contains("new name"));
        assertTrue(cmd.isExecuted());

        assertEquals(oldName, proj.getName());
        assertEquals(oldDesc, proj.getDescription());
        assertEquals(oldStartDate, proj.getStartDate());
        assertEquals(oldBudget, proj.getBudgetEstimate());
    }

    /**
     * Test exec with a startDate == null
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_InvalidStartDate() throws PermissionException {
        // 1. Create
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, admin, "new name", "new desc", null, 100);
        // 2. Exec()
        cmd.exec();
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, admin, "new name", "new desc", startDate, 100);

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link UpdateProjectModelCmd#UpdateProjectModelCmd(bugtrap03.bugdomain.Project, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, long)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_UserNull() {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, null, "new name", "new desc", startDate, 100);
    }

    /**
     * Test exec with proj == null
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_ProjNull() throws PermissionException {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(null, admin, "new name", "new desc", startDate, 100);
        cmd.exec();
    }

    /**
     * Test exec with budgetEstimate invalid (negative)
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_InvalidBudget() throws PermissionException {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, admin, "new name", "new desc", startDate, -50);
        cmd.exec();
    }

    /**
     * Test exec with insufficient permissions.
     */
    @Test(expected = PermissionException.class)
    public void testExec_NoPermission() throws PermissionException {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, dev, "new name", "new desc", startDate, 100);
        cmd.exec();
    }

    /**
     * Test toString() while there are null arguments.
     */
    @Test
    public void testToString_NullArgs() {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(null, admin, null, null, null, 100);
        assertTrue(cmd.toString().contains("Project -invalid argument-"));
        assertTrue(cmd.toString().contains("-invalid argument-, 100"));
    }

}
