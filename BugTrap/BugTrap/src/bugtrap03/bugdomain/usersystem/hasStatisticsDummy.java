package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.HealthIndicator;
import purecollections.PList;

/**
 * A dummy class for the {@link hasStatistics} interface.
 * <br>This will throw unsupportedOperationExceptions for any getters and will ignore any operations (void).
 *
 * @author Group 03
 */
public class hasStatisticsDummy implements hasStatistics {

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbDuplicateSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbNotABugReportSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbBugReportsSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public PList<HealthIndicator> getConditionIndicators() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public double getAvgLinesOfTestsSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addLinesOfTest(long nb) {

    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbTestsSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addTestsSubmitted(long nb) {
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbAssignedToClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbAssignedToUnfinished() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbPatchesSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addPatchessSubmitted(long nb) {
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public double getAvgLinesOfPatchesSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addLinesOfPatches(long nb) {
    }

}
