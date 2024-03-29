package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.CommentMailbox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.TagMailbox;
import purecollections.PList;

import java.util.Collection;

/**
 * This abstract class represents the objects on which one can subscribe.
 *
 * @author Group 03
 */
@DomainAPI
public abstract class Subject {

    /**
     * The abstract constructor for a subject, sets the subs to empty Plists.
     */
    public Subject() {
        this.tagSubs = PList.<TagMailbox>empty();
        this.commentSubs = PList.<CommentMailbox>empty();
    }

    private PList<TagMailbox> tagSubs;
    private PList<CommentMailbox> commentSubs;

    /**
     * This abstract method returns a string representation of the subject name and type.
     *
     * @return the string containing subject name and type.
     */
    @DomainAPI
    public abstract String getSubjectName();

    /**
     * This method updates all the mailboxes subscribed on a tag change from this subject.
     *
     * @param br The bugreport needed for the update
     * @see TagMailbox#update(BugReport)
     */
    protected void updateTagSubs(BugReport br) {
        for (TagMailbox tmb : this.tagSubs) {
            tmb.update(br);
        }
    }

    /**
     * Get the subscribers of tag.
     *
     * @return The TagMailBoxes that are subscribed to the change of a tag.
     */
    public PList<TagMailbox> getTagSubs() {
        return this.tagSubs;
    }

    /**
     * Get the subscribers of comments.
     *
     * @return The CommentMailBoxes that are subscribed on the creation of a comments.
     */
    public PList<CommentMailbox> getCommentSubs() {
        return this.commentSubs;
    }

    /**
     * This method adds a tag subscriber to the subject.
     *
     * @param tmb The tag mailbox to add
     * @throws IllegalArgumentException if the tmb is invalid
     * @see #isValidMb
     */
    public void addTagSub(TagMailbox tmb) throws IllegalArgumentException {
        if (isValidMb(tmb)) {
            this.tagSubs = this.tagSubs.plus(tmb);
        } else {
            throw new IllegalArgumentException("Invalid tagmailbox");
        }
    }

    /**
     * This method adds a collection of tag subscriber to the subject.
     *
     * @param tmbs The tag mailboxes to add
     * @throws IllegalArgumentException if any of the tmbs is invalid. This is checked before adding is initiated.
     * @see #isValidMb
     * @see #addTagSub(TagMailbox)
     */
    public void addTagSub(Collection<TagMailbox> tmbs) throws IllegalArgumentException {
        for (TagMailbox tmb : tmbs) {
            if (!isValidMb(tmb)) {
                throw new IllegalArgumentException("Incvalid tagMailBox");
            }
        }
        this.tagSubs = this.tagSubs.plusAll(tmbs);
    }

    /**
     * This abstract method lets subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    protected abstract void notifyTagSubs(BugReport br);

    /**
     * This method updates all the mailboxes subscribed on a comment creation on this subject.
     *
     * @param br The bug report needed for the update
     * @see CommentMailbox#update(BugReport)
     */
    protected void updateCommentSubs(BugReport br) {
        for (CommentMailbox cmb : this.commentSubs) {
            cmb.update(br);
        }
    }

    /**
     * This method adds a comment subscriber to the subject.
     *
     * @param cmb The comment mailbox to add
     * @throws IllegalArgumentException if the cmb is invalid
     * @see #isValidMb
     */
    public void addCommentSub(CommentMailbox cmb) throws IllegalArgumentException {
        if (isValidMb(cmb)) {
            this.commentSubs = this.commentSubs.plus(cmb);
        } else {
            throw new IllegalArgumentException("Invalid commentmailbox");
        }
    }

    /**
     * This method adds a collection of comment subscriber to the subject.
     *
     * @param cmbs The CommentMailBoxes to add
     * @throws IllegalArgumentException if any of the the cmbs is invalid.
     * @see #isValidMb
     * @see #addCommentSub(CommentMailbox)
     */
    public void addCommentSub(Collection<CommentMailbox> cmbs) throws IllegalArgumentException {
        for (CommentMailbox cmb : cmbs) {
            if (!isValidMb(cmb)) {
                throw new IllegalArgumentException("Invalid commentMailBox");
            }
        }
        this.commentSubs = this.commentSubs.plusAll(cmbs);
    }

    /**
     * This method checks the validity of a given mailbox.
     *
     * @param mb The mailbox to check
     * @return true if the mailbox is not null
     */
    public boolean isValidMb(AbstractMailbox mb) {
        if (mb == null) {
            return false;
        }
        return true;
    }

    /**
     * The method returns the memento for this Subject.
     *
     * @return The memento of this subject.
     */
    public SubjectMemento getMemento() {
        return new SubjectMemento(this.tagSubs, this.commentSubs);
    }

    /**
     * Set the memento of this Subject.
     *
     * @param mem The Memento to use to set.
     * @throws IllegalArgumentException When mem == null
     * @throws IllegalArgumentException When any of the arguments stored in mem is invalid for the current state.
     */
    public void setMemento(SubjectMemento mem) {
        if (mem == null) {
            throw new IllegalArgumentException("The memento to set musn't be null.");
        }

        this.tagSubs = mem.getTagSubs();
        this.commentSubs = mem.getCommentSubs();
    }

    /**
     * This abstract method let's subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    protected abstract void notifyCommentSubs(BugReport br);

    /**
     * This method returns whether or not this subject is terminated
     *
     * @return true if this subject is terminated
     */
    public abstract boolean isTerminated();
}
