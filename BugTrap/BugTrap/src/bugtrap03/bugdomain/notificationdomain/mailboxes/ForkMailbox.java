package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * @author Group 03
 */
public class ForkMailbox extends SubjectMailbox<Project, ProjectSubject> {

    public ForkMailbox(ProjectSubject projectSubject) {
        super(projectSubject);
    }

    @Override
    public String getInfo() {
        return ("You are subscribed to the forking of " + this.subject.getSubjectName());
    }

    @Override
    public Notification update(Project changedObject) {
        if (changedObject == null){
            throw new IllegalArgumentException("Changed object for a forking notification cannot be null");
        }
        StringBuilder message = new StringBuilder("\t"+this.subject.getSubjectName());
        message.append(" has forked into ");
        message.append(changedObject.getSubjectName());
        message.append(".");
        return new Notification(message.toString(), subject);
    }
}
