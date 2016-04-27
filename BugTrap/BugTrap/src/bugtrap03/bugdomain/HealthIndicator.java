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
	
	private int rank;

	/**
	 * 
	 * @param order
	 */
	HealthIndicator(int rank) {
		this.rank = rank;
	}

	/**
	 * 
	 * @return
	 */
	@DomainAPI
	public int getRank() {
		return this.rank;
	}
}
