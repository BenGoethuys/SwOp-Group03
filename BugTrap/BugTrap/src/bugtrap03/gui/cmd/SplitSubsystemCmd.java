package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetSubsystemCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class SplitSubsystemCmd implements Cmd<Subsystem[]> {

    /**
     * Execute use case 4.7, split Subsystem and return both resulting Subsystems.
     *
     * 4.7 Use Case: Split Subsystem
     * <p>
     * <br>1. The administrator indicates he wants to split a subsystem.
     * <br>2. The system shows a list of projects.
     * <br>3. The administrator selects a project.
     * <br>4. The system shows a list of subsystems of the selected project.
     * <br>5. The administrator selects a subsystem.
     * <br>6. The system asks for a name and description for both new subsystems.
     * <br>7. The administrator enters both names and descriptions.
     * <br>8. For each bug report and subsystem that is part of the original subsystem, the system asks to which new
     * subsystem to migrate it to.
     * <br>9. The administrator answers for each bug report and subsystem.
     * <br>10. The system creates two new subsystems with the same milestone as the original subsystem. The original
     * subsystem is removed.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * @return An array consisting of the 2 Subsystems split into.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalStateException When the subject is in an illegal state to perform this cmd.
     * @throws IllegalArgumentException When any of the arguments is null and shouldn't be or when in the scenario a
     * chosen option conflicted.
     */
    @Override
    public Subsystem[] exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user passed to SplitSubsystemCmd shouldn't be a null reference.");
        }
        //1. The administrator indicates he wants to split a subsystem.
        scan.println("Splitting subsystems.");
        //2. The system shows a list of projects.
        //3. The administrator selects a project.
        //4. The system shows a list of subsystems of the selected project.
        //5. The administrator selects a subsystem.
        GetSubsystemCmd cmd = new GetSubsystemCmd();
        Subsystem sub = cmd.exec(scan, model, null);

        if (sub == null) {
            throw new CancelException("No options, operation cancelled.");
        }

        //6. The system asks for a name and description for both new subsystems.
        //7. The administrator enters both names and descriptions.
        scan.println("Please enter information for subsytem 1.");
        String name1 = getSubName(scan);
        String desc1 = getSubDesc(scan);

        scan.println("Please enter information for subsytem 2.");
        String name2 = getSubName(scan);
        String desc2 = getSubDesc(scan);

        //8. For each bug report and subsystem that is part of the original subsystem, the system asks to which new subsystem to migrate it to.
        //9. The administrator answers for each bug report and subsystem.
        scan.println("Please choose which subsystems you wish to keep for subsystem 1.");
        PList<Subsystem> subs1 = getSubsystems(scan, sub);
        scan.println("Please choose which bug reports you wish to keep for subsystem 1.");
        PList<BugReport> bugReports1 = getBugReports(scan, sub);

        //10. The system creates two new subsystems with the same milestone as the original subsystem. (The original subsystem is removed.)
        Subsystem sub2 = model.splitSubsystem(sub, name1, desc1, name2, desc2, subs1, bugReports1, user);
        scan.println("Successfully split the subsystem.");
        return new Subsystem[] {sub, sub2}; 
    }

    /**
     * Ask the user for a name..
     *
     * @param scan The scanner used to interact with the user. Shouldn't be null.
     * @return The name given by the user.
     * @throws CancelException When the user aborts.
     */
    private String getSubName(TerminalScanner scan) throws CancelException {
        scan.print("Please enter a name:");
        String name = scan.nextLine();
        return name;
    }

    /**
     * Ask the user for a description.
     *
     * @param scan The scanner used to interact with the user. Shouldn't be null.
     * @return The description given by the user.
     * @throws CancelException When the user aborts.
     */
    private String getSubDesc(TerminalScanner scan) throws CancelException {
        scan.print("Please enter a description:");
        String desc = scan.nextLine();
        return desc;
    }

    /**
     * Get a list of direct subsystems of sub chosen by the user.
     *
     * @param scan The scanner used to interact with the user. Shouldn't be null.
     * @param sub The Subsystem to get the direct subsystems from. Shouldn't be null.
     * @return The list of direct subsystems of the given sub that the user chose.
     * 
     * @throws CancelException When the user wants to abort the cmd.
     */
    private PList<Subsystem> getSubsystems(TerminalScanner scan, Subsystem sub) throws CancelException {
        PList<Subsystem> result = PList.<Subsystem>empty();
        for(Subsystem subsystem : sub.getSubsystems()) {
            
            scan.println("Keep " + subsystem.getSubjectName() + " ?");
            scan.print("Yes or No?");
            String answer = scan.nextLine();
            
            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                result = result.plus(subsystem);
            }
        }
        return result;
    }

    /**
     * Get a list of direct bugReports of sub chosen by the user.
     *
     * @param scan The scanner used to interact with the user. Shouldn't be null.
     * @param sub The Subsystem to get the direct bugReports from. Shouldn't be null.
     * @return The list of direct bugReports of the given sub that the user chose.
     * 
     * @throws CancelException When the user wants to abort the cmd.
     */
    private PList<BugReport> getBugReports(TerminalScanner scan, Subsystem sub) throws CancelException {
        PList<BugReport> result = PList.<BugReport>empty();
        for(BugReport bugReport : sub.getBugReportList()) {
            
            scan.println("Keep " + bugReport.getSubjectName() + " ?");
            scan.print("Yes or No?");
            String answer = scan.nextLine();
            
            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                result = result.plus(bugReport);
            }
        }
        return result;
    }

}
