package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;

/**
 * This class represents the mailboxes dedicated to catching notifications from comment changes.
 *
 * @author Group 03
 */
public class CommentMailBox extends Mailbox {

    public CommentMailBox(Subject subjectt) throws IllegalArgumentException{
        super();
        this.setSubject(subject);
    }

    private Subject subject;

    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)){
            this.subject = subject;
        }
        throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
    }

    public void update(BugReport bugReport){
        this.addNotification(new Notification("A comment has been created on ", bugReport, this.subject));
    }

    @Override
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the creation of comments on ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }
}
