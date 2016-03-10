package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.UserPerm;

import java.util.Arrays;

/**
 * This class represents an administrator in the system
 *
 * @author Group 03
 * @version 0.1
 */
@DomainAPI
public class Administrator extends User {

    /**
     * Create an {@link Administrator} with a specific username, first name,
     * middle name and last name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param middleName     The middle name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public Administrator(String uniqueUsername, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, middleName, lastName);
    }

    /**
     * Create an {@link Administrator} with a specific username, first name and
     * last name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of User throws it.
     * @see User#User(java.lang.String, java.lang.String, java.lang.String)
     */
    public Administrator(String uniqueUsername, String firstName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, lastName);
    }

    private UserPerm[] permissions = {
            UserPerm.CREATE_PROJ, UserPerm.ASSIGN_PROJ_LEAD, UserPerm.UPDATE_PROJ, UserPerm.DELETE_PROJ, UserPerm.CREATE_SUBSYS
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
