package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.Administrator;

/**
 *
 * @author Group 03
 */
class CreateAdminModelCmd extends ModelCmd {

    /**
     * Create a new {@link CreateAdminModelCmd} that can create an {@link Administrator} when executed.
     *
     * @param model The DataMode to add the administrator to.
     * @param username The username of the administrator.
     * @param firstName The first name of the administrator.
     * @param lastName The last name of the administrator.
     *
     * @throws IllegalArgumentException When model is a null reference.
     */
    CreateAdminModelCmd(DataModel model, String username, String firstName, String lastName) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateAdminModelCmd was a null reference.");
        }
        this.model = model;
        this.username = username;
        this.middleName = null;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Create a new {@link CreateAdminModelCmd} that can create an {@link Administrator} when executed.
     *
     * @param model The DataMode to add the administrator to.
     * @param username The username of the administrator.
     * @param firstName The first name of the administrator.
     * @param middleName The middle name of the administrator.
     * @param lastName The last name of the administrator.
     *
     * @throws IllegalArgumentException When model is a null reference.
     */
    CreateAdminModelCmd(DataModel model, String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateAdminModelCmd was a null reference.");
        }
        this.model = model;
        this.username = username;
        this.middleName = middleName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Administrator admin;

    private final DataModel model;
    private final String username;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    private boolean isExecuted = false;

    /**
     * Create a new {@link Administrator} in the given {@link DataModel}.
     *
     * @return The created {@link Administrator}
     * @throws IllegalArgumentException When any of the arguments passed to the constructor is invalid.
     * @throws IllegalStateException When this ModelCmd was already executed.
     *
     * @see Administrator#Administrator(java.lang.String, java.lang.String, java.lang.String)
     * @see Administrator#Administrator(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    Administrator exec() throws IllegalArgumentException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateAdminModelCmd was already executed.");
        }

        if (middleName == null) {
            admin = new Administrator(username, firstName, lastName);
        } else {
            admin = new Administrator(username, firstName, middleName, lastName);
        }

        model.addUser(admin);
        isExecuted = true;
        return admin;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        model.deleteUser(admin);
        return true;
    }

    @Override
    boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public String toString() {
        String name = (this.admin != null) ? this.admin.getFullName() : "-invalid argument-";
        return "Created Administrator " + name;
    }

}
