package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Subject;
import bugtrap03.bugdomain.usersystem.notification.TagMailBox;

import java.util.EnumSet;

/**
 * @author Group 03
 */
class RegisterForTagNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of tags on bugreports when executed
     * @param user The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     * @param tags The tags on for which the user wishes to subscribe
     */
    RegisterForTagNotificationsModelCmd(User user, Subject subject, EnumSet<Tag> tags){
        super(user);
        this.subject = subject;
        this.tags = tags;
    }

    /**
     * Create a {@link ModelCmd} that subscribes to the given subject
     * for the change of all tags on bugreports when executed
     * @param user The user that wishes to subscribe
     * @param subject The subject on which the user wishes to subscribe
     */
    RegisterForTagNotificationsModelCmd(User user, Subject subject){
        this(user,subject,null);
    }

    private Subject subject;
    private EnumSet<Tag> tags;


    /**
     * This method executes this model command.
     * @return The created commentmailbox representing the subscription that contains the notifications
     * @throws IllegalArgumentException if on of the arguments is invalid
     * @throws IllegalStateException if the state of this command is invalid
     * @see bugtrap03.bugdomain.usersystem.notification.Mailbox#tagSubscribe(Subject)
     * @see #setExecuted()
     */
    @Override
    TagMailBox exec() throws IllegalArgumentException, IllegalStateException {
        this.setExecuted();
        TagMailBox tmb;
        if (this.tags == null){
            tmb = this.getMailbox().tagSubscribe(this.subject);
        } else {
            tmb = this.getMailbox().tagSubscribe(this.subject, this.tags);
        }
        this.setNewMailbox(tmb);
        return tmb;
    }

}
