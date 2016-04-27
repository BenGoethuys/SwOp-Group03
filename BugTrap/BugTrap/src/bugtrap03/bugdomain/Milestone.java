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
     * @param nb The numbers of the milestone.
     * 
     * @see VersionID#VersionID(int...)
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
