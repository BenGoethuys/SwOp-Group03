package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.UserPerm;

import java.util.Arrays;

/**
 * This class represents a User in the system
 * @author Vincent Derkinderen
 * @version 0.1
 */
@DomainAPI
public class Issuer extends User {

    /**
     * Create an {@link Issuer} with a specific username, first name, middle name and last name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param middleName     The middle name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public Issuer(String uniqueUsername, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, middleName, lastName);
    }

    /**
     * Create an {@link Issuer} with a specific username, first name and last name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String)
     */
    public Issuer(String uniqueUsername, String firstName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, lastName);
    }

    private UserPerm[] permissions = {
            UserPerm.CREATE_BUGREPORT, UserPerm.CREATE_COMMENT
    };

    /**
     * Check if this {@link Administrator} has the given {@link UserPerm}.
     *
     * @param perm The userPermission to check for.
     * @return Whether this has the permission.
     */
    @Override
    @DomainAPI
    public boolean hasPermission(UserPerm perm) {
        return Arrays.stream(this.permissions).anyMatch(permission -> permission == perm);
    }

}
