package bugtrap03.model;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.mailboxes.MilestoneMailbox;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
class RegisterForMilestoneNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of milestone on abstract systems
     * @param user The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     * @param milestone The milestone on for which the user wishes to subscribe
     *
     * @throws IllegalArgumentException When the given subject is null
     * @throws IllegalArgumentException When the given subject is Terminated
     */
    RegisterForMilestoneNotificationsModelCmd(User user, AbstractSystemSubject subject, Milestone milestone) throws IllegalArgumentException {
        super(user);
        if (subject == null) {
            throw new IllegalArgumentException("The given subject is null");
        }
        if (subject.isTerminated()){
            throw new IllegalArgumentException("The given subject is terminated");
        }
        this.subject = subject;
        this.milestone = milestone;
    }

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of milestone on abstract systems
     * @param user The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     * @throws IllegalArgumentException if the given subject is invalid
     * @see #RegisterForMilestoneNotificationsModelCmd(User, AbstractSystemSubject, Milestone);
     */
    RegisterForMilestoneNotificationsModelCmd(User user, AbstractSystemSubject subject) throws IllegalArgumentException {
        this(user,subject,null);
    }

    private AbstractSystemSubject subject;
    private Milestone milestone;

    /**
     * This method executes this model command.
     * @return The created TagMailBox representing the subscription that contains the notifications
     * @throws IllegalArgumentException If subject is terminated
     * @throws IllegalStateException if the state of this command is invalid
     * @see bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox#milestoneSubscribe(AbstractSystemSubject)
     * @see #setExecuted()
     */
    @Override
    MilestoneMailbox exec() throws IllegalArgumentException, IllegalStateException {
        if (subject.isTerminated()) {
            throw new IllegalArgumentException("The given subject is terminated.");
        }
        this.setExecuted();
        MilestoneMailbox mmb;
        if (this.milestone == null){
            mmb = this.getMailbox().milestoneSubscribe(this.subject);
        } else {
            mmb = this.getMailbox().milestoneSubscribe(this.subject, this.milestone);
        }
        this.setNewMailbox(mmb);
        return mmb;
    }
}
