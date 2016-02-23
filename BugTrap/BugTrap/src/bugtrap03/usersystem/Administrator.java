package bugtrap03.usersystem;

/**
 *
 * @author Admin
 * @version 0.1
 */
public class Administrator extends User {

    /**
     * Create an {@link Administrator} with a specific username, first name, middle name and last
     * name.
     *
     * @param uniqueUsername The username
     * @param firstName The first name
     * @param middleName The middle name
     * @param lastName The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public Administrator(String uniqueUsername, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, middleName, lastName);
    }

    /**
     * Create an {@link Administrator} with a specific username, first name and last
     * name.
     *
     * @param uniqueUsername The username
     * @param firstName The first name
     * @param lastName The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String)
     */
    public Administrator(String uniqueUsername, String firstName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, lastName);
    }

}
