package bugtrap03.bugdomain;

/**
 * @author Mathias
 *
 */
public enum HealthIndicator {
	HEALTY(5),
	SATISFACTORY(4),
	STABLE(3),
	SERIOUS(2),
	CRITICAL(1);
	
	private int order;

	/**
	 * 
	 * @param order
	 */
	HealthIndicator(int order) {
		this.order = order;
	}

	/**
	 * 
	 * @return
	 */
	@DomainAPI
	public int getOrder() {
		return this.order;
	}
}
