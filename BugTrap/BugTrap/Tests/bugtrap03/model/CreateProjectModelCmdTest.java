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
public class CreateProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob16" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere16" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);

        counter++;
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", dev, 100, admin);

        // test
        assertTrue(cmd.toString().contains("Created Project"));
        assertTrue(cmd.toString().contains("TestProject50"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Project project = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Created Project"));
        assertTrue(cmd.toString().contains("TestProject50"));
        assertTrue(cmd.isExecuted());
        assertTrue(model.getProjectList().contains(project));
        assertEquals("TestProject50", project.getName());
        assertEquals("50 Project", project.getDescription());
        assertEquals(100, project.getBudgetEstimate());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getProjectList().contains(project));
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, GregorianCalendar, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", startDate, dev, 100, admin);

        // test
        assertTrue(cmd.toString().contains("Created Project"));
        assertTrue(cmd.toString().contains("TestProject50"));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Project project = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Created Project"));
        assertTrue(cmd.toString().contains("TestProject50"));
        assertTrue(cmd.isExecuted());
        assertTrue(model.getProjectList().contains(project));
        assertEquals("TestProject50", project.getName());
        assertEquals("50 Project", project.getDescription());
        assertEquals(100, project.getBudgetEstimate());
        assertEquals(startDate, project.getStartDate());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getProjectList().contains(project));
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", dev, 100, admin);

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_ModelNull() {
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(null, new VersionID(), "TestProject50", "50 Project", dev, 100, admin);
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_UserNull() {
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", dev, 100, null);
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, GregorianCalendar, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_ModelNull() {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(null, new VersionID(), "TestProject50", "50 Project", startDate, dev, 100, admin);
    }

    /**
     * Test
     * {@link CreateProjectModelCmd#CreateProjectModelCmd(DataModel, VersionID, String, String, GregorianCalendar, Developer, long, bugtrap03.bugdomain.usersystem.User)}
     * with user == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_UserNull() {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", startDate, dev, 100, null);
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(model, new VersionID(), "TestProject50", "50 Project", dev, 100, dev);
        cmd.exec();
    }

}
