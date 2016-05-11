package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * @author Group 03
 */
public class ForkMailbox extends SubjectMailbox<Project, ProjectSubject> {

    /**
     * this constructor generates a new mailbox that represent the subscription on the forking of projects.
     * @param projectSubject The project from which the subscription originated from.
     */
    public ForkMailbox(ProjectSubject projectSubject) {
        super(projectSubject);
    }

    /**
     * This method returns the information about the subscription this mailbox represents.
     * @return A string of info.
     */
    @Override
    public String getInfo() {
        return ("You are subscribed to the forking of " + this.subject.getSubjectName());
    }

    /**
     * This method updates the notificationslist of this mailbox with a new notification about the change in the given object.
     * @param changedObject The object of interest that has been changed.
     *
     * @return The new notification
     * @throws IllegalArgumentException If the given object that changed is null;
     */
    @Override
    public Notification update(Project changedObject) throws IllegalArgumentException {
        if (changedObject == null){
            throw new IllegalArgumentException("Changed object for a forking notification cannot be null");
        }
        StringBuilder message = new StringBuilder("\t"+this.subject.getSubjectName());
        message.append(" has forked into ");
        message.append(changedObject.getSubjectName());
        message.append(".");
        Notification notification = new Notification(message.toString(), subject);
        this.addNotification(notification);
        return notification;
    }
}
