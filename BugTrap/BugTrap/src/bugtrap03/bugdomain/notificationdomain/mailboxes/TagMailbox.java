package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.BugReportNotification;

import java.util.EnumSet;

/**
 * This class represents the mailboxes dedicated to catching notifications from tag changes.
 *
 * @author Group 03
 */
@DomainAPI
public class TagMailbox extends SubjectMailbox<BugReport, Subject> {

    /**
     * The constructor for a new mailbox subscription to the change of tag to a given tag on the subject.
     *
     * NOTE: This constructor should be only called by {@link Mailbox#tagSubscribe(Subject)} for correct coupling.
     *
     * @param subject The subject to subscribe on.
     * @param tags The tags to subscribe on.
     *
     * @throws IllegalArgumentException if the subject is invalid.
     * @throws IllegalArgumentException if the tags are invalid
     * @see #setSubject(Subject)
     * @see #setTags(EnumSet)
     */

    public TagMailbox(Subject subject, EnumSet<Tag> tags) throws IllegalArgumentException{
        super(subject);
        this.setTags(tags);
    }

    private EnumSet<Tag> tags;

    /**
     * This method sets the tags of this mailbox.
     *
     * @param tags The tags to set.
     *
     * @throws IllegalArgumentException if the given tags are invalid.
     * @see #isValidTags(EnumSet)
     */
    private void setTags(EnumSet<Tag> tags)throws IllegalArgumentException{
        if (! this.isValidTags(tags)){
            throw new IllegalArgumentException("The given tag list is not valid");
        }
        this.tags = tags;
    }

    /**
     * This method checks the validity of the given tags.
     *
     * @param tags The tags to check.
     *
     * @return true if the Tag enumset is not empty.
     */
    private boolean isValidTags(EnumSet<Tag> tags){
        if (tags == null){
            return false;
        }
        if (tags.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * This method updates the notifications list with a new notification if the tag on a begureport has been changed.
     *
     * @param bugReport The bugreport of which the tag is changed.
     *
     * @return The added notification.
     */
    public BugReportNotification update(BugReport bugReport){
        StringBuilder message = new StringBuilder();
        if (this.tags.contains(bugReport.getTag())) {
            message.append(" has been updated with a new tag: ");
            message.append(bugReport.getTag());
            message.append(".");
            BugReportNotification newNotif = new BugReportNotification(message.toString(), bugReport, this.subject);
            this.addNotification(newNotif);
            return newNotif;
        }else{
            return null;
        }
    }

    /**
     * This method returns a String representation of the mailbox information.
     *
     * @return A String containing the subject name to which this mailbox is subscribed
     * and a textual explanation of the subscription.
     */
    @DomainAPI
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to a change of following tags: ");
        message.append(this.tags.toString());
        message.append(" on: ");
        message.append(this.subject.getSubjectName());
        return message.toString();
    }

    /**
     * This method returns a copy of the tags in which this subscription is interested.
     * @return An enumset of tags.
     */
    @DomainAPI
    public EnumSet<Tag> getTagsOfInterest(){
        return EnumSet.copyOf(this.tags);
    }
}
