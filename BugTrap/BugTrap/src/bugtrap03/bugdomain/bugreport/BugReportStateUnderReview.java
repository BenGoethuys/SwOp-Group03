package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.Tag;
import bugtrap03.bugdomain.usersystem.Developer;
import com.google.java.contract.Requires;
import purecollections.PList;

/**
 * This class represents the under review state of a bug report
 */
class BugReportStateUnderReview implements BugReportState {

    /**
     * constructor for this state
     */
    @Requires("BugReport.isValidTPatch(patch)")
    BugReportStateUnderReview(PList<String> tests, String patch){
        //TODO assertion for valid tests? isValid?
        this.tests = tests;
        this.patches = PList.<String>empty().plus(patch);
    }

    private PList<String> tests;
    private PList<String> patches;

    /**
     * This method returns the tag associated with this bug report state
     *
     * @return The tag associated with this bug report state
     */
    @Override
    public Tag getTag() {
        return Tag.UNDER_REVIEW;
    }

    /**
     * This method sets the current tag for this bug report
     *
     * @param bugReport The bug report this state belongs to
     * @param tag       The new tag of this bug report
     * @throws IllegalArgumentException If isValidTag(tag) throws this exception
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     * @throws IllegalStateException    If the given state doesn't allow a the new tag
     * @see #isValidTag(Tag)
     */
    @Override
    @Requires("bugReport.getInternState() == this && user != null && bugReport.isValidTag(tag)")
    public void setTag(BugReport bugReport, Tag tag) throws IllegalArgumentException, IllegalStateException {
        // cannot have unresolved deps, because otherwise would not have this tag
        if (tag == Tag.NOT_A_BUG) {
            bugReport.setInternState(new BugReportStateNotABug());
        }
        // Tag assigned should be the only valid left
        assert (tag == Tag.ASSIGNED);
        bugReport.setInternState(new BugReportStateAssigned());
    }

    /**
     * This method checks if the current state of the bug report allows a user to set the requested tag
     *
     * @param tag the tag to check
     * @return true if the tag is a valid tag
     */
    @Override
    public boolean isValidTag(Tag tag) {
        if (tag == Tag.NOT_A_BUG){
            return true;
        }
        if (tag == Tag.ASSIGNED){
            return true;
        }
        return false;
    }

    /**
     * This method adds a developer to this bug report issued by the given user
     *
     * @param bugReport The bug report this state belongs to
     * @param dev       The developer to assign to this bug report
     * @throws IllegalArgumentException If the given dev was null
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Override
    @Requires("bugReport.getInternState() == this && bugReport.isValidUser(user)")
    public void addUser(BugReport bugReport, Developer dev) throws IllegalArgumentException {
        bugReport.addUser(dev);
    }

    /**
     * This method adds a given test to the bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param test      The test that the user wants to add
     * @throws IllegalStateException    If the current state doesn't allow to add a test
     * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Override
    @Requires("bugReport.getInternState() == this && BugReport.isValidTest(test)")
    public void addTest(BugReport bugReport, String test) throws IllegalStateException, IllegalArgumentException {
        throw new IllegalStateException("The current state of the bug report doesn't allow adding more tests");
    }

    /**
     * This method returns all the tests associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any patches
     *
     * @return The list of tests associated with this bug report
     */
    @Override
    public PList<String> getTests() throws IllegalStateException {
        return this.tests;
    }

    /**
     * This method adds a given patch to this bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param patch     The patch that the user wants to submit
     * @throws IllegalStateException    If the given patch is invalid for this bug report
     * @throws IllegalArgumentException If the given patch is not valid for this bug report state
     */
    @Override
    @Requires("bugReport.getInternState() == this && BugReport.isValidPatch(patch)")
    public void addPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException {
        this.patches =  this.getPatches().plus(patch);
    }

    /**
     * This method returns the patches associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any patches
     *
     * @return The patches associated with this bug report
     */
    @Override
    public PList<String> getPatches() throws IllegalStateException {
        return this.patches;
    }

    /**
     * This method selects a patch for this bug report state
     *
     * @param bugReport The bug report this state belongs to
     * @param patch     The patch that the user wants to select
     * @throws IllegalStateException    If the current state doesn't allow the selecting of a patch
     * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Override
    @Requires("bugReport.getInternState() == this")
    public void selectPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException {
        if (! this.getPatches().contains(patch)){
            throw new IllegalArgumentException("The given patch is not a valid patch for this bug report state");
        }
        bugReport.setInternState(new BugReportStateResolved(this.getTests(), this.getPatches(), patch));
    }

    /**
     * This method returns the selected patch of this bug report state
     *
     * @return The selected patch for this bug report
     * @throws IllegalStateException If the given state doesn't have a select patch
     */
    @Override
    public String getSelectedPatch() throws IllegalStateException {
        throw new IllegalStateException("The current state has no selected patch");
    }

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param bugReport The bug report this state belongs to
     * @param score     The score that the creator wants to give
     * @throws IllegalStateException    If the current state doesn't allow assigning a score
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     * @throws IllegalArgumentException If the given BugReport was null or not the bug report this belongs to
     */
    @Override
    @Requires("bugReport.getInternState() == this")
    public void giveScore(BugReport bugReport, int score) throws IllegalStateException, IllegalArgumentException {
        throw new IllegalStateException("The current state doesn't allow to add a score");
    }

    /**
     * This method returns the score associated with this bug report
     *
     * @throws IllegalStateException    If the current state doesn't have any patches
     *
     * @return The score associated with this bug report
     */
    @Override
    public int getScore() throws IllegalStateException {
        throw new IllegalStateException("The current state doesn't have an score associated with it");
    }

    /**
     * This method changes this bug report to a duplicate of the given bug report
     *
     * @param bugReport The bug report this state belongs to
     * @param duplicate The bug report that this bug report is a duplicate of
     */
    @Override
    @Requires("bugReport.getInternState() == this && bugReport.isValidDuplicate(duplicate)")
    public void setDuplicate(BugReport bugReport, BugReport duplicate) throws IllegalStateException {
        // cannot have unresolved dependencies at this point
        bugReport.setInternState(new BugReportStateDuplicate(duplicate));
    }

    /**
     * This method returns the bug report this bug report is a duplicate of
     *
     * @return The bug report this bug report is a duplicate of
     * @throws IllegalStateException If the current state doesn't have a duplicate bug report
     */
    @Override
    public BugReport getDuplicate() throws IllegalStateException {
        throw new IllegalStateException("The current state of this bug report doesn't have an duplicate");
    }

    /**
     * This method check if the current state is a resolved state of a bug report
     *
     * @return true if the given state is considered resoled for a bug report
     */
    @Override
    public boolean isResolved() {
        return false;
    }
}
