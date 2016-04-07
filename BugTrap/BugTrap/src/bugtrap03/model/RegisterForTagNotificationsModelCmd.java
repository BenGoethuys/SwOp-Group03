package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.Subject;
import bugtrap03.bugdomain.usersystem.notification.TagMailBox;

import java.util.EnumSet;

/**
 * Created by Bruno on 7-4-2016.
 */
class RegisterForTagNotificationsModelCmd extends RegisterForNotificationsModelCmd {


    RegisterForTagNotificationsModelCmd(User user, Subject subject, EnumSet<Tag> tags){
        super(user);
        this.subject = subject;
        this.tags = tags;
    }

    RegisterForTagNotificationsModelCmd(User user, Subject subject){
        this(user,subject,null);
    }

    private Subject subject;
    private EnumSet<Tag> tags;

    @Override
    TagMailBox exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
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
