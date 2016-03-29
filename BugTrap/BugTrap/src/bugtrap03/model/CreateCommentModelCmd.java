package bugtrap03.model;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Group 03
 */
class CreateCommentModelCmd extends ModelCmd {

    //TODO Finish constructor for creating comment on comment.
    CreateCommentModelCmd(User user, BugReport bugReport, Comment parentComment, String text) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The bugReport passed to the CreateCommentModelCmd was a null reference.");
        }

        this.user = user;
        this.bugReport = bugReport;
        this.parentComment = parentComment;
        this.text = text;
    }

    /**
     * Create a {@link ModelCmd} that can create a {@link Comment} when executed.
     *
     * @param user The creator of the comment
     * @param bugReport The bug report to create the comment on
     * @param text The text of the new comment
     *
     * @throws IllegalArgumentException When bugReport == null
     */
    CreateCommentModelCmd(User user, BugReport bugReport, String text) throws IllegalArgumentException {
        if (bugReport == null) {
            throw new IllegalArgumentException("The BugReport passed to CreateCommentModelCmd was a null reference.");
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
     * @throws PermissionException If the given User doesn't have the permission to create the comment
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @throws IllegalArgumentException if the given comment is not valid for this bug report
     * @throws PermissionException When the user does not have sufficient permissions.
     * @see BugReport#isValidComment(Comment)
     */
    @Override
    Comment exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateCommentModelCmd was already executed.");
        }

        if (parentComment == null) {
            comment = bugReport.addComment(user, text);
        } else {
            throw new IllegalStateException("Not yet implemented!"); //TODO implement creating subComment.
        }
        isExecuted = true;
        return comment;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        //TODO: implement undo'ing subcomment.
        return bugReport.deleteComment(comment);
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

}
