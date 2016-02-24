/**
 * 
 */
package bugtrap03.bugdomain;

/**
 * @author Ben
 *
 */
public enum Tag {
	CLOSED, NEW, ASSIGNED, NOT_A_BUG, UNDER_REVIEW, RESOLVED, DUPLICATE;
	
	/**
     * This method check if the given tag is valid for the bug report
     *
     * @param tag the tag to check
     * @return true if the tag is a valid tag
     */
    public boolean isValidTag(Tag tag) {
        if (tag == null) {
            return false;
        }
        // Closed, Duplicate, NotABug is unchangeable
        if (this == Tag.CLOSED || this == Tag.DUPLICATE || this == Tag.NOT_A_BUG){
        	return false;
        }
        // will be null in initialisation: only moment Tag.New can be assigned
        if (tag == Tag.NEW){
        	return false;
        }
        if (tag == Tag.ASSIGNED && (this != Tag.NEW && this != Tag.UNDER_REVIEW)){
        	return false;
        }
        if (tag == Tag.UNDER_REVIEW && this != Tag.ASSIGNED){
        	return false;
        }
        if (tag == Tag.RESOLVED && this != Tag.UNDER_REVIEW){
        	return false;
        }
        if (tag == Tag.CLOSED && (this != Tag.RESOLVED && this != Tag.UNDER_REVIEW)){
        	return false;
        }
        return true;
    }
}
