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
public class DeveloperTest {
	
	static Developer dev;
	static Developer dev2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dev = new Developer("Dev1", "Dfirst", "Dmiddle", "Dlast");
		dev2 = new Developer("Dev2", "Dfirst", "Dlast");
	}

	@Before
	public void setUp() throws Exception {
	}



}
