package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.User;

/**
 * This class represents a notification.
 * @author group 03
 */
public class BugReportNotification extends Notification {

    /**
     * The constructor for a new notification with a given notification, a bugreport of which an attribute has changed,
     * the subject for the subscription that created the notification.
     *
     * @param notification The string message for this notification
     * @param bugReport The bugreport of which an attribute has changed.
     * @param subject The subject from the subscription that creates the notification.
     * @throws IllegalArgumentException
     */
    public BugReportNotification(String notification, BugReport bugReport, Subject subject) throws IllegalArgumentException{
        super(notification, subject);
        this.setBugReport(bugReport);
    }

    private BugReport bugReport;

    /**
     * This method sets the bugreport for this notifaction.
     *
     * @param bugReport Teh bugreport to set.
     *
     * @throws IllegalArgumentException if the bugreport is invalid.
     * @see #isValidBugReport(BugReport)
     */
    private void setBugReport(BugReport bugReport) throws IllegalArgumentException{
        if (this.isValidBugReport(bugReport)){
            this.bugReport = bugReport;
        } else {
            throw new IllegalArgumentException("The given bug report is invalid");
        }
    }

    /**
     * This method checks the validity of a given bugreport.
     *
     * @param bugReport the bugreport to check.
     *
     * @return True if the given bugreport not null
     */
    public boolean isValidBugReport(BugReport bugReport){
        if (bugReport == null){
            return false;
        }
        return true;
    }

    /**
     * This method returns a string representation of this notification when 'opened'.
     * It gives a standard textual denial if the notification is private for the given User.
     * If not, it returns a string containing the message, bugreport name and subject name.
     *
     * @param user The user that wishes to open this notification.
     *
     * @return A string representation of the contents of this notification.
     */
    @Override
    @DomainAPI
    public String open(User user){
        if (! bugReport.isVisibleTo(user)){
            return " \tThis notification is closed for you at the moment.";
        }
        StringBuilder message = new StringBuilder("\t" + this.message);
        message.append(bugReport.getTitle());
        message.append("\n\tThis notification originated from the subscription on: ");
        message.append(subject.getSubjectName());
        return message.toString();
    }

    /**
     * This method determines if a given object is equal to this notification.
     * @param object The given object to compare
     * @return true if the object is a notification with the same attributes as this notification.
     */
    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (! (object instanceof BugReportNotification)){
            return false;
        }
        BugReportNotification bugReportNotification = (BugReportNotification) object;
        if (this.subject != bugReportNotification.subject){
            return false;
        }
        if (this.bugReport != bugReportNotification.bugReport){
            return false;
        }
        if (! this.message.equals(bugReportNotification.message)){
            return false;
        }
        return true;
    }

    /**
     * This method determines the has value of this notification.
     * @return An int hashvalue.
     */
    @Override
    public int hashCode() {
        return ((((this.bugReport.hashCode() * 113) + this.subject.hashCode()) * 31) + this.message.hashCode());
    }
}
