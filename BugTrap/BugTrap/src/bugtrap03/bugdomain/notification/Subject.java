package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import purecollections.PList;

/**
 * This abstract class represents the objects on which one can subscribe.
 * @author Group 03
 */
@DomainAPI
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
    @DomainAPI
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
    
    /**
     * Get the subscribers of tag.
     * @return The TagMailBoxes that are subscribed to the change of a tag.
     */
    protected PList<TagMailBox> getTagSubs() {
        return this.tagSubs;
    }
    
    /**
     * Get the subscribers of comments.
     * @return The CommentMailBoxes that are subscribed on the creation of a comments.
     */
    protected PList<CommentMailBox> getCommentSubs() {
        return this.commentSubs;
    }
    
    /**
     * This method adds a tag subscriber to the subject.
     *
     * @param tmb The comment mailbox to add
     *
     * @throws IllegalArgumentException if the cmb is invalid
     * @see #isValidMb(Mailbox)
     */
    public void addTagSub(TagMailBox tmb)throws IllegalArgumentException{
        if (isValidMb(tmb)){
            this.tagSubs = this.tagSubs.plus(tmb);
        } else {
            throw new IllegalArgumentException("Invalid tagmailbox");
        }
    }

    /**
     * This abstract method let's subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    public abstract void notifyTagSubs(BugReport br);

    /**
     * This method updates all the mailboxes subscribed on a comment creation on this subject.
     *
     * @param br The bug report needed for the update
     * @see CommentMailBox#update(BugReport)
     */
    protected void updateCommentSubs(BugReport br){
        for (CommentMailBox cmb: this.commentSubs){
            cmb.update(br);
        }
    }

    /**
     * This method adds a commentsubscriber to the subject.
     *
     * @param cmb The comment mailbox to add
     *
     * @throws IllegalArgumentException if the cmb is invalid
     * @see #isValidMb(Mailbox)
     */
    public void addCommentSub(CommentMailBox cmb) throws IllegalArgumentException{
        if (isValidMb(cmb)){
            this.commentSubs = this.commentSubs.plus(cmb);
        } else {
            throw new IllegalArgumentException("Invalid commentmailbox");
        }
    }

    /**
     * This method checks the validity of a given mailbox.
     *
     * @param mb The mailbox to check
     *
     * @return true if the mailbox is not null
     */
    public boolean isValidMb(Mailbox mb){
        if (mb == null){
            return false;
        }
        return true;
    }

    /**
     * This abstract method let's subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    public abstract void notifyCommentSubs(BugReport br);

    /**
     * This method returns whether or not this subject is terminated
     *
     * @return true if this subject is terminated
     */
    public abstract boolean isTerminated();
}
