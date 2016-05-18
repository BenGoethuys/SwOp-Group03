package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailbox;
import bugtrap03.bugdomain.usersystem.User;

import java.util.EnumSet;

/**
 * @author Group 03
 */
class RegisterForTagNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of tags on bugreports when executed
     *
     * @param user    The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     * @param tags    The tags on for which the user wishes to subscribe
     * @throws IllegalArgumentException When the given subject is null
     * @throws IllegalArgumentException When the given subject is Terminated
     */
    RegisterForTagNotificationsModelCmd(User user, Subject subject, EnumSet<Tag> tags) throws IllegalArgumentException {
        super(user);

        if (subject == null) {
            throw new IllegalArgumentException("The given subject is null");
        }
        if (subject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated");
        }
        this.subject = subject;

        this.tags = tags;
    }

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of all tags on bugreports when executed
     *
     * @param user    The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     * @see RegisterForTagNotificationsModelCmd#RegisterForTagNotificationsModelCmd(User, Subject, EnumSet)
     */
    RegisterForTagNotificationsModelCmd(User user, Subject subject) {
        this(user, subject, null);
    }

    private Subject subject;
    private EnumSet<Tag> tags;


    /**
     * This method executes this model command.
     *
     * @return The created TagMailbox representing the subscription that contains the notifications
     * @throws IllegalArgumentException if on of the arguments is invalid
     * @throws IllegalArgumentException If subject is terminated
     * @throws IllegalStateException    if the state of this command is invalid
     * @see Mailbox#tagSubscribe(Subject)
     * @see #setExecuted()
     */
    @Override
    TagMailbox exec() throws IllegalArgumentException, IllegalStateException {
        if (subject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated.");
        }

        this.setExecuted();
        TagMailbox tmb;
        if (this.tags == null) {
            tmb = this.getMailbox().tagSubscribe(this.subject);
        } else {
            tmb = this.getMailbox().tagSubscribe(this.subject, this.tags);
        }
        this.setNewMailbox(tmb);
        return tmb;
    }

}
