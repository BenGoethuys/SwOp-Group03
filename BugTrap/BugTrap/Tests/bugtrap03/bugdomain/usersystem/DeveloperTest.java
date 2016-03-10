/**
 *
 */
package bugtrap03.bugdomain.usersystem;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
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

    @Test
    public void testNames() {
        assertEquals(dev.getFirstName(), "Dfirst");
        assertEquals(dev.getMiddleName(), "Dmiddle");
        assertEquals(dev.getLastName(), "Dlast");
        assertEquals(dev.getUsername(), "Dev1");

        assertEquals(dev2.getFirstName(), "Dfirst");
        assertEquals(dev2.getLastName(), "Dlast");
        assertEquals(dev2.getUsername(), "Dev2");
    }


}
