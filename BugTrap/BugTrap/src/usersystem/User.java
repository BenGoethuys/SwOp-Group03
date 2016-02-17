/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usersystem;

/**
 *
 * @author Admin
 */
public class User {

    /**
     * Create a {@link User} with a unique username, a firstName, optional middelName and lastName.
     * @param uniqueUsername
     * @param firstName
     * @param middleName
     * @param lastName 
     */
    public User(String uniqueUsername, String firstName, String middleName, String lastName) {
        this.uniqueUsername = uniqueUsername;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    
    /**
     * 
     * @param uniqueUsername
     * @param firstName
     * @param lastName 
     */
    public User(String uniqueUsername, String firstName, String lastName) {
        this(uniqueUsername, firstName, "", lastName);
    }
    
    

    private final String uniqueUsername;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    
    

}
