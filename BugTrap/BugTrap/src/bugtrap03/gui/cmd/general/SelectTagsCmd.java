package bugtrap03.gui.cmd.general;

import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.Cmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * @author Group 03
 */
public class SelectTagsCmd implements Cmd<EnumSet<Tag>> {
    @Override
    public EnumSet<Tag> exec(TerminalScanner scan, DataModel model, User userdummy) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null) {
            throw new IllegalArgumentException("scan and model musn't be null.");
        }
        ArrayList<Tag> selectedtags = new ArrayList<>();
        boolean selecting = true;
        while (selecting){
            selectedtags.add(this.selectTag(scan, model));
            scan.println("Do you wish to select another tag? Y/N");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("Y")){
                selecting = true;
            } else if (input.equalsIgnoreCase("N")){
                selecting = false;
            } else{
                scan.println("Invalid input, selecting of tags considered complete.");
                selecting = false;
            }
        }
        return EnumSet.copyOf(selectedtags);
    }

    private Tag selectTag(TerminalScanner scan, DataModel model) throws CancelException {
        PList<Tag> taglist = model.getAllTags();
        scan.println("Please select tag.");
        Tag tagToSet = new GetObjectOfListCmd<>(taglist, (u -> u.name()), ((u, input) -> u.name().equalsIgnoreCase(input)))
                .exec(scan, model, null);
        scan.println("Tag " + tagToSet.toString() + " added to subscription.");
        return tagToSet;
    }
}
