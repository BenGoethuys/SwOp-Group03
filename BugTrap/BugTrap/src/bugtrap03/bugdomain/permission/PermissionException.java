/**
 *
 */
package bugtrap03.bugdomain.permission;

import bugtrap03.bugdomain.DomainAPI;

/**
 * @author Ben Goethuys & Vincent Derkinderen
 */
@DomainAPI
public class PermissionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7763416390411941211L;

    /**
     * @param message
     */
    @DomainAPI
    public PermissionException(String message) {
        super(message);
    }
}
