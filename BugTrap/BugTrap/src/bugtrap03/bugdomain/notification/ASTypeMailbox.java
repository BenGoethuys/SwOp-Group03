package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.DomainAPI;

/**
 * This class represents a mailbox with a Abstract system subject and a given type subscription
 * (bugreport creation or version id adjustment)
 * @author Group 03
 */
public abstract class ASTypeMailbox<P extends Subject> extends Mailbox{
    /**
     * The constructor for a mailbox subscription of a given type to
     * a given abstract system subject.
     *
     * @param subj The subject on which the mailbox subscribes.
     * @param mbType The type of mailbox
     * @throws IllegalArgumentException if the subject or mbType is invalid
     * @see #setSubject(AbstractSystemSubject)
     */
    public ASTypeMailbox(AbstractSystemSubject subj, MailboxType mbType) throws IllegalArgumentException{
        super();
        this.setSubject(subj);
        this.setType(mbType);
    }

    private AbstractSystemSubject subject;
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
     * This method sets the subject for this mailbox.
     *
     * @param subj The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(AbstractSystemSubject subj) throws IllegalArgumentException{
        if (! this.isValidSubject(subj)){
            throw new IllegalArgumentException("This subject is invalid for this mailbox.");
        }
        this.subject = subj;
    }

    /**
     * This is a getter for the subject of this mailbox.
     * @return The subject of this ASTypeMailbox
     */
    public Subject getSubject(){
        return this.subject;
    }

    /**
     * This abstract method updates the notifications list with a new notification.
     *
     * @param changedObject The object of interest that has been changed.
     *
     * @return The added notification.
     */
    public abstract Notification update(P changedObject);

    /**
     * This method returns a String representation of the mailbox information.
     *
     * NOTE: This constructor should be only called by {@link Mailbox#creationSubscribe(AbstractSystemSubject)} for correct coupling.
     *
     * @return A String containing the subject name to which this mailbox is subscribed
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
