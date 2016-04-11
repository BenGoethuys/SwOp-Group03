package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * This class represents the mailboxes dedicated to catching notifications from comment changes.
 *
 * @author Group 03
 */
public class CommentMailBox extends Mailbox {

    /**
     * The constructor for a new mailbox subscribed to the creation of comments on the subject.
     *
     * @param subject The subject to subscribe on.
     * @throws IllegalArgumentException if the subject is invalid.
     * @see #setSubject(Subject)
     */
    public CommentMailBox(Subject subject) throws IllegalArgumentException{
        super();
        this.setSubject(subject);
    }

    private Subject subject;

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

    /**
     * This method updates the notifications list with a new notification if a comment on a bugreport has been created.
     *
     * @param bugReport The bugreport on which a comment has been created.
     */
    public void update(BugReport bugReport){
        this.addNotification(new Notification("A comment has been created on ", bugReport, this.subject));
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
        message.append("You are subscribed to the creation of comments on ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }
}
