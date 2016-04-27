package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
@DomainAPI
public abstract class Notification {

    public Notification(String message, Subject subject){
        this.setSubject(subject);
        this.setMessage(message);
    }

    protected Subject subject;
    protected String message;


    /**
     * This method sets the message for this notificationdomain.
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
     * This method sets the subject for this notificationdomain.
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
    //TODO isvalid in domainapi?
    public boolean isValidSubject(Subject subject){
        if (subject == null){
            return false;
        }
        return true;
    }

    @DomainAPI
    public abstract String open(User user);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
