package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.ProjectNotification;

/**
 * Created by Kwinten on 28/04/2016.
 */
public class ForkMailbox extends ProjectAbstractMailbox<Project, ProjectNotification> {

    public ForkMailbox(ProjectSubject projectSubject) {
        super(projectSubject);
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public ProjectNotification update(Project changedObject) {
        return null;
    }
}
