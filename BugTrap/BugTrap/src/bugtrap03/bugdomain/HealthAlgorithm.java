package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
@DomainAPI
public abstract class HealthAlgorithm {

    /**
     * A method to calculate the {@link HealthIndicator} of the {@link Subsystem}.
     * 
     * @param as The AbstractSystem to calculate the health indicator.
     * @return The health indicator of the subsystem.
     */
    @DomainAPI
    public HealthIndicator getIndicator(AbstractSystem as) {
	if (isHealthy(as)) {
	    return HealthIndicator.HEALTHY;
	}
	if (isSatisfactory(as)) {
	    return HealthIndicator.SATISFACTORY;
	}
	if (isStable(as)) {
	    return HealthIndicator.STABLE;
	}
	if (isSerious(as)) {
	    return HealthIndicator.SERIOUS;
	}
	return HealthIndicator.CRITICAL;
    }

    /**
     * Checks if the given subsystem is healthy.
     * 
     * @param as The AbstractSystem to check.
     * @return {@link HealthIndicator#HEALTHY} if the subsystem is healthy.
     */
    public abstract boolean isHealthy(AbstractSystem as);

    /**
     * Checks if the given subsystem is satisfactory.
     * 
     * @param as The AbstractSystem to check.
     * @return {@link HealthIndicator#SATISFACTORY} if the subsystem is satisfactory.
     */
    public abstract boolean isSatisfactory(AbstractSystem as);

    /**
     * Checks if the given subsystem is stable.
     * 
     * @param as The AbstractSystem to check.
     * @return {@link HealthIndicator#STABLE} if the subsystem is stable.
     */
    public abstract boolean isStable(AbstractSystem as);

    /**
     * Checks if the given subsystem is serious.
     * 
     * @param as The AbstractSystem to check.
     * @return {@link HealthIndicator#SERIOUS} if the subsystem is serious.
     */
    public abstract boolean isSerious(AbstractSystem as);

    /**
     * Checks if the indicators of the subsystems of the given AbstractSystem are greater than the given HealthIndicator and if the BugImpact is smaller than
     * the given number.
     * 
     * @param as The AbstractSystem to check.
     * @param hi The minimum HealthIndicator of the subsystems
     * @param number The maximum total BugImpact
     * @return True if the conditions are satisfied
     */
    public boolean checkSubsystem(AbstractSystem as, HealthIndicator hi, int number) {
	for (Subsystem subs : as.getAllSubsystems()) {
	    // if (subs.getIndicator(this).getRank() < hi.getRank()) {
	    if (subs.getIndicator(this).ordinal() < hi.ordinal()) {
		return false;
	    }
	}

	if (as instanceof Subsystem) {
	    if (as.getBugImpact() >= number) {
		return false;
	    }
	}
	if (as instanceof Project) {
	    double total = 0;
	    for (Subsystem subs : as.getAllSubsystems()) {
		total += subs.getBugImpact();
	    }
	    if (total >= number) {
		return false;
	    }
	}
	return true;
    }
    
    @Override
    public abstract String toString();
}
