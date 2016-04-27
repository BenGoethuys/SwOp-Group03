package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public abstract class HealthAlgorithm {

    /**
     * A method to calculate the {@link HealthIndicator} of the {@link Subsystem}.
     * 
     * @param subsystem The subsystem to calculate the health indicator.
     * @return The health indicator of the subsystem.
     */
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
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#HEALTY} if the subsystem is healthy.
     */
    public abstract boolean isHealthy(AbstractSystem as);

    /**
     * Checks if the given subsystem is satisfactory.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#SATISFACTORY} if the subsystem is satisfactory.
     */
    public abstract boolean isSatisfactory(AbstractSystem as);

    /**
     * Checks if the given subsystem is stable.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#STABLE} if the subsystem is stable.
     */
    public abstract boolean isStable(AbstractSystem as);

    /**
     * Checks if the given subsystem is serious.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#SERIOUS} if the subsystem is serious.
     */
    public abstract boolean isSerious(AbstractSystem as);

    /**
     * TODO
     * 
     * @param subsystem
     * @param hi
     * @param number
     * @return
     */
    public boolean checkSubsystem(AbstractSystem as, HealthIndicator hi, int number) {
	for (Subsystem subs : as.getAllSubsystems()) {
	    if (subs.getIndicator(this).getRank() < hi.getRank()) {
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
}
