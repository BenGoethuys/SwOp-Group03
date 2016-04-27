package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.VersionID;

/**
 * This class represents a subscription to the update of a versionID
 * @author Group 03
 */
public class VersionIDMailbox extends ASTypeMailbox<AbstractSystem> {

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
    public Notification update(AbstractSystem changedObject) {
        ASNotification newNotif = new ASNotification(
                ("The VersionID " + changedObject.getVersionID().toString() + " has been set on: "),
                changedObject, this.getSubject());
        this.addNotification(newNotif);
        return newNotif;
    }
}
