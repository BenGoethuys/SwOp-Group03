/**
 * 
 */
package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public class HealthAlgorithm2 extends HealthAlgorithm {

	private final int HEALTH = 50;
	private final int SATISFACTORY = 100;
	private final int STABLE = 500;
	private final int SERIOUS = 5000;

	@Override
	public boolean isHealthy(Subsystem subsystem) {

		for (Subsystem subs : subsystem.getAllSubsystems()) {
			if (subs.getIndicator().getOrder() < HealthIndicator.SATISFACTORY.getOrder()) {
				return false;
			}
		}

		if (subsystem.getBugImpact() >= HEALTH) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSatisfactory(Subsystem subsystem) {
		for (Subsystem subs : subsystem.getAllSubsystems()) {
			if (subs.getIndicator().getOrder() < HealthIndicator.SATISFACTORY.getOrder()) {
				return false;
			}
		}

		if (subsystem.getBugImpact() >= SATISFACTORY) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isStable(Subsystem subsystem) {
		for (Subsystem subs : subsystem.getAllSubsystems()) {
			if (subs.getIndicator().getOrder() < HealthIndicator.STABLE.getOrder()) {
				return false;
			}
		}

		if (subsystem.getBugImpact() >= STABLE) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSerious(Subsystem subsystem) {
		for (Subsystem subs : subsystem.getAllSubsystems()) {
			if (subs.getIndicator().getOrder() < HealthIndicator.SERIOUS.getOrder()) {
				return false;
			}
		}

		if (subsystem.getBugImpact() >= SERIOUS) {
			return false;
		}

		return true;
	}
}
