/**
 * 
 */
package bugtrap03.bugdomain;

/**
 * @author Ben
 *
 */
public enum Tag {
	Closed, New, Assigned, NotABug, UnderReview, Resolved, Duplicate;
	
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
        if (this == Tag.Closed || this == Tag.Duplicate || this == Tag.NotABug){
        	return false;
        }
        // will be null in initialisation: only moment Tag.New can be assigned
        if (tag == Tag.New){
        	return false;
        }
        if (tag == Tag.Assigned && (this != Tag.New && this != Tag.UnderReview)){
        	return false;
        }
        if (tag == Tag.UnderReview && this != Tag.Assigned){
        	return false;
        }
        if (tag == Tag.Resolved && this != Tag.UnderReview){
        	return false;
        }
        if (tag == Tag.Closed && (this != Tag.Resolved && this != Tag.UnderReview)){
        	return false;
        }
        return true;
    }
}
