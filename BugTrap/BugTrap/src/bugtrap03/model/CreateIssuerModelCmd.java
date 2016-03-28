package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.Issuer;

/**
 *
 * @author Group 03
 */
class CreateIssuerModelCmd extends ModelCmd {

    /**
     *
     * Create a {@link ModelCmd} that can create an {@link Issuer} when executed.
     *
     * @param model The DataModel to create the issuer in.
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     *
     * @throws IllegalArgumentException When model is a null reference.
     */
    CreateIssuerModelCmd(DataModel model, String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateIssuerModelCmd was a null reference.");
        }

        this.model = model;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Create a new {@link ModelCmd} that can create an {@link Issuer} when executed.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     *
     * @throws IllegalArgumentException When model is a null reference.
     */
    CreateIssuerModelCmd(DataModel model, String username, String firstName, String lastName) throws IllegalArgumentException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateIssuerModelCmd was a null reference.");
        }

        this.model = model;
        this.username = username;
        this.middleName = null;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Issuer issuer;

    private final DataModel model;
    private final String username;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    private boolean isExecuted = false;

    /**
     * Create a new {@link Issuer} in the given {@link DataModel}.
     *
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments passed to the constructor is invalid.
     * @throws IllegalStateException When this ModelCmd was already executed.
     *
     * @see Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String)
     * @see Issuer#Issuer(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    Issuer exec() throws IllegalArgumentException, IllegalStateException {
        if (this.isExecuted()) {
            throw new IllegalStateException("The CreateIssuerModelCmd was already executed.");
        }

        if (middleName == null) {
            issuer = new Issuer(username, firstName, lastName);
        } else {
            issuer = new Issuer(username, firstName, middleName, lastName);
        }

        model.addUser(issuer);
        isExecuted = true;
        return issuer;
    }

    @Override
    boolean undo() {
        if (!this.isExecuted()) {
            return false;
        }

        model.deleteUser(issuer);
        return true;
    }

    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

}
