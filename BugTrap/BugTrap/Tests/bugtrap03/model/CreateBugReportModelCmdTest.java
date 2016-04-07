package bugtrap03.model;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class CreateBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");

        counter++;
    }

    /**
     * Test
     * {@link CreateBugReportModelCmd#CreateBugReportModelCmd(bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, purecollections.PList, bugtrap03.bugdomain.Milestone, boolean)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsys, dev, "title", "desc", null, PList.<BugReport>empty(), null, false);

        // test
        assertTrue(cmd.toString().contains("BugReport -invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.

        // 2. Exec()
        BugReport bugReport = cmd.exec();

        // test
        assertTrue(cmd.isExecuted());
        assertTrue(subsys.getAllBugReports().contains(bugReport));
        assertNull(bugReport.getTrigger());
        assertNull(bugReport.getError());
        assertEquals(bugReport.getTitle(), "title");

        assertTrue(cmd.toString().contains(bugReport.getTitle()));
        assertTrue(cmd.toString().contains("Created") || cmd.toString().contains("created"));

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(subsys.getAllBugReports().contains(bugReport));
    }

    /**
     * Test
     * {@link CreateBugReportModelCmd#CreateBugReportModelCmd(bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, purecollections.PList, bugtrap03.bugdomain.Milestone, boolean, java.lang.String, java.lang.String, java.lang.String)}
     * in a default scenario
     *
     * @throws PermissionException
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        // 1. create
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsys, dev, "title", "desc", null, PList.<BugReport>empty(), new Milestone(2, 0, 0), false, "trigger", "stackTrace", "error");

        // test
        assertTrue(cmd.toString().contains("BugReport -invalid argument-"));
        assertFalse(cmd.undo()); //can't undo yet.

        // 2. exec()
        BugReport bugReport = cmd.exec();

        // test
        assertTrue(cmd.isExecuted());
        assertTrue(subsys.getAllBugReports().contains(bugReport));
        assertEquals("trigger", bugReport.getTrigger());
        assertEquals("error", bugReport.getError());
        assertEquals("stackTrace", bugReport.getStacktrace());
        assertEquals("title", bugReport.getTitle());

        assertTrue(cmd.toString().contains(bugReport.getTitle()));
        assertTrue(cmd.toString().contains("Created") || cmd.toString().contains("created"));

        // 3. undo
        assertTrue(cmd.undo());

        // test
        assertFalse(subsys.getAllBugReports().contains(bugReport));
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsys, dev, "title", "desc", null, PList.<BugReport>empty(), null, false);

        // 2. Exec()
        BugReport bugReport = cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateBugReportModelCmd#CreateBugReportModelCmd(bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, purecollections.PList, bugtrap03.bugdomain.Milestone, boolean)}
     * with subsystem == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_SubsystemNull() {
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(null, dev, "title", "desc", null, PList.<BugReport>empty(), null, false);
    }

    /**
     * Test
     * {@link CreateBugReportModelCmd#CreateBugReportModelCmd(bugtrap03.bugdomain.Subsystem, bugtrap03.bugdomain.usersystem.User, java.lang.String, java.lang.String, java.util.GregorianCalendar, purecollections.PList, bugtrap03.bugdomain.Milestone, boolean, java.lang.String, java.lang.String, java.lang.String)}
     * with subsystem == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_SubsystemNull() {
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(null, dev, "title", "desc", null, PList.<BugReport>empty(), new Milestone(2, 0, 0), false, "trigger", "stackTrace", "error");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsys, admin, "title", "desc", null, PList.<BugReport>empty(), null, false);
        cmd.exec();
    }
}
