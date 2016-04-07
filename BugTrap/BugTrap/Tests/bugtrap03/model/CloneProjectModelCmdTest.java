package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import java.util.GregorianCalendar;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Admin
 */
public class CloneProjectModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private Developer dev;
    private Developer dev2;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob1" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere1" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");

        dev2 = model.createDeveloper("Developer2OverHere1" + counter, "first", "last");

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
        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, new GregorianCalendar(), 50);

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

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(model.getProjectList().contains(clone));
        assertTrue(model.getProjectList().contains(proj));
    }

    /**
     * Test
     * {@link AddUsersToBugReportModelCmd#AddUsersToBugReportModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, purecollections.PList)}
     * in a default scenario.
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
        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, proj, new VersionID(2, 0, 1), dev, new GregorianCalendar(), 50);

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
    @Test
    public void testCons_ClonesourceNull() {
        // 1. Create
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(model, null, new VersionID(2, 0, 1), dev, new GregorianCalendar(), 50);
        // 2. Exec()
        Project clone = cmd.exec();
        // 3. undo()
        assertNull(clone);
    }

}
