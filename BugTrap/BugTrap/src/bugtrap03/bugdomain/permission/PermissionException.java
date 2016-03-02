/**
 *
 */
package bugtrap03.bugdomain.permission;

/**
 * @author Ben Goethuys & Vincent Derkinderen
 */
public class PermissionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7763416390411941211L;

    /**
     * @param message
     */
    public PermissionException(String message) {
        super(message);
    }
}
