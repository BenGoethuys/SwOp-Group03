package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.ProjectNotification;

/**
 * @author Group 03
 */
public abstract class ProjectAbstractMailbox<P extends Subject, Q extends ProjectNotification> extends AbstractMailbox<P, Q> {

    public ProjectAbstractMailbox(ProjectSubject projectSubject){
        this.setSubject(projectSubject);
    }

    protected ProjectSubject subject;

    /**
     * This method sets the subject for this mailbox.
     *
     * @param subject The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(ProjectSubject subject) throws IllegalArgumentException{
        if (! this.isValidSubject(subject)){
            throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
        }
        this.subject = subject;
    }
}
