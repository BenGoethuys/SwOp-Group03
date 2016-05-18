package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetIntCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This command represents the use case for showing the notifications of the logged in user
 *
 * @author Group 03
 */
public class ShowNotificationsCmd implements Cmd<Mailbox> {
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        ArrayList<Notification> notificationsAL = new ArrayList<Notification>(user.getMailbox().getAllNotifications());
//        Collections.sort(notificationsAL, new Comparator<Notification>() {
//            @Override
//            public int compare(Notification o1, Notification o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
        notificationsAL.sort(Comparator.comparing(Notification::getDate).reversed());
        PList<Notification> notifications = PList.<Notification>empty().plusAll(notificationsAL);
        int size = notifications.size();
        if (size < 1) {
            scan.println("No notifications in mailbox.");
            return null;
        }
        scan.println("Length of inbox " + size);
        scan.println("How many notifications do you wish to see? (numeric)");
        int index = 0;
        while (index <= 0) {
            index = new GetIntCmd().exec(scan, null, null);
            if (index > size) {
                index = size;
            }
            if (index <= 0) {
                scan.println("Invalid input, give new number.");
            }
        }
        int counter = 0;
        Notification temp;
        scan.println("Your notifications, ordered by newest first:");
        while (index > 0 && (!notifications.isEmpty())) {
            temp = notifications.getFirst();
            notifications = notifications.minusFirst();
            index--;
            scan.println(counter + ". " + temp.open(user));
            counter++;
        }
        return user.getMailbox();
    }
}
