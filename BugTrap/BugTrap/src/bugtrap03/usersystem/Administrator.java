package bugtrap03.usersystem;

public class Administrator extends User {

	public Administrator(String uniqueUsername, String firstName, String middleName, String lastName) {
		super(uniqueUsername, firstName, middleName, lastName);
	}

	public Administrator(String uniqueUsername, String firstName, String lastName) {
		super(uniqueUsername, firstName, lastName);
	}

}
