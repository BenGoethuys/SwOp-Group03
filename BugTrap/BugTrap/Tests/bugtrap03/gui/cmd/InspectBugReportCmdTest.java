package bugtrap03.gui.cmd;


import java.util.ArrayDeque;

import org.junit.Test;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * 
 * @author Group 03
 *
 */
public class InspectBugReportCmdTest {

    DataModel model;
    Developer lead;
    Issuer issuer;
    Administrator admin;
    Project projectA;
    Subsystem subsystemA2;
    Subsystem subsystemA3;
    Subsystem subsystemA3_1;
    BugReport bugRep1;
    BugReport bugRep2;

    /**
     * Test method for
     * {@link bugtrap03.gui.cmd.InspectBugReportCmd#exec(bugtrap03.gui.terminal.TerminalScanner, bugtrap03.model.DataModel, bugtrap03.bugdomain.usersystem.User)}
     * .
     */
    @Test
    public void testExec() throws IllegalArgumentException, PermissionException {
        // Setup variables.
        model = new DataModel();
        lead = model.createDeveloper("Leader007", "Leader007", "Leader007");
        issuer = model.createIssuer("Issuer007", "Issuer007", "Issuer007", "Issuer007");
        admin = model.createAdministrator("Admin007", "Admin007", "Admin007");

        projectA = model.createProject("ProjectTest0", "Project for testing 0", lead, 500, admin);
        // make subsystems
        subsystemA2 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA2",
                "Description of susbsystem A2");
        subsystemA3 = model.createSubsystem(admin, projectA, new VersionID(), "SubsystemA3",
                "Description of susbsystem A3");
        subsystemA3_1 = model.createSubsystem(admin, subsystemA3, new VersionID(), "SubsystemA3.1",
                "Description of susbsystem A3.1");

        bugRep1 = model.createBugReport(issuer, "bugRep over here", "createComment has an output error",
                PList.<BugReport> empty(), subsystemA2);
        bugRep2 = model.createBugReport(issuer, "Used library not in repository", "title says it all.",
                PList.<BugReport> empty(), subsystemA3_1);

        ArrayDeque<String> question = new ArrayDeque<>();
        ArrayDeque<String> answer = new ArrayDeque<>();
        UpdateBugReportCmd cmd = new UpdateBugReportCmd();
    }

}
