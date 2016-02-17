package bugdomain;

public class VersionID implements Comparable<VersionID> {

	private int firstNb;
	private int secondNb;
	private int thirdNb;

	/**
	 * Creates a versionID for a project.
	 * 
	 * @param nb1
	 *            The first digit of the versionID.
	 * @param nb2
	 *            The second digit of the versionID.
	 * @param nb3
	 *            The third digit of the versionID.
	 */
	public VersionID(int nb1, int nb2, int nb3) {
		setFirstNb(nb1);
		setSecondNb(nb2);
		setThirdNb(nb3);
	}

	/**
	 * 
	 * @return The first digit of the versionID
	 */
	public int getFirstNb() {
		return firstNb;
	}

	/**
	 * 
	 * @param firstNb
	 */
	private void setFirstNb(int firstNb) {
		if (firstNb != 0) {
			this.firstNb = firstNb;
		} else {
			throw new IllegalArgumentException("The fist digit in the versionID must be greather than 0.");
		}
	}

	public int getSecondNb() {
		return secondNb;
	}

	private void setSecondNb(int secondNb) {
		this.secondNb = secondNb;
	}

	public int getThirdNb() {
		return thirdNb;
	}

	private void setThirdNb(int thirdNb) {
		this.thirdNb = thirdNb;
	}

	@Override
	public String toString() {
		return firstNb + "." + secondNb + "." + thirdNb;
	}

	@Override
	public int compareTo(VersionID other) {
		if (this.getFirstNb() == other.getFirstNb() && this.getSecondNb() == other.getSecondNb()
				&& this.getThirdNb() == other.getThirdNb()) {
			return 0;
		}
		if (this.getFirstNb() < other.getFirstNb()) {
			return -1;
		} else if (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() < other.getSecondNb()) {
			return -1;
		} else if (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() >= other.getSecondNb()
				&& this.getThirdNb() < other.getThirdNb()) {
			return -1;
		}

		return 1;
	}
}
