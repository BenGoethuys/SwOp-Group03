package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * This class represents the mailboxes dedicated to catching notifications from creations.
 *
 * @author Group 03
 */
public class CreationMailBox extends Mailbox {

    public CreationMailBox(AbstractSystemSubject subj) throws IllegalArgumentException{
        super();
        this.setSubject(subj);
    }

    private AbstractSystemSubject subject;

    private void setSubject(AbstractSystemSubject subj) throws IllegalArgumentException{
        if (this.isValidSubject(subj)){
            this.subject = subj;
        }
        throw new IllegalArgumentException("This subject is invalid for this mailbox.");
    }

    public void update(BugReport bugReport){
        this.addNotification(new Notification("The following bugreport has been created: ", bugReport, this.subject));
    }

    @Override
    public String getInfo(){
        StringBuilder message = new StringBuilder();
        message.append("You are subscribed to the creation of Bugreports on ");
        message.append(this.subject.getSubjectName());
        message.append(" and all it's subsystems");
        return message.toString();
    }
}
