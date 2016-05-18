package bugtrap03.model;

import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;
import bugtrap03.bugdomain.usersystem.User;

/**
 * This class represents the 'action' of registering/subscribing for a certain notification and it holds the Mailbox associated with
 * this new registration.
 *
 * @author Group 03
 */
abstract class RegisterForNotificationsModelCmd extends ModelCmd {

    /**
     * Create an abstract {@link ModelCmd}
     *
     * @param user The user that wishes to subscribe.
     */
    RegisterForNotificationsModelCmd(User user) {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("The given user for notification registration is invalid!");
        }
        this.userMailbox = user.getMailbox();
        this.isExecuted = false;
    }

    private Mailbox userMailbox;
    private boolean isExecuted;
    private AbstractMailbox newMailbox;

    /**
     * This method checks the validity of a given user.
     *
     * @param user The user to check.
     * @return True if the user is not null.
     */
    public boolean isValidUser(User user) {
        if (user == null || user.getMailbox() == null) {
            return false;
        }
        return true;
    }

    /**
     * Gets the mailbox of the subscriber for this cmd
     *
     * @return the Mailbox of the subscriber
     */
    protected Mailbox getMailbox() {
        return this.userMailbox;
    }

    /**
     * Sets the new mailbox for new notifications.
     *
     * @param mb The new Mailbox
     * @throws IllegalArgumentException if the new mailbox is invalid
     * @see #isValidNewMailBox
     */
    protected void setNewMailbox(AbstractMailbox mb) throws IllegalArgumentException {
        if (this.isValidNewMailBox(mb)) {
            this.newMailbox = mb;
        } else throw new IllegalArgumentException("Invalid mailbox");
    }

    /**
     * Returns the validity of a given mailbox.
     *
     * @param mb the mailbox to check.
     * @return true if the given mailbox is not null
     */
    private boolean isValidNewMailBox(AbstractMailbox mb) {
        if (mb == null) {
            return false;
        }
        return true;
    }

    /**
     * This method undo's the result of an execution of this command.
     *
     * @return true if the command has been succesfully undone.
     */
    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }
        this.getMailbox().unsubscribe(this.newMailbox);
        return true;
    }

    /**
     * Returns whether or not this command has been executed.
     *
     * @return true if isExecuted
     */
    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

    /**
     * Sets the isExecuted variable of this command to true if the command has not been executed before.
     *
     * @throws IllegalStateException if the command has already been executed.
     */
    protected void setExecuted() {
        if (!this.isExecuted()) {
            this.isExecuted = true;
        } else {
            throw new IllegalStateException("This model command was already executed");
        }
    }

    /**
     * Returns the information of this model command a a string.
     *
     * @return a String containing the model cmd information.
     */
    @Override
    public String toString() {
        if (this.newMailbox == null && !this.isExecuted()) {
            return ("Subscription not yet created.");
        }
        if (this.newMailbox == null && this.isExecuted()) {
            throw new IllegalStateException("Empty subscription created.");
        }
        return ("Created subscription: \n" + this.newMailbox.getInfo());
    }
}
