package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import purecollections.PList;

/**
 * @author Group 03
 */
public class ProjectSubjectMemento extends AbstractSystemSubjectMemento {
    protected ProjectSubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailBox> commentSubs,
                                    PList<CreationMailBox> creationSubs, PList<MilestoneMailbox> milestoneSubs,
                                    PList<VersionIDMailbox> versionIDSubs, PList<ForkMailbox> forkSubs) {
        super(tagSubs, commentSubs, creationSubs, milestoneSubs, versionIDSubs);
        this.forkSubs = forkSubs;
    }

    private final PList<ForkMailbox> forkSubs;

    /**
     * This method gets the final list of fork subscribers.
     * @return A Plist of fork mailboxes.
     */
    public PList<ForkMailbox> getForkSubs(){return this.forkSubs;}
}
