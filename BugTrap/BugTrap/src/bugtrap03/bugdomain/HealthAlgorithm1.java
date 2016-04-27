package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public class HealthAlgorithm1 extends HealthAlgorithm {

	private final int HEALTHY = 50;
	private final int SATISFACTORY = 100;
	private final int STABLE = 500;
	private final int SERIOUS = 1000;

	@Override
	public boolean isHealthy(Subsystem subsystem) {
		return checkSubsystem(subsystem, HealthIndicator.HEALTHY, HEALTHY);
	}

	@Override
	public boolean isSatisfactory(Subsystem subsystem) {
		return checkSubsystem(subsystem, HealthIndicator.SATISFACTORY, SATISFACTORY);
	}

	@Override
	public boolean isStable(Subsystem subsystem) {
		return checkSubsystem(subsystem, HealthIndicator.STABLE, STABLE);
	}

	@Override
	public boolean isSerious(Subsystem subsystem) {
		return checkSubsystem(subsystem, HealthIndicator.SERIOUS, SERIOUS);
	}
}
