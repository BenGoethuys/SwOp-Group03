package bugtrap03.model;

import bugtrap03.bugdomain.notification.Mailbox;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;

/**
 * This class represents the unregistration command.
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
        if (mailbox == user.getMailbox()) {
            return false;
        }
        PList<Mailbox> allBoxes = user.getMailbox().getAllBoxes();
        if (! allBoxes.contains(mailbox)){
            return false;
        }
        return true;
    }

    /**
     * This method executes this unregistration command.
     * @return the mailbox that represented the
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Override
    Mailbox exec() throws IllegalArgumentException, IllegalStateException {
        if (this.isExecuted()){
            throw new IllegalStateException("This unsubscribe command is already executed and cannot be executed again.");
        }
        if (this.unsubscriber.getMailbox().unsubscribe(this.mailbox)){
            this.isExecuted = true;
            return this.mailbox;
        }
        throw new IllegalStateException("Something went wrong while unsubscribing. Mailbox not found in subscriptions.");
    }

    /**
     * This method undoes the unregistration if it had been executed before.
     * @return true if successfully undone the unregistration.
     */
    @Override
    boolean undo() {
        if (! isExecuted()){
            return false;
        }
        this.unsubscriber.getMailbox().addBox(this.mailbox);
        this.mailbox.activate();
        return true;
    }

    /**
     * This method returns wheter or not this command has been executed
     * @return the value of isExecuted.
     */
    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

    /**
     * This method prints the current state of this command.
     * @return A string containing the status of this command.
     */
    @Override
    public String toString() {
        if (! this.isExecuted()){
            return ("This unregistration has not yet been executed.");
        } else{
            return (this.unsubscriber.getFullName() + " has unregistered from notifications by deleting subscription: "
                    + this.mailbox.getInfo());
        }
    }
}
