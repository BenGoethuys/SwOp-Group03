package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import purecollections.PList;


/**
 * @author Group 03
 */
public abstract class AbstractMailbox<UpdatedObjectClass extends Subject> {

    public AbstractMailbox() throws IllegalArgumentException {
        this.notifications = PList.<Notification>empty();
        this.activate();
    }

    private PList<Notification> notifications;
    protected boolean isTerminated;

    /**
     * This method returns the specific notifications belonging to this mailbox.
     *
     * @return A PList of notifications belonging to this specific mailbox.
     */
    public PList<Notification> getNotifications() {
        return this.notifications;
    }

    /**
     * This method adds a notification to this mailbox.
     *
     * @param notif The notification to add.
     */
    public void addNotification(Notification notif) throws IllegalArgumentException {
        if (!this.isTerminated) {
            this.notifications = this.getNotifications().plus(notif);
        }
    }

    /**
     * This method returns a string representing the information about this subscription.
     *
     * @return A string with information.
     */
    @DomainAPI
    public abstract String getInfo();

    /**
     * This abstract method updates the notifications list with a new notification.
     *
     * @param changedObject The object of interest that has been changed.
     * @return The added notification.
     */
    @DomainAPI
    public abstract Notification update(UpdatedObjectClass changedObject);

    /**
     * This method sets the terminated status to false;
     */
    public void activate() {
        this.isTerminated = false;
    }

}
