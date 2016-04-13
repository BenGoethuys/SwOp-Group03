package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;

import org.junit.Before;
import org.junit.Test;
import purecollections.PList;

import static org.junit.Assert.*;

/**
 *
 * @author Group 03
 */
public class SetTagForBugReportModelCmdTest {

    private static int counter = Integer.MIN_VALUE;

    private DataModel model;
    private Administrator admin;
    private Project proj;
    private Developer dev;
    private Subsystem subsys;
    private BugReport bugRep;
    private BugReport bugRepWrongState;

    private Developer dev2;
    private Developer dev3;

    private PList<Developer> devList;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("BlubBlabBlob18" + counter, "first", "last");
        dev = model.createDeveloper("DeveloperOverHere18" + counter, "first", "last");
        proj = model.createProject("TestProject50", "Testing stuff over here", dev, 50, admin);
        subsys = model.createSubsystem(admin, proj, "fancy name", "fancy description");
        bugRep = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);
        bugRepWrongState = model.createBugReport(subsys, dev, "title", "desc", PList.<BugReport>empty(), null, false);

        model.assignToProject(proj, dev, dev, Role.TESTER);
        model.assignToProject(proj, dev, dev, Role.PROGRAMMER);

        dev2 = model.createDeveloper("Developer2OverHere18" + counter, "first", "last");
        dev3 = model.createDeveloper("Developer3OverHere18" + counter, "first", "last");

        devList = PList.<Developer>empty();
        devList = devList.plus(dev2);
        devList = devList.plus(dev3);

        model.addUsersToBugReport(dev, bugRep, devList);
        model.addTest(bugRep, dev, "test here");

        counter++;
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateInvalidBugReportNull(){
        new SetTagForBugReportModelCmd(null, Tag.NOT_A_BUG, dev);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateInvalidBugReportTerminated(){
        proj.setTerminated(true);
        assertTrue(bugRep.isTerminated());
        new SetTagForBugReportModelCmd(bugRep, Tag.NOT_A_BUG, dev);
    }

    @Test
    public void testGoodScenarioCons() throws PermissionException {
        Tag old = bugRep.getTag();
        Tag tag = Tag.NOT_A_BUG;
        // 1. Add
        SetTagForBugReportModelCmd cmd = new SetTagForBugReportModelCmd(bugRep, tag, dev);

        // test
        assertTrue(cmd.toString().contains("Set the Tag"));
        assertTrue(cmd.toString().contains(tag.name()));
        assertTrue(cmd.toString().contains("for BugReport"));
        assertTrue(cmd.toString().contains(bugRep.getTitle()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        cmd.exec();

        // test
        assertEquals(tag, bugRep.getTag());
        assertTrue(cmd.isExecuted());

        // 3. undo()
        assertTrue(cmd.undo());

        // test
        assertNotEquals(tag, bugRep.getTag());
        assertEquals(old, bugRep.getTag());
    }

    @Test (expected = IllegalStateException.class)
    public void testExecTwice() throws PermissionException {
        SetTagForBugReportModelCmd cmd = new SetTagForBugReportModelCmd(bugRep, Tag.NOT_A_BUG, dev);
        cmd.exec();

        // invalid second execute
        cmd.exec();
    }
}
