package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public class AbstractSystemSubjectMemento extends SubjectMemento {
    
    protected AbstractSystemSubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailbox> commentSubs, PList<CreationMailbox> creationSubs,
                                           PList<MilestoneMailbox> milestoneSubs, PList<VersionIDMailbox> versionIDSubs) {
        super(tagSubs, commentSubs);
        
        this.creationSubs = creationSubs;
        this.milestoneSubs = milestoneSubs;
        this.versionIDSubs = versionIDSubs;
    }
    
    private final PList<CreationMailbox> creationSubs;
    private final PList<MilestoneMailbox> milestoneSubs;
    private final PList<VersionIDMailbox> versionIDSubs;

    /**
     * This method returns the final list of creationsubs.
     * @return A Plist of creation mailboxes.
     */
    public PList<CreationMailbox> getCreationSubs() {
        return this.creationSubs;
    }

    /**
     * This method returns the final list of milestonesubs.
     * @return A Plist of mileston mailboxes.
     */
    public PList<MilestoneMailbox> getMilestoneSubs() { return this.milestoneSubs;}

    /**
     * This method returns the final list of versionIDsubs.
     * @return A Plist of versionID mailboxes.
     */
    public PList<VersionIDMailbox> getVersionIDSubs(){ return this.versionIDSubs;}
    
}
