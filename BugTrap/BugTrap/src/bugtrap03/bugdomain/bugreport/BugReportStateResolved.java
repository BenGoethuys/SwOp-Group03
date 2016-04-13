package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.usersystem.Developer;
import com.google.java.contract.Requires;
import purecollections.PList;

/**
 * This class represents the resolved state of a bug report
 */
class BugReportStateResolved implements BugReportState {

    /**
     * constructor for this state
     *
     * <br><dt><b>Preconditions:</b><dd> selectedPatch is a valid patch in the previous state.
     */
    @Requires("for (String test: tests) { BugReport.isValidTest(test) } && " +
            "for (String patch: patches) { BugReport.isValidPatch(patch) } &&")
    BugReportStateResolved(PList<String> tests, PList<String> patches, String selectedPatch){
        this.tests = tests;
        this.patches = patches;
        this.selectedPatch = selectedPatch;
    }

    private PList<String> tests;
    private PList<String> patches;
    private String selectedPatch;

    /**
     * This method returns the tag associated with this bug report state
     *
     * @return The tag associated with this bug report state
     */
    @Override
    public Tag getTag() {
        return Tag.RESOLVED;
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
    public BugReportState setTag(BugReport bugReport, Tag tag) throws IllegalArgumentException, IllegalStateException {
        // should be the only valid tag
        assert (tag == Tag.NOT_A_BUG);
        BugReportState newState = new BugReportStateNotABug();
        bugReport.setInternState(newState);
        return newState;
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
    public BugReportState addUser(BugReport bugReport, Developer dev) throws IllegalArgumentException {
        bugReport.addUser(dev);
        return this;
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
    public BugReportState addTest(BugReport bugReport, String test) throws IllegalStateException, IllegalArgumentException {
        throw new IllegalStateException("There is already a selected patch, no more tests allowed");
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
    public BugReportState addPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException {
        throw new IllegalStateException("There is already a selected patch, no more patches allowed");
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
    public BugReportState selectPatch(BugReport bugReport, String patch) throws IllegalStateException, IllegalArgumentException {
        throw new IllegalStateException("There is already a selected patch");
    }

    /**
     * This method returns the selected patch of this bug report state
     *
     * @return The selected patch for this bug report
     * @throws IllegalStateException If the given state doesn't have a select patch
     */
    @Override
    public String getSelectedPatch() throws IllegalStateException {
        return this.selectedPatch;
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
    public BugReportState giveScore(BugReport bugReport, int score) throws IllegalStateException, IllegalArgumentException {
        if (! this.isValidScore(score)){
            throw new IllegalArgumentException("The given score is not a score from 1 to 5");
        }
        BugReportState newState = new BugReportStateClosed(this.getTests(), this.getPatches(), this.getSelectedPatch(), score);
        bugReport.setInternState(newState);
        bugReport.notifyTagSubs(bugReport);
        return newState;
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
     * this method checks if the give score is a valid score for a bug report in this state
     * @param score The score to check
     * @return  True if the given score is a score on a 1 to 5 scale
     */
    protected static boolean isValidScore(int score){
        if (score > 5 || score < 1){
            return false;
        }
        return true;
    }

    /**
     * This method changes this bug report to a duplicate of the given bug report
     *
     * @param bugReport The bug report this state belongs to
     * @param duplicate The bug report that this bug report is a duplicate of
     */
    @Override
    @Requires("bugReport.getInternState() == this && bugReport.isValidDuplicate(duplicate)")
    public BugReportState setDuplicate(BugReport bugReport, BugReport duplicate) throws IllegalStateException {
        // cannot have unresolved dependencies at this point
        BugReportState newState = new BugReportStateDuplicate(duplicate);
        bugReport.setInternState(newState);
        return newState;
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

    /**
     * This method returns state specific information
     *
     * @return The details of this state as a String
     */
    @Override
    public String getDetails() {
        StringBuilder str = new StringBuilder();
        str.append("\n tag: ").append(this.getTag().name());
        // add tests
        str.append("\n tests: ");
        for (String test : this.getTests()){
            str.append("\n \t ").append(test);
        }
        // add patches
        str.append("\n patches: ");
        for (String patch : this.getPatches()){
            str.append("\n \t ").append(patch);
        }
        // add selected
        str.append("\n selected patch: ").append(this.getSelectedPatch());
        return str.toString();
    }
}
