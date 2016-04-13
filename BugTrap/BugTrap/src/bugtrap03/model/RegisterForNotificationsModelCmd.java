package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Mailbox;

/**
 * @author Group 03
 */
abstract class RegisterForNotificationsModelCmd extends ModelCmd {

    /**
     * Create a abstract {@link ModelCmd}
     * @param user
     */
    RegisterForNotificationsModelCmd(User user){
        // FIXME: null check
        this.subscriber = user;
        this.isExecuted = false;
    }

    private User subscriber;
    private boolean isExecuted;
    private Mailbox newMailbox;

    /**
     * Gets the mailbox of the subscriber for this cmd
     *
     * @return the Mailbox of the subscriber
     */
    protected Mailbox getMailbox(){
        return this.subscriber.getMailbox();
    }

    /**
     * Sets the new mailbox for new notifications.
     *
     * @param mb The new Mailbox
     * @throws IllegalArgumentException if the new mailbox is invalid
     * @see #isValidNewMailBox(Mailbox)
     */
    protected void setNewMailbox(Mailbox mb) throws IllegalArgumentException {
        if (this.isValidNewMailBox(mb)){
            this.newMailbox = mb;
        } else throw new IllegalArgumentException("Invalid mailbox");
    }

    /**
     * Returns the validity of a given mailbox.
     *
     * @param mb the mailbox to check.
     * @return true if the given mailbox is not null
     */
    private boolean isValidNewMailBox(Mailbox mb){
        if(mb == null){
            return false;
        }
        return true;
    }

    /**
     * This method undo's the result of an execution of this command.
     * @return true if the command has been succesfully undone.
     */
    @Override
    boolean undo() {
        if (! this.isExecuted()) {
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
    protected void setExecuted(){
        if (! this.isExecuted()){
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
        //FIXME: Kwinten newMailbox can be null?
        return ("Created subscription: \n" + this.newMailbox.getInfo());
    }
}
