package bugtrap03.bugdomain.usersystem.notification;

import purecollections.PList;

/**
 * This abstract class represents the objects on which one can subscribe.
 * @author Group 03
 */
public abstract class Subject {

    public Subject(){
        this.tagSubs = PList.<TagMailBox>empty();
        this.commentSubs = PList.<CommentMailBox>empty();
    }

    private PList<TagMailBox> tagSubs;
    private PList<CommentMailBox> commentSubs;

    protected abstract String getSubjectName();
}
