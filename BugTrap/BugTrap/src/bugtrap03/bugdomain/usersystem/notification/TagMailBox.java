package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;

/**
 * This class represents the mailboxes dedicated to catching notifications from tag changes.
 *
 * @author Group 03
 */
public class TagMailBox extends Mailbox {

    public TagMailBox(Subject subjectt, Tag[] tags) throws IllegalArgumentException{
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
        message.append("You are subscribed to a change of tag in ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }
}
