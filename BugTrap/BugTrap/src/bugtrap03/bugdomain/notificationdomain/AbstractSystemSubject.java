package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import java.util.Collection;

import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import purecollections.PList;

/**
 * @author Group 03
 */
@DomainAPI
public abstract class AbstractSystemSubject extends Subject {

    public AbstractSystemSubject(){
        super();
        this.creationSubs = PList.<CreationMailbox>empty();
        this.milestoneSubs = PList.<MilestoneMailbox>empty();
        this.versionIdSubs = PList.<VersionIDMailbox>empty();
    }

    private PList<CreationMailbox> creationSubs;
    private PList<MilestoneMailbox> milestoneSubs;
    private PList<VersionIDMailbox> versionIdSubs;

    /**
     * This method updates all the mailboxes subscribed on a AbstractSystem creation on this subject.
     *
     * @param br The bug report needed for the update
     * @see CreationMailbox#update(BugReport)
     */
    protected void updateCreationSubs(BugReport br){
        for (CreationMailbox cmb: this.creationSubs){
            cmb.update(br);
        }
    }
    
    /**
     * Get the subscribers of a creation event.
     *
     * @return The CreationMailBoxes that are subscribed on the creation of an AbstractSystem.
     */
    public PList<CreationMailbox> getCreationSubs() {
        return this.creationSubs;
    }

    /**
     * This method adds a creation subscriber to the subject.
     *
     * @param cmb The CreationMailbox to add
     *
     * @throws IllegalArgumentException if the cmb is invalid
     * @see Subject#isValidMb
     */
    public void addCreationSub(CreationMailbox cmb) throws IllegalArgumentException{
        if (isValidMb(cmb)){
            this.creationSubs = this.creationSubs.plus(cmb);
        } else {
            throw new IllegalArgumentException("Invalid creationmailbox");
        }
    }
    
    /**
     * This method adds a collection of creation subscriber to the subject.
     *
     * @param cmbs The CreationMailBoxes to add
     *
     * @throws IllegalArgumentException if the any of the cmbs is invalid
     * @see Subject#isValidMb
     * @see #addCreationSub(CreationMailbox)
     */
    public void addCreationSub(Collection<CreationMailbox> cmbs) {
        for (CreationMailbox cmb : cmbs) {
            if (!isValidMb(cmb)) {
                throw new IllegalArgumentException("Creationmailbox from collection to add to abstract system subject is invalid");
            }
        }
        this.creationSubs = this.creationSubs.plusAll(cmbs);
    }

    /**
     * This method adds a milestone mailbox to the list of subscription on milestone changes of this subject.
     * @param mmb The milestone mailbox to add.
     * @throws IllegalArgumentException if the given mailbox is invalid
     * @see #isValidMb(AbstractMailbox)
     */
    public void addMilestoneSub(MilestoneMailbox mmb) throws IllegalArgumentException{
        if (! isValidMb(mmb)){
            throw new IllegalArgumentException("The milestone mailbox is invalid");
        }
        this.milestoneSubs = this.milestoneSubs.plus(mmb);
    }

    /**
     * this method adds a collection of milestone mailboxes to the subscribers list of this subject.
     * @param mmbs The collection of milestone mailboxes.
     * @throws IllegalArgumentException If one of the mailboxes is invalid.
     * @see #isValidMb(AbstractMailbox)
     */
    public void addMilestoneSub(Collection<MilestoneMailbox> mmbs) throws IllegalArgumentException{
            for (MilestoneMailbox mmb: mmbs) {
                if (!isValidMb(mmb)) {
                    throw new IllegalArgumentException("The milestone mailbox from the given collection is invalid");
                }
            }
        this.milestoneSubs = this.milestoneSubs.plusAll(mmbs);
    }

    /**
     * This method returns the list of mailboxes representing subscriptions to milestone changes on this subject.
     * @return A PList of milestone mailboxes.
     */
    public PList<MilestoneMailbox> getMilestoneSubs(){
        return this.milestoneSubs;
    }

    /**
     * This method updates the milestone mailboxes.
     * @param as The abstract system of which the milestone has been changed.
     */
    protected void updateMilestoneSubs(AbstractSystem as){
        for (MilestoneMailbox mmb: this.getMilestoneSubs()){
            mmb.update(as);
        }
    }

    /**
     * This method adds a versionID mailbox to the list of subscription on versionID changes of this subject.
     * @param vimb The milestone mailbox to add.
     * @throws IllegalArgumentException if the given mailbox is invalid
     * @see #isValidMb(AbstractMailbox)
     */
    public void addVersionIDSub(VersionIDMailbox vimb) throws IllegalArgumentException{
        if (! isValidMb(vimb)){
            throw new IllegalArgumentException("The given versionID mailbox is invalid");
        }
        this.versionIdSubs = this.versionIdSubs.plus(vimb);
    }

    /**
     * This method adds a collection versionID mailboxes to the list of subscription on versionID changes of this subject.
     * @param vimbs The collection of milestone mailboxes to add.
     * @throws IllegalArgumentException if one of the given mailboxes is invalid
     * @see #isValidMb(AbstractMailbox)
     */
    public void addVersionIDSub(Collection<VersionIDMailbox> vimbs) throws IllegalArgumentException{
        for (VersionIDMailbox vimb:vimbs) {
            if (!isValidMb(vimb)) {
                throw new IllegalArgumentException("The given versionID mailbox is invalid");
            }
        }
        this.versionIdSubs = this.versionIdSubs.plusAll(vimbs);
    }

    /**
     * This method returns the list versionID mailboxes for this abstract system subject
     * @return The Plist of versionID mailboxes.
     */
    public PList<VersionIDMailbox> getVersionIDSubs(){
        return this.versionIdSubs;
    }

    /**
     * This method updates all the versionID mailboxes.
     * @param as The abstract system of which the versionId has changed
     */
    protected void updateVersionIDSubs(AbstractSystem as){
        for (VersionIDMailbox vimb: this.versionIdSubs){
            vimb.update(as);
        }
    }



        /**
     * The method returns the memento for this AbstractSystemSubject.
     *
     * @return The memento of this system subject.
     */
    @Override
    public AbstractSystemSubjectMemento getMemento() {
        return new AbstractSystemSubjectMemento(getTagSubs(), getCommentSubs(), creationSubs, milestoneSubs, versionIdSubs);
    }
    
    @Override
    public void setMemento(SubjectMemento mem) {
        super.setMemento(mem);
        
        if(mem instanceof AbstractSystemSubjectMemento) {
            AbstractSystemSubjectMemento aMem = (AbstractSystemSubjectMemento) mem;
            this.creationSubs = aMem.getCreationSubs();
            this.milestoneSubs = aMem.getMilestoneSubs();
            this.versionIdSubs = aMem.getVersionIDSubs();
        }
    }
    
    /**
     * This abstract method lets subjects notify subjects higher in the hierarchy to update their creation subs;
     *
     * @param br The bugreport that has been created.
     */
    public abstract void notifyCreationSubs(BugReport br);

    /**
     * This abstract method lets subjects notify subjects higher in the hierarchy to update their milestonsubs.
     *
     * @param as The abstract system of which the milestone has been updated.
     */
    public abstract void notifyMilestoneSubs(AbstractSystem as);

    /**
     * This abstract method lets subjects notify subjects higher in the hierarchy to update their versionIDsubs.
     *
     * @param as The abstract system of which the versionID has been updated.
     */
    public abstract void notifyVersionIDSubs(AbstractSystem as);
}
