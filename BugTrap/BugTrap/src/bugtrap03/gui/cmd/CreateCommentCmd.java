package bugtrap03.gui.cmd;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.gui.cmd.general.CancelException;
import bugtrap03.gui.terminal.TerminalScanner;
import bugtrap03.model.DataModel;

import java.util.ArrayList;

/**
 * This command represents the use case for creating a comment in the system
 *
 * @author Group 03
 */
public class CreateCommentCmd implements Cmd {

    /**
     * Execute this command and possibly return a result.
     * <p>
     * <br> 1. The issuer indicates he wants to create a comment.
     * <br> 2. Include use case Select Bug Report.
     * <br> 3. The system shows a list of all comments of the selected bug
     * report.
     * <br> 4. The issuer indicates if he wants to comment directly on the bug
     * report or on some other comment.
     * <br> 5. The system asks for the text of the comment.
     * <br> 6. The issuer writes his comment.
     * <br> 7. The system adds the comment to the selected use case.
     *
     * @param scan  The scanner used to interact with the person.
     * @param model The model used for model access.
     * @param user  The {@link User} who wants to executes this command.
     * @return The newly created comment.
     * @throws PermissionException      When the user does not have sufficient
     *                                  permissions.
     * @throws CancelException          When the users wants to abort the current cmd
     * @throws IllegalArgumentException When scan, model or user is a null reference.
     * @see DataModel#createComment(User, BugReport, String)
     * @see DataModel#createComment(User, Comment, String)
     */
    @Override
    public Comment exec(TerminalScanner scan, DataModel model, User user) throws PermissionException, CancelException, IllegalArgumentException {
        if (scan == null || model == null || user == null) {
            throw new IllegalArgumentException("scan, model and user musn't be null.");
        }

        // 1. The issuer indicates he wants to create a comment.
        // 2. Include use case Select Bug Report.
        BugReport bugRep = new SelectBugReportCmd().exec(scan, model, user);

        // 3. The system shows a list of all comments of the selected bugreport.
        // 4. The issuer indicates if he wants to comment directly on the bug report or on some other comment.
        ArrayList<Comment> list = new ArrayList<>(bugRep.getAllComments());

        scan.println("Please select a comment: ");
        scan.println("Available comments:");
        for (int i = 0; i < list.size(); i++) {
            Comment comment = list.get(i);
            scan.println(i + ". " + comment.getText());
        }

        Comment comment = null;
        boolean done = false;
        do {
            scan.print("I choose (Nr): (leave blank to create comment on the bugreport)");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("")) {
                done = true;
            } else {
                try {
                    int index = Integer.parseInt(input);
                    if (index >= 0 && index < list.size()) {
                        comment = list.get(index);
                        done = true;
                    } else {
                        scan.println("Invalid input.");
                    }
                } catch (NumberFormatException ex) {
                    scan.println("Invalid input.");
                }
            }
        } while (!done);

        // 5. The system asks for the text of the comment.
        // 6. The issuer writes his comment.
        // 7. The system adds the comment to the selected use case.
        Comment newComment;
        if (comment == null) {
            scan.println("You want to create a comment on the selected bug report");
            scan.println("Please enter the text of the comment: ");
            String text = scan.nextLine();
            newComment = model.createComment(user, bugRep, text);
        } else {
            scan.print("You want to create a comment on the selected comment: ");
            scan.println(comment.getText());
            scan.println("Please enter the text of the comment: ");
            String text = scan.nextLine();
            newComment = model.createComment(user, comment, text);
        }
        scan.println("Comment created");
        return newComment;
    }
}
