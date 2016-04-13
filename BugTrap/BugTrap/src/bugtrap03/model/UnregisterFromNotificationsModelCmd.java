package bugtrap03.model;

import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;

/**
 * @author Group 03
 */
public class UnregisterFromNotificationsModelCmd extends ModelCmd{
    public UnregisterFromNotificationsModelCmd(User user, Mailbox mailbox){
        if (! isValidUser(user)){
            throw new IllegalArgumentException("Invalid User for unregistration from notifications.");
        }
        this.unsubscriber = user;
        if (! isValidMailbox(mailbox, user)){
            throw new IllegalArgumentException("Invalid Mailbox for unregistration from notifiactions");
        }
        this.mailbox = mailbox;
        this.isExecuted = false;
    }

    private User unsubscriber;
    private Mailbox mailbox;
    private boolean isExecuted;

    /**
     * This method checks the validity of a given user.
     * @param user The user to check.
     * @return True if the user is not null.
     */
    public boolean isValidUser(User user){
        if (user == null){
            return false;
        }
        return true;
    }

    /**
     * This method checks the validity of a given user.
     * @param mailbox The user to check.
     * @return True if the user is not null or the given mailox cannot be found in the all the mailboxes from the user.
     */
    public boolean isValidMailbox(Mailbox mailbox, User user){
        if (mailbox == null){
            return false;
        }
        PList<Mailbox> allBoxes = user.getMailbox().getAllBoxes();
        if (! allBoxes.contains(mailbox)){
            return false;
        }
        return true;
    }

    @Override
    Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {

        this.isExecuted = true;
        return null;
    }

    @Override
    boolean undo() {
        if (! isExecuted()){
            return false;
        }
        this.unsubscriber.getMailbox().addBox(this.mailbox);
        return true;
    }

    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

    @Override
    public String toString() {
        return null;
    }
}
