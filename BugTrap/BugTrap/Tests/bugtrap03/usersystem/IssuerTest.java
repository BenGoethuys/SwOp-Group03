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

	@Test
	public void testNames() {
		assertEquals(issuer.getFirstName(), "Ifirst");
		assertEquals(issuer.getLastName(), "Ilast");
		assertEquals(issuer.getUsername(), "Issuer");

		assertEquals(issuer2.getFirstName(), "I2first");
		assertEquals(issuer2.getLastName(), "I2last");
		assertEquals(issuer2.getMiddleName(), "I2middle");
		assertEquals(issuer2.getUsername(), "Issuer2");
	}
	
}
