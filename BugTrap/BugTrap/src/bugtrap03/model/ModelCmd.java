package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;

/**
 *
 * @author Group 03
 */
public abstract class ModelCmd {
    
    //TODO: Add toString() to every ModelCmd.

    /**
     * Execute the given Command.
     *
     * @returns This is ModelCommand-subclass specific. null when there is nothing to report.
     * @throws IllegalArgumentException When there is an illegal argument passed.
     * @throws NullPointerException When there is a null where it shouldn't. Read ModelCmd specific documentation.
     * @throws PermissionException When the user does not have sufficient permissions
     * @throws IllegalStateException When the command is already executed.
     */
    abstract Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException;

    /**
     * Undo this command when possible.
     *
     * @returns Whether the operation was undone.
     */
    abstract boolean undo();

    /**
     * Whether this command is executed at least once already.
     * @return Whether this command is executed.
     */
    abstract boolean isExecuted();
}
