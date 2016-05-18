package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubject;
import bugtrap03.bugdomain.notificationdomain.mailboxes.AbstractMailbox;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.SelectMilestoneCmd;
import bugtrap03.gui.cmd.general.SelectTagsCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * @author Group 03
 */
public class RegisterFromASSubjCmd implements Cmd<AbstractMailbox> {

    /**
     * Create a cmd which can be used to execute the scenario to register for a certain change on the abstractSystemSubject
     *
     * @param abstractSystemSubject The AbstractSystemSubject to register on.
     */
    public RegisterFromASSubjCmd(AbstractSystemSubject abstractSystemSubject) {
        this.abstractSystemSubject = abstractSystemSubject;
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("newtag", 1);
        this.subsriptionTypes.put("specifictags", 2);
        this.subsriptionTypes.put("comment", 3);
        this.subsriptionTypes.put("creation", 4);
        this.subsriptionTypes.put("newmilestone", 5);
        this.subsriptionTypes.put("specificmilestone", 6);
        this.subsriptionTypes.put("version", 7);
    }

    private HashMap<String, Integer> subsriptionTypes;
    private AbstractSystemSubject abstractSystemSubject;

    /**
     * Execute the command and register for a certain change.
     *
     * @param scan  The scanner used to interact with the user
     * @param model The model used to access the system data.
     * @param user  The user who wants to execute this cmd.
     * @return The Mailbox created by registering for a certain change.
     * @throws CancelException          When the user aborted the cmd
     * @throws IllegalArgumentException When scan, model or user == null
     * @throws IllegalArgumentException When the abstractSystemSubject == null
     */
    @Override
    public AbstractMailbox exec(TerminalScanner scan, DataModel model, User user) throws CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        scan.println("Select subscription type.");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(new TreeSet<>(this.subsriptionTypes.keySet())),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subscriptionType = subscriptionType.toLowerCase();
        Integer index = this.subsriptionTypes.get(subscriptionType);
        AbstractMailbox newMailbox;
        switch (index) {
            case 1:
                newMailbox = model.registerForAllTagsNotifications(user, abstractSystemSubject);
                break;
            case 2:
                EnumSet<Tag> selectedTags = new SelectTagsCmd().exec(scan, model, user);
                newMailbox = model.registerForSpecificTagsNotifications(user, abstractSystemSubject, selectedTags);
                break;
            case 3:
                newMailbox = model.registerForCommentNotifications(user, abstractSystemSubject);
                break;
            case 4:
                newMailbox = model.registerForCreationNotifications(user, abstractSystemSubject);
                break;
            case 5:
                newMailbox = model.registerForAllMilestoneNotifactions(user, abstractSystemSubject);
                break;
            case 6:
                Milestone milestone = new SelectMilestoneCmd().exec(scan, null, null);
                newMailbox = model.registerForMilestoneNotifactions(user, abstractSystemSubject, milestone);
                break;
            case 7:
                newMailbox = model.registerForVersionNotifications(user, abstractSystemSubject);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with selecting " +
                        "the type of notification registration");
        }
        return newMailbox;
    }
}

