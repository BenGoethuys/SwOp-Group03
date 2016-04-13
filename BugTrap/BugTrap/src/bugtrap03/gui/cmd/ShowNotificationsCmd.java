package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.notification.Notification;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * @author Group 03
 */
public class ShowNotificationsCmd implements Cmd {
    @Override
    public Mailbox exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        PList<Notification> notifications = user.getMailbox().getAllNotifications();
        int size = notifications.size();
        if (size < 1){
            scan.println("No notifications in mailbox.");
            return null;
        }
        scan.println("Length of inbox "+ size);
        scan.println("How many notifications do you wish to see? (numeric)");
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
        int counter = 0;
        Notification temp;
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
