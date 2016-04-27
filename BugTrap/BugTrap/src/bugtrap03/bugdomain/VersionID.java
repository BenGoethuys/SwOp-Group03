package bugtrap03.bugdomain;

import java.util.Arrays;

/**
 * TODO
 * This class represents a version id consisting out of 3 numbers, separated by
 * a dot. <br>
 * This is an Immutable class.
 * 
 * @author Group 03.
 */
@DomainAPI
public class VersionID implements Comparable<VersionID> {

    private int[] numbers;

    /**
     * TODO
     * Creates a versionID for a project.
     * 
     * @param nb
     */
    public VersionID(int... nb) {
	this.numbers = nb;
    }

    /**
     * TODO
     */
    public VersionID() {
	this(0);
    }

    /**
     * This is a getter for a list of digits of the versionID.
     * 
     * @return A list of digits of the versionID.
     */
    @DomainAPI
    public int[] getNumbers() {
	return this.numbers;
    }

    /**
     * Visual representation of a versionID.
     */
    @Override
    @DomainAPI
    public String toString() {
	String versionID = "";
	int[] numbers = this.getNumbers();
	int max = numbers.length - 1;
	for (int i = 0; i < max; i++) {
	    versionID += Integer.toString(numbers[i]);
	    versionID += ".";
	}
	versionID += Integer.toString(numbers[max]);

	return versionID;
    }

    /**
     * Compares two versionIDs.
     *
     * @return 0 if and only if two versionIDs are equal, 1 if and only if the
     *         second versionID is greater than the first versionID, -1
     *         otherwise.
     */
    @Override
    @DomainAPI
    public int compareTo(VersionID other) {
	int[] numbersThis = this.getNumbers();
	int[] numbersOther = other.getNumbers();
	int lengthThis = numbersThis.length;
	int lengthOther = numbersOther.length;
	int min = Math.min(lengthThis, lengthOther);


	for (int i = 0; i < min; i++) {
	    if (numbersThis[i] < numbersOther[i]) {
		return -1;
	    }
	    if (numbersThis[i] > numbersOther[i]) {
		return 1;
	    }
	}

	if (lengthThis == lengthOther) {
	    return 0;
	} else if (lengthThis > lengthOther) {
	    for (int i = lengthOther; i < lengthThis; i++) {
		if (numbersThis[i] != 0) {
		    return 1;
		}
	    }
	} else {
	    for (int i = lengthThis; i < lengthOther; i++) {
		if (numbersOther[i] != 0) {
		    return -1;
		}
	    }
	}

	return 0;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	for (int i = 0; i < numbers.length; i++) {
	    result += numbers[i];
	}
	result = (prime * result) + prime;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof VersionID)) {
	    return false;
	}
	VersionID other = (VersionID) obj;
	if (!Arrays.equals(numbers, other.numbers)) {
	    return false;
	}
	return true;
    }

    /**
     * Clones a VersionID.
     * 
     * @return A clone of the Version ID.
     */
    @Override
    public VersionID clone() {
	return new VersionID(this.getNumbers());
    }
}
