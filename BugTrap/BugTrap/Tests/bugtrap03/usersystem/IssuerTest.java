/**
 * 
 */
package bugtrap03.usersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Mathias //TODO
 *
 */
public class IssuerTest {
	
	static Issuer issuer;
	static Issuer issuer2;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		issuer = new Issuer("Issuer", "Ifirst", "Ilast");
		issuer2 = new Issuer("Issuer2", "I2first", "I2middle", "I2last");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testIssuerStringStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testIssuerStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#User(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUserStringStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#User(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUserStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#isValidUsername(java.lang.String)}.
	 */
	@Test
	public void testIsValidUsername() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#isValidFirstName(java.lang.String)}.
	 */
	@Test
	public void testIsValidFirstName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#getMiddleName()}.
	 */
	@Test
	public void testGetMiddleName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#isValidMiddleName(java.lang.String)}.
	 */
	@Test
	public void testIsValidMiddleName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#getLastName()}.
	 */
	@Test
	public void testGetLastName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#isValidLastName(java.lang.String)}.
	 */
	@Test
	public void testIsValidLastName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#getFullName()}.
	 */
	@Test
	public void testGetFullName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#hasPermission(bugtrap03.permission.UserPerm)}.
	 */
	@Test
	public void testHasPermission() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link bugtrap03.usersystem.User#hasRolePermission(bugtrap03.permission.RolePerm, bugtrap03.bugdomain.Project)}.
	 */
	@Test
	public void testHasRolePermission() {
		fail("Not yet implemented");
	}

}
