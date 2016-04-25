package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * This class represents the mailboxes dedicated to catching notifications from bug report creations.
 *
 * @author Group 03
 */
@DomainAPI
public class GeneralTypeMailbox extends Mailbox {

    /**
     * The constructor for a mailbox subscription to the
     * creation of bugreports on the given abstract system subject.
     *
     * @param subj The subject on which the mailbox subscribes.
     *
     * @param mbType
     * @throws IllegalArgumentException if the subject is invalid
     * @see #setSubject(AbstractSystemSubject)
     */
    public GeneralTypeMailbox(AbstractSystemSubject subj, MailboxType mbType) throws IllegalArgumentException{
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
     * This method updates the notifications list with a new notification if a bugreport has been created.
     *
     * @param bugReport The created bugreport.
     *
     * @return The added notification.
     */
    public BugReportNotification update(BugReport bugReport){
        BugReportNotification newNotif = new BugReportNotification("The following bugreport has been created: ", bugReport, this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }

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
