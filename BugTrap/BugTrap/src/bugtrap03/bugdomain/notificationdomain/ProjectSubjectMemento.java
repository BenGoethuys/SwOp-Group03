package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import purecollections.PList;

/**
 * @author Group 03
 */
public class ProjectSubjectMemento extends AbstractSystemMemento {

    protected ProjectSubjectMemento(PList<TagMailBox> tagSubs, PList<CommentMailbox> commentSubs,
                                    PList<CreationMailbox> creationSubs, PList<MilestoneMailbox> milestoneSubs,
                                    PList<VersionIDMailbox> versionIDSubs, VersionID versionID, String name, String description, PList<Subsystem> children,
                                    AbstractSystem parent, Milestone milestone, boolean isTerminated, PList<ForkMailbox> forkSubs) {
        super(tagSubs, commentSubs, creationSubs, milestoneSubs, versionIDSubs, versionID, name,
                description, children, parent, milestone, isTerminated);
        this.forkSubs = forkSubs;
    }

    private final PList<ForkMailbox> forkSubs;

    /**
     * This method gets the final list of fork subscribers.
     * @return A PList of fork mailboxes.
     */
    public PList<ForkMailbox> getForkSubs(){return this.forkSubs;}
}
