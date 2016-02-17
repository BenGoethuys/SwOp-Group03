package usersystem;

public class Issuer extends User {

	public Issuer(String uniqueUsername, String firstName, String middleName, String lastName) {
		super(uniqueUsername, firstName, middleName, lastName);
	}

	public Issuer(String uniqueUsername, String firstName, String lastName) {
		super(uniqueUsername, firstName, lastName);
	}

}
