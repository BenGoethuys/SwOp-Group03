package bugtrap03.model;

import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;

/**
 *
 * @author Admin
 */
public class SplitSubsystemModelCmd extends ModelCmd {

    
    
    @Override
    Subsystem[] exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean undo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean isExecuted() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
