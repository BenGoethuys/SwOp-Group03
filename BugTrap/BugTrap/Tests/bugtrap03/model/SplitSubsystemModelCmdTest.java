package bugtrap03.model;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import java.util.Arrays;
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
public class SplitSubsystemModelCmdTest {

    private Administrator admin;
    private Developer lead;
    private DataModel model;
    private Project projectA;
    private Project projectB;
    private Subsystem subsystemA1;
    private Subsystem subsystemA2;
    private Subsystem subsystemA3;
    private Subsystem subsystemA3_1;
    private Subsystem subsystemA3_2;
    private BugReport bugRep1;

    static int counter = Integer.MIN_VALUE;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("SplitSub_1" + counter, "Luky", "Luke");
        admin = model.createAdministrator("SplitSub_2" + counter, "adminT", "bie");
        Issuer issuer = model.createIssuer("SplitSub_3" + counter, "BMW", "looks", "nice");
        projectA = model.createProject(new VersionID(), "SplitSub3", "Project for testing 0", lead, 500, admin);

        // make subsystems
        subsystemA1 = model.createSubsystem(admin, projectA, "SubsystemA1", "Description of subsystem A1");
        subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of subsystem A2");
        subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of subsystem A3");
        subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of subsystem A3.1");
        subsystemA3_2 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of subsystem A3.2");

        bugRep1 = model.createBugReport(subsystemA3, issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport>empty(), null, 1, false);

