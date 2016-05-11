package bugtrap03.bugdomain;

/**
 * An enumeration of health indicators. <br>
 * Order them from most critical to healthy.
 *
 * @author Mathias
 */
@DomainAPI
public enum HealthIndicator {
    CRITICAL, SERIOUS, STABLE, SATISFACTORY, HEALTHY;

    HealthIndicator() {
    }

}
