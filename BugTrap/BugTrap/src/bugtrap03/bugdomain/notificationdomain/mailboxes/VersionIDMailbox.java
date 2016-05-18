package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * This class represents a subscription to the update of a versionID
 *
 * @author Group 03
 */
public class VersionIDMailbox extends SubjectMailbox<AbstractSystem, AbstractSystemSubject> {

    /**
     * This is the constructor of a mailbox that represents the subscription
     * on the update of VersionID's under the given abstract system subject
     *
     * @param abstractSystemSubject The subject of the subscriptionystem
     * @throws IllegalArgumentException if the given abstractSubject is invalid
     * @see #isValidSubject(Subject)
     */
    public VersionIDMailbox(AbstractSystemSubject abstractSystemSubject) throws IllegalArgumentException {
        super(abstractSystemSubject);
    }

    /**
     * This method returns the information about the subscription this mailbox represents.
     *
     * @return A string containing info.
     */
    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder("You are subscribed to the change of VersionIDs on ");
        info.append(this.subject.getSubjectName());
        info.append(" and all it's subsystems.");
        return info.toString();
    }

    /**
     * This method updates the mailbox with a new notification about the changed object.
     *
     * @param changedObject The object of interest that has been changed.
     * @return The new notification.
     * @throws IllegalArgumentException if the changed object is null.
     */
    @Override
    public Notification update(AbstractSystem changedObject) throws IllegalArgumentException {
        if (changedObject == null) {
            throw new IllegalArgumentException("changed object cannot be null to update a mailbox");
        }
        StringBuilder message = new StringBuilder("\tThe VersionID ");
        message.append(changedObject.getVersionID().toString());
        message.append(" has been set on: ");
        message.append(changedObject.getSubjectName());
        Notification newNotif = new Notification(message.toString(), this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }
}
