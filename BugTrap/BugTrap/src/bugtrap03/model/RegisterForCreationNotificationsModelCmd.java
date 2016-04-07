package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.AbstractSystemSubject;
import bugtrap03.bugdomain.usersystem.notification.CreationMailBox;

/**
 * @author Group 03
 */
public class RegisterForCreationNotificationsModelCmd extends RegisterForNotificationsModelCmd {

    RegisterForCreationNotificationsModelCmd(User user, AbstractSystemSubject abstractSystemSubject){
        super(user);
        this.abstractSystemSubject = abstractSystemSubject;
    }

    private AbstractSystemSubject abstractSystemSubject;

    @Override
    CreationMailBox exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        this.setExecuted();
        CreationMailBox cmb = this.getMailbox().creationSubscribe(this.abstractSystemSubject);
        this.setNewMailbox(cmb);
        return cmb;
    }


}
