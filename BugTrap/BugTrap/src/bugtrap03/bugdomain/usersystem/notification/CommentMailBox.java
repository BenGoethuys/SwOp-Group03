package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.Comment;

/**
 * This class represents the mailboxes dedicated to catching notifications from comment changes.
 *
 * @author Group 03
 */
public class CommentMailBox extends Mailbox {

    public CommentMailBox(Subject subjectt) throws IllegalArgumentException{
        this.setSubject(subject);
    }

    private Subject subject;

    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)){
            this.subject = subject;
        }
        throw new IllegalArgumentException("The given subject is not valid for this type of mailbox");
    }
}
