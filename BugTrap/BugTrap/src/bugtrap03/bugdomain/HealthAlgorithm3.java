/**
 * 
 */
package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public class HealthAlgorithm3 extends HealthAlgorithm {

	private final int HEALTH = 10;
	private final int SATISFACTORY = 100;
	private final int STABLE = 250;
	private final int SERIOUS = 500;

	@Override
	public boolean isHealthy(Subsystem subsystem) {
		return checkSubsystem(this, subsystem, HealthIndicator.HEALTY, HEALTH);
	}

	@Override
	public boolean isSatisfactory(Subsystem subsystem) {
		return checkSubsystem(this, subsystem, HealthIndicator.STABLE, SATISFACTORY);
	}

	@Override
	public boolean isStable(Subsystem subsystem) {
		return checkSubsystem(this, subsystem, HealthIndicator.STABLE, STABLE);
	}

	@Override
	public boolean isSerious(Subsystem subsystem) {
		return checkSubsystem(this, subsystem, HealthIndicator.SERIOUS, SERIOUS);
	}

}
