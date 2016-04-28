package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.SubjectMemento;
import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class AbstractSystemSubjectMemento extends SubjectMemento {
    
    protected AbstractSystemSubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailBox> commentSubs, PList<CreationMailBox> creationSubs,
                                           PList<MilestoneMailbox> milestoneSubs, PList<VersionIDMailbox> versionIDSubs) {
        super(tagSubs, commentSubs);
        
        this.creationSubs = creationSubs;
        this.milestoneSubs = milestoneSubs;
        this.versionIDSubs = versionIDSubs;
    }
    
    private final PList<CreationMailBox> creationSubs;
    private final PList<MilestoneMailbox> milestoneSubs;
    private final PList<VersionIDMailbox> versionIDSubs;

    /**
     * This method returns the static list of creationsubs.
     * @return A Plist of creation mailboxes.
     */
    PList<CreationMailBox> getCreationSubs() {
        return this.creationSubs;
    }

    /**
     * This method returns the static list of milestonesubs.
     * @return A Plist of mileston mailboxes.
     */
    PList<MilestoneMailbox> getMilestoneSubs() { return this.milestoneSubs;}

    /**
     * This method returns the static list of versionIDsubs.
     * @return A Plist of versionID mailboxes.
     */
    PList<VersionIDMailbox> getVersionIDSubs(){ return this.versionIDSubs;}
    
}