        counter++;
    }

    /**
     *
     * @throws PermissionException
     */
    @Test
    public void testSplitSubsystem_Regular() throws PermissionException {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = PList.<Subsystem>empty();
        keepSubs = keepSubs.plus(subsystemA3_1);
        PList<BugReport> keepBR = PList.<BugReport>empty();
        keepBR = keepBR.plus(bugRep1);

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, admin);

        // test
        assertTrue(cmd.toString().contains("Split subsystem"));
        assertTrue(cmd.toString().contains(subsystemA3.getName()));
        assertFalse(cmd.undo()); //can't undo yet.
        assertFalse(cmd.isExecuted());

        // 2. Exec()
        Subsystem subR = cmd.exec();
        Subsystem[] result = new Subsystem[]{subsystemA3, subR};
        int index1 = 0;
        int index2 = 1;

        // test
        assertTrue(cmd.toString().contains("Split subsystem"));
        assertTrue(cmd.toString().contains(subsystemA3.getName()));
        assertTrue(cmd.toString().contains(subR.getName()));
        assertTrue(cmd.isExecuted());

        assertTrue(result[index1].getName().equals("NEW1"));
        assertTrue(result[index2].getName().equals("NEW2"));
        assertTrue(result[index1].getDescription().equals("DescriptionNew1"));
        assertTrue(result[index2].getDescription().equals("DescriptionNew2"));
        assertTrue(result[index1].getAllSubsystems().contains(subsystemA3_1));
        assertFalse(result[index1].getAllSubsystems().contains(subsystemA3_2));
        assertTrue(result[index2].getAllSubsystems().contains(subsystemA3_2));
        assertFalse(result[index2].getAllSubsystems().contains(subsystemA3_1));
        assertTrue(result[index1].getAllBugReports().contains(bugRep1));
        assertFalse(result[index2].getAllBugReports().contains(bugRep1));

        //Test subs
        result[index1].getCommentSubs().equals(subsystemA3.getCommentSubs());
        result[index2].getCommentSubs().equals(subsystemA3.getCommentSubs());

        result[index1].getCreationSubs().equals(subsystemA3.getCreationSubs());
        result[index2].getCreationSubs().equals(subsystemA3.getCreationSubs());

        result[index1].getMilestoneSubs().equals(subsystemA3.getMilestoneSubs());
        result[index2].getMilestoneSubs().equals(subsystemA3.getMilestoneSubs());

        result[index1].getTagSubs().equals(subsystemA3.getTagSubs());
        result[index2].getTagSubs().equals(subsystemA3.getTagSubs());

        result[index1].getVersionIDSubs().equals(subsystemA3.getVersionIDSubs());
        result[index2].getVersionIDSubs().equals(subsystemA3.getVersionIDSubs());

        // 3. undo()
        assertTrue(cmd.undo());

        assertTrue(subR.isTerminated());
        assertTrue(subsystemA3.getName().equals("SubsystemA3"));
        assertTrue(subsystemA3.getDescription().equals("Description of subsystem A3"));
        assertTrue(subsystemA3.getAllSubsystems().contains(subsystemA3_1));
        assertTrue(subsystemA3.getAllSubsystems().contains(subsystemA3_2));
        assertTrue(subsystemA3.getAllBugReports().contains(bugRep1));

    }

    /**
     *
     * @throws PermissionException
     */
    @Test
    public void testSplitSubsystem_EmptyLists() throws PermissionException {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = null;
        PList<BugReport> keepBR = null;

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, admin);

        Subsystem subR = cmd.exec();

        Subsystem[] result = new Subsystem[]{subsystemA3, subR};
        int index1 = 0;
        int index2 = 1;

        assertTrue(result[index1].getName().equals("NEW1"));
        assertTrue(result[index2].getName().equals("NEW2"));
        assertTrue(result[index1].getDescription().equals("DescriptionNew1"));
        assertTrue(result[index2].getDescription().equals("DescriptionNew2"));
        assertFalse(result[index1].getAllSubsystems().contains(subsystemA3_1));

        assertFalse(result[index1].getAllSubsystems().contains(subsystemA3_2));
        assertTrue(result[index2].getAllSubsystems().contains(subsystemA3_2));

        assertTrue(result[index2].getAllSubsystems().contains(subsystemA3_1));
        assertFalse(result[index1].getAllBugReports().contains(bugRep1));
        assertTrue(result[index2].getAllBugReports().contains(bugRep1));

        //Test subs
        result[index1].getCommentSubs().equals(subsystemA3.getCommentSubs());
        result[index2].getCommentSubs().equals(subsystemA3.getCommentSubs());

        result[index1].getCreationSubs().equals(subsystemA3.getCreationSubs());
        result[index2].getCreationSubs().equals(subsystemA3.getCreationSubs());

        result[index1].getMilestoneSubs().equals(subsystemA3.getMilestoneSubs());
        result[index2].getMilestoneSubs().equals(subsystemA3.getMilestoneSubs());

        result[index1].getTagSubs().equals(subsystemA3.getTagSubs());
        result[index2].getTagSubs().equals(subsystemA3.getTagSubs());

        result[index1].getVersionIDSubs().equals(subsystemA3.getVersionIDSubs());
        result[index2].getVersionIDSubs().equals(subsystemA3.getVersionIDSubs());
    }

    @Test(expected = PermissionException.class)
    public void testCons_NoPerm() throws PermissionException {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = PList.<Subsystem>empty();
        keepSubs = keepSubs.plus(subsystemA3_1);
        PList<BugReport> keepBR = PList.<BugReport>empty();
        keepBR = keepBR.plus(bugRep1);

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, lead);
        cmd.exec();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_UserNull() {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = PList.<Subsystem>empty();
        keepSubs = keepSubs.plus(subsystemA3_1);
        PList<BugReport> keepBR = PList.<BugReport>empty();
        keepBR = keepBR.plus(bugRep1);

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCons_SubsystemNull() {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = PList.<Subsystem>empty();
        keepSubs = keepSubs.plus(subsystemA3_1);
        PList<BugReport> keepBR = PList.<BugReport>empty();
        keepBR = keepBR.plus(bugRep1);

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(null, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, lead);
    }

    /**
     * Test the exec() for a second time.
     *
     * @throws PermissionException Never
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalExec() throws PermissionException {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = null;
        PList<BugReport> keepBR = null;

        // 1. Create
        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, admin);
        cmd.exec();

        // 2. Exec()
        cmd.exec();

        cmd.exec(); // <-- error.
    }

    @Test
    public void testSplitSubsystem_Terminated() throws PermissionException {
        String newName1 = "NEW1";
        String newName2 = "NEW2";
        String newDesc1 = "DescriptionNew1";
        String newDesc2 = "DescriptionNew2";
        PList<Subsystem> keepSubs = null;
        PList<BugReport> keepBR = null;

        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystemA3, newName1, newDesc1, newName2, newDesc2, keepSubs, keepBR, admin);

        model.deleteProject(admin, projectA);

        boolean happened = false;
        try {
            Subsystem subR = cmd.exec();
        } catch (IllegalStateException ex) {
            happened = true;
        }
        assertTrue(happened);

        assertTrue(subsystemA3.getName().equals("SubsystemA3"));
        assertTrue(subsystemA3.getDescription().equals("Description of subsystem A3"));
        assertTrue(subsystemA3.getAllSubsystems().contains(subsystemA3_1));
        assertTrue(subsystemA3.getAllSubsystems().contains(subsystemA3_2));
        assertTrue(subsystemA3.getAllBugReports().contains(bugRep1));
    }

}
