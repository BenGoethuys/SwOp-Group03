package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * @author Group 03
 */
public class MilestoneMailbox extends SubjectMailbox<AbstractSystem, AbstractSystemSubject> {

    /**
     * The constructor for a mailbox representing the subscription on the change of specific milestones.
     *
     * @param milestone             The specified milestone of interest for this subscription. Can be null if all milestones are of interest.
     * @param abstractSystemSubject The object from which the subscription originates.
     */
    public MilestoneMailbox(Milestone milestone, AbstractSystemSubject abstractSystemSubject) {
        super(abstractSystemSubject);
        this.milestone = milestone;
    }

    /**
     * A constructor without for a mailbox interested in all milestones.
     *
     * @param abstractSystemSubject The object from which the subscription originates.
     */
    public MilestoneMailbox(AbstractSystemSubject abstractSystemSubject) {
        this(null, abstractSystemSubject);
    }

    private Milestone milestone;

    /**
     * Tyis method returns the information about the subscription this mailbox represents.
     *
     * @return A string containing info.
     */
    @Override
    public String getInfo() {
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the change of ");
        if (this.milestone == null) {
            message.append("a new milestone");
        } else {
            message.append("the specific milestone ");
            message.append(this.milestone.toString());
        }
        message.append(" on: ");
        message.append(subject.getSubjectName());
        return message.toString();
    }

    /**
     * This method updated the notificationslist of this mailbox with a new notification about the interesting change
     * in the given object.
     *
     * @param changedObject The object of interest that has been changed.
     * @return The new notification
     * @throws IllegalArgumentException if the given changed object is null.
     */
    @Override
    public Notification update(AbstractSystem changedObject) throws IllegalArgumentException {
        if (changedObject == null) {
            throw new IllegalArgumentException("changed object cannot be null to update a mailbox");
        }
        Notification asNotification;
        if (this.milestone == null) {
            asNotification = new Notification(("\tThe milestone " + changedObject.getMilestone().toString() + " has been set on: "), this.subject);
        } else {
            if (this.milestone == changedObject.getMilestone()) {
                asNotification = new Notification("\tThe milestone " + this.milestone.toString() + " has been set on: ", this.subject);

            } else {
                asNotification = null;
            }
        }
        if (asNotification != null) {
            this.addNotification(asNotification);
        }
        return asNotification;
    }
}
