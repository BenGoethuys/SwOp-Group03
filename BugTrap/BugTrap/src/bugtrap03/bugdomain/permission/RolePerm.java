package bugtrap03.bugdomain.permission;

import bugtrap03.bugdomain.DomainAPI;

/**
 * This enum contains objects of Type RolePerm, representing permissions that a role can have
 *
 * @author Group 03
 */
@DomainAPI
public enum RolePerm {
    ASSIGN_DEV_PROJECT,
    ASSIGN_DEV_BUGREPORT,

    ASSIGN_TEST_ROLE,
    ASSIGN_PROG_ROLE,

    SET_TAG_CLOSED,
    SET_TAG_DUPLICATE,
    SET_TAG_UNDER_REVIEW,
    SET_TAG_RESOLVED,
    SET_TAG_NOT_A_BUG,
    SPECIAL; // nobody has permission
}
