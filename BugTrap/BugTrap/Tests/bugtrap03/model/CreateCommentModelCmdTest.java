package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class CreateCommentModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Subsystem subsys;
    private BugReport bugRep;
    private Developer dev;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob5" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere5" + counter, "first", "last");
        proj = model.createProject(new VersionID(), "TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, 1, false);

        counter++;
    }

    /**
     * Test
     * {@link CreateCommentModelCmd#CreateCommentModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, java.lang.String)}
     * in a default scenario.
     *
     * @throws PermissionException Never
     */
    @Test
    public void testGoodScenarioCons1() throws PermissionException {
        // 1. Create
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, bugRep, "text here");

        // test
        assertTrue(cmd.toString().contains("comment"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Comment comment = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("comment"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertTrue(cmd.isExecuted());
        assertEquals(1, bugRep.getCommentList().size());
        assertTrue(bugRep.getCommentList().contains(comment));

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(bugRep.getCommentList().contains(comment));
    }

    /**
     * Test
     * {@link CreateBugReportModelCmd#CreateBugReportModelCmd(Subsystem, bugtrap03.bugdomain.usersystem.User, String, String, java.util.GregorianCalendar, PList, bugtrap03.bugdomain.Milestone, double, boolean, String, String, String)}
     * in a default scenario
     *
     * @throws PermissionException
     */
    @Test
    public void testGoodScenarioCons2() throws PermissionException {
        Comment parentComment = model.createComment(dev, bugRep, "parent Comment here");
        // 1. Create
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, parentComment, "text here");

        // test
        assertTrue(cmd.toString().contains("comment"));
        assertTrue(cmd.toString().contains(parentComment.getText()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Comment comment = cmd.exec();

        // test
        assertTrue(cmd.toString().contains("comment"));
        assertTrue(cmd.toString().contains(parentComment.getText()));
        assertTrue(cmd.isExecuted());
        assertEquals(2, bugRep.getAllComments().size());
        assertTrue(bugRep.getAllComments().contains(comment));
        assertTrue(parentComment.getSubComments().contains(comment));

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertFalse(bugRep.getAllComments().contains(comment));
        assertEquals(1, bugRep.getAllComments().size());
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        // 1. Create
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, bugRep, "text here");

        // 2. Exec()
        Comment comment = cmd.exec();

        cmd.exec(); // <-- error.
    }

    /**
     * Test
     * {@link CreateCommentModelCmd#CreateCommentModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.Comment, java.lang.String)}
     * with parentComment == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons2_CommentNull() {
        Comment comm = null;
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, comm, "text here");
    }

    /**
     * Test
     * {@link CreateCommentModelCmd#CreateCommentModelCmd(bugtrap03.bugdomain.usersystem.User, bugtrap03.bugdomain.bugreport.BugReport, java.lang.String)}
     * with bugreport == null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons1_BugReportNull() {
        BugReport rep = null;
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, rep, "text here");
    }

    /**
     * Test exec() with an administrator who has no permissions.
     *
     * @throws PermissionException Always
     */
    @Test(expected = PermissionException.class)
    public void testNoPermissions() throws PermissionException {
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(admin, bugRep, "text here");
        cmd.exec();
    }

    /**
     * Test constructor with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCons_BugReportTerminated() throws PermissionException {
        model.deleteProject(admin, proj);
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, bugRep, "text here");
    }

    /**
     * Test exec() with terminated bugReport
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExec_BugReportTerminated() throws PermissionException {
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(dev, bugRep, "text here");
        model.deleteProject(admin, proj);
        cmd.exec();
    }
}
