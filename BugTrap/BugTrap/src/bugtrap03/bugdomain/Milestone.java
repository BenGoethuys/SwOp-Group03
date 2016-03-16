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

}
