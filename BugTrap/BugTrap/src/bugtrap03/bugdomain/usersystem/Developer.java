package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.RolePerm;

/**
 * This class represents a developer in th system
 * @author Vincent Derkinderen
 * @version 0.1
 */
@DomainAPI
public class Developer extends Issuer {

    /**
     * Create a {@link Developer} with a specific username, first name, middle name and last
     * name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param middleName     The middle name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of Issuer throws it.
     * @see Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public Developer(String uniqueUsername, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, middleName, lastName);
    }

    /**
     * Create a {@link Developer} with a specific username, first name and last
     * name.
     *
     * @param uniqueUsername The username
     * @param firstName      The first name
     * @param lastName       The last name
     * @throws IllegalArgumentException When the creator of Issuer throws it.
     * @see Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String)
     */
    public Developer(String uniqueUsername, String firstName, String lastName) throws IllegalArgumentException {
        super(uniqueUsername, firstName, lastName);
    }

    /**
     * This method checks if this developer has a specific RolePermission for a given project
     *
     * @param perm      The permission to check
     * @param project   The project he needs a permission for
     * @return
     */
    @Override
    @DomainAPI
    public boolean hasRolePermission(RolePerm perm, Project project) {
        return project.hasPermission(this, perm);
    }

}
