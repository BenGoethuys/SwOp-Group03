package bugtrap03.bugdomain.usersystem;

import bugtrap03.bugdomain.usersystem.User;

/**
 * {@link User} test class.
 * 
 * @author Admin
 */
public class UserTestDummy extends User {

    public UserTestDummy(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        super(username, firstName, middleName, lastName);
    }

    public UserTestDummy(String username, String firstName, String lastName) throws IllegalArgumentException {
        super(username, firstName, lastName);
    }

}
