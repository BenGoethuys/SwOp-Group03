package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.cmd.general.bugReport.GiveScoreToBugReportCmd;
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
                // 4. The system asks for the corresponding information for that tag.
                switch (tagToSet){
                    case ASSIGNED :
                        this.setAssigned(user, bugrep, scan, model);
                        break;
                    case UNDER_REVIEW :
                        this.setUnderReview(user, bugrep, scan, model);
                        break;
                    case RESOLVED :
                        new SelectPatchForBugReportCmd(bugrep).exec(scan, model, user);
                        break;
                    case CLOSED :
                        new GiveScoreToBugReportCmd(bugrep).exec(scan, model, user);
                        break;
                    case DUPLICATE:
                        //TODO
                        break;
                    //TODO
                }
                //TODO: Mathias / Ben 5. The issuer provides the requested information.

                // 6. The system gives the selected bug report the new tag.
                // 4a.The developer does not have the permission to assign the
                // tag: the use case ends.
                model.setTag(bugrep, tagToSet, user);
            } catch (IllegalArgumentException iae) {
                scan.println("Invalid tag, select other tag");
                //System.out.println(iae.toString());
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

    /**
     * This method tries to set the tag of the given bug report to the Tag assigned
     *
     * @param user      The user that wants to change the Tag
     * @param bugReport The bugReport that needs a Tag change
     * @param scanner   The scanner of the cmd' s
     * @param model     The model of the cmd' s
     *
     * @throws PermissionException      If the given user doesn't have the needed permissions
     * @throws IllegalStateException    If the current bugReport doesn't allow the tag change
     */
    private void setAssigned(User user, BugReport bugReport, TerminalScanner scanner, DataModel model) throws PermissionException, IllegalStateException {
        if (bugReport.getTag() == Tag.NEW){
            new AssignToBugReportCmd(bugReport).exec(scanner, model, user);
        } else if (bugReport.getTag() == Tag.UNDER_REVIEW){
            bugReport.setTag(Tag.UNDER_REVIEW, user);
        } else {
            throw new IllegalStateException("The requested Tag cannot be set to the given bug report");
        }
    }

    /**
     * This method tries to set the Tag of the given bug report to UnderReview
     *
     * @param user      The user that wants to change the tag
     * @param bugReport The bug report that needs a tag change
     * @param scanner   The scanner of the cmd' s
     * @param model     The model of the cmd' s
     *
     * @throws PermissionException      If the given user doesn't have the needed permissions
     * @throws IllegalStateException    If the current bugReport doesn't allow the tag change
     */
    private void setUnderReview(User user, BugReport bugReport, TerminalScanner scanner, DataModel model) throws PermissionException, IllegalStateException {
        if (bugReport.getTests().isEmpty()){
            new ProposeTestCmd(bugReport).exec(scanner, model, user);
        }
        new ProposeTestCmd(bugReport).exec(scanner, model, user);
    }
}
