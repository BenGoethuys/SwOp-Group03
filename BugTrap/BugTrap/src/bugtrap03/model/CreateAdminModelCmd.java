package bugtrap03.model;

import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.usersystem.Administrator;

/**
 *
 * @author Admin
 */
public class CreateAdminModelCmd extends ModelCmd {

    /**
     * Create a new {@link CreateAdminModelCmd} that can create an {@link Administrator} when executed.
     *
     * @param model The DataMode to add the developer to.
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     */
    CreateAdminModelCmd(DataModel model, String username, String firstName, String lastName) {
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

    @Override
    Administrator exec() throws IllegalArgumentException, IllegalStateException {
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

}
