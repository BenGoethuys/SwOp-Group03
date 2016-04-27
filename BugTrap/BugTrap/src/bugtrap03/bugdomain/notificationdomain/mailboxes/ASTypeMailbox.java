package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import bugtrap03.bugdomain.notificationdomain.Subject;

/**
 * This class represents a mailbox with a Abstract system asSubject and a given type subscription
 * (bugreport creation or version id adjustment)
 * @author Group 03
 */
public abstract class ASTypeMailbox<P extends Subject, Q extends Notification> extends AsSubjAbstractMailbox<P,Q> {
    /**
     * The constructor for a mailbox subscription of a given type to
     * a given abstract system asSubject.
     *
     * @param subj The asSubject on which the mailbox subscribes.
     * @param mbType The type of mailbox
     * @throws IllegalArgumentException if the asSubject or mbType is invalid
     * @see #isValidMailboxType(MailboxType)
     */
    public ASTypeMailbox(AbstractSystemSubject subj, MailboxType mbType) throws IllegalArgumentException{
        super(subj);
        this.setType(mbType);
    }

    private MailboxType type;

    /**
     * This method sets the mailbox type of this mailbox.
     * @param mailboxType The type to set.
     * @throws IllegalArgumentException if the given type is invalid
     * @see #isValidMailboxType(MailboxType)
     */
    private void setType(MailboxType mailboxType) throws IllegalArgumentException{
        if (! this.isValidMailboxType(mailboxType)){
            throw new IllegalArgumentException("The given mailboxtype is invalid for this mailbox");
        }
        this.type = mailboxType;
    }

    /**
     * This method checks the validity of a given mailbox type
     * @param mailboxType The mailboxtype to check.
     * @return True if the mailboxtype is not null.
     */
    public boolean isValidMailboxType(MailboxType mailboxType){
        if (mailboxType == null){
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation of the mailbox information.
     *
     * NOTE: This constructor should be only called by {@link Mailbox#creationSubscribe(AbstractSystemSubject)} for correct coupling.
     *
     * @return A String containing the asSubject name to which this mailbox is subscribed
     * and a textual explanation of the subscription.
     */
    @Override
    @DomainAPI
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to ");
        message.append(this.type.getMBTypeInfo());
        message.append(" on ");
        message.append(this.subject.getSubjectName());
        message.append(" and all it's subsystems.");
        return message.toString();
    }
}
