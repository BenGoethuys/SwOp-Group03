package bugtrap03.gui.cmd;

import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.cmd.general.CancelException;
import java.util.ArrayDeque;
import java.util.GregorianCalendar;
import org.junit.Test;
import purecollections.PList;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 *
 * @author Admin
 */
public class CreateBugReportCmdTest {

    //TODO: Do not make use of direct methodes for subsystems etc.
    @Test
    public void testExecByIndex() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();

        Developer lead = model.createDeveloper("meGoodLead125", "Luky", "Luke");
        Developer maria = model.createDeveloper("Mae146", "Maria", "developer");
        Issuer charlie = model.createIssuer("noDev125", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin125", "adminT", "bie");
        // create projectA
        Project projectA = model.createProject("ProjectA", "Description of projectA", lead, 10000, admin);
        // add asked roles
        projectA.setRole(lead, lead, Role.PROGRAMMER);
        projectA.setRole(lead, maria, Role.TESTER);
        // make subsystems
        projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = model.createSubsystem(projectA, new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = model.createSubsystem(projectA, new VersionID(), "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = model.createSubsystem(subsystemA3, new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
        Subsystem subsystemA3_2 = model.createSubsystem(subsystemA3_1, new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");
        // make bug report 2
        BugReport bugRep2 = model.createBugReport(charlie, "Crash while processing user input", "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15), PList.<BugReport>empty(), subsystemA3_1);
        bugRep2.addUser(lead, lead);
        bugRep2.addUser(lead, maria);
        // mak bug report 3
        BugReport bugRep1 = model.createBugReport(lead, "SubsystemA2 feezes", "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4), PList.<BugReport>empty(), subsystemA2);

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateBugReportCmd cmd = new CreateBugReportCmd();

        //Setup scenario
        question.add("Available projects:");
        question.add("0. ProjectA");
        question.add("I choose: ");
        answer.add("test wrong input");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add(projectA.getDetails());
        question.add("Available subsystems:");
        question.add("0. SubsystemA1");
        question.add("1. SubsystemA2");
        question.add("2. SubsystemA3");
        question.add("3. SubsystemA3.1");
        question.add("4. SubsystemA3.2");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("3");
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("BugReport title:");
        answer.add("BR Title");
        question.add("BugReport description:");
        answer.add("tester description");
        question.add("Choose a dependency.");
        question.add("Available bugReports:");
        question.add("0. " + bugRep1.getTitle() + " " + bugRep1.getUniqueID());
        question.add("1. " + bugRep2.getTitle() + " " + bugRep2.getUniqueID());
        question.add("I choose: (leave blank if done)");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("wrong input here");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("0");
        question.add("Added dependency: " + bugRep1.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add(bugRep2.getTitle());
        question.add("Added dependency: " + bugRep2.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add("");
        question.add("Ended selection.");
        
        //answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        BugReport bugReport = cmd.exec(scan, model, maria);

        //Test effects.
    }
    
    @Test
    public void testExecByName() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();

        Developer lead = model.createDeveloper("meGoodLead146", "Luky", "Luke");
        Developer maria = model.createDeveloper("Mae46", "Maria", "developer");
        Issuer charlie = model.createIssuer("noDev146", "BadLuck", "Luke");
        Administrator admin = model.createAdministrator("admin146", "adminT", "bie");
        // create projectA
        Project projectA = model.createProject("ProjectA", "Description of projectA", lead, 10000, admin);
        // add asked roles
        projectA.setRole(lead, lead, Role.PROGRAMMER);
        projectA.setRole(lead, maria, Role.TESTER);
        // make subsystems
        projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
        Subsystem subsystemA2 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA2", "Description of susbsystem A2");
        Subsystem subsystemA3 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA3", "Description of susbsystem A3");
        Subsystem subsystemA3_1 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
        subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");
        // make bug report 2
        BugReport bugRep2 = subsystemA3_1.addBugReport(charlie, "Crash while processing user input", "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15), PList.<BugReport>empty());
        bugRep2.addUser(lead, lead);
        bugRep2.addUser(lead, maria);
        // mak bug report 3
        BugReport bugRep1 = subsystemA2.addBugReport(lead, "SubsystemA2 feezes", "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4), PList.<BugReport>empty());

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateBugReportCmd cmd = new CreateBugReportCmd();

        //Setup scenario
        question.add("Available projects:");
        question.add("0. ProjectA");
        question.add("I choose: ");
        answer.add(" test wrong input");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add(projectA.getName());
        question.add("You have chosen:");
        question.add(projectA.getDetails());
        question.add("Available subsystems:");
        question.add("0. SubsystemA1");
        question.add("1. SubsystemA2");
        question.add("2. SubsystemA3");
        question.add("3. SubsystemA3.1");
        question.add("4. SubsystemA3.2");
        question.add("I choose: ");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("20");
        question.add("Invalid input.");
        question.add("I choose: ");
        answer.add("3");
        question.add("You have chosen:");
        question.add(subsystemA3_1.getName());
        question.add("BugReport title:");
        answer.add("BR Title");
        question.add("BugReport description:");
        answer.add("tester description");
        question.add("Choose a dependency.");
        question.add("Available bugReports:");
        question.add("0. " + bugRep1.getTitle() + " " + bugRep1.getUniqueID());
        question.add("1. Crash while processing user input 0");
        question.add("I choose: (leave blank if done)");
        answer.add("5");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("-1");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("wrong input here");
        question.add("Invalid input.");
        question.add("I choose: (leave blank if done)");
        answer.add("0");
        question.add("Added dependency: " + bugRep1.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add(bugRep2.getTitle());
        question.add("Added dependency: " + bugRep2.getTitle());
        question.add("I choose: (leave blank if done)");
        answer.add("");
        question.add("Ended selection.");
        
        //answer.add(leadName);
        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        BugReport bugReport = cmd.exec(scan, model, maria);

        //Test effects.
    }
}