/**
 * 
 */
package bugtrap03.bugdomain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bugtrap03.usersystem.Issuer;
import purecollections.PList;

/**
 * @author Ben
 *
 */
public class CommentTest {
	
	static Issuer issuer;
	static String text;
	static Comment comment1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		issuer = new Issuer("comment0DitGebruiktNiemandAnders", "bla", "bla");
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
	 * Test method for {@link bugtrap03.bugdomain.Comment#Comment(bugtrap03.usersystem.Issuer, java.lang.String)}.
	 */
	@Test
	public void testComment() {
		Comment comment = new Comment(issuer, text);
		assertEquals(issuer, comment.getCreator());
		assertEquals(text, comment.getText());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCommentInvalidIssuer() {
		new Comment(null, text);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCommentInvalidText() {
		new Comment(issuer, null);
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#getCreator()}.
	 */
	@Test
	public void testGetCreator() {
		assertEquals(issuer, comment1.getCreator());
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#isValidCreator(bugtrap03.usersystem.Issuer)}.
	 */
	@Test
	public void testIsValidCreator() {
		assertTrue(comment1.isValidCreator(issuer));
		assertFalse(comment1.isValidCreator(null));
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#getText()}.
	 */
	@Test
	public void testGetText() {
		assertEquals(text, comment1.getText());
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#isValidText(java.lang.String)}.
	 */
	@Test
	public void testIsValidText() {
		assertTrue(comment1.isValidText(text));
		assertFalse(comment1.isValidText(null));
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#getSubComments()}.
	 */
	@Test
	public void testGetSubComments() {
		Comment comment = new Comment(issuer, text);
		assertTrue(comment.getSubComments().isEmpty());
		
		Comment comment2 = new Comment(issuer, text);
		comment.addSubComment(comment2);
		assertFalse(comment.getSubComments().isEmpty());
		assertTrue(comment.getSubComments().contains(comment2));
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#isValidSubComments(purecollections.PList)}.
	 */
	@Test
	public void testIsValidSubComments() {
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
	 * Test method for {@link bugtrap03.bugdomain.Comment#addSubComment(bugtrap03.bugdomain.Comment)}.
	 */
	@Test
	public void testAddSubComment() {
		Comment comment = new Comment(issuer, text);
		Comment comment2 = new Comment(issuer, text);
		assertTrue(comment.getSubComments().isEmpty());
		comment.addSubComment(comment2);
		assertTrue(comment.getSubComments().contains(comment2));
		
		assertTrue(comment2.getSubComments().isEmpty());
		comment2.addSubComment(issuer, text);
		assertFalse(comment2.getSubComments().isEmpty());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddSubCommentInvalidNull(){
		comment1.addSubComment(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddSubCommentInvalidSelf(){
		comment1.addSubComment(comment1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddSubCommentInvalidIssuer(){
		comment1.addSubComment(null, text);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddSubCommentInvalidText(){
		comment1.addSubComment(issuer, null);
	}

	/**
	 * Test method for {@link bugtrap03.bugdomain.Comment#isValidSubComment(bugtrap03.bugdomain.Comment)}.
	 */
	@Test
	public void testIsValidSubComment() {
		Comment comment = new Comment(issuer, text);
		assertTrue(comment1.isValidSubComment(comment));
		assertFalse(comment1.isValidSubComment(null));
	}

}
