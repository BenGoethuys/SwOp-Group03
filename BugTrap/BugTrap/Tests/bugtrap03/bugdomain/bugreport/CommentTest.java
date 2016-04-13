/**
 *
 */
package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;
import bugtrap03.bugdomain.usersystem.Issuer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import purecollections.PList;

import static org.junit.Assert.*;

/**
 * @author Ben
 */
public class CommentTest {

    static Issuer issuer;
    static String text;
    static Comment comment1;
    static Administrator admin;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        issuer = new Issuer("comment0DitGebruiktNiemandAnders", "bla", "bla");
        admin = new Administrator("comment1DitGebruiktNiemandAnders", "bla", "hihi");
        text = "this is a comment";
        comment1 = new Comment(issuer, text);
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link Comment#Comment(bugtrap03.bugdomain.usersystem.User, String)}.
     */
    @Test
    public void testComment() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        assertEquals(issuer, comment.getCreator());
        assertEquals(text, comment.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCommentInvalidIssuer() throws PermissionException {
        new Comment(null, text);
    }

    @Test(expected = PermissionException.class)
    public void testCommentNoPermission() throws PermissionException {
        new Comment(admin, "Valid text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCommentInvalidText() throws PermissionException {
        new Comment(issuer, null);
    }

    /**
     * Test method for {@link Comment#getCreator()}.
     */
    @Test
    public void testGetCreator() {
        assertEquals(issuer, comment1.getCreator());
    }

    /**
     * Test method for
     * {@link Comment#isValidCreator(bugtrap03.bugdomain.usersystem.User)}.
     */
    @Test
    public void testIsValidCreator() {
        assertTrue(Comment.isValidCreator(issuer));
        assertFalse(Comment.isValidCreator(null));
        assertFalse(Comment.isValidCreator(admin));
    }

    /**
     * Test method for {@link Comment#getText()}.
     */
    @Test
    public void testGetText() {
        assertEquals(text, comment1.getText());
    }

    /**
     * Test method for
     * {@link Comment#isValidText(java.lang.String)}.
     */
    @Test
    public void testIsValidText() {
        assertTrue(Comment.isValidText(text));
        assertFalse(Comment.isValidText(null));
    }

    /**
     * Test method for {@link Comment#getSubComments()}.
     */
    @Test
    public void testGetSubComments() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        assertTrue(comment.getSubComments().isEmpty());

        Comment comment2 = new Comment(issuer, text);
        comment.addSubComment(comment2);
        assertFalse(comment.getSubComments().isEmpty());
        assertTrue(comment.getSubComments().contains(comment2));
    }

    @Test
    public void testGetAllComments() throws PermissionException {
        Comment comment = new Comment(issuer, "bla bla");
        assertTrue(comment.getSubComments().isEmpty());
        assertTrue(comment.getAllComments(null).contains(comment));
        assertEquals(1, comment.getAllComments(null).size());

        Comment comment2 = new Comment(issuer, "hihi");
        comment.addSubComment(comment2);
        assertTrue(comment.getSubComments().contains(comment2));
        assertTrue(comment.getAllComments(null).contains(comment2));

        Comment comment3 = new Comment(issuer, "hoho");
        comment2.addSubComment(comment3);
        assertFalse(comment.getSubComments().contains(comment3));
        assertTrue(comment.getAllComments(null).contains(comment3));
    }

    /**
     * Test method for
     * {@link Comment#isValidSubComments(purecollections.PList)}
     * .
     */
    @Test
    public void testIsValidSubComments() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        PList<Comment> validListEmpty = PList.<Comment>empty();
        PList<Comment> validList = validListEmpty.plus(comment1);
        PList<Comment> nullPointer = null;
        PList<Comment> invalidListContSelf = validListEmpty.plus(comment);

        assertTrue(comment.isValidSubComments(validListEmpty));
        assertTrue(comment.isValidSubComments(validList));
        assertFalse(comment.isValidSubComments(nullPointer));
        assertFalse(comment.isValidSubComments(invalidListContSelf));
    }

    /**
     * Test method for
     * {@link Comment#addSubComment(Comment)}
     * .
     */
    @Test
    public void testAddSubComment() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        Comment comment2 = new Comment(issuer, text);
        assertTrue(comment.getSubComments().isEmpty());
        comment.addSubComment(comment2);
        assertTrue(comment.getSubComments().contains(comment2));

        assertTrue(comment2.getSubComments().isEmpty());
        Comment returnComment = comment2.addSubComment(issuer, text);
        assertFalse(comment2.getSubComments().isEmpty());
        assertEquals(comment2.getSubComments().getFirst(), returnComment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSubCommentInvalidNull() {
        comment1.addSubComment(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSubCommentInvalidSelf() {
        comment1.addSubComment(comment1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSubCommentInvalidIssuer() throws PermissionException {
        comment1.addSubComment(null, text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSubCommentInvalidText() throws PermissionException {
        comment1.addSubComment(issuer, null);
    }

    @Test(expected = PermissionException.class)
    public void testAddSubCommentNoPermission() throws PermissionException {
        comment1.addSubComment(admin, "Valid text");
    }

    /**
     * Test method for
     * {@link Comment#isValidSubComment(Comment)}
     * .
     */
    @Test
    public void testIsValidSubComment() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        assertTrue(comment1.isValidSubComment(comment));
        assertFalse(comment1.isValidSubComment(null));
    }

    @Test
    public void testCommentTreeToString() throws PermissionException {
        Comment comment = new Comment(issuer, text);
        Comment subCom = comment.addSubComment(issuer, "SubComment 1");
        Comment subSubCom = subCom.addSubComment(issuer, "SubComment 1.1");
        Comment subCom2 = comment.addSubComment(issuer, "SubComment 2");

        String res = Comment.commentsTreeToString(comment.getAllComments(null));

        assertTrue(res.contains("1. " + comment.getText()));
        assertTrue(res.contains("1.1. " + subCom.getText()));
        assertTrue(res.contains("1.1.1. " + subSubCom.getText()));
        assertTrue(res.contains("1.2. " + subCom2.getText()));
    }

}
