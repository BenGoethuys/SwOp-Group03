package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.Tag;
import purecollections.PList;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * This class represents a mailbox for a user.
 * @author Group 03
 */
public class Mailbox {

    /**
     * The constructor for a general mailbox.
     */
    public Mailbox(){
        this.boxes = PList.<Mailbox>empty();
        this.notifications = PList.<Notification>empty();
    }

    PList<Mailbox> boxes;
    PList<Notification> notifications;

    /**
     * This method returns all the notifications belonging to this mailbox.
     * These are the notifications kept by the specific mail boxes of which this mailbox is composed.
     *
     * @return a PList of notifications belonging to this mailbox.
     */
    public PList<Notification> getAllNotifications(){
        ArrayList<Notification> nfs = new ArrayList<>(this.getNotifications());
        for (Mailbox mb: this.getBoxes()){
            nfs.addAll(mb.getAllNotifications());
        }
        return PList.<Notification>empty().plusAll(nfs);
    }

    /**
     * This method returns the specific notifications belonging to this mailbox.
     *
     * @return A PList of notifications belonging to this specific mailbox.
     */
    protected PList<Notification> getNotifications(){
        return this.notifications;
    }

    /**
     * This method adds a notification to this mailbox.
     *
     * @param notif The notification to add.
     */
    protected void addNotification(Notification notif){
        this.notifications = this.getNotifications().plus(notif);
    }

    /**
     * This method returns the list of mail boxes belonging to this mailbox.
     *
     * @return A PList of mailboxes belonging to this mailbox.
     */
    public PList<Mailbox> getBoxes(){
        return this.boxes;
    }

    private void addBox(Mailbox mb){
        this.boxes = this.getBoxes().plus(mb);
    }

    /**
     * This method check if a given subject is valid or not.
     *
     * @param subj The subject to test it's validity.
     *
     * @return True if the subject is not null.
     */
    public boolean isValidSubject(Subject subj){
        if (subj == null){
            return false;
        }
        return true;
    }

    /**
     * This method returns a string representing the information about this subscription.
     *
     * @return A string with information.
     */
    public String getInfo(){
        return "This subscription doesn't have any information about itself";
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
        this.addBox(cmb);
        return cmb;
    }

    /**
     * This method unsubscibes the mailbox by deleting the mailbox representing the subscription.
     *1
     * @param mb The mailbox representing the subscription.
     */
    public void unsubscribe(Mailbox mb){
        this.boxes = this.getBoxes().minus(mb);
    }
}

