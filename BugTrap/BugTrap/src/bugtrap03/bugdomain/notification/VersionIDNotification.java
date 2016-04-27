package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
public class VersionIDNotification extends ASNotification {


    /**
     * This is the constructor of notifications for a change on an abstract system
     *
     * @param message        The message of this notification
     * @param abstractSystem The AS with a change interesting for this notification
     * @param subject        The subject from the original subscription
     * @throws IllegalArgumentException if one of the arguments is invalid
     * @see #isValidAS(AbstractSystem)
     * @see Notification#Notification(String, Subject)
     */
    public VersionIDNotification(String message, AbstractSystem abstractSystem, Subject subject) throws IllegalArgumentException {
        super(message, abstractSystem, subject);
    }
}
