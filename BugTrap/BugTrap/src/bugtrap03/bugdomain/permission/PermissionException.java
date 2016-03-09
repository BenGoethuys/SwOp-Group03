package bugtrap03.bugdomain.permission;

import bugtrap03.bugdomain.DomainAPI;

/**
 * This class represents an exception thrown when someone/something has insufficient permission to do something
 * @author Group 03
 */
@DomainAPI
public class PermissionException extends Exception {

    /**
     * Auto generated id
     */
    private static final long serialVersionUID = -7763416390411941211L;

    /**
     * Constructor to make a new Permission exception with a given message
     * @param message The message that explains why this permission is thrown
     */
    @DomainAPI
    public PermissionException(String message) {
        super(message);
    }
}
