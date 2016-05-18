package bugtrap03.model;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Milestone;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.User;

import java.util.HashMap;

/**
 * @author Group 03
 */
class SetMilestoneAbstractSystemModelCmd extends ModelCmd {

    /**
     * The constructor of this Cmd
     *
     * @param user           The user that wants to change the milestone
     * @param abstractSystem The abstractSystem that needs a milestone change
     * @param milestone      The new milestone
     */
    SetMilestoneAbstractSystemModelCmd(User user, AbstractSystem abstractSystem, Milestone milestone) {
        if (user == null) {
            throw new IllegalArgumentException("The given user cannot be null");
        }
        if (abstractSystem == null) {
            throw new IllegalArgumentException("The given abstractSystem cannot be null");
        }
        if (milestone == null) {
            throw new IllegalArgumentException("The given milestone cannot be null");
        }

        this.user = user;
        this.abstractSystem = abstractSystem;
        this.milestone = milestone;
        this.isExecuted = false;
        this.oldValues = new HashMap<>();
    }

    private User user;
    private AbstractSystem abstractSystem;
    private Milestone milestone;
    private boolean isExecuted;
    private HashMap<AbstractSystem, Milestone> oldValues;

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
    AbstractSystem exec() throws IllegalArgumentException, NullPointerException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateIssuerModelCmd was already executed.");
        }

        // save state
        oldValues.put(abstractSystem, abstractSystem.getMilestone());
        for (Subsystem subsystem : abstractSystem.getAllSubsystems()) {
            oldValues.put(subsystem, subsystem.getMilestone());
        }

        // set new milestone
        abstractSystem.setMilestone(user, milestone);
        isExecuted = true;
        return abstractSystem;
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
        try {
            abstractSystem.setMilestone(user, oldValues.get(abstractSystem));
        } catch (PermissionException exception) {
            // should not happen, because had permission in the first place
        }
        for (Subsystem subsystem : abstractSystem.getAllSubsystems()) {
            try {
                subsystem.setMilestone(user, oldValues.get(subsystem));
            } catch (PermissionException e) {
                // should never happen
            }
        }

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
        return "Updated milestone of " + this.abstractSystem.getName();
    }
}
