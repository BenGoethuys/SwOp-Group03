package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by Kwinten on 07/03/2016.
 */
public class UpdateBugReportCmd implements Cmd {
    /**
     * <p>
     * <br> 1. The developer indicates he wants to update a bug report.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The developer suggests a new tag for the bug report.
     * <br> 4. The system gives the selected bug report the new tag.
     * <br> 4a. The developer does not have the permission to assign the tag:
     */


    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {

        BugReport bugrep = new SelectBugReportCmd().exec(scan, model, user);
        //TODO make model method
        PList<Tag> taglist = model.getAllTags();
        scan.println("Available tags: \n");
        for (int i=0; i < taglist.size(); i++){
            scan.println(i + ": \t" + taglist.get(i).toString() + "\n");
        }
        Tag tagToSet = null;

        do{
            scan.print("I choose tag: ");
            if (scan.hasNextInt()) { // by index
                int index = scan.nextInt();// input
                if (index >= 0 && index < taglist.size()) {
                    tagToSet = taglist.get(index);
                } else {
                    scan.println("Invalid input.");
                }
            } else { // by name
                String input = scan.nextLine(); // input
                try {
                    tagToSet = taglist.parallelStream().filter(u -> u.toString().equals(input)).findFirst().get();
                } catch (NoSuchElementException ex) {
                    scan.println("Invalid input.");
                }
            }
        }while (tagToSet == null);

        scan.println("You have selected: \t" + tagToSet.toString());
        model.setTag(bugrep, tagToSet, user);
        scan.println("The tag has been set.");
        return bugrep;
    }
}
