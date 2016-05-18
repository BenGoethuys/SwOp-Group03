package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.model.DataModel;
import org.junit.Before;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Admin
 */
public class SelectTagsCmdTest {

    private DataModel model;
    private Administrator admin;
    private Developer lead;
    private Project project1;
    private Project project2;
    private Subsystem subsys1;
    private Subsystem subsys2;
    private Subsystem subsys11;

    private static int counter = Integer.MIN_VALUE;

    @Before
    public void setUp() throws PermissionException {
        model = new DataModel();
        admin = model.createAdministrator("Jammer" + counter, "first", "last");
        lead = model.createDeveloper("Jamme1r" + counter, "first", "last");
        project1 = model.createProject(new VersionID(), "Project1", "ProjDesc", lead, 100, admin);
        project2 = model.createProject(new VersionID(), "Project2", "projDesc", lead, 100, admin);
        subsys1 = model.createSubsystem(admin, project1, "subsys1", "desc");
        subsys2 = model.createSubsystem(admin, project1, "subsys2", "desc");
        model.createSubsystem(admin, project2, "testExtra", "desc");
        subsys11 = model.createSubsystem(admin, subsys1, "subsys11", "desc");

        counter++;
    }

    @Test
    public void testExec_multi_NY() throws PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectTagsCmd cmd = new SelectTagsCmd();

        addOptions(question);
        question.add("I choose: ");
        answer.add("NEW");
        question.add("Tag NEW added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("y");
        addOptions(question);
        question.add("I choose: ");
        answer.add("2");
        question.add("Tag NOT_A_BUG added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("n");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        EnumSet<Tag> set = cmd.exec(scan, model, null);

        assertTrue(set.contains(Tag.NEW));
        assertTrue(set.contains(Tag.NOT_A_BUG));
        assertEquals(2, set.size());
    }

    @Test
    public void testExec_NotNY() throws PermissionException, CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectTagsCmd cmd = new SelectTagsCmd();

        addOptions(question);
        question.add("I choose: ");
        answer.add("NEW");
        question.add("Tag NEW added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("y");
        addOptions(question);
        question.add("I choose: ");
        answer.add("2");
        question.add("Tag NOT_A_BUG added to subscription.");
        question.add("Do you wish to select another tag? Y/N");
        answer.add("k");
        question.add("Invalid input, selecting of tags considered complete.");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        EnumSet<Tag> set = cmd.exec(scan, model, null);

        assertTrue(set.contains(Tag.NEW));
        assertTrue(set.contains(Tag.NOT_A_BUG));
        assertEquals(2, set.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_nullScan() throws CancelException {
        SelectTagsCmd cmd = new SelectTagsCmd();
        EnumSet<Tag> set = cmd.exec(null, model, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExec_nullModel() throws CancelException {
        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        SelectTagsCmd cmd = new SelectTagsCmd();

        addOptions(question);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        EnumSet<Tag> set = cmd.exec(scan, null, null);
    }

    /**
     * Add the options questions.
     *
     * @param questions
     */
    private void addOptions(ArrayDeque<String> questions) {
        questions.add("Please select tag.");
        questions.add("Available options:");
        questions.add("0. NEW");
        questions.add("1. ASSIGNED");
        questions.add("2. NOT_A_BUG");
        questions.add("3. UNDER_REVIEW");
        questions.add("4. CLOSED");
        questions.add("5. RESOLVED");
        questions.add("6. DUPLICATE");
    }

}
