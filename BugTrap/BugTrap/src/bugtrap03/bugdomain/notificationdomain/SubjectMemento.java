package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailBox;
import purecollections.PList;

/**
 * A memento storing the list of TagMailBoxes and CommentMailBoxes.
 * 
 * @author Group 03
 */
public class SubjectMemento {
    
    protected SubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailBox> commentSubs) {
        this.tagSubs = tagSubs;
        this.commentSubs = commentSubs;
    }
    
    private final PList<TagMailBox> tagSubs;
    private final PList<CommentMailBox> commentSubs;
    
    public PList<TagMailBox> getTagSubs() {
        return this.tagSubs;
    }
    
    public PList<CommentMailBox> getCommentSubs() {
        return this.commentSubs;
    }
    
}
