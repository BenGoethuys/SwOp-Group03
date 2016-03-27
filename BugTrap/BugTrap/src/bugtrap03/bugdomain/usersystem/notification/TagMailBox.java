package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;

import java.util.EnumSet;

/**
 * This class represents the mailboxes dedicated to catching notifications from tag changes.
 *
 * @author Group 03
 */
public class TagMailBox extends Mailbox {

    public TagMailBox(Subject subject, EnumSet<Tag> tags) throws IllegalArgumentException{
        this.setSubject(subject);
        this.setTags(tags);
    }

    private Subject subject;
    private EnumSet<Tag> tags;

    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)){
            this.subject = subject;
        }
        throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
    }

    private void setTags(EnumSet<Tag> tags)throws IllegalArgumentException{
        if (this.isValidTags(tags)){
            this.tags = tags;
        }
        throw new IllegalArgumentException("The given tag list is not valid");
    }

    private boolean isValidTags(EnumSet<Tag> tags){
        if (tags.isEmpty()){
            return false;
        }
        return true;
    }

    public void update(BugReport bugReport){
        StringBuilder message = new StringBuilder();
        message.append("The tag ");
        message.append(bugReport.getTag());
        message.append(" has been set on ");
        this.addNotification(new Notification(message.toString(), bugReport, this.subject));
    }

    @Override
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to a change of following tags: ");
        message.append(this.tags.toString());
        message.append(" on ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }
}
