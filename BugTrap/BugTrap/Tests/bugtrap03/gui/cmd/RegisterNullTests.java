package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.gui.cmd.general.SelectTagsCmd;
import bugtrap03.model.DataModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

/**
 * @author Group 03
 */
public class RegisterNullTests {

    @BeforeClass
    public static void setUpBeforeClass() {
        index = 1000;
    }

    @Before
    public void setup() throws Exception {
        index++;
        model = new DataModel();
        question = new ArrayDeque<>();
        answer = new ArrayDeque<>();
        developerRegisterCmd = model.createDeveloper("developerRegisterCmd" + index, "firstname", "lastname");
        adminRegisterCmd = model.createAdministrator("adminRegisterCmd" + index, "firstname", "lastname");
        project = model.createProject(new VersionID(), "title", "desc", developerRegisterCmd, 1000, adminRegisterCmd);

    }

    private static int index;

    private static DataModel model;
    private static ArrayDeque<String> question;
    private static ArrayDeque<String> answer;

    private static Project project;
    private static Developer developerRegisterCmd;
    private static Administrator adminRegisterCmd;

    private static Cmd cmd;

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull1() throws Exception {
        generalNullScan(new RegisterFromBugReportCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull1() throws Exception {
        generalNullMode(new RegisterFromBugReportCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull1() throws Exception {
        generalNullUser(new RegisterFromBugReportCmd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull2() throws Exception {
        generalNullScan(new RegisterFromProjectCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull2() throws Exception {
        generalNullMode(new RegisterFromProjectCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull2() throws Exception {
        generalNullUser(new RegisterFromProjectCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull3() throws Exception {
        generalNullScan(new RegisterFromSubsystemCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull3() throws Exception {
        generalNullMode(new RegisterFromSubsystemCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull3() throws Exception {
        generalNullUser(new RegisterFromSubsystemCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull4() throws Exception {
        generalNullScan(new RegisterFromASSubjCmd(project));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull4() throws Exception {
        generalNullMode(new RegisterFromASSubjCmd(project));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull4() throws Exception {
        generalNullUser(new RegisterFromASSubjCmd(project));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull5() throws Exception {
        generalNullScan(new SelectTagsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull5() throws Exception {
        generalNullMode(new SelectTagsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull6() throws Exception {
        generalNullScan(new ShowNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull6() throws Exception {
        generalNullMode(new ShowNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull6() throws Exception {
        generalNullUser(new ShowNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull7() throws Exception {
        generalNullScan(new UnregisterFromNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull7() throws Exception {
        generalNullMode(new UnregisterFromNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull7() throws Exception {
        generalNullUser(new UnregisterFromNotificationsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull8() throws Exception {
        generalNullScan(new RegisterFromProjSubjectCmd(project));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull8() throws Exception {
        generalNullMode(new RegisterFromProjSubjectCmd(project));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull8() throws Exception {
        generalNullUser(new RegisterFromProjSubjectCmd(project));
    }

    private static void generalNullUser(Cmd cmd) throws Exception {
        //set up scenario
        answer.add("dummy");
        question.add("dummy");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, model, null);
        // Test effects.
    }

    private static void generalNullMode(Cmd cmd) throws Exception {
        //set up scenario
        answer.add("dummy");
        question.add("dummy");
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);
        // Execute scenario
        cmd.exec(scan, null, developerRegisterCmd);
        // Test effects.
    }

    private static void generalNullScan(Cmd cmd) throws Exception {
        // Execute scenario
        cmd.exec(null, model, developerRegisterCmd);
        // Test effects.
    }

}
