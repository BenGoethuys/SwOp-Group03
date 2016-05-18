package bugtrap03.bugdomain;

/**
 * This class represents 3th health algorithm
 *
 * @author Group 03
 */
@DomainAPI
public class HealthAlgorithm3 extends HealthAlgorithm {

    private final int HEALTHY = 10;
    private final int SATISFACTORY = 100;
    private final int STABLE = 250;
    private final int SERIOUS = 500;

    @DomainAPI
    public HealthAlgorithm3() {
    }
    
    @Override
    public boolean isHealthy(AbstractSystem as) {
        return checkSubsystem(as, HealthIndicator.HEALTHY, HEALTHY);
    }

    @Override
    public boolean isSatisfactory(AbstractSystem as) {
        return checkSubsystem(as, HealthIndicator.STABLE, SATISFACTORY);
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
        return "Algorithm 3";
    }
}
