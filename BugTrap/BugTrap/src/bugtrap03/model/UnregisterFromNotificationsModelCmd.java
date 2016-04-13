package bugtrap03.model;

import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
public class UnregisterFromNotificationsModelCmd extends ModelCmd{
    public UnregisterFromNotificationsModelCmd(User user, Mailbox mailbox){
        if (! isValidUser(user)){
            throw new IllegalArgumentException("Invalid User for unregistration from notifications.");
        }
        this.unsubscriber = user;
        if (! isValidMailbox(mailbox)){
            throw new IllegalArgumentException("Invalid Mailbox for unregistration from notifiactions");
        }
        this.mailbox =
    }

    private User unsubscriber;

    @Override
    Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        return null;
    }

    @Override
    boolean undo() {
        return false;
    }

    @Override
    boolean isExecuted() {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
