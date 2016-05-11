package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailbox;
import purecollections.PList;

/**
 * A memento storing the list of TagMailBoxes and CommentMailBoxes.
 * 
 * @author Group 03
 */
public class SubjectMemento {
    
    protected SubjectMemento(PList<TagMailbox> tagSubs, PList<CommentMailbox> commentSubs) {
        this.tagSubs = tagSubs;
        this.commentSubs = commentSubs;
    }
    
    private final PList<TagMailbox> tagSubs;
    private final PList<CommentMailbox> commentSubs;
    
    public PList<TagMailbox> getTagSubs() {
        return this.tagSubs;
    }
    
    public PList<CommentMailbox> getCommentSubs() {
        return this.commentSubs;
    }
    
}
