package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.HealthIndicator;
import purecollections.PList;

/**
 *
 * @author Group 03
 */
public interface hasStatistics {

    
    //TODO: remove big comment.
    
    /*
    
* Performance metrics (V Developers - miss een apart object aan dev toevoegen zodat niet al die dingen in dev staan?)
(En wanneer undo moet ook die telling ongedaan worden gemaakt.)
Reporting
- # duplicate submitted (dyn - undoable)
- # NotABugReport submitted (dyn - undoable)
- # bugReports submitted (dyn - undoable)
Leadership
- condition indicators van projects that dev lead. (dynamisch berekenen)
Test
- avg lines of code for each submitted test (stored total # lines and calculate average)
- # tests submitted (stored)
Problem Solving
- # Closed BR the dev is assigned to (dynamic, caution consistent - undoable)
- # Unfinished BR the dev is assigned to (dyamic, caution consistent - undoable)
- # patches submitted. (stored)
- avg lines of code in patches (stored total # lines in Dev and calculate average)
    
    
     */
    /**
     * Get the amount of duplicate bug reports submitted.
     *
     * @return The amount of duplicate bug reports submitted.
     */
    public long getNbDuplicateSubmitted();

    /**
     * Get the amount of NotABugReport bug reports submitted.
     *
     * @return The amount of NotABugReport bug reports submitted.
     */
    public long getNbNotABugReportSubmitted();

    /**
     * Get the amount of bug reports submitted.
     *
     * @return The amount of bug reports submitted.
     */
    public long getNbBugReportsSubmitted();

    /**
     * Get the health indicators of all projects involved.
     *
     * @return The health indicators of all projects involved.
     */
    public PList<HealthIndicator> getConditionIndicators();

    //TODO: Ben & Vincent undo-able ! don't forget.
    /**
     * Get the average amount of lines written for submitted tests.
     *
     * @return The average amount of lines submitted tests contain.
     */
    public double getAvgLinesOfTestsSubmitted();

    /**
     * Add to the total amount of lines written for tests.
     *
     * @param nb The amount of lines to add. Can be negative.
     */
    public void addLinesOfTest(long nb);

    /**
     * Get the amount of tests submitted.
     *
     * @return The amount of tests submitted.
     */
    public long getNbTestsSubmitted();

    /**
     * Add to the amount of tests submitted.
     *
     * @param nb The amount of tests to add. Can be negative.
     */
    public void addTestsSubmitted(long nb);

    /**
     * Get the amount of closed bug reports to which 'you' are assigned.
     *
     * @return The amount of closed bug reports to which 'you' are assigned.
     */
    public long getNbAssignedToClosed();

    /**
     * Get the amount of unfinished bug reports to which 'you' are assigned.
     *
     * @return The amount of unfinished bug reports to which 'you' are assigned.
     */
    public long getNbAssignedToUnfinished();

    /**
     * Get the amount of patches submitted.
     *
     * @return The amount of patches submitted.
     */
    public long getNbPatchesSubmitted();

    /**
     * Add to the amount of patches submitted.
     *
     * @param nb The amount of patches to add. Can be negative.
     */
    public void addPatchessSubmitted(long nb);

    /**
     * Get the average amount of lines written for submitted patches.
     *
     * @return The average amount of lines submitted patches contain.
     */
    public double getAvgLinesOfPatchesSubmitted();

    /**
     * Add to the total amount of lines written for patches.
     *
     * @param nb The amount of lines to add. Can be negative.
     */
    public void addLinesOfPatches(long nb);

}
