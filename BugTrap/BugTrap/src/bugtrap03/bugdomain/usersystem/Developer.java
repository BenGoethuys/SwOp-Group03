package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.HealthIndicator;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.permission.UserPerm;

import java.util.Arrays;
import purecollections.PList;

/**
 * This class represents a developer in th system
 *
 * @author Group 03
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

    private UserPerm[] permissions = {
            UserPerm.SET_MILESTONE
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
        boolean check = super.hasPermission(perm);
        if (check) {
            return true;
        }
        return Arrays.stream(this.permissions).anyMatch(permission -> permission == perm);
    }

    /**
     * This method checks if this developer has a specific RolePermission for a given project
     *
     * @param perm    The permission to check
     * @param project The project he needs a permission for
     * @return true if this developer has the given role permission in the given project
     */
    @Override
    @DomainAPI
    public boolean hasRolePermission(RolePerm perm, Project project) {
        return project.hasPermission(this, perm);
    }
    
    /** Statistics **/
    
//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public long getNbDuplicateSubmitted() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public long getNbNotABugReportSubmitted() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public long getNbBugReportsSubmitted() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public PList<HealthIndicator> getConditionIndicators() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public double getAvgLinesOfTestsSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addLinesOfTest(long nb) {

    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbTestsSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addTestsSubmitted(long nb) {
    }

//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public long getNbAssignedToClosed() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     *
//     * @return Nothing.
//     * @throws UnsupportedOperationException Always
//     */
//    @Override
//    public long getNbAssignedToUnfinished() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public long getNbPatchesSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addPatchessSubmitted(long nb) {
    }

    /**
     *
     * @return Nothing.
     * @throws UnsupportedOperationException Always
     */
    @Override
    public double getAvgLinesOfPatchesSubmitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Nothing.
     */
    @Override
    public void addLinesOfPatches(long nb) {
    }

}
