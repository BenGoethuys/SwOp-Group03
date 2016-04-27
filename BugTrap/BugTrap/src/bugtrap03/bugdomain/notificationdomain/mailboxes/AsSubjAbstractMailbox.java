package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import bugtrap03.bugdomain.notificationdomain.Subject;

/**
 * Created by Kwinten on 27/04/2016.
 */
public abstract class AsSubjAbstractMailbox<P extends Subject, Q extends Notification> extends AbstractMailbox<P,Q> {

    /**
     * The constructor for this abstract mailbox that contains a Abstract sytem subject
     * @param abstractSystemSubject The subject of this mailbox subscription.
     * @throws IllegalArgumentException if the given subject is invalid
     * @see AbstractMailbox#isValidSubject(Subject)
     */
    public AsSubjAbstractMailbox(AbstractSystemSubject abstractSystemSubject) throws IllegalArgumentException{
        this.setSubject(abstractSystemSubject);
    }

    protected AbstractSystemSubject subject;

    /**
     * This method sets the subject for this mailbox.
     *
     * @param subject The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(AbstractSystemSubject subject) throws IllegalArgumentException{
        if (! this.isValidSubject(subject)){
            throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
        }
        this.subject = subject;
    }
}
