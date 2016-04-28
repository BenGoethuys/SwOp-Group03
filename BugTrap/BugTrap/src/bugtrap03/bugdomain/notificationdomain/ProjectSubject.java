package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.notificationdomain.mailboxes.ForkMailbox;
import purecollections.PList;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Group 03
 */
public abstract class ProjectSubject extends AbstractSystem {

    /**
     * A constructor for this project subject with a list of fork mailboxes.
     * @param parent The parent of this project subject
     * @param version The version ID of this proejct subject
     * @param name The name of this project subject
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
     * @param parent The parent of this project subject
     * @param name The name of this project subject
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
     * @return A PList of Fork Mailboxes subscribed to this project.
     */
    public PList<ForkMailbox> getForkSubs(){
        return this.forkSubs;
    }

    /**
     * This method adds a fork mailbox, a subscriber on the forking of this project subject, to the list of subscribers.
     * @param forkMailbox The fork mailbox to add.
     */
    public void addForkSub(ForkMailbox forkMailbox){
        this.forkSubs = forkSubs.plus(forkMailbox);
    }

    /**
     * This method adds a collection of fork mailboxes, subscribers on the forking oof this project subject,
     * to the list of subscribers
     * @param forkMailboxes The collection of fork mailboxes to add.
     */
    public void addForkSub(Collection<ForkMailbox> forkMailboxes){
        this.forkSubs = this.forkSubs.plusAll(forkMailboxes);
    }

    /**
     * This method notifies the fork sbs of this project method that a project has been forked.
     * @param project
     */
    @DomainAPI
    public void notifyForkSubs(Project project){
        for (ForkMailbox fmb: this.getForkSubs()){
            fmb.update(project);
        }
    }

    //TODO memento


}