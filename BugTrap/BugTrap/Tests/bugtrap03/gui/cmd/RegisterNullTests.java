package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notification.AbstractSystemSubject;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.gui.cmd.general.RegisterFromASCmd;
import bugtrap03.gui.cmd.general.SelectTagsCmd;
import bugtrap03.model.DataModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertTrue;

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

    }

    private static int index;

    private static DataModel model;
    private static ArrayDeque<String> question;
    private static ArrayDeque<String> answer;

    private static Developer developerRegisterCmd;

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
        generalNullScan(new RegisterFromASCmd(new AbstractSystemSubjectDummy()));
        //FIXME: use of non DomainAPI AbstractSystemSubjectDummy -> needs to be in same package ! -> copy to this package also?
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull4() throws Exception {
        generalNullMode(new RegisterFromASCmd(new AbstractSystemSubjectDummy()));
        //FIXME: (Ben CHECK IF FIXED) use of non DomainAPI AbstractSystemSubjectDummy -> needs to be in same package ! -> copy to this package also?
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNull4() throws Exception {
        generalNullUser(new RegisterFromASCmd(new AbstractSystemSubjectDummy()));
        //FIXME: (Ben CHECK IF FIXED) use of non DomainAPI AbstractSystemSubjectDummy -> needs to be in same package ! -> copy to this package also?
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanNull5() throws Exception {
        generalNullScan(new SelectTagsCmd());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModelNull5() throws Exception {
        generalNullMode(new SelectTagsCmd());

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

    class AbstractSystemSubjectDummy extends AbstractSystemSubject {

        @Override
        public void notifyCreationSubs(BugReport br) {
            this.updateCreationSubs(br);
        }

        @Override
        public String getSubjectName() {
            return "naam";
        }

        @Override
        public void notifyTagSubs(BugReport br) {
            this.updateTagSubs(br);
        }

        @Override
        public void notifyCommentSubs(BugReport br) {
            this.updateCommentSubs(br);
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

    }
}
