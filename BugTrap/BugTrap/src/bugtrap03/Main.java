package bugtrap03;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * This is the runnable main class of the BugTrap system
 *
 * @author Group 03
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        model = new DataModel();

        initDemoSystem(model);
        //testSystem(model);

        Terminal terminal = new Terminal(model);
        terminal.openView();
    }

    private static DataModel model;

    /*public static void testSystem(DataModel model) {
        String username = "007a";
        String firstName = "James";
        String lastName = "Bond";

        String username2 = "002a";
        String username3 = "008a";

        model.createAdministrator(username, firstName, lastName);
        model.createAdministrator(username2, firstName, lastName);
        model.createIssuer(username3, firstName, lastName);
    }*/

    private static void initDemoSystem(DataModel model) {
        Administrator admin = model.createAdministrator("curt", "Frederick", "Sam", "Curtis");
        Issuer doc = model.createIssuer("doc", "John", "Doctor");
        Issuer charlie = model.createIssuer("charlie", "Charles", "Arnold", "Berg");
        Developer major = model.createDeveloper("major", "Joseph", "Mays");
        Developer maria = model.createDeveloper("maria", "Maria", "Carney");
        
        try {
            // create projectA
            Project projectA = model.createProject(new VersionID(5,0), "ProjectA", "Description of projectA", major, 10000, admin);
            // add asked roles
            model.assignToProject(projectA, major, maria, Role.PROGRAMMER);
            model.assignToProject(projectA, major, maria, Role.TESTER);
            // make subsystems
            Subsystem subsystemA1 = model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
            Subsystem subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
            Subsystem subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
            Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of susbsystem A3.1");
            Subsystem subsystemA3_2 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");
            // make bug report 2
            BugReport bugRep2 = model.createBugReport(subsystemA3_1, charlie, "Crash while processing user input",
                    "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 0, 15),
                    PList.<BugReport>empty(), null, 2.3, false, null, null, "Internal Error 45: The...");
            // add user -> assigned
            model.addUsersToBugReport(major, bugRep2, PList.<Developer>empty().plusAll(Arrays.asList(major, maria)));
            // make bug report 3
            model.createBugReport(subsystemA2, major, "SubsystemA2 feezes", "If the function process_dfe is invoked with ...",
                    new GregorianCalendar(2016, 1, 4), PList.<BugReport>empty(), new Milestone(3, 2), 5.8, true,
                    "Launch with command line invocation:...", "Exception in thread \"main\" java.lang...", null);

            // set milestones
            model.setMilestone(major, projectA, new Milestone(2,5));
            model.setMilestone(major, subsystemA1, new Milestone(2,5,1));
            //model.setMilestone(major, subsystemA2, new Milestone(2,5));
            model.setMilestone(major, subsystemA3, new Milestone(2,8,5));
            model.setMilestone(major, subsystemA3_1, new Milestone(2,8,5,3));
            model.setMilestone(major, subsystemA3_2, new Milestone(2,9));
        } catch (IllegalArgumentException | PermissionException e) {
            System.err.println("Unexpected error at initDemo");
            System.err.println(e.getMessage());
            // should be valid
        }
        Project projectB;
        try {
            // create projectB
            projectB = model.createProject(new VersionID(0,4), "ProjectB", "Description of projectB", maria, 10000, admin);
            // add asked roles
            model.assignToProject(projectB, maria, major, Role.PROGRAMMER);
            // add tester to ProjectB -> is needed bug not in assignment
            model.assignToProject(projectB, maria, major, Role.TESTER);
            // make subsystems
            Subsystem subsystemB1 = model.createSubsystem(admin, projectB, "SubsystemB1", "Description of susbsystem B1");
            Subsystem subsystemB2 = model.createSubsystem(admin, projectB, "SubsystemB2", "Description of susbsystem B2");
            model.createSubsystem(admin, subsystemB2, "SubsystemB2.1", "Description of susbsystem B2.1");
            // make bug report 1
            BugReport bugRep1 = model.createBugReport(subsystemB1, doc, "The function parse_ewd returns unexpected results",
                    "If the function parse_ewd is invoked while ...", new GregorianCalendar(2016, 0, 3),
                    PList.<BugReport>empty(), new Milestone(1,1), 7.1, false, null, null, null);
            // add user -> assigned
            model.addUsersToBugReport(maria, bugRep1, PList.<Developer>empty().plus(maria));
            // add tests -> assignedWithTest
            model.addTest(bugRep1, major, "bool test_invalid_args1(){...}");
            model.addTest(bugRep1, major, "test 2");
            model.addTest(bugRep1, major, "test 3");

            // add patch
            model.addPatch(bugRep1, major, "e3109fcc9...");
            model.addPatch(bugRep1, major, "patch 2");
            model.addPatch(bugRep1, major, "patch 3");

            // select patch -> underReview
            model.selectPatch(bugRep1, maria, "e3109fcc9...");

            // give score -> closed
            model.giveScore(bugRep1, doc, 4);

            // set milestones
            model.setMilestone(major, projectB, new Milestone(1,2));
            model.setMilestone(major, subsystemB1, new Milestone(1,3));
        } catch (IllegalArgumentException | PermissionException e) {
            System.err.println("Unexpected error at initDemo");
            System.err.println(e.getMessage());
            // should be valid
        }
    }

}
