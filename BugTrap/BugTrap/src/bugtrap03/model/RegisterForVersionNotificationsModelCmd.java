package bugtrap03.model;

import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.mailboxes.VersionIDMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
public class RegisterForVersionNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * This is the constructor for the command to subscribe for the forking of a project.
     * @param user The user that wishes to subscribe
     * @param subject The project from which the user wishes to subscribe
     * @throws IllegalArgumentException If the projectsubject is null
     * @throws IllegalArgumentException if the given project subject is terminated
     */
    RegisterForVersionNotificationsModelCmd(User user, AbstractSystemSubject subject) {
        super(user);
        if (subject == null){
            throw new IllegalArgumentException("Projects subject cannot be null");
        }
        if (subject.isTerminated()){
            throw new IllegalArgumentException("The given project subject is terminated");
        }
        this.subject = subject;
    }

    private AbstractSystemSubject subject;

    /**
     * This method executes this command and creates a subscription for forking on the subject of this cmd
     * @return The created VersionIDMailbox that represents the subscription
     * @throws IllegalArgumentException If subject of this cmd is terminated
     * @throws IllegalStateException If the state of this cmd is invalid
     * @see bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox#versionIDSubscribe(AbstractSystemSubject)
     * @see #setExecuted()
     */
    @Override
    VersionIDMailbox exec() throws IllegalArgumentException, IllegalStateException {
        if (subject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated.");
        }
        this.setExecuted();
        VersionIDMailbox vmb = this.getMailbox().versionIDSubscribe(subject);
        this.setNewMailbox(vmb);
        return vmb;
    }
}
