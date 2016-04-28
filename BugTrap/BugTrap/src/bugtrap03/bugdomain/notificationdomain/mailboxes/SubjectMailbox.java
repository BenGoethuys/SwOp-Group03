package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;

/**
 * Created by Kwinten on 28/04/2016.
 */
public class SubjectMailbox<UpdatedObjectClass extends Subject, SubjectClass extends Subject> extends AbstractMailbox<UpdatedObjectClass> {

    public SubjectMailbox(SubjectClass subject){
        super();
        this.setSubject(subject);
    }

    protected SubjectClass subject;

    /**
     * This method sets the subject for this mailbox.
     *
     * @param subject The subject to subscribe on.
     *
     * @throws IllegalArgumentException If the given subject is invalid.
     * @see #isValidSubject(Subject)
     */
    private void setSubject(SubjectClass subject) throws IllegalArgumentException{
        if (! this.isValidSubject(subject)){
            throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
        }
        this.subject = subject;
    }

    /**
     * This method check if a given subject is valid or not.
     *
     * @param subj The subject to test it's validity.
     *
     * @return True if the subject is not null.
     */
    public boolean isValidSubject(Subject subj){
        if (subj == null){
            return false;
        }
        return true;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Notification update(UpdatedObjectClass changedObject) {
        return null;
    }
}
