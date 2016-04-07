package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Developer;
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
public class ProposeTestCmd implements Cmd {

    /**
     * Executing the command resulting in the possible adding of a Test for a certain {@link BugReport}.
     * <p>
     * 1. The developer indicates that he wants to submit a test for some bugReport.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The system shows the form for uploading the test code.
     * <br> 4. The developer provides the details for uploading the test code.
     * <br> 5. The system attaches the test to the bug report.
     * <br><u> Extensions:</u>
     * <br> 3a. The developer is not assigned as a tester to the corresponding project.
     * <br> 1. The use case ends here.
     *
     * @param scan The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user The {@link User} who wants to executes this command.
     * 
     * @return The BugReport where a Test was proposed to.
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws CancelException When the users wants to abort the current cmd
     * @throws IllegalArgumentException If scan, model or user is null
     * @throws IllegalStateException When a BugReport is chosen with a state that does not allow adding a test.
     * 
     * @see DataModel#addTest(bugtrap03.bugdomain.bugreport.BugReport, bugtrap03.bugdomain.usersystem.User, java.lang.String) 
     */
    @Override
    public BugReport exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException, IllegalStateException {
        if(!(user instanceof Developer)) {
            throw new PermissionException("A Developer is required to propose a test.");
        }

        // 1. The developer indicates that he wants to submit a test for some bugReport.
        // 2. Include use case Select Bug Report.
        BugReport bugReport = (new SelectBugReportCmd()).exec(scan, model, user); //IllegalArg for scan,model,user == null
        String text;

        // 3. The system shows the form for uploading the test code.
        // 4. The developer provides the details for uploading the test code.
        scan.println("If you wish to submit a file please type yes or no.");
        if (scan.nextLine().equalsIgnoreCase("yes")) {
            scan.println("Please select the test code file.");

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
                throw new CancelException("Cancelled submitting test file.");
            }
        } else {
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
        
        // 5. The system attaches the test to the bug report.
        // Extension 3a. The developer is not assigned as a tester to the corresponding project. (= PermissionException)
        // 1. The use case ends here.
        model.addTest(bugReport, user, text); //Permission, IllegalState, IllegalArg
        return bugReport;
    }

}
