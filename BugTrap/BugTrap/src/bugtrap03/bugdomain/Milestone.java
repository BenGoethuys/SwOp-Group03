package bugtrap03.bugdomain;

/**
 * This extends a {@link VersionID} and adds a 'M' in front of the String form.
 * <br> This is an Immutable class.
 * 
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
    public Milestone(int... nb) {
        super(nb);
    }

    /**
     * Creates a default Milestone.
     */
    @DomainAPI
    public Milestone() {
	super();
    }

    /**
     * Visual representation of a Milestone.
     */
    @Override
    @DomainAPI
    public String toString() {
	String milestone = "M";
	int[] numbers = this.getNumbers();
	int max = numbers.length - 1;
	for (int i = 0; i < max; i++) {
	    milestone += Integer.toString(numbers[i]);
	    milestone += ".";
	}
	milestone += Integer.toString(numbers[max]);

	return milestone;
    }
}
