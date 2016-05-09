package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;

/**
 *
 * An Immutable class that holds several statistics.
 *
 * @author Group 03
 */
@DomainAPI
public class Statistics {

    private long testLines;
    private long testAmount;

    private long patchLines;
    private long patchAmount;

    /**
     * Get the average amount of lines written for submitted tests.
     *
     * @return The average amount of lines submitted tests contain.
     */
    @DomainAPI
    public double getAvgLinesOfTestsSubmitted() {
        return testLines / testAmount;
    }

    /**
     * Add to the total amount of lines written for tests.
     * <br> The resulting amount will never be less than 0.
     *
     * @param nb The amount of lines to add. Can be negative.
     * @return The new Statistics object wherein the changes are performed.
     */
    public Statistics addLinesOfTest(long nb) {
        Statistics stats = this.clone();
        stats.testLines = Math.max(stats.testLines + nb, 0);
        return stats;
    }

    /**
     * Get the amount of tests submitted.
     *
     * @return The amount of tests submitted.
     */
    @DomainAPI
    public long getNbTestsSubmitted() {
        return this.testAmount;
    }

    /**
     * Add to the amount of tests submitted.
     * <br> The resulting amount will never be less than 0.
     *
     * @param nb The amount of tests to add. Can be negative.
     * @return The new Statistics object wherein the changes are performed.
     */
    public Statistics addTestsSubmitted(long nb) {
        Statistics stats = this.clone();
        stats.testAmount = Math.max(stats.testAmount, 0);
        return stats;
    }

    /**
     * Get the amount of patches submitted.
     *
     * @return The amount of patches submitted.
     */
    @DomainAPI
    public long getNbPatchesSubmitted() {
        return patchAmount;
    }

    /**
     * Add to the amount of patches submitted.
     * <br> The resulting amount will never be less than 0.
     *
     * @param nb The amount of patches to add. Can be negative.
     * @return The new Statistics object wherein the changes are performed.
     */
    public Statistics addPatchessSubmitted(long nb) {
        Statistics stats = this.clone();
        stats.patchAmount = Math.max(stats.patchAmount + nb, 0);
        return stats;
    }

    /**
     * Get the average amount of lines written for submitted patches.
     *
     * @return The average amount of lines submitted patches contain.
     */
    @DomainAPI
    public double getAvgLinesOfPatchesSubmitted() {
        return patchLines / patchAmount;
    }

    /**
     * Add to the total amount of lines written for patches.
     * <br> The resulting amount will never be less than 0.
     *
     * @param nb The amount of lines to add. Can be negative.
     * @return The new Statistics object wherein the changes are performed.
     */
    public Statistics addLinesOfPatches(long nb) {
        Statistics stats = this.clone();
        stats.patchLines = Math.max(stats.patchLines + nb, 0);
        return stats;
    }

    /**
     * Creates and returns a copy of this object.
     * <br> This object will have all values the same as the values currently in this object.
     *
     * @return A clone of this object.
     */
    @Override
    @DomainAPI
    public Statistics clone() {
        Statistics stats = new Statistics();

        stats.patchLines = this.patchLines;
        stats.patchAmount = this.patchAmount;

        stats.testLines = this.testLines;
        stats.testAmount = this.testAmount;

        return stats;
    }

}
