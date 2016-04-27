package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.notificationdomain.notification.ASNotification;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;

/**
 * This class represents a subscription to the update of a versionID
 * @author Group 03
 */
public class VersionIDMailbox extends ASTypeMailbox<AbstractSystem, ASNotification> {

    /**
     * This is the constructor of a mailbox that represents the subscription
     * on the update of VersionID's under the given abstract system subject
     * @param abstractSystemSubject The subject of the subscriptionystem
     * @throws IllegalArgumentException if the given absytractSubject is invalid
     * @see ASTypeMailbox#ASTypeMailbox(AbstractSystemSubject, MailboxType)
     */
    public VersionIDMailbox(AbstractSystemSubject abstractSystemSubject) throws IllegalArgumentException{
        super(abstractSystemSubject, MailboxType.VERSIONID_UPDATE);
    }

    @Override
    public ASNotification update(AbstractSystem changedObject) {
        ASNotification newNotif = new ASNotification(
                ("The VersionID " + changedObject.getVersionID().toString() + " has been set on: "),
                changedObject, this.subject);
        this.addNotification(newNotif);
        return newNotif;
    }
}
