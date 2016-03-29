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

    /**
     * The constructor for a new mailbox subscription to the change of tag to a given tag on the subject.
     *
     * @param subject The subject to subscribe on.
     * @param tags The tags to subscribe on.
     *
     * @throws IllegalArgumentException if the subject is invalid.
     * @throws IllegalArgumentException if the tags are invalid
     * @see #setSubject(Subject)
     * @see #setTags(EnumSet)
     */
    public TagMailBox(Subject subject, EnumSet<Tag> tags) throws IllegalArgumentException{
        this.setSubject(subject);
        this.setTags(tags);
    }

    private Subject subject;
    private EnumSet<Tag> tags;

    /**
     * This method sets the subject for this mailbox.
     *
     * @param subject The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)){
            this.subject = subject;
        }
        throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
    }

    /**
     * This method sets the tags of this mailbox.
     *
     * @param tags The tags to set.
     *
     * @throws IllegalArgumentException if the given tags are invalid.
     * @see #isValidTags(EnumSet)
     */
    private void setTags(EnumSet<Tag> tags)throws IllegalArgumentException{
        if (this.isValidTags(tags)){
            this.tags = tags;
        }
        throw new IllegalArgumentException("The given tag list is not valid");
    }

    /**
     * This method checks the validity of the given tags.
     *
     * @param tags The tags to check.
     *
     * @return true if the Tag enumset is not empty.
     */
    private boolean isValidTags(EnumSet<Tag> tags){
        if (tags.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * This method updates the notifications list with a new notification if the tag on a begureport has been changed.
     *
     * @param bugReport The bugreport of which the tag is changed.
     */
    public void update(BugReport bugReport){
        StringBuilder message = new StringBuilder();
        message.append("The tag ");
        message.append(bugReport.getTag());
        message.append(" has been set on ");
        this.addNotification(new Notification(message.toString(), bugReport, this.subject));
    }

    /**
     * This method returns a String representation of the mailbox information.
     *
     * @return A String containing the subject name to which this mailbox is subscribed
     * and a textual explanation of the subscription.
     */
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
