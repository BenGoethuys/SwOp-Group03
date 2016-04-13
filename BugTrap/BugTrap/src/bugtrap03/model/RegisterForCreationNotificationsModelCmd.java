package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.AbstractSystemSubject;
import bugtrap03.bugdomain.notification.CreationMailBox;

/**
 * @author Group 03
 */
class RegisterForCreationNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject for the creation of bugreports when executed
     * @param user the user that wishes to subscribe
     * @param abstractSystemSubject the abstract system subject on which the user wishes to subscribe
     *
     * @throws IllegalArgumentException When the given abstractSystemSubject is null
     * @throws IllegalArgumentException When the given abstractSystemSubject is Terminated
     */
    RegisterForCreationNotificationsModelCmd(User user, AbstractSystemSubject abstractSystemSubject){
        super(user);

        if (abstractSystemSubject == null) {
            throw new IllegalArgumentException("The given abstractSystemSubject is null");
        }
        if (abstractSystemSubject.isTerminated()){
            throw new IllegalArgumentException("The given abstractSystemSubject is terminated");
        }

        this.abstractSystemSubject = abstractSystemSubject;
    }

    private AbstractSystemSubject abstractSystemSubject;

    /**
     * This method executes this model command.
     * @return The created creationmailbox representing the subscription that contains the notifications
     * @throws IllegalArgumentException if on of the arguments is invalid
     * @throws IllegalArgumentException If abstractSystemSubject is terminated
     * @throws IllegalStateException if the state of this command is invalid
     * @see bugtrap03.bugdomain.notification.Mailbox#creationSubscribe(AbstractSystemSubject)
     * @see #setExecuted()
     */
    @Override
    CreationMailBox exec() throws IllegalArgumentException, IllegalStateException {
        if (abstractSystemSubject.isTerminated()) {
            throw new IllegalArgumentException("The given abstractSystemSubject is terminated.");
        }
        
        this.setExecuted();
        CreationMailBox cmb = this.getMailbox().creationSubscribe(this.abstractSystemSubject);
        this.setNewMailbox(cmb);
        return cmb;
    }


}
