package bugtrap03.model;

import bugtrap03.bugdomain.DomainAPI;
import bugtrap03.bugdomain.permission.PermissionException;

/**
 * @author Group 03
 */
@DomainAPI
public abstract class ModelCmd {

    /**
     * Execute the given Command.
     *
     * @throws IllegalArgumentException When there is an illegal argument passed.
     * @throws NullPointerException     When there is a null where it shouldn't. Read ModelCmd specific documentation.
     * @throws PermissionException      When the user does not have sufficient permissions
     * @throws IllegalStateException    When the command is already executed.
     * @returns This is ModelCommand-subclass specific. null when there is nothing to report.
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
     *
     * @return Whether this command is executed.
     */
    abstract boolean isExecuted();

    @DomainAPI
    @Override
    public abstract String toString();
}
