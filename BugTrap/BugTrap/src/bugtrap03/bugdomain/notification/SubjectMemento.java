package bugtrap03.bugdomain.notification;

import purecollections.PList;

/**
 * A memento storing the list of TagMailBoxes and CommentMailBoxes.
 * 
 * @author Group 03
 */
public class SubjectMemento {
    
    SubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailBox> commentSubs) {
        this.tagSubs = tagSubs;
        this.commentSubs = commentSubs;
    }
    
    private final PList<TagMailBox> tagSubs;
    private final PList<CommentMailBox> commentSubs;
    
    PList<TagMailBox> getTagSubs() {
        return this.tagSubs;
    }
    
    PList<CommentMailBox> getCommentSubs() {
        return this.commentSubs;
    }
    
}
