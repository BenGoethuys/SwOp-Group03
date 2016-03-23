package bugtrap03.model;

import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Issuer;

/**
 *
 * @author Group 03
 */
public class CreateDeveloperModelCmd extends ModelCmd {

    /**
     * Create a {@link CreateDeveloperModelCmd} which can create a new {@link Developer} when executed.
     *
     * @param model The DataModel to add the developer to.
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     */   
    CreateDeveloperModelCmd(DataModel model, String username, String firstName, String middleName, String lastName) {
        this.model = model;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Create a new {@link ModelCmd} that can create an {@link Issuer} when executed.
     *
     * @param model The DataMode to add the developer to.
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     */
    CreateDeveloperModelCmd(DataModel model, String username, String firstName, String lastName) {
        this.model = model;
        this.username = username;
        this.middleName = null;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private final DataModel model;
    private final String username;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    private boolean isExecuted = false;

    /**
     * Create a new {@link Developer} in the given {@link DataModel}.
     *
     * @return The created {@link Developer}
     * @throws IllegalArgumentException When any of the arguments passed to the constructor is invalid.
     * @throws IllegalStateException When this ModelCmd was already executed.
     * 
     * @see Developer#Developer(java.lang.String, java.lang.String, java.lang.String) 
     * @see Developer#Developer(java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
     */
    @Override
    Developer exec() throws IllegalArgumentException, IllegalStateException {
        if (model == null) {
            throw new IllegalArgumentException("The DataModel passed to the CreateIssuerModelCmd was a null reference.");
        }
        if(this.isExecuted()) {
            throw new IllegalStateException("The CreateDeveloperModelCmd was already executed.");
        }

        isExecuted = true;

        Developer dev;
        if (middleName == null) {
            dev = new Developer(username, firstName, lastName);
        } else {
            dev = new Developer(username, firstName, middleName, lastName);
        }

        model.addUser(dev);
        return dev;
    }

    @Override
    boolean undo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    boolean isExecuted() {
        return this.isExecuted;
    }

}
