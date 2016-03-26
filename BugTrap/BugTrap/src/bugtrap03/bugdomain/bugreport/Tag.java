package bugtrap03.bugdomain.bugreport;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.RolePerm;

/**
 * This enum contains objects of Type Tag
 *
 * @author Ben Goethuys & Vincent Derkinderen
 */
@DomainAPI
public enum Tag {
    NEW(RolePerm.SPECIAL),
    ASSIGNED(RolePerm.SET_TAG_ASSIGNED),
    NOT_A_BUG(RolePerm.SET_TAG_NOT_A_BUG),
    UNDER_REVIEW(RolePerm.SPECIAL),
    CLOSED(RolePerm.SPECIAL),
    RESOLVED(RolePerm.SPECIAL),
    DUPLICATE(RolePerm.SET_TAG_DUPLICATE);

    /**
     * General constructor for objects of Type Tag
     *
     * @param neededPerm the needed RolePerm to set this tag
     * @see RolePerm
     */
    Tag(RolePerm neededPerm) {
        this.neededPerm = neededPerm;
    }

    private RolePerm neededPerm;

    /**
     * This method returns the needed permission to set this object
     *
     * @return the needed permission
     */
    @DomainAPI
    public RolePerm getNeededPerm() {
        return this.neededPerm;
    }
}
