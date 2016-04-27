package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.SubjectMemento;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailBox;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class AbstractSystemSubjectMemento extends SubjectMemento {
    
    protected AbstractSystemSubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailBox> commentSubs, PList<CreationMailBox> creationSubs) {
        super(tagSubs, commentSubs);
        
        this.creationSubs = creationSubs;
    }
    
    private final PList<CreationMailBox> creationSubs;
    
    PList<CreationMailBox> getCreationSubs() {
        return this.creationSubs;
    }
    
}
