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
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return the bugreport of which the tag has been updated.
     * @throws PermissionException When the user doesn't have the needed permission to set the tag.
     * @throws CancelException When the user wants to abort the process
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException {

        BugReport bugrep = new SelectBugReportCmd().exec(scan, model, user);
        Tag tagToSet;
        do {
            tagToSet = this.selectTag(scan, model);
            try {
                model.setTag(bugrep, tagToSet, user);
            } catch (IllegalArgumentException iae) {
                scan.println("Invalid tag, select other tag");
                tagToSet = null;
            }
        }while (tagToSet == null);

        scan.println("The tag " + tagToSet.toString() + " has been set.");
        return bugrep;
    }

    /**
     * This methods let's the user select a tag.
     * @param @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @return the selected tag
     * @throws CancelException When the users wants to abort the process
     */
    private Tag selectTag(TerminalScanner scan, DataModel model) throws CancelException{
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
        return tagToSet;
    }
}
