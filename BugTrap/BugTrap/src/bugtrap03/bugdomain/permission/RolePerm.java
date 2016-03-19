package bugtrap03.bugdomain.permission;

import bugtrap03.bugdomain.DomainAPI;

/**
 * This enum contains objects of Type RolePerm, representing permissions that a role can have
 *
 * @author Group 03
 */
@DomainAPI
public enum RolePerm {
    // for project stuff:
    //FIXME: is assign_dev_project redundant?
    ASSIGN_DEV_PROJECT,
    ASSIGN_TEST_ROLE,
    ASSIGN_PROG_ROLE,

    // for bug report stuff:
    ASSIGN_DEV_BUG_REPORT,
    ADD_TEST,
    ADD_PATCH,
    SELECT_PATCH,
    SET_TAG_ASSIGNED,
    SET_TAG_DUPLICATE,
    SET_TAG_NOT_A_BUG,

    // nobody has permission:
    SPECIAL;
}
