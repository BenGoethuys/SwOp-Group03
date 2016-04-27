package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;

/**
 * @author Group 03
 */
class mergeSubsystemsModelCmd extends ModelCmd {



    /**
     * Execute the given Command.
     *
     * @throws IllegalArgumentException When there is an illegal argument passed.
     * @throws NullPointerException     When there is a null where it shouldn't. Read ModelCmd specific documentation.
     * @throws PermissionException      When the user does not have sufficient permissions
     * @throws IllegalStateException    When the command is already executed.
     * @returns This is ModelCommand-subclass specific. null when there is nothing to report.
     */
    @Override
    Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        return null;
    }

    /**
     * Undo this command when possible.
     *
     * @returns Whether the operation was undone.
     */
    @Override
    boolean undo() {
        return false;
    }

    /**
     * Whether this command is executed at least once already.
     *
     * @return Whether this command is executed.
     */
    @Override
    boolean isExecuted() {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
