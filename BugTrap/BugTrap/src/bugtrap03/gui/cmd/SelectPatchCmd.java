package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.cmd.general.GetObjectOfListCmd;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import purecollections.PList;

/**
 *
 * A SelectPatchForBugReport scenario where the user selects a patch for a certain bug report.
 *
 * @author Group 03
 */
public class SelectPatchCmd implements Cmd<String> {

    /**
     * Creates a {@link Cmd} for selecting a patch from the list of patches of a bugReport.
     *
     * @param bugReport The bugRep to select from.
     */
    public SelectPatchCmd(BugReport bugReport) {
        this.bugReport = bugReport;
    }

    /**
     * Creates a {@link Cmd} for selecting a patch from the list of patches of a bugReport. A scenario to select the bug
     * report is included.
     */
    public SelectPatchCmd() {
    }

    private BugReport bugReport;

    /**
     * Executing this scenario results in the possible selecting of a patch for a certain bug report.
     * <p>
     * 1. The developer indicates that he wants to select a patch for some bug report.
     * <br> 2. Include use case Select Bug Report if required.
     * <br> 3. The system shows a list of possible patches.
     * <br> 4. The developer selects a patch.
     * <br> 5. The system selects the patch for the bug report.
     * <br><u>Extensions:</u> 5a. The developer does not have sufficient permissions.
     * <br> 1. The use case ends here.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     *
     * @return The BugReport that a patch was selected for.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan or model or user == null
     * @see DataModel#selectPatch(BugReport, User, String)
     */
    @Override
    public String exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. The developer indicates that he wants to select a patch for some bug report.
        // 2. Include use case Select Bug Report if required.
        scan.println("Selecting patch.");
        BugReport bugRep;
        if (bugReport == null) {
            scan.println("Select a bug report.");
            bugRep = (new SelectBugReportCmd()).exec(scan, model, user);  //IllegalArg for scan,model,user == null
            scan.println("Selecting patch.");
        } else {
            bugRep = bugReport;
        }

        PList<String> patches = bugRep.getPatches();
        if (patches.isEmpty()) {
            throw new IllegalStateException("There are no patches in this bug report to select from.");
        }

        // 3. The system shows a list of possible patches.
        // 4. The developer selects a patch.  
        scan.println("Use the index in front of the patch to select a patch.");
        GetObjectOfListCmd<String> selectedPatchCmd = new GetObjectOfListCmd<>(patches, (p -> p), ((p, input) -> p.equals(input)));
        String selectedPatch = selectedPatchCmd.exec(scan, model, user);

        // 5. The system selects the patch for the bug report.
        // extension: 5a. The developer does not have sufficient permissions.
        // 1. The use case ends here.
        model.selectPatch(bugRep, user, selectedPatch);

        scan.println("The selected patch is set to: " + selectedPatch);

        return selectedPatch;
    }

}
