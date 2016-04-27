package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import java.util.Collection;

import bugtrap03.bugdomain.notificationdomain.mailboxes.CreationMailBox;
import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;
import purecollections.PList;

/**
 * @author Group 03
 */
@DomainAPI
public abstract class AbstractSystemSubject extends Subject {

    public AbstractSystemSubject(){
        super();
        this.creationSubs = PList.<CreationMailBox>empty();
    }

    private PList<CreationMailBox> creationSubs;

    /**
     * This method updates all the mailboxes subscribed on a AbstractSystem creation on this subject.
     *
     * @param br The bug report needed for the update
     * @see CreationMailBox#update(BugReport)
     */
    protected void updateCreationSubs(BugReport br){
        for (CreationMailBox cmb: this.creationSubs){
            cmb.update(br);
        }
    }
    
    /**
     * Get the subscribers of a creation event.
     *
     * @return The CreationMailBoxes that are subscribed on the creation of an AbstractSystem.
     */
    protected PList<CreationMailBox> getCreationSubs() {
        return this.creationSubs;
    }

    /**
     * This method adds a creation subscriber to the subject.
     *
     * @param cmb The CreationMailBox to add
     *
     * @throws IllegalArgumentException if the cmb is invalid
     * @see Subject#isValidMb(Mailbox)
     */
    public void addCreationSub(CreationMailBox cmb) throws IllegalArgumentException{
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
     * @see Subject#isValidMb(Mailbox)
     * @see #addCreationSub(CreationMailBox) 
     */
    public void addCreationSub(Collection<CreationMailBox> cmbs) {
        for (CreationMailBox cmb : cmbs) {
            if (!isValidMb(cmb)) {
                throw new IllegalArgumentException("Incvalid CreationMailBox");
            }
        }

        for (CreationMailBox cmb : cmbs) {
            this.addCreationSub(cmb);
        }
    }
    
        /**
     * The method returns the memento for this AbstractSystemSubject.
     *
     * @return The memento of this system subject.
     */
    @Override
    public AbstractSystemSubjectMemento getMemento() {
        return new AbstractSystemSubjectMemento(getTagSubs(), getCommentSubs(), creationSubs);
    }
    
    @Override
    public void setMemento(SubjectMemento mem) {
        super.setMemento(mem);
        
        if(mem instanceof AbstractSystemSubjectMemento) {
            AbstractSystemSubjectMemento aMem = (AbstractSystemSubjectMemento) mem;
            this.creationSubs = aMem.getCreationSubs();
        }
    }

    /**
     * This abstract method lets subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    public abstract void notifyCreationSubs(BugReport br);
}
