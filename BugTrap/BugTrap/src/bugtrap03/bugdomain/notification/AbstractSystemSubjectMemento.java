package bugtrap03.bugdomain.notification;

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
