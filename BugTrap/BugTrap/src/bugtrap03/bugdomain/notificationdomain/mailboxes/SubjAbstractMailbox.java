package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import bugtrap03.bugdomain.notificationdomain.Subject;

/**
 * @author Groupt 03
 */
public abstract class SubjAbstractMailbox<P extends Subject, Q extends Notification> extends AbstractMailbox<P,Q> {

    /**
     * The constructor for this abstract mailbox that contains a normal subject
     * @param subject The subject of this mailbox subscription.
     * @throws IllegalArgumentException if the given subject is invalid
     * @see AbstractMailbox#isValidSubject(Subject)
     */
    public SubjAbstractMailbox(Subject subject) throws IllegalArgumentException{
        this.setSubject(subject);
    }

    protected Subject subject;

    /**
     * This method sets the subject for this mailbox.
     *
     * @param subject The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (! this.isValidSubject(subject)){
            throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
        }
        this.subject = subject;
    }
}
