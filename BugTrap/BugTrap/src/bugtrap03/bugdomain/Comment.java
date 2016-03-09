package bugtrap03.bugdomain;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.Issuer;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;

import java.util.ArrayList;

/**
 * This is a class representing a comment.
 *
 * @author Group 03
 */
@DomainAPI
public class Comment {

    /**
     * This method initialises an Comment
     *
     * @param issuer the issuer that creates this comment
     * @param text   the comment text for this comment
     * @throws IllegalArgumentException if the given creator is not a valid creator for this comment
     * @throws PermissionException if the given creator doesn't have the needed permissions
     * @see Comment#isValidCreator(User)
     */
    public Comment(User issuer, String text) throws IllegalArgumentException, PermissionException {
        this.setCreator(issuer);
        this.setText(text);
        this.setSubComments(PList.<Comment>empty());
    }

    private User creator;
    private String text;
    private PList<Comment> SubComments;

    /**
     * Returns the creator of the comment
     *
     * @return the creator
     */
    @DomainAPI
    public User getCreator() {
        return creator;
    }

    /**
     * This method changes the creator of his comment
     *
     * @param creator the creator to set
     * @throws IllegalArgumentException if the given creator is not a valid creator for this comment
     * @throws PermissionException if the given creator doesn't have the needed permissions
     * @see Comment#isValidCreator(User)
     * @see Comment#isValidText(String)
     */
    private void setCreator(User creator) throws IllegalArgumentException, PermissionException {
        if (creator != null && ! creator.hasPermission(UserPerm.CREATE_COMMENT)){
            throw new PermissionException("The given creator for this comment doesn't have the needed permission");
        }
        if (! Comment.isValidCreator(creator)) {
            throw new IllegalArgumentException("The given Issuer is not a valid creator for this comment");
        }
        this.creator = creator;
    }

    /**
     * This method check if the given creator is a valid creator for the comment
     *
     * @param creator the creator to check
     * @return true if the creator is a valid creator
     */
    @DomainAPI
    public static boolean isValidCreator(User creator) {
        if (creator == null) {
            return false;
        }
        if (! creator.hasPermission(UserPerm.CREATE_COMMENT)){
            return false;
        }
        return true;
    }

    /**
     * This method returns the text of this comment
     *
     * @return the text
     */
    @DomainAPI
    public String getText() {
        return text;
    }

    /**
     * This method sets the text of this comment
     *
     * @param text the text to set
     * @throws IllegalArgumentException if the given text is not valid for this comment
     * @see Comment#isValidText(String)
     */
    private void setText(String text) throws IllegalArgumentException {
        if (!Comment.isValidText(text)) {
            throw new IllegalArgumentException("The given text is not valid for this comment");
        }
        this.text = text;
    }

    /**
     * This method check if the given text is a valid text for this comment
     *
     * @param text the text to check
     * @return true if the given text is a valid text for this comment
     */
    @DomainAPI
    public static boolean isValidText(String text) {
        if (text == null) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the comments on this comment (responses)
     *
     * @return the subComments of this comment
     */
    @DomainAPI
    public PList<Comment> getSubComments() {
        return SubComments;
    }

    /**
     * This method returns all comments in this comment (deep search) including this comment
     *
     * @return all the comments in this comment
     */
    @DomainAPI
    public PList<Comment> getAllComments() {
        ArrayList<Comment> list = new ArrayList<>();
        list.add(this);
        for (Comment comment : this.getSubComments()) {
            list.addAll(comment.getAllComments());
        }
        return PList.<Comment>empty().plusAll(list);
    }

    /**
     * This method sets the SubComments for this comment
     *
     * @param subComments the subComments to set
     * @throws IllegalArgumentException if the given PList is not valid for this comment
     * @see Comment#isValidSubComments(PList<Comment>)
     */
    private void setSubComments(PList<Comment> subComments) throws IllegalArgumentException {
        if (!isValidSubComments(subComments)) {
            throw new IllegalArgumentException("The given PList is invalid as subComments-list for this comment");
        }
        SubComments = subComments;
    }

    /**
     * This method check if the given PList is valid as the subComments-list of the comment
     *
     * @param subComments the list to check
     * @return true if the given PList is valid for this comment
     */
    protected boolean isValidSubComments(PList<Comment> subComments) {
        if (subComments == null) {
            return false;
        }

        // cannot add null to PList -> no check needed

        for (Comment comment : subComments) {
            if (comment == this) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method adds a sub-comment to this comment
     *
     * @param comment the sub-comment to add to this comment
     * @throws IllegalArgumentException if the given comment is not valid for this comment
     * @see Comment#isValidSubComment(Comment)
     */
    protected Comment addSubComment(Comment comment) throws IllegalArgumentException {
        if (!this.isValidSubComment(comment)) {
            throw new IllegalArgumentException("The given comment is not a valid sub-comment for this comment");
        }
        this.SubComments = this.SubComments.plus(comment);
        return comment;
    }

    /**
     * This method makes a Comment object and adds it to the sub-comment list
     *
     * @param creator the creator of the comment
     * @param text    the text of the comment
     * @throws IllegalArgumentException if the given parameters are not valid for this comment
     * @throws PermissionException if the given creator doesn't have the needed permissions
     * @see Comment(Issuer, String)
     */
    public Comment addSubComment(User creator, String text) throws IllegalArgumentException, PermissionException {
        return this.addSubComment(new Comment(creator, text));
    }

    /**
     * This method checks if the given comment is a valid sub-comment for this comment
     *
     * @param comment the comment to check
     * @return true if the given comment is valid as a sub-comment for this comment
     */
    @DomainAPI
    public boolean isValidSubComment(Comment comment) {
        if (comment == null) {
            return false;
        }
        if (comment == this) {
            return false;
        }
        return true;
    }

}
