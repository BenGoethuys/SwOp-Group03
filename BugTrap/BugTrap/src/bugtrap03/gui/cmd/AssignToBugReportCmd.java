package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * This class represents the assign to bug report use case
 * Created by Ben Goethuys on 07/03/2016.
 */
public class AssignToBugReportCmd implements Cmd {
    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The developer indicates he wants to assign a developer to a bug report.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The system shows a list of developers that are involved in the project.
     * <br> 4. The logged in developer selects one or more of the developers to assign to the selected bug report on top of those already assigned.
     * <br> 5. The systems assigns the selected developers to the selected bug report.
     * <br> Extensions:
     * <br> 3a. The selected bug report is of a project that the logged in developer is not involved in as lead or tester.
     * <br> 1. The use case returns to step 2.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return null if there is no result specified.
     * @throws PermissionException When the user does not have sufficient
     *                             permissions.
     * @throws CancelException     When the users wants to abort the current cmd
     */
    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {
        BugReport bugRep = new SelectBugReportCmd().exec(scan, model, user);

        if (! user.hasRolePermission(RolePerm.ASSIGN_DEV_BUGREPORT, bugRep.getSubsystem().getParentProject())){
            throw new PermissionException("You don't have the needed permission");
        }

        PList<Developer> list = model.getDeveloperInproject(bugRep);

        //Retrieve & process user input.
        HashSet<Developer> devList = new HashSet<>();
        Developer dev = null;
        boolean done = false;
        do {
            do {
                scan.println("I choose: (leave blank when done)");
                if (scan.hasNextInt()) { //by index
                    int index = scan.nextInt();//input
                    if (index >= 0 && index < list.size()) {
                        dev = list.get(index);
                    } else {
                        scan.println("Invalid input.");
                    }
                } else { //by username
                    String input = scan.nextLine(); //input
                    if (input.equalsIgnoreCase("")){
                        done = true;
                    } else {
                        try {
                            dev = list.parallelStream().filter(u -> u.getUsername().equals(input)).findFirst().get();
                        } catch (NoSuchElementException ex) {
                            scan.println("Invalid input.");
                        }
                    }
                }
            } while (dev == null);
            devList.add(dev);
            dev = null;
        } while (! done);

        model.addUsersToBugReport(user, bugRep, devList);

        return null;
    }
}