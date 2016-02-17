package usersystem;

/**
 * A {User} can be identified by his/her username. The uniqueness of this
 * username is not guaranteed within this class.
 *
 * @author Admin
 * @version 1.0
 */
public class User {

    /**
     * Create a {@link User} with a username, a firstName, middelName and
     * lastName.
     *
     * @param username The unique username of this user.
     * @param firstName The first name of this user.
     * @param middleName The middle name of this user.
     * @param lastName The last name of this user.
     *
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see isValidUsername(String username)
     * @see isValidFirstName(String firstName)
     * @see isValidMiddleName(String middleName)
     * @see isValidLastName(String lastName)
     */
    public User(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        setUsername(username);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    /**
     * Create a {@link User} with a username, a firstName and lastName.
     *
     * @param username The unique username of this user.
     * @param firstName The first name of this user.
     * @param lastName The last name of this user.
     *
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see isValidUsername(String username)
     * @see isValidFirstName(String firstName)
     * @see isValidLastName(String lastName)
     */
    public User(String username, String firstName, String lastName) {
        this(username, firstName, "", lastName);
    }

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Get the username of this {@link User}
     *
     * @return The username of this user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set username as the username of this user, if it is valid.
     *
     * @param username The new username.
     * @throws IllegalArgumentException When the username is not valid.
     * @see isValidUsername(String username)
     */
    private void setUsername(String username) throws IllegalArgumentException {
        if (isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("username:" + username + " is not a valid username.");
        }
    }

    /**
     * Check if username is a valid username. Uniqueness is not checked.
     *
     * @param username The username to check.
     * @return Whether the username is valid.
     */
    public boolean isValidUsername(String username) {
        return (username != null && !username.equalsIgnoreCase(""));
    }

    /**
     * Get the first name of this {@link User}
     *
     * @return The first name of this user.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set firstName as the first name of this user, if it is valid.
     *
     * @param firstName The new first name.
     * @throws IllegalArgumentException When the firstName is not valid.
     * @see isValidFirstName(String firstName)
     */
    private void setFirstName(String firstName) throws IllegalArgumentException {
        if (isValidFirstName(firstName)) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("First name:" + firstName + " is not a valid first name.");
        }
    }

    /**
     * Check if firstName is a valid first name.
     *
     * @param firstName The first name to check.
     * @return Whether the first name is valid.
     */
    public boolean isValidFirstName(String firstName) {
        return (firstName != null && !firstName.equalsIgnoreCase(""));
    }

    /**
     * Get the middle name of this {@link User}
     *
     * @return The middle name of this user.
     */
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * Set middleName as the middle name of this user, if it is valid.
     *
     * @param middleName The new middle name.
     * @throws IllegalArgumentException When the middleName is not valid.
     * @see isValidmiddleName(String middleName)
     */
    private void setMiddleName(String middleName) {
        if (isValidMiddleName(middleName)) {
            this.middleName = middleName;
        } else {
            throw new IllegalArgumentException("Middle name:" + middleName + " is not a valid middle name.");
        }
    }

    /**
     * Check if middle name is a valid middle name.
     *
     * @param middleName The middle name to check.
     * @return Whether the middle name is valid.
     */
    public boolean isValidMiddleName(String middleName) {
        return middleName != null;
    }

    /**
     * Get the last name of this {@link User}
     *
     * @return The last name of this user.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set lastName as the last name of this user, if it is valid.
     *
     * @param lastName The new last name.
     * @throws IllegalArgumentException When the last name is not valid.
     * @see isValidLastName(String lastName)
     */
    private void setLastName(String lastName) {
        if (isValidLastName(lastName)) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Middle name:" + middleName + " is not a valid middle name.");
        }
    }

    /**
     * Check if lastName is a valid last name.
     *
     * @param lastName The last name to check.
     * @return Whether the lastName is valid.
     */
    public boolean isValidLastName(String lastName) {
        return (lastName != null && !lastName.equals(""));
    }
}
