package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.notificationdomain.mailboxes.Mailbox;

import java.util.HashSet;
import java.util.Objects;

/**
 * A User can be identified by his/her username. The uniqueness of this username
 * is guaranteed by checking with all other ever created Users.
 *
 * @author Group 03
 * @version 1.1
 */
@DomainAPI
public abstract class User {

    /**
     * Create a {@link User} with a username, a firstName, middelName and
     * lastName.
     *
     * @param username   The unique username of this user.
     * @param firstName  The first name of this user.
     * @param middleName The middle name of this user.
     * @param lastName   The last name of this user.
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see #isValidUsername(String username)
     * @see #isValidFirstName(String firstName)
     * @see #isValidMiddleName(String middleName)
     * @see #isValidLastName(String lastName)
     * @see #isValidMailbox(Mailbox)
     */
    public User(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        setUsername(username);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setMailbox(new Mailbox());
        stats = new Statistics();
    }

    /**
     * Create a {@link User} with a username, a firstName and lastName.
     *
     * @param username  The unique username of this user.
     * @param firstName The first name of this user.
     * @param lastName  The last name of this user.
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see #isValidUsername(String username)
     * @see #isValidFirstName(String firstName)
     * @see #isValidLastName(String lastName)
     */
    public User(String username, String firstName, String lastName) throws IllegalArgumentException {
        this(username, firstName, "", lastName);
    }

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private Mailbox mailbox;
    
    private Statistics stats;

    private static HashSet<String> takenUsernames = new HashSet<String>();

    /**
     * Get the username of this {@link User}
     *
     * @return The username of this user.
     */
    @DomainAPI
    public String getUsername() {
        return this.username;
    }

    /**
     * Set username as the username of this user, if it is valid.
     *
     * @param username The new username.
     * @throws IllegalArgumentException When the username is not valid.
     * @see #isValidUsername(String username)
     */
    private void setUsername(String username) throws IllegalArgumentException {
        if (isValidUsername(username)) {
            this.username = username;
            takenUsernames.add(username); //not concurrent
        } else {
            throw new IllegalArgumentException("username:" + username + " is not a valid username.");
        }
    }

    /**
     * Set the mailbox of this user to the given mailbox
     *
     * @param mailbox The new Mailbox.
     * @throws IllegalArgumentException When the mailbox is not valid.
     * @see #isValidMailbox(Mailbox)
     */
    private void setMailbox(Mailbox mailbox) throws IllegalArgumentException {
        if (isValidMailbox(mailbox)){
            this.mailbox = mailbox;
        } else {
            throw new IllegalArgumentException("Invalid mailbox");
        }
    }

    /**
     * Checks the validity of a given mailbox.
     *
     * @param mailbox The mailbox to check.
     * @return True if the mailbox is not null;
     */
    @DomainAPI
    public Boolean isValidMailbox(Mailbox mailbox){
        if (mailbox == null){
            return false;
        }
        return true;
    }

    /**
     * Gets the mailbox of this user.
     *
     * @return the Mailbox of this user.
     */
    @DomainAPI
    public Mailbox getMailbox(){
        return this.mailbox;
    }

    /**
     * Check if username is a valid username. Uniqueness is checked with all
     * ever created Users.
     *
     * @param username The username to check.
     * @return Whether the username is valid. False when null.
     */
    @DomainAPI
    public boolean isValidUsername(String username) {
        return (username != null && !username.equalsIgnoreCase("") && !takenUsernames.contains(username) && !username.matches("[0-9]+"));
    }

    /**
     * Get the first name of this {@link User}
     *
     * @return The first name of this user.
     */
    @DomainAPI
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set firstName as the first name of this user, if it is valid.
     *
     * @param firstName The new first name.
     * @throws IllegalArgumentException When the firstName is not valid.
     * @see #isValidFirstName(String firstName)
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
     * @return Whether the first name is valid. False when null or equal to "".
     */
    @DomainAPI
    public boolean isValidFirstName(String firstName) {
        return (firstName != null && !firstName.equalsIgnoreCase(""));
    }

    /**
     * Get the middle name of this {@link User}
     *
     * @return The middle name of this user.
     */
    @DomainAPI
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * Set middleName as the middle name of this user, if it is valid.
     *
     * @param middleName The new middle name.
     * @throws IllegalArgumentException When the middleName is not valid.
     * @see #isValidMiddleName(String middleName)
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
     * @return Whether the middle name is valid. False when null.
     */
    @DomainAPI
    public boolean isValidMiddleName(String middleName) {
        return middleName != null;
    }

    /**
     * Get the last name of this {@link User}
     *
     * @return The last name of this user.
     */
    @DomainAPI
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set lastName as the last name of this user, if it is valid.
     *
     * @param lastName The new last name.
     * @throws IllegalArgumentException When the last name is not valid.
     * @see #isValidLastName(String lastName)
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
     * @return Whether the lastName is valid. False when null or equal to "".
     */
    @DomainAPI
    public boolean isValidLastName(String lastName) {
        return (lastName != null && !lastName.equals(""));
    }

    /**
     * Get the full name of this {@link User}. This means the first, middle and
     * last name in that order.
     *
     * @return The full name of this user.
     * @see #getFirstName()
     * @see #getMiddleName()
     * @see #getLastName()
     */
    @DomainAPI
    public String getFullName() {
        if (middleName.equals("")) {
            return this.firstName + " " + this.lastName;
        } else {
            return this.firstName + " " + this.middleName + " " + this.lastName;
        }
    }

    /**
     * Checks if two Users are equal.
     *
     * @param other The User to compare.
     * @return True if the Users are equal concerning the username, firstname,
     * middlename and lastname.
     */
    @Override
    @DomainAPI
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other instanceof User) {
            User otherUser = (User) other;
            return otherUser.getFirstName().equals(getFirstName()) &&
                    otherUser.getUsername().equals(getUsername()) &&
                    otherUser.getMiddleName().equals(getMiddleName()) &&
                    otherUser.getLastName().equals(getLastName());
        }
        return false;
    }

    @Override
    @DomainAPI
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.username);
        return hash;
    }

    /**
     * Check if this {@link User} has the given {@link UserPerm}.
     *
     * @param perm The userPermission to check for.
     * @return Whether this has the permission.
     */
    @DomainAPI
    public boolean hasPermission(UserPerm perm) {
        return false;
    }

    /**
     * Check if this {@link User} has the given {@link RolePerm} on a certain
     * project.
     *
     * @param perm    The rolePermission to check for.
     * @param project The project to check for.
     * @return Whether this has the permission perm for project.
     */
    @DomainAPI
    public boolean hasRolePermission(RolePerm perm, Project project) {
        return false;
    }
    
    /**
     * Get the object that holds the statistics for this user.
     * @return The {@link Statistics} that hold the current stats of this user. (Never null)
     */
    @DomainAPI
    public Statistics getStats() {
        return stats;
    }
    
    /**
     * Set the current statistics to the given statistics.
     * 
     * @param stats The statistics to set.
     * @throws IllegalArgumentException When stats == null
     */
    public void setStats(Statistics stats) throws IllegalArgumentException {
        if(stats != null) {
            throw new IllegalArgumentException("The given statistics must not be null.");
        }
        
        this.stats = stats;
    }
}
