package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.usersystem.User;

/**
 * This class represents a notification.
 * @author group 03
 */
public class Notification {

    /**
     * The constructor for a new notification with a given notification, a bugreport of which an attribute has changed,
     * the subject for the subscription that created the notification.
     *
     * @param notification The string message for this notification
     * @param bugReport The bugreport of which an attribute has changed.
     * @param subject The subject from the subscription that creates the notification.
     * @throws IllegalArgumentException
     */
    public Notification(String notification, BugReport bugReport, Subject subject) throws IllegalArgumentException{
        this.setMessage(notification);
        this.setBugReport(bugReport);
        this.setSubject(subject);
    }

    private String message;
    private BugReport bugReport;
    private Subject subject;

    /**
     * This method sets the message for this notification.
     *
     * @param message The String message to set.
     *
     * @throws IllegalArgumentException if the message is invalid
     * @see #isValidMessage(String)
     */
    private void setMessage(String message) throws  IllegalArgumentException{
        if (this.isValidMessage(message)){
            this.message = message;
        } else {
            throw new IllegalArgumentException("The given message is invalid");
        }
    }

    /**
     * This method checks the validity of a given message.
     *
     * @param string the message to check
     *
     * @return true if the message is not null or empty
     */
    public boolean isValidMessage(String string){
        if (string == null){
            return false;
        }
        if (string.equals("")){
            return false;
        }
        return true;
    }

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
     * This method sets the subject for this notification.
     *
     * @param subject The subject to set.
     *
     * @throws IllegalArgumentException if the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)) {
            this.subject = subject;
        } else {
            throw new IllegalArgumentException("The given subject is invalid");
        }
    }

    /**
     * This method checks the validity of a given subject.
     *
     * @param subject The subject to check.
     *
     * @return True if the given subject is not null.
     */
    public boolean isValidSubject(Subject subject){
        if (subject == null){
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
        if (! (object instanceof Notification)){
            return false;
        }
        Notification notification = (Notification) object;
        if (this.subject != notification.subject){
            return false;
        }
        if (this.bugReport != notification.bugReport){
            return false;
        }
        if (! this.message.equals(notification.message)){
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
