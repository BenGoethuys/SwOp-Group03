package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.ForkMailbox;
import purecollections.PList;

import java.util.Collection;

/**
 * @author Group 03
 */
public abstract class ProjectSubject extends AbstractSystem {

    /**
     * A constructor for this project subject with a list of fork mailboxes.
     *
     * @param parent      The parent of this project subject
     * @param version     The version ID of this project subject
     * @param name        The name of this project subject
     * @param description The description of this project subject
     * @throws IllegalArgumentException if one of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, VersionID, String, String);
     */
    public ProjectSubject(AbstractSystem parent, VersionID version, String name, String description) throws IllegalArgumentException {
        super(parent, version, name, description);
        this.forkSubs = PList.<ForkMailbox>empty();
    }

    /**
     * Secondary constructor for a project subject.
     *
     * @param parent      The parent of this project subject
     * @param name        The name of this project subject
     * @param description The description of this project subject
     * @throws IllegalArgumentException if one of the arguments is invalid
     * @see AbstractSystem#AbstractSystem(AbstractSystem, String, String);
     */
    public ProjectSubject(AbstractSystem parent, String name, String description) throws IllegalArgumentException {
        super(parent, name, description);
        this.forkSubs = PList.<ForkMailbox>empty();
    }

    private PList<ForkMailbox> forkSubs;

    /**
     * This method returns the lsit of fork subscribers of this project subject
     *
     * @return A PList of Fork Mailboxes subscribed to this project.
     */
    public PList<ForkMailbox> getForkSubs() {
        return this.forkSubs;
    }

    /**
     * This method adds a fork mailbox, a subscriber on the forking of this project subject, to the list of subscribers.
     *
     * @param forkMailbox The fork mailbox to add.
     */
    public void addForkSub(ForkMailbox forkMailbox) {
        this.forkSubs = forkSubs.plus(forkMailbox);
    }

    /**
     * This method adds a collection of fork mailboxes, subscribers on the forking oof this project subject,
     * to the list of subscribers
     *
     * @param forkMailboxes The collection of fork mailboxes to add.
     * @throws IllegalArgumentException if one of the forkmailboxes in the collection is invalid
     * @see #isValidMb(AbstractMailbox)
     */
    public void addForkSub(Collection<ForkMailbox> forkMailboxes) throws IllegalArgumentException {
        for (ForkMailbox fmb : forkMailboxes) {
            if (!isValidMb(fmb)) {
                throw new IllegalArgumentException("Collection of forkmailboxes to add cantains invalid value");
            }
        }
        this.forkSubs = this.forkSubs.plusAll(forkMailboxes);
    }

    /**
     * The method returns the memento for this ProjectSubject.
     *
     * @return The memento of this project subject.
     */
    @Override
    public ProjectSubjectMemento getMemento() {
        return new ProjectSubjectMemento(this.getTagSubs(), this.getCommentSubs(), this.getCreationSubs(), this.getMilestoneSubs(),
                this.getVersionIDSubs(), this.getVersionID(), this.getName(), this.getDescription(), getSubsystems(), getParent(),
                getMilestone(), this.isTerminated(), this.forkSubs);
    }

    @Override
    public void setMemento(SubjectMemento mem) {
        super.setMemento(mem);
        if (mem instanceof ProjectSubjectMemento) {
            ProjectSubjectMemento aMem = (ProjectSubjectMemento) mem;
            this.forkSubs = aMem.getForkSubs();
        }
    }

    /**
     * This method notifies the fork sbs of this project method that a project has been forked.
     *
     * @param project The newly forked project.
     */
    @DomainAPI
    public void notifyForkSubs(Project project) {
        for (ForkMailbox fmb : this.getForkSubs()) {
            fmb.update(project);
        }
    }
}
