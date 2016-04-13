package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.bugdomain.notification.Subject;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.SelectTagsCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.*;

/**
 * @author Group 03
 */
public class RegisterFromBugReportCmd implements Cmd{
    public RegisterFromBugReportCmd(){
        this.subsriptionTypes = new HashMap<>();
        this.subsriptionTypes.put("alltags",1);
        this.subsriptionTypes.put("specictags",2);
        this.subsriptionTypes.put("comment",3);
    }

    private HashMap<String, Integer> subsriptionTypes;

    @Override
    public Subject exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        BugReport selectedBugRep = new SelectBugReportCmd().exec(scan, model, user);

        scan.println("Select subscription type.");
        String subscriptionType = new GetObjectOfListCmd<>(PList.<String>empty().plusAll(this.subsriptionTypes.keySet()),
                u -> (u.toString()), ((u, input) -> ((u.equalsIgnoreCase(input))))).exec(scan, model, null);
        subscriptionType = subscriptionType.toLowerCase();
        Integer index = this.subsriptionTypes.get(subscriptionType);
        switch (index) {
            case 1:
                model.registerForAllTagsNotifications(user, selectedBugRep);
                break;
            case 2:
                EnumSet<Tag> selectedTags = new SelectTagsCmd().exec(scan, model, user);
                model.registerForSpecificTagsNotifications(user, selectedBugRep, selectedTags);
                break;
            case 3:
                model.registerForCommentNotifications(user, selectedBugRep);
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with selecting " +
                        "the type of notification registration");
        }

        return selectedBugRep;
    }
}
