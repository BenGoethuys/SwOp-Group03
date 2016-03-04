/**
 *
 */
package bugtrap03.gui.cmd;

import bugtrap03.DataModel;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import java.util.ArrayDeque;
import static java.util.GregorianCalendar.DATE;
import static java.util.GregorianCalendar.MONTH;
import static java.util.GregorianCalendar.YEAR;
import java.util.GregorianCalendar;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import testCollection.MultiByteArrayInputStream;
import testCollection.TerminalTestScanner;

/**
 * TODO
 *
 * @author Group 03
 *
 */
public class CreateProjectCmdTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.CreateProjectCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.DataModel, bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testCreateExec() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        User lead = model.createDeveloper("meGoodLead", "Luky", "Luke");
        User issuer = model.createIssuer("noDev", "BadLuck", "Luke");
        User admin = model.createAdministrator("admin", "adminT", "bie");

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateProjectCmd cmd = new CreateProjectCmd();

        String projName = "test projectName";
        String projDesc = " ";
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.MONTH, 1);
        String projDate = date.get(GregorianCalendar.YEAR) + "-" + (date.get(GregorianCalendar.MONTH) + 1) + "-" + date.get(GregorianCalendar.DATE);
        String projBudget = "50";
        String leadName = lead.getUsername();

        //Setup scenario
        question.add("Create or clone a new project?");
        question.add("Create or clone: ");
        answer.add("expect wrong input");
        question.add("Invalid input. Use create or clone.");
        question.add("Create or clone: ");
        answer.add("cReate");
        question.add("Project name:");
        answer.add(projName);
        question.add("Project description:");
        answer.add(projDesc);
        question.add("Project starting date (YYYY-MM-DD):");
        answer.add("2015:10:10");
        question.add("Invalid input.");
        question.add("Project starting date (YYYY-MM-DD):");
        answer.add(projDate);
        question.add("Project budget estimate:");
        answer.add("lol");
        question.add("Invalid input.");
        question.add("Project budget estimate:");
        answer.add(projBudget);
        question.add("Chose a lead developer.");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        question.add("I chose: ");
        answer.add("25");
        question.add("Invalid input.");
        question.add("I chose: ");
        answer.add("test");
        question.add("Invalid input.");
        question.add("I chose: ");
        answer.add(leadName);

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        Project project = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(leadName, project.getLead().getUsername());
        assertEquals(projName, project.getName());
        assertEquals(projDesc, project.getDescription());
        assertEquals(date.get(GregorianCalendar.YEAR), project.getStartDate().get(GregorianCalendar.YEAR));
        assertEquals(date.get(GregorianCalendar.MONTH), project.getStartDate().get(GregorianCalendar.MONTH));
        assertEquals(date.get(GregorianCalendar.DATE), project.getStartDate().get(GregorianCalendar.DATE));
        assertEquals(projBudget, Long.toString(project.getBudgetEstimate()));
    }

    @Test
    public void testCloneExec() throws PermissionException, CancelException {
        //Setup variables.
        DataModel model = new DataModel();
        Developer lead = model.createDeveloper("meGoodLead2", "Luky", "Luke");
        User issuer = model.createIssuer("noDev2", "BadLuck", "Luke");
        User admin = model.createAdministrator("admin2", "adminT", "bie");
        Project proj = model.createProject("ProjectFT2", "desc here about test default.", lead, 1000, admin);

        ArrayDeque<String> question = new ArrayDeque();
        ArrayDeque<String> answer = new ArrayDeque();
        CreateProjectCmd cmd = new CreateProjectCmd();
        
        GregorianCalendar date = new GregorianCalendar();
        date.add(GregorianCalendar.MONTH, 1);
        String dateStr = date.get(YEAR) + "-" + date.get(MONTH) + "-" + date.get(DATE);
        VersionID newVersionID = new VersionID(1,2,3);
        long budget = 50;

        //Setup scenario
        question.add("Create or clone a new project?");
        question.add("Create or clone: ");
        answer.add("expect wrong input");
        question.add("Invalid input. Use create or clone.");
        question.add("Create or clone: ");
        answer.add("clOne");
        question.add("Available projects:");
        question.add("0. " + proj.getName());
        question.add("I chose: ");
        answer.add("lol");
        question.add("Invalid input.");
        question.add("I chose: ");
        answer.add("0");
        question.add("You have chosen:");
        question.add("" + proj.getDetails());
        question.add("new VersionID (format=a.b.c):");
        answer.add("lol");
        question.add("Invalid input. Please try again using format: a.b.c");
        question.add("new VersionID (format=a.b.c):");
        answer.add(newVersionID.toString());
        question.add("New starting date (format=YYYY-MM-DD):");
        answer.add("55");
        question.add("Invalid input. Please try again using format YYYY-MM-DD");
        question.add("New starting date (format=YYYY-MM-DD):");
        answer.add(dateStr);
        question.add("New budget Estimate:");
        answer.add("lol");
        question.add("Invalid input. Please try again.");
        question.add("New budget Estimate:");
        answer.add(Long.toString(budget));
        question.add("Chose a lead developer.");
        question.add("Available options:");
        question.add("0. " + lead.getUsername());
        answer.add(lead.getUsername());

        TerminalTestScanner scan = new TerminalTestScanner(new MultiByteArrayInputStream(answer), question);

        //Execute scenario
        Project project = cmd.exec(scan, model, admin);

        //Test effects.
        assertEquals(lead.getUsername(), project.getLead().getUsername());
        assertEquals(project.getVersionID(), newVersionID);
        assertEquals(date.get(GregorianCalendar.YEAR), project.getStartDate().get(GregorianCalendar.YEAR));
        assertEquals(date.get(GregorianCalendar.MONTH), project.getStartDate().get(GregorianCalendar.MONTH));
        assertEquals(date.get(GregorianCalendar.DATE), project.getStartDate().get(GregorianCalendar.DATE));
        assertEquals(budget, project.getBudgetEstimate());

    }

}
