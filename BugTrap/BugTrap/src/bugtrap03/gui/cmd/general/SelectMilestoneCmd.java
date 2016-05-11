package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

/**
 * @author Group 03
 */
public class SelectMilestoneCmd implements Cmd<Milestone> {
    @Override
    public Milestone exec(TerminalScanner scan, DataModel modelDummy, User userDummy) throws CancelException {
        Milestone bugReportMilestone = null;
        scan.print("Enter a new milestone: (format a.b.c) ");
        do {
            String input = scan.nextLine();
            String[] milestoneStr = input.split("\\.");

            int nb1, nb2, nb3;
            try {
                nb1 = Integer.parseInt(milestoneStr[0]);
                nb2 = Integer.parseInt(milestoneStr[1]);
                nb3 = Integer.parseInt(milestoneStr[2]);

                bugReportMilestone = new Milestone(nb1, nb2, nb3);
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                scan.println("Invalid input. Please try again using format: a.b.c");
            }
        } while (bugReportMilestone == null);
        return bugReportMilestone;
    }
}
