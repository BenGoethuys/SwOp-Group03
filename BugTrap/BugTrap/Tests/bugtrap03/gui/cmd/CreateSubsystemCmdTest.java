/**
 *
 */
package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.cmd.general.CancelException;
import static org.junit.Assert.*;

import bugtrap03.model.DataModel;
import java.util.ArrayDeque;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * TODO
 *
 * @author Group 03
 *
 */
public class CreateSubsystemCmdTest {

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.CreateSubsystemCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}.
     * Using index to chose which abstractSystem to use as the parent.
     */
    @Test
    public void testExecIndex() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead054", "Luky", "Luke");
        Issuer issuer = model.createIssuer("noDev054", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin054", "adminT", "bie");
        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        Subsystem subsystemA1 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateSubsystemCmd cmd = new CreateSubsystemCmd();

        //Setup scenario
        question.add("Available projects and subsystems:");
        question.add("0. " + projectA.getName());
        question.add("1. " + subsystemA1.getName());
        question.add("2. " + subsystemA2.getName());
        question.add("3. " + subsystemA3.getName());
        question.add("4. " + subsystemA3_1.getName());
        question.add("5. " + subsystemA3_2.getName());
        question.add("6. " + proj1.getName());
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("tester");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(subsystemA3_1.getName());
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("Subsystem name:");
        answer.add("lol");
        question.add("Subsystem description:");
        answer.add("ping pong");
        question.add("Created subsystem " + "lol");
        
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        Subsystem chosen = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(chosen.getName(), "lol");
        assertEquals(chosen.getDescription(), "ping pong");
        assertEquals(model.getAllProjectsAndSubsystems().size(), 8);
        assertTrue(model.getAllProjectsAndSubsystems().contains(chosen));
        assertTrue(model.getAllSubsystems(projectA).contains(chosen));
    }

    
    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.CreateSubsystemCmd#exec(bugtrap03.gui.terminal.TerminalScanner, DataModel, bugtrap03.bugdomain.usersystem.User)}.
     * Using names to chose which abstractSystem to use as the parent.
     */
    @Test
    public void testExecName() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead055", "Luky", "Luke");
        Issuer issuer = model.createIssuer("noDev055", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin055", "adminT", "bie");
        Project projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        Project proj1 = model.createProject("ProjectTest1", "Project for testing 1", lead, 1000, admin);

        // make subsystems
        Subsystem subsystemA1 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateSubsystemCmd cmd = new CreateSubsystemCmd();

        //Setup scenario
        question.add("Available projects and subsystems:");
        question.add("0. " + projectA.getName());
        question.add("1. " + subsystemA1.getName());
        question.add("2. " + subsystemA2.getName());
        question.add("3. " + subsystemA3.getName());
        question.add("4. " + subsystemA3_1.getName());
        question.add("5. " + subsystemA3_2.getName());
        question.add("6. " + proj1.getName());
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("tester");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("4");
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("Subsystem name:");
        answer.add("lol");
        question.add("Subsystem description:");
        answer.add("ping pong");
        question.add("Created subsystem " + "lol");
        
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        Subsystem chosen = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(chosen.getName(), "lol");
        assertEquals(chosen.getDescription(), "ping pong");
        assertEquals(model.getAllProjectsAndSubsystems().size(), 8);
        assertTrue(model.getAllProjectsAndSubsystems().contains(chosen));
        assertTrue(model.getAllSubsystems(projectA).contains(chosen));
    }

}
