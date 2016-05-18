package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Group 03
 */
public class CloneProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob8" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere8" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);

        counter++;
    }

    /**
     * Test
     * {@link CloneProjectModelCmd#CloneProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.Project, bugtrap03.bugdomain.VersionID, bugtrap03.bugdomain.usersystem.Developer, java.util.GregorianCalendar, long)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, startDate, 50);

        // test
        assertTrue(cmd.toString().contains("Cloned") || cmd.toString().contains("cloned"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Project clone = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("Cloned") || cmd.toString().contains("cloned"));
        assertTrue(cmd.toString().contains(proj.getName()));
        assertTrue(cmd.isExecuted());
        assertTrue(model.getProjectList().contains(clone));
        assertTrue(clone != proj);
        assertTrue(clone != null);

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getProjectList().contains(clone));
        assertTrue(model.getProjectList().contains(proj));
    }

    /**
     * Test exec with a startDate == null
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_InvalidStartDate() throws PermissionException {
        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, null, 50);
        // 2. Exec()
        Project clone = cmd.exec();
        // 3. undo()
        assertTrue(cmd.undo());
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, startDate, 50);

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CloneProjectModelCmd#CloneProjectModelCmd(bugtrap03.model.DataModel, bugtrap03.bugdomain.Project, bugtrap03.bugdomain.VersionID, bugtrap03.bugdomain.usersystem.Developer, java.util.GregorianCalendar, long)}
     * with model == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ModelNull() {
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(null, proj, new VersionID(2, 0, 1), dev, null, 50);
    }

    /**
     * Test creation of a clone with null as a cloneSource.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_ClonesourceNull() {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, null, new VersionID(2, 0, 1), dev, startDate, 50);
    }

    /**
     * Test constructor with terminated clonesSource
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_CloneSourceTerminated() throws PermissionException {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        model.deleteProject(admin, proj);
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, startDate, 50);
    }

    /**
     * Test exec() with terminated clonesSource
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_CloneSourceTerminated() throws PermissionException {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);

        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, startDate, 50);
        model.deleteProject(admin, proj);
        cmd.exec();
    }

}
