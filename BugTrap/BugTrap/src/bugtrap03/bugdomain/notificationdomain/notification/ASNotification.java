package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
public class ASNotification extends Notification{
    /**
     * This is the constructor of notifications for a change on an abstract system
     * @param message The message of this notification
     * @param abstractSystem The AS with a change interesting for this notification
     * @param subject The subject from the original subscription
     * @throws IllegalArgumentException if one of the arguments is invalid
     * @see #isValidAS(AbstractSystem)
     * @see Notification#Notification(String, Subject)
     */
    public ASNotification(String message, AbstractSystem abstractSystem, Subject subject)
            throws IllegalArgumentException{
        super(message, subject);
        this.setAbstractSystem(abstractSystem);
    }

    protected AbstractSystem abstractSystem;

    /**
     * This method sets the AS of this Notification if the AS is valid
     * @param abstractSystem The AS to set
     * @throws IllegalArgumentException if the AS is invalid
     * @see #isValidAS(AbstractSystem)
     */
    private void setAbstractSystem(AbstractSystem abstractSystem) throws IllegalArgumentException{
        if (!this.isValidAS(abstractSystem)){
            throw new IllegalArgumentException("Invalid abstract system for this notification");
        }
        this.abstractSystem = abstractSystem;
    }

    /**
     * This method checks the validity of a given abstract system
     * @param abstractSystem The AS to check
     * @return True is the AS is not null
     */
    public boolean isValidAS(AbstractSystem abstractSystem){
        if (abstractSystem == null){
            return false;
        }
        return true;
    }

    /**
     * This method opens the notification and returns a string representing the contents of this notification
     * @param user The user that wishes to open the notification
     * @return The string message of this notification
     */
    @Override
    @DomainAPI
    public String open(User user) {
        StringBuilder message = new StringBuilder("\t" + this.message);
        message.append(abstractSystem.getName());
        message.append("\n\tThis notification originated from the subscription on: ");
        message.append(subject.getSubjectName());
        return message.toString();
    }

    /**
     * This method checks the equality of this ASNotification to a given object.
     * @param object The object to compare to
     * @return True if the message, subject and AS are equal
     */
    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (! (object instanceof ASNotification)){
            return false;
        }
        ASNotification asNotification = (ASNotification) object;
        if (this.subject != asNotification.subject){
            return false;
        }
        if (this.abstractSystem != asNotification.abstractSystem){
            return false;
        }
        if (! this.message.equals(asNotification.message)){
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
        return ((((this.abstractSystem.hashCode() * 113) + this.subject.hashCode()) * 31) + this.message.hashCode());
    }
}
