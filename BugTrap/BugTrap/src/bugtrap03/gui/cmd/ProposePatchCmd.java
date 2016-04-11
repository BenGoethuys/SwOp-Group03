package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;

/**
 *
 * @author Group 03
 */
public class ProposePatchCmd implements Cmd<BugReport> {

    
    /**
     * Creates a {@link Cmd} for the ProposePatch scenario where the bugRep to propose to is already chosen.
     * @param bugReport The bugRep to propose to.
     */
    public ProposePatchCmd(BugReport bugReport) {
        this.bugReport = bugReport;
    }
    
    /**
     * Creates a {@link Cmd} for the ProposePatch scenario which includes a scenario to select the bug report.
     */
    public ProposePatchCmd() {
    }
    
    private BugReport bugReport = null;
    
    /**
     * Executing the command resulting in the possible adding of a Test for a certain {@link BugReport}
     * <p>
     * 1. The developer indicates that he wants to submit a patch for some bug report.
     * <br> 2. Include use case Select Bug Report if required.
     * <br> 3. The system shows the form for uploading the patch.
     * <br> 4. The developer provides the details for uploading the patch.
     * <br> 5. The system attaches the patch to the bug report.
     * <br><u>Extensions:</u> 3a. The developer is not assigned as a developer to the project.
     * <br> 1. The use case ends here.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     *
     * @return The BugReport where a patch was proposed to.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException If scan, model or user is null
     * @throws IllegalStateException When a BugReport is chosen with a state that does not allow adding a patch.
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        // 1. The developer indicates that he wants to submit a patch for some bug report.
        // 2. Include use case Select Bug Report if required.
        BugReport bugRep = (bugReport != null) ? bugReport : (new SelectBugReportCmd()).exec(scan, model, user); //IllegalArg for scan,model,user == null
        String text;

        // 3. The system shows the form for uploading the patch.
        // 4. The developer provides the details for uploading the patch.
        scan.println("If you wish to submit a file please type yes or no.");
        if (scan.nextLine().equalsIgnoreCase("yes")) {
            /* By file */
            scan.println("Please select the patch code file.");

            //Setup fileChooser
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            boolean choseAFile = chooser.showDialog(null, "select") == JFileChooser.APPROVE_OPTION;

            //Process input file
            if (choseAFile) {
                File file = chooser.getSelectedFile();
                try {
                    /*List<String> lines = Files.readAllLines(file.toPath());
                    StringBuilder strBuilder = new StringBuilder();
                    for (String line : lines) {
                        strBuilder.append(line).append("\n");
                    }
                    text = strBuilder.toString();*/
                    text = new String(Files.readAllBytes(file.toPath()));
                } catch (FileNotFoundException ex) {
                    throw new CancelException("Could not find file " + file.toPath() + ". Cancelling operation.");
                } catch (IOException ex) {
                    throw new CancelException("IO Exception occured. Cancelling operation.");
                }
            } else {
                throw new CancelException("Cancelled submitting patch file.");
            }
        } else {
            /* By manual Text input */
            scan.println("You have chosen to insert text. (Leave blank to finish the text)");
            scan.print("text: ");

            // Take input
            StringBuilder strBuilder = new StringBuilder();
            String temp;
            do {
                temp = scan.nextLine();
                strBuilder.append(temp).append("\n");
            } while (!temp.equalsIgnoreCase(""));

            // Save input
            text = strBuilder.toString();
        }

        // 5. The system attaches the patch to the bug report.
        // Extension 3a. The developer is not assigned as a developer to the project. (=PermissionException)
        //TODO: Ben 3a. should be thrown when is not assigned a developer BUT model.addPatch throws when not a programmer.
        //-------> Is throwing when not a programmer correct and in the assignment?
        // 1. The use case ends here.
        model.addPatch(bugRep, user, text); //Permission, IllegalState, IllegalArg
        return bugRep;
    }
}
