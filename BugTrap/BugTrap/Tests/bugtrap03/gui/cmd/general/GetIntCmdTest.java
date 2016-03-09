package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.CreateSubsystemCmd;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class GetIntCmdTest {

    @Test
    public void testExec() throws CancelException {
        DataModel model = new DataModel();

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetIntCmd cmd = new GetIntCmd();

        //Setup scenario
        question.add("Give number: ");
        answer.add("wrongInput");
        question.add("Invalid input, please enter a number");
        answer.add("-5.2");
        question.add("Invalid input, please enter a number");
        answer.add("-5");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        int chosen = cmd.exec(scan, null, null);

        //Test effects.
        assertEquals(chosen, -5);
    }

    @Test(expected = CancelException.class)
    public void testAbortExec() throws CancelException {
        DataModel model = new DataModel();

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        GetIntCmd cmd = new GetIntCmd();

        //Setup scenario
        question.add("Give number: ");
        answer.add("wrongInput");
        question.add("Invalid input, please enter a number");
        answer.add("abort");

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        int chosen = cmd.exec(scan, null, null);
    }

}
