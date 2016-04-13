package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import purecollections.PList;

/**
 * Created by Kwinten on 23/03/2016.
 */
public abstract class AbstractSystemSubject extends Subject {

    public AbstractSystemSubject(){
        super();
        this.CreationSubs = PList.<CreationMailBox>empty();
    }

    private PList<CreationMailBox> CreationSubs;

    /**
     * This method updates all the mailboxes subscribed on a comment creation on this subject.
     *
     * @param br The bugreport needed for the update
     * @see CommentMailBox#update(BugReport)
     */
    protected void updateCreationSubs(BugReport br){
        for (CreationMailBox cmb: this.CreationSubs){
            cmb.update(br);
        }
    }

    /**
     * This method adds a commentsubscriber to the subject.
     *
     * @param cmb The comment mailbox to add
     *
     * @throws IllegalArgumentException if the cmb is invalid
     * @see Subject#isValidMb(Mailbox)
     */
    public void addCreationSub(CreationMailBox cmb) throws IllegalArgumentException{
        if (isValidMb(cmb)){
            this.CreationSubs = this.CreationSubs.plus(cmb);
        } else {
            throw new IllegalArgumentException("Invalid creationmailbox");
        }
    }

    /**
     * This abstract method let's subjects notify subjects higher in the hierarchy.
     *
     * @param br The bugreport of which an attribute has changed.
     */
    public abstract void notifyCreationSubs(BugReport br);
}
