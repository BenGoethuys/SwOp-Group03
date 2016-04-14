package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.notification.Notification;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetIntCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Group 03
 */
public class ShowNotificationsCmd implements Cmd<Mailbox> {
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        ArrayList<Notification> notificationsAL = new ArrayList<Notification>(user.getMailbox().getAllNotifications());
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        PList<Notification> notifications = PList.<Notification>empty().plusAll(notificationsAL);
        Collections.reverse(notifications);
        int size = notifications.size();
        if (size < 1){
            scan.println("No notifications in mailbox.");
            return null;
        }
        scan.println("Length of inbox "+ size);
        scan.println("How many notifications do you wish to see? (numeric)");
        int index = 0;
        while(index <= 0){
            index = new GetIntCmd().exec(scan, null, null);
            if (index > size) {
                index = size;
            }
            if (index <= 0){
                scan.println("Invalid input, give new number.");
            }
        }
        int counter = 0;
        Notification temp;
        scan.println("Your notifications, ordered by newest first:");
        while (index > 0 && (! notifications.isEmpty())){
            temp = notifications.getFirst();
            notifications = notifications.minusFirst();
            index--;
            scan.println(counter + ". " + temp.open(user));
            counter++;
        }
        return user.getMailbox();
    }
}
