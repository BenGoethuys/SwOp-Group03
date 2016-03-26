package bugtrap03.model;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.bugdomain.VersionID;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.User;

/**
 *
 * @author Admin
 */
class CreateSubsystemModelCmd extends ModelCmd {

    /**
     * Creates a {@link ModelCmd} that can creates a new subsystem in the given Project/Subsystem
     *
     * @param user The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsystem to
     * @param versionID The versionID of the new Subsystem.
     * @param name The name of the new Subsystem
     * @param description The description of the new Subsystem
     * 
     * @return The created subsystem
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When abstractSystem == null
     */
    CreateSubsystemModelCmd(User user, AbstractSystem abstractSystem, VersionID versionID, String name, String description) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("The user passed to the CreateSubsystemModelCmd");
        }

        if (abstractSystem == null) {
            throw new IllegalArgumentException("The abstractSystem passed to CreateSubsystemModelCmd was a null reference.");
        }

        this.user = user;
        this.abstractSystem = abstractSystem;
        this.versionID = versionID;
        this.name = name;
        this.description = description;
    }

    /**
     * Creates a {@link ModelCmd} that can creates a new subsystem in the given Project/Subsystem
     *
     * @param user The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsystem to
     * @param name The name of the new Subsystem
     * @param description The description of the new Subsystem
     * 
     * @return The created subsystem
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When abstractSystem == null
     */
    CreateSubsystemModelCmd(User user, AbstractSystem abstractSystem, String name, String description) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("The user passed to the CreateSubsystemModelCmd");
        }

        if (abstractSystem == null) {
            throw new IllegalArgumentException("The abstractSystem passed to CreateSubsystemModelCmd was a null reference.");
        }

        this.user = user;
        this.abstractSystem = abstractSystem;
        this.versionID = null;
        this.name = name;
        this.description = description;
    }

    private final User user;
    private final AbstractSystem abstractSystem;
    private final VersionID versionID;
    private final String name;
    private final String description;

    private boolean isExecuted;
    private Subsystem system;

    /**
     * This method creates a new subsystem in the given Project/Subsystem
     *
     * @return The newly created subsystem.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * @throws PermissionException If the user doesn't have the permission to create a subsystem
     * @throws IllegalArgumentException When name or description are invalid.
     */
    @Override
    Subsystem exec() throws IllegalArgumentException, PermissionException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateIssuerModelCmd was already executed.");
        }

        if (!user.hasPermission(UserPerm.CREATE_SUBSYS)) {
            throw new PermissionException("You don't have the needed permission");
        }

        if (versionID == null) {
            system = abstractSystem.makeSubsystemChild(name, description);
        } else {
            system = abstractSystem.makeSubsystemChild(versionID, name, description);
        }
        isExecuted = true;
        return system;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        abstractSystem.deleteChild(system);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

}
