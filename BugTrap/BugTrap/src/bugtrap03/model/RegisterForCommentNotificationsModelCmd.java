package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.CommentMailBox;
import bugtrap03.bugdomain.usersystem.notification.Subject;

/**
 * @author Group 03
 */
class RegisterForCommentNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the creation of comments on bug reports when executed
     * @param user The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     *
     * @throws IllegalArgumentException When the given subject is null
     * @throws IllegalArgumentException When the given subject is Terminated
     */
    RegisterForCommentNotificationsModelCmd(User user, Subject subject){
        super(user);

        if (subject == null) {
            throw new IllegalArgumentException("The given subject is null");
        }
        if (subject.isTerminated()){
            throw new IllegalArgumentException("The given subject is terminated");
        }
        this.subject = subject;
    }

    private Subject subject;

    /**
     * This method executes this model command.
     * @return The created commentmailbox representing the subscription that contains the notifications
     * @throws IllegalArgumentException if on of the arguments is invalid
     * @throws IllegalArgumentException If subject is terminated
     * @throws IllegalStateException if the state of this command is invalid
     * @see Mailbox#commentSubscribe(Subject)
     * @see #setExecuted()
     */
    @Override
    CommentMailBox exec() throws IllegalArgumentException, IllegalStateException {
        if (subject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated.");
        }
        
        this.setExecuted();
        CommentMailBox cmb = this.getMailbox().commentSubscribe(this.subject);
        this.setNewMailbox(cmb);
        return cmb;
    }
}
