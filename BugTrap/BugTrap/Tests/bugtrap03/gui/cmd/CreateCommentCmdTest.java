package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Comment;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
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
    public void testExecIndexTitle() throws PermissionException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("trollol", "Luky", "Luke");
        Issuer issuer = model.createIssuer("Cows", "Fly", "High");
        Administrator admin = model.createAdministrator("Admiral", "Kwinten", "JK");

        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        Subsystem subsystemA1 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");

        
        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateCommentCmd cmd = new CreateCommentCmd();

        //Setup scenario
        addSearchModeOptions(question);
        answer.add("expect wrong input");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("trollol error");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        question.add("Please enter the required search term ...");
        question.add("enter text: tester");
        question.add("No bugreports in the system match the search term");
        addSearchModeOptions(question);
        answer.add("title");
        question.add("Please enter the required search term ...");
        question.add("enter text: ");
        answer.add("A2");
        question.add("Please select a bug report:");
        question.add("Available bugReports:");

        //answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        //Comment comment = cmd.exec(scan, model, admin);
        //Test effects.
        assertEquals(model.getProjectList().size(), 1);

    }

    private void addSearchModeOptions(ArrayDeque<String> question) {
        question.add("Please select a search mode:");
        question.add("0. title");
        question.add("1. description");
        question.add("2. creator");
        question.add("3. assigned");
        question.add("4. uniqueId");
    }

}
