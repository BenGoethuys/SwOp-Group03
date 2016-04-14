package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetIntCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * @author Group 03
 */
public class UnregisterFromNotificationsCmd implements Cmd<Mailbox> {

    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws CancelException, IllegalArgumentException, IllegalStateException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        PList<Mailbox> allBoxes = user.getMailbox().getAllBoxes();
        int size = allBoxes.size();
        if (size < 1){
            scan.println("No subscriptions to show.");
            return null;
        }
        scan.println("Please select a subscription from list. (use index)");
        for (int i=0;i<size;i++){
            scan.println(i + ". " + allBoxes.get(i).getInfo());
        }
        int index = -1;
        while(index < 0){
                index = new GetIntCmd().exec(scan, null, null);
                if (index >= size) {
                    index = size - 1;
                }
                if (index < 0) {
                    scan.println("Invalid input, give new index.");
                }
        }
        Mailbox selectedMB = allBoxes.get(index);
        scan.println("You selected: " + selectedMB.getInfo());
        model.unregisterFromNotifications(user, selectedMB);
        return selectedMB;
    }
}
