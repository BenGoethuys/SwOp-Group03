package bugtrap03;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.gui.terminal.Terminal;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.Administrator;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Role;

import java.util.Scanner;

/**
 *
 * @author Group 03
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        model = new DataModel();

        initDemoSystem(model);

        Terminal terminal = new Terminal(model);
        terminal.openView();
    }

    private static DataModel model;

    public static void initDemoSystem(DataModel model) {
        Administrator admin = model.createAdministrator("curt", "Frederick", "Sam", "Curtis");
        model.createIssuer("doc", "John", "Doctor");
        model.createIssuer("charlie", "Charles", "Arnold", "Berg");
        Developer major = model.createDeveloper("major", "Joseph", "Mays");
        Developer maria = model.createDeveloper("maria", "Maria", "Carney");
        Project projectA;
        Subsystem subsystemA3;
        Subsystem subsystemB2;
        try {
			projectA = model.createProject("ProjectA", "Description of projectA", major, 10000, admin);
			projectA.setRole(admin, major, Role.PROGRAMMER);
			projectA.setRole(major, maria, Role.TESTER);
			projectA.makeSubsystemChild(new VersionID(), "SubsystemA1", "Description of susbsystem A1");
			projectA.makeSubsystemChild(new VersionID(), "SubsystemA2", "Description of susbsystem A2");
			subsystemA3 = projectA.makeSubsystemChild(new VersionID(), "SubsystemA3", "Description of susbsystem A3");
			subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.1", "Description of susbsystem A3.1");
			subsystemA3.makeSubsystemChild(new VersionID(), "SubsystemA3.2", "Description of susbsystem A3.2");
		} catch (IllegalArgumentException | PermissionException e) {
			// should be valid
		}
        Project projectB;
        try {
			projectB = model.createProject("ProjectB", "Description of projectB", maria, 10000, admin);
			projectB.setRole(maria, major, Role.PROGRAMMER);
			projectB.makeSubsystemChild(new VersionID(), "SubsystemB1", "Description of susbsystem B1");
			subsystemB2 = projectB.makeSubsystemChild(new VersionID(), "SubsystemB2", "Description of susbsystem B2");
			subsystemB2.makeSubsystemChild(new VersionID(), "SubsystemB2.1", "Description of susbsystem B2.1");
		} catch (IllegalArgumentException | PermissionException e) {
			// should be valid
		}
        
    }

}
