package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * This class represents the assign to bug report use case
 *
 * @author Group 03
 */
public class AssignToBugReportCmd implements Cmd {

    /**
     * Creates a {@link Cmd} for the AssignToBugReport scenario where the bugReport to add to is already chosen.
     * @param bugReport The bugReport to assign to.
     */
    public AssignToBugReportCmd(BugReport bugReport) {
        this.bugReport = bugReport;
    }
    
    /**
     * Creates a {@link Cmd} for the AssignToBugReport scenario which includes a scenario to select the bug report.
     */
    public AssignToBugReportCmd() {
    }
    
    private BugReport bugReport = null;
    
    
    /**
     * Execute this command and possibly return a result.
     * <p>
     * <p>
     * <br> 1. The developer indicates he wants to assign a developer to a bug
     * report.
     * <br> 2. Include use case Select Bug Report if required.
     * <br> 3. The system shows a list of developers that are involved in the
     * project.
     * <br> 4. The logged in developer selects one or more of the developers to
     * assign to the selected bug report on top of those already assigned.
     * <br> 5. The systems assigns the selected developers to the selected bug
     * report.
     * <br> Extensions:
     * <br> 3a. The selected bug report is of a project that the logged in
     * developer is not involved in as lead or tester.
     * <br> 1. The use case returns to step 2.
     *
     * @param scan  The {@link TerminalScanner} used to interact with the person.
     * @param model The {@link DataModel} used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The {@link BugReport} selected to assign users to.
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan, model,user is null
     * @see DataModel#addUsersToBugReport(User, BugReport, PList)
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. The developer indicates he wants to assign a developer to a bug report. 
        boolean hasPerm = false;
        BugReport bugRep;
        do {
            // 2. Include use case Select Bug Report if required.
            bugRep = (bugReport != null) ? bugReport : new SelectBugReportCmd().exec(scan, model, user);

            // 3a. The selected bug report is of a project that the logged in developer is not involved in as lead or tester.
            // 1. The use case returns to step 2.
            if (!bugRep.canAssignDeveloper(user)) {
                scan.println("You don't have the required permission.");
            } else {
                hasPerm = true;
            }
        } while (!hasPerm);

        // 3. Show a list of developers that are involved in the project. 
        PList<Developer> list = model.getDeveloperInProject(bugRep);
        scan.println("Available developers: ");
        for (int i = 0; i < list.size(); i++) {
            scan.println(i + ". " + list.get(i).getUsername());
        }

        // Retrieve & process user input.
        // 4. Select one or more of the developers to assign to the selected 
        // bug report on top of those already assigned.
        HashSet<Developer> devList = new HashSet<>();
        Developer dev = null;
        boolean done = false;
        do {
            do {
                scan.print("I choose: (leave blank when done)");
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("")) {
                    done = true;
                } else {
                    try { // By index
                        int index = Integer.parseInt(input);// input
                        if (index >= 0 && index < list.size()) {
                            dev = list.get(index);
                        } else {
                            scan.println("Invalid input.");
                        }
                    } catch (NumberFormatException | InputMismatchException e) { // by username
                        try {
                            dev = list.parallelStream().filter(u -> u.getUsername().equals(input)).findFirst().get();
                        } catch (NoSuchElementException ex) {
                            scan.println("Invalid input.");
                        }
                    }
                }

            } while (dev == null && !done);
            if (dev != null) {
                devList.add(dev);
                scan.println("Added " + dev.getUsername());
                dev = null;
            }
        } while (!done);

        // 5. The systems assigns the selected developers to the selected bug report.
        model.addUsersToBugReport(user, bugRep, PList.<Developer>empty().plusAll(devList));
        scan.println("Finished assigning.");
        return bugRep;
    }
}
