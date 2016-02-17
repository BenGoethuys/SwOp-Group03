package usersystem;

public class Developer extends Issuer {

    public Developer(String uniqueUsername, String firstName, String middleName, String lastName) {
        super(uniqueUsername, firstName, middleName, lastName);
    }

    public Developer(String uniqueUsername, String firstName, String lastName) {
        super(uniqueUsername, firstName, lastName);
    }

}
