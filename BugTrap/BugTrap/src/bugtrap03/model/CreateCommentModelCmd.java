package bugtrap03.model;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
class CreateCommentModelCmd extends ModelCmd {

    /**
     * Create a {@link ModelCmd} that can create a {@link Comment} on a {@link Comment} when executed.
     *
     * @param user          The creator of the comment
     * @param parentComment The comment to create the comment on
     * @param text          The text of the new comment
     * @throws IllegalArgumentException When parentComment == null
     */
    CreateCommentModelCmd(User user, Comment parentComment, String text) throws IllegalArgumentException {
        if (parentComment == null) {
            throw new IllegalArgumentException("The parrentComment passed to the CreateCommentModelCmd was a null reference.");
        }

        this.bugReport = null;
        this.user = user;
        this.parentComment = parentComment;
        this.text = text;
    }

    /**
     * Create a {@link ModelCmd} that can create a {@link Comment} on a {@link BugReport} when executed.
     *
     * @param user      The creator of the comment
     * @param bugReport The bug report to create the comment on
     * @param text      The text of the new comment
     * @throws IllegalArgumentException When bugReport == null
     * @throws IllegalArgumentException When the given bug report is terminated
     */
    CreateCommentModelCmd(User user, BugReport bugReport, String text) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The BugReport passed to CreateCommentModelCmd was a null reference.");
        }
        if (bugReport.isTerminated()) {
            throw new IllegalArgumentException("The given bug report is terminated");
        }

        this.user = user;
        this.bugReport = bugReport;
        this.text = text;
        parentComment = null;
    }

    private final User user;
    private final BugReport bugReport;
    private final String text;

    private final Comment parentComment;

    private boolean isExecuted;

    private Comment comment;

    /**
     * This method creates a comment on a given BugReport
     *
     * @return The new generated comment
     * @throws PermissionException      If the given User doesn't have the permission to create the comment
     * @throws IllegalStateException    When this ModelCmd was already executed.
     * @throws IllegalArgumentException if the given comment is not valid for this bug report
     * @throws IllegalArgumentException If parentComment == null && is terminated
     * @throws PermissionException      When the user does not have sufficient permissions.
     * @see BugReport#isValidComment(Comment)
     */
    @Override
    Comment exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateCommentModelCmd was already executed.");
        }

        if (parentComment == null) {
            if (bugReport.isTerminated()) {
                throw new IllegalArgumentException("The given bugReport is terminated.");
            }

            comment = bugReport.addComment(user, text);
        } else {
            comment = parentComment.addSubComment(user, text);
        }
        isExecuted = true;
        return comment;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        if (parentComment == null) {
            return bugReport.deleteComment(comment);
        } else {
            return parentComment.deleteComment(comment);
        }
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        if (parentComment == null) {
            return "Created a comment " + text + " on a BugReport " + bugReport.getTitle();
        } else {
            return "Created a comment " + text + " on a Comment " + parentComment.getText();
        }
    }

}
