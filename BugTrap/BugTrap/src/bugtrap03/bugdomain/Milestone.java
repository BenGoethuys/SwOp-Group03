package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public class Milestone extends VersionID {

    /**
     * 
     * @param first
     * @param second
     * @param third
     */
    public Milestone(int first, int second, int third) {
        super(first, second, third);
    }

    /**
     * 
     * @param first
     * @param second
     */
    public Milestone(int first, int second) {
        super(first, second);
    }

    /**
     * 
     * @param first
     */
    public Milestone(int first) {
        super(first);
    }

    @Override
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
