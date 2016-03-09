package bugtrap03.gui.cmd;

import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.model.DataModel;
import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetProjectCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import purecollections.PList;

import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * This commands represents the use case for creating a bug report in the system
 *
 * @author Group 03
 */
public class CreateBugReportCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br>
     * 1. The issuer indicates he wants to file a bug report. <br>
     * 2. The system shows a list of projects. <br>
     * 3. The issuer selects a project. <br>
     * 4. The system shows a list of subsystems of the selected project. <br>
     * 5. The issuer selects a subsystem. <br>
     * 6. The system shows the bug report creation form. <br>
     * 7. The issuer enters the bug report details: title and description. <br>
     * 8. The system shows a list of possible dependencies of this bug report.
     * These are the bug reports of the same project. <br>
     * 9. The issuer selects the dependencies. <br>
     * 10. The system creates the bug report.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return The created bug report.
     * @throws PermissionException When the user does not have sufficient
     *             permissions.
     * @throws CancelException When the users wants to abort the current cmd
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException {

        Project proj = new GetProjectCmd().exec(scan, model, user);

        PList<Subsystem> subsysList = model.getAllSubsystems(proj);

        Subsystem subsys = new GetObjectOfListCmd<Subsystem>(subsysList, (u -> u.getName()), ((u, input) -> u.getName().equals(input)))
                .exec(scan, model, user);

        scan.println("You have chosen:");
        scan.println(subsys.getName());

        // BugReport title
        scan.print("BugReport title:");
        String bugreportTitle = scan.nextLine();

        // BugReport description
        scan.print("BugReport description:");
        String bugReportDesc = scan.nextLine();

        // BugReport Dependencies
        scan.println("Choose a dependency.");
        PList<BugReport> possibleDeps = proj.getAllBugReports();
        
        scan.println("Available bugReports:");
        for (int i = 0; i < possibleDeps.size(); i++) {
            BugReport bugrep = possibleDeps.get(i);
            scan.println(i + ". " + bugrep.getTitle() + " " + bugrep.getUniqueID());
        }

        // Retrieve & process user input.
        HashSet<BugReport> depList = new HashSet<>();
        boolean done = false;
        do {
            scan.print("I choose: (leave blank if done)");
            String input = scan.nextLine(); // input
            if (input.equalsIgnoreCase("")) {
                scan.println("Ended selection.");
                done = true;
            } else {
                try {
                    Integer index = Integer.parseInt(input);
                    if (index >= 0 && index < possibleDeps.size()) {
                        BugReport currDep = possibleDeps.get(index);
                        depList.add(currDep);
                        scan.println("Added dependency: " + currDep.getTitle());
                    } else {
                        scan.println("Invalid input.");
                    }
                } catch (NumberFormatException ex) {
                    try {
                        // No int. Try title.
                        BugReport currDep = possibleDeps.parallelStream().filter(u -> u.getTitle().equals(input))
                                .findFirst().get();
                        depList.add(currDep);
                        scan.println("Added dependency: " + currDep.getTitle());
                    } catch (NoSuchElementException ex2) {
                        scan.println("Invalid input.");
                    }
                }
            }
        } while (!done);

        BugReport bugreport = model.createBugReport(user, bugreportTitle, bugReportDesc,
                PList.<BugReport> empty().plusAll(depList), subsys);
        scan.println("Created new bug report.");
        return bugreport;
    }
}
