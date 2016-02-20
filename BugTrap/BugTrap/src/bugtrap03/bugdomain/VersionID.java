package bugtrap03.bugdomain;

public class VersionID implements Comparable<VersionID> {

	private int firstNb;
	private int secondNb;
	private int thirdNb;

	/**
	 * Creates a versionID for a project.
	 * 
	 * @param nb1 The first digit of the versionID.
	 * @param nb2 The second digit of the versionID.
	 * @param nb3 The third digit of the versionID.
	 */
	public VersionID(int nb1, int nb2, int nb3) {
		setFirstNb(nb1);
		setSecondNb(nb2);
		setThirdNb(nb3);
	}

	/**
	 * This is a getter for the first digit of the versionID.
	 * 
	 * @return The first digit of the versionID.
	 */
	public int getFirstNb() {
		return firstNb;
	}

	/**
	 * Sets the first digit of the versionID on the given number.
	 * 
	 * @param firstNb The first digit of the versionID.
	 */
	private void setFirstNb(int firstNb) {
		if (firstNb != 0) {
			this.firstNb = firstNb;
		} else {
			throw new IllegalArgumentException("The fist digit in the versionID must be greather than 0.");
		}
	}

	/**
	 * This is a getter for the second digit of the versionID.
	 * 
	 * @return The second digit of the versionID.
	 */
	public int getSecondNb() {
		return secondNb;
	}

	/**
	 * Sets the second digit of the versionID on the given number.
	 * 
	 * @param secondNb The second digit of the versionID.
	 */
	private void setSecondNb(int secondNb) {
		this.secondNb = secondNb;
	}

	/**
	 * This is a getter for the third digit of the versionID.
	 * 
	 * @return The third digit of the versionID.
	 */
	public int getThirdNb() {
		return thirdNb;
	}

	/**
	 * Sets the third digit of the versionID on the given number.
	 * 
	 * @param thirdNb The third digit of the versionID.
	 */
	private void setThirdNb(int thirdNb) {
		this.thirdNb = thirdNb;
	}

	/**
	 * Visual representation of a versionID.
	 */
	@Override
	public String toString() {
		return firstNb + "." + secondNb + "." + thirdNb;
	}

	/**
	 * Compares two versionIDs.
	 * 
	 * @return 0 if and only if two versionIDs are equal, 1 if and only if the
	 *         second versionID is greater than the first versionID, -1
	 *         otherwise.
	 * 
	 */
	@Override
	public int compareTo(VersionID other) {
		if (this.getFirstNb() == other.getFirstNb() && this.getSecondNb() == other.getSecondNb()
				&& this.getThirdNb() == other.getThirdNb()) {
			return 0;
		}
		if ((this.getFirstNb() < other.getFirstNb())
				|| (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() < other.getSecondNb())
				|| (this.getFirstNb() >= other.getFirstNb() && this.getSecondNb() >= other.getSecondNb()
						&& this.getThirdNb() < other.getThirdNb())) {
			return -1;
		}
		return 1;
	}
}