package bugtrap03.model;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.AbstractSystemMemento;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;
import purecollections.PList;

/**
 *
 * A {@link ModelCmd} that when executed will split a {@link Subsystem} into itself and another newly created subsystem.
 * 
 * @author Group 03
 */
class SplitSubsystemModelCmd extends ModelCmd {
    
    /**
     * Create a ModelCmd that when executed can split the given subsystem into the subsystem and an extra one.
     * 
     * @param subsystem The Subsystem to split into this and another subsystem.
     * @param subsystem1Name The new name for the first subsystem.
     * @param subsystem1Desc The new description for the first subsystem.
     * @param subsystem2Name The name for the newly created subsystem.
     * @param subsystem2Desc The description for the newly created subsystem.
     * @param subsystems1 The list of subsystems that subsystem will hold when they are both in this list and a direct subsystem. null will be converted into an empty list.
     * @param bugReports1 The list of BugReports that subsystem will hold when they are both in this list and a direct bugReport. null will be converted into an empty list.
     * @param user The user that wants to split subsystem.
     * 
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When subsystem == null
     * 
     * @see Subsystem#split(String, String, String, String, PList, PList, User)
     */
    SplitSubsystemModelCmd(Subsystem subsystem, String subsystem1Name, String subsystem1Desc, String subsystem2Name, String subsystem2Desc, PList<Subsystem> subsystems1, PList<BugReport> bugReports1, User user) {
        if(user == null || subsystem == null) {
            throw new IllegalArgumentException("SplitSubsystemModelCmd does not accept user or subsystem == null.");
        }
        
        this.subsystem = subsystem;
        this.subsystem1Name = subsystem1Name;
        this.subsystem1Desc = subsystem1Desc;
        this.subsystem2Name = subsystem2Name;
        this.subsystem2Desc = subsystem2Desc;
        
        this.subsystems1 = (subsystems1 != null) ? subsystems1 : PList.<Subsystem>empty();
        this.bugReports1 = (bugReports1 != null) ? bugReports1 : PList.<BugReport>empty();
        this.user = user;
    }
        
    private final Subsystem subsystem;
    private Subsystem subsystem2;
    private final String subsystem1Name;
    private final String subsystem1Desc;
    private final String subsystem2Name;
    private final String subsystem2Desc;
    
    private final PList<Subsystem> subsystems1;
    private final PList<BugReport> bugReports1;
            
    private final User user;
    
    private AbstractSystemMemento parentMemento;
    private AbstractSystem parent;
    
    private boolean isExecuted = false;
    
    /**
     * 
     * Split the given subsystem into itself and an extra newly created subsystem such as described in 
     * {@link Subsystem#split(String, String, String, String, PList, PList, User)}.
     * @return The extra newly created Subsystem.
     * 
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws IllegalArgumentException When any of the arguments passed to the constructor is invalid.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * 
     * @see Subsystem#split(String, String, String, String, PList, PList User)
     * 
     */
    @Override
    Subsystem exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The SplitSubsystemModelCmd was already executed.");
        }

        //Store memento
        parentMemento = subsystem.getParent().getMemento();
        
        //Execute
        subsystem2 = subsystem.split(subsystem1Name, subsystem1Desc, subsystem2Name, subsystem2Desc, subsystems1, bugReports1, user);
        isExecuted = true;
        return subsystem2;
    }
    

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }
        
        //restore hierarchy & remove newly created.
        parent.setMemento(parentMemento);
        subsystem2.setTerminated(true);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        if(subsystem2 == null) {
            return "Split subsystem" + subsystem.getName();
        } else {
            return "Split subsystem into " + subsystem.getName() + " and " + subsystem2.getName();
        }
    }

}
