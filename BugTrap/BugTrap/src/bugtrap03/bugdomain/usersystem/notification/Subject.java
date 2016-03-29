package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import purecollections.PList;

/**
 * This abstract class represents the objects on which one can subscribe.
 * @author Group 03
 */
public abstract class Subject {

    /**
     * The abstract constructor for a subject, sets the subs to empty Plists.
     */
    public Subject(){
        this.tagSubs = PList.<TagMailBox>empty();
        this.commentSubs = PList.<CommentMailBox>empty();
    }

    private PList<TagMailBox> tagSubs;
    private PList<CommentMailBox> commentSubs;

    /**
     * This abstract method returns a string representation of the subject name and type.
     *
     * @return the string containing subject name and type.
     */
    public abstract String getSubjectName();

    /**
     * This method updates all the mailboxes subscribed on a tag change from this subject.
     *
     * @param br The bugreport needed for the update
     * @see TagMailBox#update(BugReport)
     */
    protected void updateTagSubs(BugReport br){
        for (TagMailBox tmb: this.tagSubs){
            tmb.update(br);
        }
    }

    protected abstract void notifyTagSubs(BugReport br);

    protected void updateCommentSubs(BugReport br){
        for (CommentMailBox cmb: this.commentSubs){
            cmb.update(br);
        }
    }

    protected abstract void notifyCommentSubs(BugReport br);
}
