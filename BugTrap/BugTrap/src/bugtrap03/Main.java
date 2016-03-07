package bugtrap03;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
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

    public static void testSystem(DataModel model) {
        String username = "007a";
        String firstName = "James";
        String lastName = "Bond";

        String username2 = "002a";
        String username3 = "008a";

        model.createAdministrator(username, firstName, lastName);
        model.createAdministrator(username2, firstName, lastName);
        model.createIssuer(username3, firstName, lastName);
    }

    public static void initDemoSystem(DataModel model) {
        Administrator admin = model.createAdministrator("curt", "Frederick", "Sam", "Curtis");
        Issuer doc = model.createIssuer("doc", "John", "Doctor");
        Issuer charlie = model.createIssuer("charlie", "Charles", "Arnold", "Berg");
        Developer major = model.createDeveloper("major", "Joseph", "Mays");
        Developer maria = model.createDeveloper("maria", "Maria", "Carney");

        try {
            // create projectA
            Project projectA = model.createProject("ProjectA", "Description of projectA", major, 10000, admin);
            // add asked roles
            model.assignToProject(projectA, major, major, Role.PROGRAMMER);
            model.assignToProject(projectA, major, maria, Role.TESTER);
            // make subsystems
            model.createSubsystem(admin, projectA, "SubsystemA1", "Description of susbsystem A1");
            Subsystem subsystemA2 = model.createSubsystem(admin, projectA, "SubsystemA2", "Description of susbsystem A2");
            Subsystem subsystemA3 = model.createSubsystem(admin, projectA, "SubsystemA3", "Description of susbsystem A3");
            Subsystem subsystemA3_1 = model.createSubsystem(admin, subsystemA3, "SubsystemA3.1", "Description of susbsystem A3.1");
            model.createSubsystem(admin, subsystemA3, "SubsystemA3.2", "Description of susbsystem A3.2");
            // make bug report 2
            BugReport bugRep2 = model.createBugReport(charlie, "Crash while processing user input", "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15), PList.<BugReport>empty(), subsystemA3_1);
            model.addUsersToBugReport(major, bugRep2, PList.<Developer>empty().plusAll(Arrays.asList(major, maria)));
            // mak bug report 3
            model.createBugReport(major, "SubsystemA2 feezes", "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4), PList.<BugReport>empty(), subsystemA2);
        } catch (IllegalArgumentException | PermissionException e) {
            System.err.println("Unexpected error at initDemo");
            System.err.println(e.getMessage());
            // should be valid
        }
        Project projectB;
        try {
            // create projectB
            projectB = model.createProject("ProjectB", "Description of projectB", maria, 10000, admin);
            // add asked roles
            model.assignToProject(projectB, maria, major, Role.PROGRAMMER);
            // make subsystems
            Subsystem subsystemB1 = model.createSubsystem(admin, projectB, "SubsystemB1", "Description of susbsystem B1");
            Subsystem subsystemB2 = model.createSubsystem(admin, projectB, "SubsystemB2", "Description of susbsystem B2");
            model.createSubsystem(admin, subsystemB2, "SubsystemB2.1", "Description of susbsystem B2.1");
            // make bug report 1
            BugReport bugRep1 = model.createBugReport(doc, "The function parse_ewd returns unexpected results", "If the function parse_ewd is invoked while ...", new GregorianCalendar(2016, 1, 3), PList.<BugReport>empty(), subsystemB1);
            model.addUsersToBugReport(maria, bugRep1, PList.<Developer>empty().plus(maria));
            model.setTag(bugRep1, Tag.UNDER_REVIEW, major);
            model.setTag(bugRep1, Tag.RESOLVED, doc);
            model.setTag(bugRep1, Tag.CLOSED, maria);
        } catch (IllegalArgumentException | PermissionException e) {
            System.err.println("Unexpected error at initDemo");
            System.err.println(e.getMessage());
            // should be valid
        }

    }

}
