package bugtrap03.bugdomain;

import bugtrap03.bugdomain.usersystem.User;

/**
 * An enumeration of health indicators. <br>
 * Order them from most critical to healthy.
 * 
 * @author Mathias
 *
 */
public enum HealthIndicator {
    CRITICAL, SERIOUS, STABLE, SATISFACTORY, HEALTHY;

    HealthIndicator() {
    }

    public HealthIndicator[] getAllHealthIndicators(User user) {
	
	return null;
    }
}
