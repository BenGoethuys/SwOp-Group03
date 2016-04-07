package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.CommentMailBox;
import bugtrap03.bugdomain.usersystem.notification.Subject;

/**
 * @author Group 03
 */
class RegisterForCommentNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    RegisterForCommentNotificationsModelCmd(User user, Subject subject){
        super(user);
        this.subject = subject;
    }

    private Subject subject;

    @Override
    CommentMailBox exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        this.setExecuted();
        CommentMailBox cmb = this.getMailbox().commentSubscribe(this.subject);
        this.setNewMailbox(cmb);
        return cmb;
    }
}
