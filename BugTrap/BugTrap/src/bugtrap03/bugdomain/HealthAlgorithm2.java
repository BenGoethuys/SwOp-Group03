package bugtrap03.bugdomain;

/**
 * @author Mathias
 */
@DomainAPI
public class HealthAlgorithm2 extends HealthAlgorithm {

    private final int HEALTHY = 50;
    private final int SATISFACTORY = 100;
    private final int STABLE = 500;
    private final int SERIOUS = 5000;

    @DomainAPI
    public HealthAlgorithm2() {
    }

    @Override
    public boolean isHealthy(AbstractSystem as) {
	return checkSubsystem(as, HealthIndicator.SATISFACTORY, HEALTHY);
    }

    @Override
    public boolean isSatisfactory(AbstractSystem as) {
	return checkSubsystem(as, HealthIndicator.SATISFACTORY, SATISFACTORY);
    }

    @Override
    public boolean isStable(AbstractSystem as) {
	return checkSubsystem(as, HealthIndicator.STABLE, STABLE);
    }

    @Override
    public boolean isSerious(AbstractSystem as) {
	return checkSubsystem(as, HealthIndicator.SERIOUS, SERIOUS);
    }

    @Override
    public String toString() {
	return "Algorithm 2";
    }
}
