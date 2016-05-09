package bugtrap03.model;

import bugtrap03.bugdomain.notificationdomain.ProjectSubject;
import bugtrap03.bugdomain.notificationdomain.mailboxes.ForkMailbox;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
public class RegisterForForkNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * This is the constructor for the command to subscribe for the forking of a project.
     * @param user The user that wishes to subscribe
     * @param projectSubject The project from which the user wishes to subscribe
     * @throws IllegalArgumentException If the projectsubject is null
     * @throws IllegalArgumentException if the given project subject is terminated
     */
    public RegisterForForkNotificationsModelCmd(User user, ProjectSubject projectSubject) throws IllegalArgumentException{
        super(user);
        if (projectSubject == null){
            throw new IllegalArgumentException("Projects subject cannot be null");
        }
        if (projectSubject.isTerminated()){
            throw new IllegalArgumentException("The given project subject is terminated");
        }
        this.projectSubject = projectSubject;
    }

    private ProjectSubject projectSubject;

    /**
     * This method executes this command and creates a subscription for forking on the subject of this cmd
     * @return The created forkmailbox that represents the subscription
     * @throws IllegalArgumentException If subject of this cmd is terminated
     * @throws IllegalStateException If the state of this cmd is invalid
     */
    @Override
    ForkMailbox exec() throws IllegalArgumentException, IllegalStateException {
        if (projectSubject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated.");
        }
        this.setExecuted();
        ForkMailbox fmb = this.getMailbox().forkSubscribe(projectSubject);
        this.setNewMailbox(fmb);
        return fmb;
    }
}
