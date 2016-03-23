package bugtrap03.bugdomain.usersystem.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * This class can be seen as the concept of a MailBox using Notifications as 'mails'.
 * <br> A MailBox can contain both received notifications and other MailBox sources which in their turn can also contain
 * notifications.
 *
 * @param U The type of Subject wherefrom this MailBox will receive Notifications.
 *
 * @author Group03
 */
//TODO: Doc + Implementation.
//TODO: Interface instead of class (with abstract void update) 
//and then a class GeneralMailBox composing other MailBoxs? (
public class MailBox<U extends Subject> implements Observer<U> {

    public MailBox() {
        this.otherSources = new ArrayList<>();
    }

    /* List of other MailBox, sources of Notifications. */
    private List<MailBox<? extends U>> otherSources;

    /**
     * Adds the given MailBox as an extra source of 'information' for this MailBox.
     *
     * @param box The mailbox to add as a source.
     * @throws IllegalArgumentException When box is null.
     */
    public void addSource(MailBox<? extends U> box) throws IllegalArgumentException {
        if (box == null) {
            throw new IllegalArgumentException("Can not add a null reference as a source for a MailBox.");
        }
        otherSources.add(box);
    }

    /**
     * A way to inform this MailBox the Subject subj has been updated. This MailBox will verify for interest and when it
     * is interested it will update its for a new notification about this subj change.
     *
     * @param subj The Subject that has been updated.
     */
    @Override
    public void update(U subj) {
        //TODO: Add IllegalArg when sub == null.
        //TODO: Implement .. code checking for interest starts here? IN Sub_Mailboxs.
        /* 
        This one will most likely mainly remain empty as it will never subscribe to any Subjects
        and only contains a few sources (mailboxs) that will each observe a list of certain
        Subject types and when this MailBox wants the notifcations it has to merge sort (using Date) all notifications
        of all sub Mailboxs.
        */
    }
   
}
