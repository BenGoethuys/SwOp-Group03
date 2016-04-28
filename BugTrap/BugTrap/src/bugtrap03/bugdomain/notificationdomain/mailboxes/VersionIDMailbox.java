package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * This class represents a subscription to the update of a versionID
 * @author Group 03
 */
public class VersionIDMailbox extends SubjectMailbox<AbstractSystem, AbstractSystemSubject>{

    /**
     * This is the constructor of a mailbox that represents the subscription
     * on the update of VersionID's under the given abstract system subject
     * @param abstractSystemSubject The subject of the subscriptionystem
     * @throws IllegalArgumentException if the given abstractSubject is invalid
     * @see #isValidSubject(Subject)
     */
    public VersionIDMailbox(AbstractSystemSubject abstractSystemSubject) throws IllegalArgumentException{
        super(abstractSystemSubject);
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder ("You are subscribed to the change of VersionIDs on ");
        info.append(this.subject.getSubjectName());
        info.append(" and all it's subsystems.");
        return info.toString();
    }

    @Override
    public Notification update(AbstractSystem changedObject) {
        StringBuilder message = new StringBuilder("\tThe VersionID ");
        message.append(changedObject.getVersionID().toString());
        message.append(" has been set on: ");
        message.append(changedObject.getSubjectName());
        Notification newNotif = new Notification(message.toString(), this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }
}
