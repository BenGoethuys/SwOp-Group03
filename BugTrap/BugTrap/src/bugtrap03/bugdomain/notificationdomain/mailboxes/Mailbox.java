package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.notificationdomain.TagMailBox;
import bugtrap03.bugdomain.notificationdomain.notification.Notification;
import purecollections.PList;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * This class represents a mailbox for a user.
 * @author Group 03
 */
@DomainAPI
public class Mailbox extends AbstractMailbox<Subject, Notification> {

    /**
     * The constructor for a general mailbox.
     */
    public Mailbox(){
        this.boxes = PList.<AbstractMailbox>empty();
        this.notifications = PList.<Notification>empty();
        this.activate();
    }

    private PList<AbstractMailbox> boxes;
    private PList<Notification> notifications;
    private boolean isTerminated;

    /**
     * This method returns all the notifications belonging to this mailbox.
     * These are the notifications kept by the specific mail boxes of which this mailbox is composed.
     *
     * @return a PList of notifications belonging to this mailbox.
     */
    @DomainAPI
    public PList<Notification> getAllNotifications(){
        ArrayList<Notification> nfs = new ArrayList<>(this.getNotifications());
        for (AbstractMailbox mb: this.getBoxes()){
            nfs.addAll(mb.getNotifications());
            if (mb instanceof Mailbox) {
                Mailbox AMb = (Mailbox) mb;
                nfs.addAll(AMb.getAllNotifications());
            }
        }
        return PList.<Notification>empty().plusAll(nfs);
    }

    /**
     * This method returns all the mailboxes belonging to this mailbox.
     * These are the mailboxes of which this mailbox is composed.
     *
     * @return A PList of mailboxes belonging to this mailbox.
     */
    @DomainAPI
    public PList<AbstractMailbox> getAllBoxes(){
        ArrayList<AbstractMailbox> allBoxes = new ArrayList<AbstractMailbox>(this.getBoxes());
        for (AbstractMailbox mb: this.getBoxes()){
            if (mb instanceof Mailbox) {
                Mailbox AMb = (Mailbox) mb;
                allBoxes.addAll(AMb.getAllBoxes());
            }
        }
        return PList.<AbstractMailbox>empty().plusAll(allBoxes);
    }

    /**
     * This method returns the list of mail boxes belonging to this mailbox.
     *
     * @return A PList of mailboxes belonging to this mailbox.
     */
    private PList<AbstractMailbox> getBoxes(){
        return this.boxes;
    }


    /**
     * This method adds a mailbox to the list of mailboxes belonging to this mailbox.
     * @param mb The mailbox to add.
     * @throws IllegalArgumentException if the given mailbox is this mailbox
     * @throws IllegalArgumentException if the given mailbox already is in your (sub)list of mailboxes
     */
    public void addBox(AbstractMailbox mb)throws IllegalArgumentException{
        if (mb == this){
            throw new IllegalArgumentException("You cannot add yourself as a mailbox");
        }
        if (this.getAllBoxes().contains(mb)){
            throw new IllegalArgumentException("You are already have this mailbox as a subscription)");
        }
        this.boxes = this.getBoxes().plus(mb);
    }

    /**
     * This method returns a string representing the information about this subscription.
     *
     * @return A string with information.
     */
    @DomainAPI
    public String getInfo(){
        return ("This is a composite mailbox with no subject.");
    }

    @Override
    public Notification update(Subject changedObject) {
        return null;
    }

    /**
     * This method makes a subscription to a subject for the specified tags on the bugreports related to that subject.
     *
     * @param subject The subject for the subscription.
     * @param tags The tags for which the subscription is interested.
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see TagMailBox#TagMailBox(Subject, EnumSet)
     */
    public TagMailBox tagSubscribe(Subject subject, EnumSet<Tag> tags) throws IllegalArgumentException{
        TagMailBox tmb = new TagMailBox(subject, tags);
        subject.addTagSub(tmb);
        this.addBox(tmb);
        return tmb;
    }

    /**
     * This method makes a subscription to a subject for all the tags on the bugreports related to that subject.
     *
     * @param subject The subject for the subscription.
     * @throws IllegalArgumentException if any of the arguments is invalid
     * @see #tagSubscribe(Subject, EnumSet)
     */
    public TagMailBox tagSubscribe(Subject subject) throws IllegalArgumentException{
        EnumSet<Tag> tags = EnumSet.allOf(Tag.class);
        return this.tagSubscribe(subject, tags);
    }

    /**
     * This method makes a subscription to a subject for the creation of a comment on that subject.
     *
     * @param subject The subject for the subscription.
     * @throws IllegalArgumentException if the subject is invalid
     * @see CommentMailBox#CommentMailBox(Subject)
     */
    public CommentMailBox commentSubscribe(Subject subject) throws IllegalArgumentException{
        CommentMailBox cmb = new CommentMailBox(subject);
        subject.addCommentSub(cmb);
        this.addBox(cmb);
        return cmb;
    }

    /**
     * This method makes a subscription to a subject for the creation of bugreport on that subject.
     *
     * @param abstractSystemSubject The subject for the subscription
     * @throws IllegalArgumentException if the subject is invalid
     * @see CreationMailBox#CreationMailBox(AbstractSystemSubject)
     */
    public CreationMailBox creationSubscribe(AbstractSystemSubject abstractSystemSubject) throws IllegalArgumentException{
        CreationMailBox cmb = new CreationMailBox(abstractSystemSubject);
        abstractSystemSubject.addCreationSub(cmb);
        this.addBox(cmb);
        return cmb;
    }

    /**
     * This method unsubscribes the mailbox by deleting the mailbox representing the subscription.
     *1
     * @param mb The mailbox representing the subscription.
     */
    public boolean unsubscribe(AbstractMailbox mb){
        if (this.boxes.contains(mb)){
            this.boxes = this.getBoxes().minus(mb);
            mb.isTerminated = true;
            return true;
        } else{
            boolean value = false;
            for (AbstractMailbox mailbox: this.boxes){
                if (mailbox instanceof Mailbox){
                    Mailbox AMb = (Mailbox) mailbox;
                    value = (value || AMb.unsubscribe(mb));
                }
            }
            return value;
        }

    }

    /**
     * This method sets the terminated status to false;
     */
    public void activate(){
        this.isTerminated = false;
    }

}

