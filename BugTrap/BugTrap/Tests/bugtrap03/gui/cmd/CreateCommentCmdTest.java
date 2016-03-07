package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Comment;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class CreateCommentCmdTest {

    @Test
    public void test() {
        //Setup variables.
        DataModel model = new DataModel();
        User lead = model.createDeveloper("trollol", "Luky", "Luke");
        User issuer = model.createIssuer("Cows", "Fly", "High");
        User admin = model.createAdministrator("Admiral", "Kwinten", "JK");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateCommentCmd cmd = new CreateCommentCmd();

        //Setup scenario
        question.add("Create or clone a new project?");
        question.add("Create or clone: ");
        answer.add("expect wrong input");
        question.add("I choose: ");
        //answer.add(leadName);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        //Comment comment = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(model.getProjectList().size(), 1);
       
    }

}
