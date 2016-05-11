package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.User;

import java.util.GregorianCalendar;

/**
 * @author Group 03
 */
@DomainAPI
public class Notification {

    public Notification(String message, Subject subject) {
        this.setSubject(subject);
        this.setMessage(message);
        this.date = new GregorianCalendar();
    }

    protected Subject subject;
    protected String message;
    private final GregorianCalendar date;

    /**
     * This method returns the date of this notificataion
     * @return The final date of the notification
     */
    public GregorianCalendar getDate(){
        return this.date;
    }

    /**
     * This method sets the message for this notification.
     *
     * @param message The String message to set.
     * @throws IllegalArgumentException if the message is invalid
     * @see #isValidMessage(String)
     */
    private void setMessage(String message) throws IllegalArgumentException {
        if (this.isValidMessage(message)) {
            this.message = message;
        } else {
            throw new IllegalArgumentException("The given message is invalid");
        }
    }

    /**
     * This method checks the validity of a given message.
     *
     * @param string the message to check
     * @return true if the message is not null or empty
     */
    public boolean isValidMessage(String string) {
        if (string == null) {
            return false;
        }
        if (string.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * This method sets the subject for this notification.
     *
     * @param subject The subject to set.
     * @throws IllegalArgumentException if the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(Subject subject) throws IllegalArgumentException {
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
     * @return True if the given subject is not null.
     */
    @DomainAPI
    public boolean isValidSubject(Subject subject) {
        if (subject == null) {
            return false;
        }
        return true;
    }

    /**
     * This method opens this notification.
     * @param user The user that wishes to open this notification
     * @return A string of the message.
     */
    @DomainAPI
    public String open(User user){
        StringBuilder message = new StringBuilder(this.message);
        message.append("\n\tThis notification originated from the subscription on: ");
        message.append(subject.getSubjectName());
        return message.toString();
    }

    /**
     * This nethod tells if this notification is equal to a given object.
     * @param object The object to compare
     * @return true if the given object is a notification with the same subject and message.
     */
    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (! (object instanceof Notification)){
            return false;
        }
        Notification notification = (Notification) object;
        if (this.subject != notification.subject){
            return false;
        }
        if (! this.message.equals(notification.message)){
            return false;
        }
        return true;
    };

    /**
     * this method calculates a hash value for this notification based upon the hashvalue of the subject and message.
     * @return An int hash value.
     */
    @Override
    public int hashCode(){
    return((113*this.subject.hashCode())*this.message.hashCode());
    }
}
