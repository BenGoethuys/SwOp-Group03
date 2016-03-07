package bugtrap03.bugdomain;

/**
 *  This class represents a version id consisting out of 3 numbers, separated by a dot
 */
@DomainAPI
public class VersionID implements Comparable<VersionID> {

    private int firstNb;
    private int secondNb;
    private int thirdNb;

    /**
     * Creates a versionID for a project.
     *
     * @param nb1 The first digit of the versionID.
     * @param nb2 The second digit of the versionID.
     * @param nb3 The third digit of the versionID.
     */
    @DomainAPI
    public VersionID(int nb1, int nb2, int nb3) {
        this.setFirstNb(nb1);
        this.setSecondNb(nb2);
        this.setThirdNb(nb3);
    }

    /**
     * Creates a versionID for a project.
     *
     * @param nb1 The first digit of the versionID.
     * @param nb2 The second digit of the versionID.
     */
    @DomainAPI
    public VersionID(int nb1, int nb2) {
        this(nb1, nb2, 0);
    }

    /**
     * Creates a versionID for a project.
     *
     * @param nb1 The first digit of the versionID.
     */
    @DomainAPI
    public VersionID(int nb1) {
        this(nb1, 0, 0);
    }

    /**
     * Creates a basic versionID.
     */
    @DomainAPI
    public VersionID() {
        this(0, 0, 1);
    }

    /**
     * This is a getter for the first digit of the versionID.
     *
     * @return The first digit of the versionID.
     */
    @DomainAPI
    public int getFirstNb() {
        return firstNb;
    }

    /**
     * Sets the first digit of the versionID on the given number.
     *
     * @param firstNb The first digit of the versionID.
     */
    private void setFirstNb(int firstNb) {
        this.firstNb = firstNb;
    }

    /**
     * This is a getter for the second digit of the versionID.
     *
     * @return The second digit of the versionID.
     */
    @DomainAPI
    public int getSecondNb() {
        return secondNb;
    }

    /**
     * Sets the second digit of the versionID on the given number.
     *
     * @param secondNb The second digit of the versionID.
     */
    private void setSecondNb(int secondNb) {
        this.secondNb = secondNb;
    }

    /**
     * This is a getter for the third digit of the versionID.
     *
     * @return The third digit of the versionID.
     */
    @DomainAPI
    public int getThirdNb() {
        return thirdNb;
    }

    /**
     * Sets the third digit of the versionID on the given number.
     *
     * @param thirdNb The third digit of the versionID.
     */
    private void setThirdNb(int thirdNb) {
        this.thirdNb = thirdNb;
    }

    /**
     * Visual representation of a versionID.
     */
    @Override
    @DomainAPI
    public String toString() {
        return firstNb + "." + secondNb + "." + thirdNb;
    }

    /**
     * Compares two versionIDs.
     *
     * @return 0 if and only if two versionIDs are equal, 1 if and only if the
     *         second versionID is greater than the first versionID, -1
     *         otherwise.
     */
    @Override
    @DomainAPI
    public int compareTo(VersionID other) {
        if (this.getFirstNb() == other.getFirstNb() && this.getSecondNb() == other.getSecondNb()
                && this.getThirdNb() == other.getThirdNb()) {
            return 0;
        }
        if ((this.getFirstNb() < other.getFirstNb())
                || (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() < other.getSecondNb())
                || (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() >= other.getSecondNb()
                        && this.getThirdNb() < other.getThirdNb())) {
            return -1;
        }
        return 1;
    }

    /**
     * Checks if two versionIDs are equal.
     * 
     * @param other The versionID to compare.
     * @return True if the version ID's are equal.
     */
    @Override
    @DomainAPI
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof VersionID) {
            VersionID otherID = (VersionID) other;
            return this.compareTo(otherID) == 0;
        }
        return false;
    }
}
