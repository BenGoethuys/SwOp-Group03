package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 * This class represents the update bug report scenario
 *
 * @author Group 03
 */
public class UpdateBugReportCmd implements Cmd {

    /**
     * <p>
     * <br> 1. The developer indicates he wants to update a bug report.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The developer suggests a new tag for the bug report.
     * <br> 4. The system gives the selected bug report the new tag.
     * <br> 4a. The developer does not have the permission to assign the tag: the use case ends.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The BugReport of which the tag has been updated.
     * @throws PermissionException      When the user doesn't have the needed
     *                                  permission to set the tag.
     * @throws CancelException          When the user wants to abort the process
     * @throws IllegalArgumentException If scan, model or user is null
     * @see GetObjectOfListCmd#exec(TerminalScanner, DataModel, User)
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user)
            throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }
        // 1. The developer indicates he wants to update a bug report.
        // 2. Include use case Select Bug Report.
        BugReport bugrep = new SelectBugReportCmd().exec(scan, model, user);

        // Ask Tag
        Tag tagToSet;
        do {
            // 3. The developer suggests a new tag for the bug report.
            tagToSet = this.selectTag(scan, model);
            try {
                // 4. The system gives the selected bug report the new tag.
                // 4a.The developer does not have the permission to assign the
                // tag: the use case ends.
                model.setTag(bugrep, tagToSet, user);
            } catch (IllegalArgumentException iae) {
                scan.println("Invalid tag, select other tag");
                tagToSet = null;
            }
        } while (tagToSet == null);

        scan.println("The tag " + tagToSet.toString() + " has been set.");
        return bugrep;
    }

    /**
     * This methods lets the user select a tag.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @return The selected tag
     * @throws CancelException When the users wants to abort the process
     */
    private Tag selectTag(TerminalScanner scan, DataModel model) throws CancelException {
        PList<Tag> taglist = model.getAllTags();

        Tag tagToSet = new GetObjectOfListCmd<>(taglist, (u -> u.name()), ((u, input) -> u.name().equalsIgnoreCase(input)))
                .exec(scan, model, null);

        scan.println("You have selected: \t" + tagToSet.toString());
        return tagToSet;
    }
}
