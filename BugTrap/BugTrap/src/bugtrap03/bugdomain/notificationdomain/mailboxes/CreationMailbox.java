package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;

/**
 * This class represents the mailboxes dedicated to catching notifications from bug report creations.
 *
 * @author Group 03
 */
public class CreationMailbox extends SubjectMailbox<BugReport, AbstractSystemSubject> {

    /**
     * The constructor for a mailbox subscription to the
     * creation of bugreports on the given abstract system subject.
     *
     * @param subj The subject on which the mailbox subscribes.
     * @throws IllegalArgumentException if the subject is invalid
     * @see SubjectMailbox#isValidSubject(Subject)
     */
    public CreationMailbox(AbstractSystemSubject subj) throws IllegalArgumentException {
        super(subj);
    }

    /**
     * This method returns the information of the subscription this mailbox represents.
     *
     * @return A string containing the info.
     */
    public String getInfo() {
        StringBuilder info = new StringBuilder("You are subscribed to the creation of bugreports on ");
        info.append(this.subject.getSubjectName());
        info.append(" and all it's subsystems.");
        return info.toString();
    }

    /**
     * This method updates the notifications list with a new notification if a bugreport has been created.
     *
     * @param bugReport The created bugreport.
     * @return The added notification.
     */
    @Override
    public BugReportNotification update(BugReport bugReport) {
        BugReportNotification newNotif = new BugReportNotification(" has been created.", bugReport, this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }

}
