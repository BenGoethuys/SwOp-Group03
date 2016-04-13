package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;

/**
 * @author Group 03
 */
public class UnregisterFromNotificationsModelCmd extends ModelCmd{
    @Override
    Object exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        return null;
    }

    @Override
    boolean undo() {
        return false;
    }

    @Override
    boolean isExecuted() {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
