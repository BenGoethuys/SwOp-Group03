package bugtrap03.model;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.AbstractSystemMemento;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

/**
 * @author Group 03
 */
class MergeSubsystemsModelCmd extends ModelCmd {

    /**
     * The constructor of this model cmd
     *
     * @param user              The user that wants to merge the subsystems
     * @param subsystem1        The first subsystem to merge
     * @param subsystem2        The second subsystem to merge
     * @param newName           The new name of the merged subsystem
     * @param newDescription    The new description of the merged subsystem
     */
    MergeSubsystemsModelCmd(User user, Subsystem subsystem1, Subsystem subsystem2, String newName, String newDescription){
        // user null, newName null and newDescription null will be tested automatically
        if (subsystem1 == null || subsystem2 == null){
            throw new IllegalArgumentException("The given subsystem cannot be null");
        }

        this.user = user;
        this.subsystem1 = subsystem1;
        this.subsystem2 = subsystem2;
        this.newName = newName;
        this.newDescription = newDescription;
    }

    private final User user;
    private final Subsystem subsystem1;
    private final Subsystem subsystem2;
    private final String newName;
    private final String newDescription;

    private AbstractSystemMemento memento;
    private AbstractSystem parent;

    private boolean isExecuted = false;


    /**
     * Execute the given Command.
     *
     * @throws IllegalArgumentException When there is an illegal argument passed.
     * @throws NullPointerException     When there is a null where it shouldn't. Read ModelCmd specific documentation.
     * @throws PermissionException      When the user does not have sufficient permissions
     * @throws IllegalStateException    When the command is already executed.
     *
     * @returns This is ModelCommand-subclass specific. null when there is nothing to report.
     */
    @Override
    Subsystem exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {

        if (this.isExecuted()) {
            throw new IllegalStateException("The SplitSubsystemModelCmd was already executed.");
        }

        // get highest parent of both
        if (subsystem1.getParent() == subsystem2){
            this.parent = subsystem2.getParent();
        } else {
            this.parent = subsystem1.getParent();
        }

        // make memento of parent
        this.memento = this.parent.getMemento();

        Subsystem subsystem;
        try {
            // call merge method
            if (subsystem1.getParent() == subsystem2) {
                subsystem = subsystem2.mergeWithSubsystem(user, subsystem1, newName, newDescription);
            } else {
                subsystem = subsystem1.mergeWithSubsystem(user, subsystem2, newName, newDescription);
            }

        } catch (Exception exc){
            // something went wrong -> restore state
            parent.setMemento(memento);

            // throw error further
            throw exc;
        }

        this.isExecuted = true;
        return subsystem;
    }

    /**
     * Undo this command when possible.
     *
     * @returns Whether the operation was undone.
     */
    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        parent.setMemento(memento);
        return true;
    }

    /**
     * Whether this command is executed at least once already.
     *
     * @return Whether this command is executed.
     */
    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

    @Override
    public String toString() {
        return "Merge subsystem " + subsystem1.getName() + " with subsystem "
                + subsystem2.getName() + " into: " + newName;
    }
}
