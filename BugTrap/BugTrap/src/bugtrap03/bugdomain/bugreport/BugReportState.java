package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.usersystem.Developer;
import com.google.java.contract.Requires;
import purecollections.PList;

/**
 * An interface for the different states of a bug report
 *
 * Classes that implement this interface should only be used by a bug report
 * Only a bug report will use the methods of this interface
 */
interface BugReportState {

    /**
     * This method returns the tag associated with this bug report state
     *
     * @return  The tag associated with this bug report state
     */
    Tag getTag();

    /**
     * This method sets the current tag for this bug report
     *
     * @param bugReport The bug report this state belongs to
     * @param tag   The new tag of this bug report
     *
     * @throws IllegalArgumentException If isValidTag(tag) throws this exception
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     * @throws IllegalStateException    If the current state doesn't allow the new Tag
     *
     * @see #isValidTag(Tag)
     */
    @Requires("bugReport.getInternState() == this && user != null && bugReport.isValidTag(tag)")
    BugReportState setTag(BugReport bugReport, Tag tag) throws IllegalArgumentException, IllegalStateException;

    /**
     * This method check if the given tag is valid for the bug report
     *
     * @param tag the tag to check
     *
     * @return true if the tag is a valid tag
     */
    boolean isValidTag(Tag tag);

    /**
     * This method adds a developer to this bug report issued by the given user
     *
     * @param bugReport The bug report this state belongs to
     * @param dev       The developer to assign to this bug report
     *
     * @throws IllegalArgumentException If the given dev was null
     */
    @Requires("bugReport.getInternState() == this && bugReport.isValidUser(user)")
    BugReportState addUser(BugReport bugReport, Developer dev) throws IllegalArgumentException;

    /**
     * This method adds a given test to the bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param test  The test that the user wants to add
     *
     * @throws IllegalStateException    If the current state doesn't allow to add a test
     * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
     */
    @Requires("bugReport.getInternState() == this && BugReport.isValidTest(test)")
    BugReportState addTest(BugReport bugReport, String test) throws IllegalStateException, IllegalArgumentException;

    /**
     * This method returns all the tests associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any tests
     *
     * @return  The list of tests associated with this bug report
     */
    PList<String> getTests() throws IllegalStateException;

    /**
     * This method adds a given patch to this bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param patch The patch that the user wants to submit
     *
     * @throws IllegalStateException    If the given patch is invalid for this bug report
     * @throws IllegalArgumentException If the given patch is not valid for this bug report state
     */
    @Requires("bugReport.getInternState() == this && BugReport.isValidPatch(patch)")
    BugReportState addPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException;

    /**
     * This method returns the patches associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any patches
     *
     * @return  The patches associated with this bug report
     */
    PList<String> getPatches() throws IllegalStateException;

    /**
     * This method selects a patch for this bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param patch The patch that the user wants to select
     *
     * @throws IllegalStateException    If the current state doesn't allow the selecting of a patch
     * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Requires("bugReport.getInternState() == this")
    BugReportState selectPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException;

    /**
     * This method returns the selected patch of this bug report state
     *
     * @throws IllegalStateException    If the given state doesn't have a select patch
     *
     * @return The selected patch for this bug report
     */
    String getSelectedPatch() throws IllegalStateException;

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param bugReport The bug report this state belongs to
     * @param score The score that the creator wants to give
     *
     * @throws IllegalStateException    If the current state doesn't allow assigning a score
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Requires("bugReport.getInternState() == this")
    BugReportState giveScore(BugReport bugReport, int score) throws IllegalStateException, IllegalArgumentException;

    /**
     * This method returns the score associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any patches
     *
     * @return  The score associated with this bug report
     */
    int getScore() throws IllegalStateException;

    /**
     * This method changes this bug report to a duplicate of the given bug report
     * @param bugReport The bug report this state belongs to
     * @param duplicate The bug report that this bug report is a duplicate of
     */
    @Requires("bugReport.getInternState() == this && bugReport.isValidDuplicate(duplicate)")
    BugReportState setDuplicate(BugReport bugReport, BugReport duplicate) throws IllegalStateException;

    /**
     * This method returns the bug report this bug report is a duplicate of
     *
     * @throws IllegalStateException    If the current state doesn't have a duplicate bug report
     *
     * @return  The bug report this bug report is a duplicate of
     */
    BugReport getDuplicate() throws IllegalStateException;

    /**
     * This method check if the current state is a resolved state of a bug report
     *
     * @return true if the given state is considered resolved for a bug report
     */
    boolean isResolved();

    /**
     * This method returns state specific information
     *
     * @return The details of this state as a String
     */
    String getDetails();

    /**
     * This method returns the multiplier of this state
     *
     * @return  The multiplier
     */
    double getMultiplier();

}
