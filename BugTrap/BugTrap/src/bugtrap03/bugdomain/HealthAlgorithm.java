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
    public HealthIndicator getIndicator(Subsystem subsystem) {
	if (isHealthy(subsystem)) {
	    return HealthIndicator.HEALTHY;
	}
	if (isSatisfactory(subsystem)) {
	    return HealthIndicator.SATISFACTORY;
	}
	if (isStable(subsystem)) {
	    return HealthIndicator.STABLE;
	}
	if (isSerious(subsystem)) {
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
    public abstract boolean isHealthy(Subsystem subsystem);

    /**
     * Checks if the given subsystem is satisfactory.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#SATISFACTORY} if the subsystem is satisfactory.
     */
    public abstract boolean isSatisfactory(Subsystem subsystem);

    /**
     * Checks if the given subsystem is stable.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#STABLE} if the subsystem is stable.
     */
    public abstract boolean isStable(Subsystem subsystem);

    /**
     * Checks if the given subsystem is serious.
     * 
     * @param subsystem The subsystem to check.
     * @return {@link HealthIndicator#SERIOUS} if the subsystem is serious.
     */
    public abstract boolean isSerious(Subsystem subsystem);

    /**
     * TODO
     * 
     * @param subsystem
     * @param hi
     * @param number
     * @return
     */
    public boolean checkSubsystem(Subsystem subsystem, HealthIndicator hi, int number) {
	for (Subsystem subs : subsystem.getAllSubsystems()) {
	    if (subs.getIndicator(this).getRank() < hi.getRank()) {
		return false;
	    }
	}

	if (subsystem.getBugImpact() >= number) {
	    return false;
	}

	return true;
    }
}
