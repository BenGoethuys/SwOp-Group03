package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.RolePerm;

import java.util.Arrays;

/**
 * This class represents roles in the system
 *
 * @author Group 03
 * @version 1.0
 */
@DomainAPI
public enum Role {

    LEAD(RolePerm.SPECIAL, RolePerm.SET_TAG_NOT_A_BUG, RolePerm.SET_TAG_DUPLICATE, RolePerm.SELECT_PATCH,
            RolePerm.SET_TAG_ASSIGNED, RolePerm.ASSIGN_DEV_PROJECT, RolePerm.ASSIGN_DEV_BUG_REPORT,
            RolePerm.ASSIGN_PROG_ROLE, RolePerm.ASSIGN_TEST_ROLE),
    TESTER(RolePerm.ASSIGN_TEST_ROLE, RolePerm.ASSIGN_DEV_BUG_REPORT, RolePerm.ADD_TEST),
    PROGRAMMER(RolePerm.ASSIGN_PROG_ROLE, RolePerm.ADD_PATCH);

    /**
     * A Role with a certain permission required to assign someone this role and
     * a list of permissions users of this role have.
     *
     * @param neededPerm The needed permission to assign this role.
     * @param perms      The permissions that come with this role.
     */
    Role(RolePerm neededPerm, RolePerm... perms) {
        this.neededPerm = neededPerm;
        this.permissions = perms;
    }

    private RolePerm neededPerm;
    private RolePerm[] permissions;

    /**
     * Check if this role comes with a certain permission.
     *
     * @param perm The permission to check for.
     * @return True if the role has the permission.
     */
    @DomainAPI
    public boolean hasPermission(RolePerm perm) {
        if (perm == RolePerm.SPECIAL) {
            return false;
        }
        return Arrays.stream(this.permissions).anyMatch(u -> u == perm);
    }

    /**
     * Get the permission required to assign someone this role.
     *
     * @return The permission required to assign this role.
     */
    @DomainAPI
    public RolePerm getNeededPerm() {
        return this.neededPerm;
    }
}
