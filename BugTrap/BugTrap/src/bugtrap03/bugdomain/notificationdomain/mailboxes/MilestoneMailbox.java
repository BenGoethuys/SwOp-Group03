package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * @author Group 03
 */
public class MilestoneMailbox extends SubjectMailbox<AbstractSystem, AbstractSystemSubject> {

    public MilestoneMailbox(Milestone milestone, AbstractSystemSubject abstractSystemSubject) {
        super(abstractSystemSubject);
        this.milestone = milestone;
    }

    public MilestoneMailbox(AbstractSystemSubject abstractSystemSubject){
        this(null, abstractSystemSubject);
    }

    private Milestone milestone;

    @Override
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the change of ");
        if (this.milestone == null){
            message.append("a new milestone");
        } else{
            message.append("the specific milestone ");
            message.append(this.milestone.toString());
        }
        message.append(" on: ");
        message.append(subject.getSubjectName());
        return message.toString();
    }

    @Override
    public Notification update(AbstractSystem changedObject) throws IllegalArgumentException {
        if(changedObject == null){
            throw new IllegalArgumentException("changed object cannot be null to update a mailbox");
        }
        Notification asNotification;
        if(this.milestone == null){
            asNotification = new Notification(("\tThe milestone " + changedObject.getMilestone().toString() + " has been set on: "), this.subject);
        } else{
            if (this.milestone == changedObject.getMilestone()){
                asNotification = new Notification("\tThe milestone " + this.milestone.toString() + " has been set on: ", this.subject);

            } else{
                asNotification = null;
            }
        }
        return asNotification;
    }
}
