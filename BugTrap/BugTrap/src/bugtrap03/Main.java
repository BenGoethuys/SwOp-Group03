package bugtrap03;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import purecollections.PList;

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
            projectA.setRole(major, major, Role.PROGRAMMER);
            projectA.setRole(major, maria, Role.TESTER);
            // make subsystems
            projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
            Subsystem subsystemA2 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA2", "Description of susbsystem A2");
            Subsystem subsystemA3 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA3", "Description of susbsystem A3");
            Subsystem subsystemA3_1 = subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
            subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");
            // make bug report 2
            BugReport bugRep2 = subsystemA3_1.addBugReport(charlie, "Crash while processing user input", "If incorrect user input is entered into the system ...", new GregorianCalendar(2016, 1, 15), PList.<BugReport>empty());
            bugRep2.addUser(major, major);
            bugRep2.addUser(major, maria);
            // mak bug report 3
            subsystemA2.addBugReport(major, "SubsystemA2 feezes", "If the function process_dfe is invoked with ...", new GregorianCalendar(2016, 2, 4), PList.<BugReport>empty());

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
            projectB.setRole(maria, major, Role.PROGRAMMER);
            // make subsystems
            Subsystem subsystemB1 = projectB.makeSubsystemChild(new VersionID(), "SubsystemB1", "Description of susbsystem B1");
            Subsystem subsystemB2 = projectB.makeSubsystemChild(new VersionID(), "SubsystemB2", "Description of susbsystem B2");
            subsystemB2.makeSubsystemChild(new VersionID(), "SubsystemB2.1", "Description of susbsystem B2.1");
            // make bug report 1
            BugReport bugRep1 = subsystemB1.addBugReport(doc, "The function parse_ewd returns unexpected results", "If the function parse_ewd is invoked while ...", new GregorianCalendar(2016, 1, 3), PList.<BugReport>empty());
            bugRep1.addUser(maria, maria);
            bugRep1.setTag(Tag.UNDER_REVIEW, major);
            bugRep1.setTag(Tag.RESOLVED, doc);
            bugRep1.setTag(Tag.CLOSED, maria);
        } catch (IllegalArgumentException | PermissionException e) {
            System.err.println("Unexpected error at initDemo");
            System.err.println(e.getMessage());
            // should be valid
        }

    }

}
