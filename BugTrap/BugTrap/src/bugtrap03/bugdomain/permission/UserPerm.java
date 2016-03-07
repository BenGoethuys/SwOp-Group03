package bugtrap03.bugdomain.permission;

/**
 * This enum contains objects of Type UserPerm, representing permissions that a user can have
 *
 * @author Ben Goethuys & Vincent Derkinderen
 */
public enum UserPerm {
    CREATE_PROJ,
    ASSIGN_PROJ_LEAD,
    UPDATE_PROJ,
    DELETE_PROJ,
    CREATE_SUBSYS,
    CREATE_BUGREPORT,
    CREATE_COMMENT,
    INSPECT_BUGREPORT;
}
