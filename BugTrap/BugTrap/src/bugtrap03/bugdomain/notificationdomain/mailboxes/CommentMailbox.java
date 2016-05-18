package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;

/**
 * This class represents the mailboxes dedicated to catching notifications from comment changes.
 *
 * @author Group 03
 */
@DomainAPI
public class CommentMailbox extends SubjectMailbox<BugReport, Subject> {

    /**
     * The constructor for a new mailbox subscribed to the creation of comments on the subject.
     *
     * @param subject The subject to subscribe on.
     * @throws IllegalArgumentException if the subject is invalid.
     * @see #setSubject(Subject)
     */
    public CommentMailbox(Subject subject) throws IllegalArgumentException {
        super(subject);
    }

    /**
     * This method returns a String representation of the mailbox information.
     * <p>
     * NOTE: This constructor should be only called by {@link Mailbox#commentSubscribe(Subject)} for correct coupling.
     *
     * @return A String containing the subject name to which this mailbox is subscribed
     * and a textual explanation of the subscription.
     */
    @DomainAPI
    public String getInfo() {
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the creation of comments on ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }

    /**
     * This method updates the notifications list with a new notification if a comment on a bugreport has been created.
     *
     * @param bugReport The bugreport on which a comment has been created.
     * @return The added notification.
     */
    @Override
    public BugReportNotification update(BugReport bugReport) {
        BugReportNotification newNotif = new BugReportNotification(" has been commented upon.", bugReport, this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }
}
