package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * This class represents the mailboxes dedicated to catching notifications from bug report creations.
 *
 * @author Group 03
 */
public class CreationMailBox extends Mailbox {

    /**
     * The constructor for a mailbox subscription to the
     * creation of bugreports on the given abstract system subject.
     *
     * @param subj The subject on which the mailbox subscribes.
     *
     * @throws IllegalArgumentException if the subject is invalid
     * @see #setSubject(AbstractSystemSubject)
     */
    public CreationMailBox(AbstractSystemSubject subj) throws IllegalArgumentException{
        super();
        this.setSubject(subj);
    }

    private AbstractSystemSubject subject;

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
     */
    public void update(BugReport bugReport){
        this.addNotification(new Notification("The following bugreport has been created: ", bugReport, this.subject));
    }

    /**
     * This method returns a String representation of the mailbox information.
     *
     * @return A String containing the subject name to which this mailbox is subscribed
     * and a textual explanation of the subscription.
     */
    @Override
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the creation of Bugreports on ");
        message.append(this.subject.getSubjectName());
        message.append(" and all it's subsystems");
        return message.toString();
    }
}
