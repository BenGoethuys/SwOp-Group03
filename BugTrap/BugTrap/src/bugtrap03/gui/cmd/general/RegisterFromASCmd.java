package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.usersystem.notification.AbstractSystemSubject;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * @author Group 03
 */
public class RegisterFromASCmd implements Cmd {
    public RegisterFromASCmd(AbstractSystemSubject abstractSystemSubject) {
        this.abstractSystemSubject = abstractSystemSubject;
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("alltags", 1);
        this.subsriptionTypes.put("specictags", 2);
        this.subsriptionTypes.put("comment", 3);
        this.subsriptionTypes.put("creation", 4);
    }

    private HashMap<String, Integer> subsriptionTypes;
    private AbstractSystemSubject abstractSystemSubject;

    @Override
    public Object exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        scan.println("Select subscription type.");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(this.subsriptionTypes.keySet()),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subscriptionType = subscriptionType.toLowerCase();
        Integer index = this.subsriptionTypes.get(subscriptionType);
        switch (index) {
            case 1:
                model.registerForAllTagsNotifications(user, abstractSystemSubject);
                break;
            case 2:
                EnumSet<Tag> selectedTags = new SelectTagsCmd().exec(scan, model, user);
                model.registerForSpecificTagsNotifications(user, abstractSystemSubject, selectedTags);
                break;
            case 3:
                model.registerForCommentNotifications(user, abstractSystemSubject);
                break;
            case 4:
                model.registerForCreationNotifications(user, abstractSystemSubject);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with selecting " +
                        "the type of notification registration");
        }
        return null;
    }
}

