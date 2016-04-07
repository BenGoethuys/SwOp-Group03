package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.AbstractSystemSubject;
import bugtrap03.bugdomain.usersystem.notification.CreationMailBox;
import bugtrap03.bugdomain.usersystem.notification.Mailbox;

/**
 * Created by Bruno on 7-4-2016.
 */
public abstract class RegisterForNotificationsModelCmd extends ModelCmd {

    RegisterForNotificationsModelCmd(User user){
        this.subscriber = user;
        this.isExecuted = false;
    }

    private User subscriber;
    private boolean isExecuted;
    private Mailbox newMailbox;

    protected Mailbox getMailbox(){
        return this.subscriber.getMailbox();
    }

    protected void setNewMailbox(Mailbox mb){
        if (this.isValidMailBox(mb)){
            this.newMailbox = mb;
        } else throw new IllegalArgumentException("Invalid mailbox");
    }

    private boolean isValidMailBox(Mailbox mb){
        if(mb == null){
            return false;
        }
        return true;
    }

    @Override
    boolean undo() {
        if (! this.isExecuted()) {
            return false;
        }
        this.subscriber.getMailbox().unsubscribe(this.newMailbox);
        return true;
    }

    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

    protected void setExecuted(){
        if (! this.isExecuted()){
            this.isExecuted = true;
        } else {
            throw new IllegalStateException("This model command was already executed");
        }
    }

    @Override
    public String toString() {
        return ("Created subscription: " + this.newMailbox.getInfo());
    }
}
