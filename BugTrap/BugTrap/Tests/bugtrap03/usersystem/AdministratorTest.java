/**
 * 
 */
package bugtrap03.usersystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Mathias
 *
 */
public class AdministratorTest {
	
	Administrator admin;
	Administrator admin2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		admin = new Administrator("Admin", "Afirst", "Amiddle", "Alast");
		admin2 = new Administrator("Admin2", "A2first", "A2last");
	}

	@Test
	public void testNames() {
		assertEquals(admin.getFirstName(), "Afirst");
		assertEquals(admin.getMiddleName(), "Amiddle");
		assertEquals(admin.getLastName(), "Alast");
		assertEquals(admin.getUsername(), "Admin");
		
		assertEquals(admin2.getFirstName(), "A2first");
		assertEquals(admin2.getLastName(), "A2last");
		assertEquals(admin2.getUsername(), "Admin2");
	}
	
}
