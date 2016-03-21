package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.User;
import bugtrap03.misc.Tree;
import com.google.java.contract.Requires;
import java.util.Enumeration;
import purecollections.PList;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This is a class representing a comment.
 *
 * @author Group 03
 */
@DomainAPI
public class Comment {

    /**
     * This method initializes an Comment
     *
     * @param issuer the issuer that creates this comment
     * @param text the comment text for this comment
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
        if (creator != null && !creator.hasPermission(UserPerm.CREATE_COMMENT)) {
            throw new PermissionException("The given creator for this comment doesn't have the needed permission");
        }
        if (!Comment.isValidCreator(creator)) {
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
        if (!creator.hasPermission(UserPerm.CREATE_COMMENT)) {
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
     * This method returns all comments in this comment (deep search) including this comment.
     *
     * @param tree The {@link Tree} to add these comments onto. When null a new Tree will be used.
     * @return The tree containing all the comments of this comment.
     * 
     * @see Tree#Tree() 
     * @see Tree#addTree(java.lang.Object) 
     */
    @DomainAPI
    public Tree<Comment> getAllComments(Tree<Comment> tree) {
        if(tree == null) {
            tree = new Tree();
        }

        //Add ourself to the tree.
        Tree<Comment> thisNode = tree.addTree(this);
        
        //Let subComments add themselves.
        for (Comment comment : this.getSubComments()) {
            comment.getAllComments(thisNode);
        }
        
        return tree;
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
     * @param text the text of the comment
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

    /**
     * Get the String form of the given Tree structure. This assumes all objects in the given structure are of type
     * {@link Comment} and the top node carries null.
     *
     * @param top The Tree structure used to get the String format of.
     * @return The result of converting the Tree structure to a Comment. When
     *
     * @throws ClassCastException When the Tree structure does not contain a Comment object.
     */
    @Requires("top != null")
    public static String commentsTreeToString(Tree top) throws ClassCastException {
        StringBuilder str = new StringBuilder();

        Enumeration<DefaultMutableTreeNode> childIt = top.children();
        int count = 1;
        while (childIt.hasMoreElements()) {
            DefaultMutableTreeNode node = childIt.nextElement();
            Comment comment = (Comment) node.getUserObject();
            String preString = Integer.toString(count);

            str.append("\n \t ");
            str.append(preString).append(". ");
            str.append(comment.getText());
            commentsTreeToString(node, str, preString);
            count++;
        }

        return str.toString();
    }

    /**
     * Convert the passed treeStructure to a String. This is used by
     * {@link Comment#commentsTreeToString(javax.swing.tree.DefaultMutableTreeNode)} and should not be used elsewhere.
     *
     * @param node The node of which to print the children.
     * @param str The StringBuilder used to build upon.
     * @param preString The preString which will contain the pre format of each Comment. (e.g 2.1)
     *
     * @see Comment#commentsTreeToString(javax.swing.tree.DefaultMutableTreeNode)
     */
    private static void commentsTreeToString(DefaultMutableTreeNode node, StringBuilder str, String preString) {
        if (node == null || str == null || preString == null) {
            return;
        }

        Enumeration<DefaultMutableTreeNode> childIt = node.children();
        int count = 1;
        while (childIt.hasMoreElements()) {
            DefaultMutableTreeNode subNode = childIt.nextElement();
            Comment comment = (Comment) node.getUserObject();
            String subPreString = preString + "." + count;

            str.append("\n \t ");
            str.append(subPreString).append(". ");
            str.append(comment.getText());
            commentsTreeToString(node, str, subPreString);
            count++;
        }
    }
}
