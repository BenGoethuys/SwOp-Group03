package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public abstract class HealthAlgorithm {

	public HealthIndicator getIndicator(Subsystem subsystem) {
		if (isHealthy(subsystem)) {
			return HealthIndicator.HEALTY;
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

	public abstract boolean isHealthy(Subsystem subsystem);

	public abstract boolean isSatisfactory(Subsystem subsystem);

	public abstract boolean isStable(Subsystem subsystem);

	public abstract boolean isSerious(Subsystem subsystem);

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
