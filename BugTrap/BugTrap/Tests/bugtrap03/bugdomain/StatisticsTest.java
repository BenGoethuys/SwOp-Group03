package bugtrap03.bugdomain;

import bugtrap03.bugdomain.usersystem.Statistics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Group 03
 */
public class StatisticsTest {

    @Test
    public void testConstructor() {
        Statistics stats = new Statistics();

        assertEquals(0, stats.getNbPatchesSubmitted());
        assertEquals(0, stats.getNbTestsSubmitted());
        assertEquals(0, stats.getAvgLinesOfPatchesSubmitted(), 1e-15);
        assertEquals(0, stats.getAvgLinesOfTestsSubmitted(), 1e-15);
    }

    @Test
    public void testImmutable() {
        Statistics stats = new Statistics();

        stats.addLinesOfPatches(50);
        assertEquals(0, stats.getAvgLinesOfPatchesSubmitted(), 1e-15);

        stats.addLinesOfTest(40);
        assertEquals(0, stats.getAvgLinesOfTestsSubmitted(), 1e-15);

        stats.addPatchessSubmitted(2);
        assertEquals(0, stats.getNbPatchesSubmitted());

        stats.addTestsSubmitted(3);
        assertEquals(0, stats.getNbTestsSubmitted());
    }

    @Test
    public void testAdd() {
        Statistics stats = new Statistics();

        /* Test add */
        stats = stats.addLinesOfPatches(50);
        stats = stats.addLinesOfTest(40);
        stats = stats.addPatchessSubmitted(2);
        stats = stats.addTestsSubmitted(3);

        assertEquals(2, stats.getNbPatchesSubmitted());
        assertEquals(3, stats.getNbTestsSubmitted());
        assertEquals(25, stats.getAvgLinesOfPatchesSubmitted(), 0);
        assertEquals(13.333333, stats.getAvgLinesOfTestsSubmitted(), 1e-4);

        /* Test negative */
        stats = new Statistics();

        stats = stats.addLinesOfPatches(-5);
        assertEquals(0, stats.getNbPatchesSubmitted());

        stats = stats.addLinesOfTest(-10);
        assertEquals(0, stats.getNbTestsSubmitted());

        stats = stats.addPatchessSubmitted(-20);
        assertEquals(0, stats.getAvgLinesOfPatchesSubmitted(), 1e-15);

        stats = stats.addTestsSubmitted(-50);
        assertEquals(0, stats.getAvgLinesOfTestsSubmitted(), 1e-15);
    }

}
