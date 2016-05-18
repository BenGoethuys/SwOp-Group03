package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * This command asks the user to input the details of a new milestone and returns this object
 *
 * @author Group 03
 */
public class SelectMilestoneCmd implements Cmd<Milestone> {

    @Override
    public Milestone exec(TerminalScanner scan, DataModel modelDummy, User userDummy) throws CancelException {
        if (scan == null) {
            throw new IllegalArgumentException("scan musn't be null.");
        }

        Milestone bugReportMilestone = null;
        scan.print("Enter a new milestone: (format a.b.c...) ");
        do {
            String input = scan.nextLine();
            String[] milestoneStr = input.split("\\.");

            int[] milestoneInt = new int[milestoneStr.length];
            try {
                for (int i = 0; i < milestoneStr.length; i++) {
                    milestoneInt[0] = Integer.parseInt(milestoneStr[i]);
                }
                bugReportMilestone = new Milestone(milestoneInt);
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                scan.println("Invalid input. Please try again using format: a.b.c...");
            }
        } while (bugReportMilestone == null);
        return bugReportMilestone;
    }
}
