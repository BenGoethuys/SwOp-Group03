package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;

/**
 * This class represents the mailboxes dedicated to catching notifications from bug report creations.
 *
 * @author Group 03
 */
@DomainAPI
public class CreationMailBox extends TypeMailbox<BugReport, BugReportNotification> {

    /**
     * The constructor for a mailbox subscription to the
     * creation of bugreports on the given abstract system subject.
     *
     * @param subj The subject on which the mailbox subscribes.
     * @throws IllegalArgumentException if the subject is invalid
     * @see TypeMailbox#ASTypeMailbox(AbstractSystemSubject, MailboxType)
     */
    public CreationMailBox(AbstractSystemSubject subj) throws IllegalArgumentException {
        super(subj, MailboxType.CREATION_BUGREP);
    }

    /**
     * This method updates the notifications list with a new notification if a bugreport has been created.
     *
     * @param bugReport The created bugreport.
     *
     * @return The added notification.
     */
    @Override
    public BugReportNotification update(BugReport bugReport){

        BugReportNotification newNotif = new BugReportNotification("The following bugreport has been created: ", bugReport, this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }

}
