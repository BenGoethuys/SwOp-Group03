package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public abstract class HealthAlgorithm {
	
	public HealthIndicator getIndicator() {
		
		return null;
	}
	
	
	public abstract boolean isHealthy();
	public abstract boolean isSatisfactory();
	public abstract boolean isStable();
	public abstract boolean isSerious();
	public abstract boolean isCritical();
	
	
}
