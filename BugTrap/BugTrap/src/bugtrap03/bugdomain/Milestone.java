package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
@DomainAPI
public class Milestone extends VersionID {

    /**
     * Creates a Milestone.
     * 
     * @param first The first digit of the Milestone.
     * @param second The second digit of the Milestone.
     * @param third The third digit of the Milestone.
     * 
     * @see VersionID#VersionID(int, int, int)
     */
    @DomainAPI
    public Milestone(int first, int second, int third) {
        super(first, second, third);
    }

    /**
     * Creates a Milestone
     * 
     * @param first The first digit of the Milestone.
     * @param second The second digit of the Milestone.
     * 
     * @see VersionID#VersionID(int, int)
     */
    @DomainAPI
    public Milestone(int first, int second) {
        super(first, second);
    }

    /**
     * Creates a Milestone
     * 
     * @param first The first digit of the Milestone.
     * 
     * @see VersionID#VersionID(int)
     */
    @DomainAPI
    public Milestone(int first) {
        super(first);
    }

    /**
     * Visual representation of a Milestone.
     */
    @Override
    @DomainAPI
    public String toString() {
        if (this.getThirdNb() != 0) {
            return "M" + this.getFirstNb() + "." + this.getSecondNb() + "." + this.getThirdNb();
        } else if (this.getSecondNb() != 0) {
            return "M" + this.getFirstNb() + "." + this.getSecondNb();
        } else {
            return "M" + this.getFirstNb();
        }
    }

    /**
     * Compares two Milestones.
     *
     * @return 0 if and only if two Milestones are equal, 1 if and only if the
     *         second Milestone is greater than the first Milestone, -1
     *         otherwise.
     */
    @DomainAPI
    public int compareTo(Milestone other) {
        if (this.getFirstNb() == other.getFirstNb() && this.getSecondNb() == other.getSecondNb()
                && this.getThirdNb() == other.getThirdNb()) {
            return 0;
        }
        if (this.getFirstNb() < other.getFirstNb()) {
            return -1;
        } else if (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() < other.getSecondNb()) {
            return -1;
        } else if (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() >= other.getSecondNb()
                && this.getThirdNb() < other.getThirdNb()) {
            return -1;
        }
        return 1;
    }

    /**
     * Checks if two Milestones are equal.
     *
     * @param other The Milestone to compare.
     * @return True if the Milestones are equal.
     */
    @Override
    @DomainAPI
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof Milestone) {
            Milestone otherID = (Milestone) other;
            return this.compareTo(otherID) == 0;
        }
        return false;
    }

}
