package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Mailbox;
import bugtrap03.bugdomain.usersystem.notification.Notification;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * Created by Bruno on 11-4-2016.
 */
public class UnregisterFromNotificationsCmd implements Cmd {
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user) throws CancelException, IllegalArgumentException, IllegalStateException {
        PList<Mailbox> allBoxes = user.getMailbox().getAllBoxes();
        int size = allBoxes.size();
        if (size < 1){
            scan.println("No subscriptions to show.");
            return null;
        }
        scan.println("Please select a subscription. (use index)");
        for (int i=0;i<allBoxes.size();i++){
            scan.println(i + ". " + allBoxes.get(i).getInfo());
        }
        int index = 0;
        while(index == 0){
            if (! scan.hasNextInt()){
                scan.println("Invalid input, try again");
            } else{
                index = scan.nextInt();
                if (index < 1 || index > size){
                    index = 0;
                    scan.println("Invalid input, try again");
                }
            }
        }
        Mailbox selected = allBoxes.get(index);
        scan.println("You selected: " + selected.getInfo());
        user.getMailbox().unsubscribe(selected);
        return selected;
    }
}
