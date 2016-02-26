package bugtrap03;

import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.permission.UserPerm;
import bugtrap03.usersystem.Administrator;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import bugtrap03.usersystem.User;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 * @version 0.1
 */
public class DataModel {

    /**
     *
     */
    public DataModel() {
        this.userList = new ArrayList<>();
        this.projectList = new ArrayList<>();
    }

    private final ArrayList<User> userList;
    private final ArrayList<Project> projectList;

    /**
     * Add the {@link User} to the list of users.
     *
     * @param user The user to add.
     * @throws NullPointerException If user is null.
     */
    private void addUser(User user) throws NullPointerException {
        if (user == null) {
            throw new NullPointerException();
        }
        this.userList.add(user);
    }

    /**
     * Create a new {@link Issuer} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Issuer#Issuer(String, String, String, String)
     */
    public Issuer createIssuer(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        Issuer issuer = new Issuer(username, firstName, middleName, lastName);
        addUser(issuer);
        return issuer;
    }

    /**
     * Create a new {@link Issuer} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Issuer#Issuer(String, String, String)
     */
    public Issuer createIssuer(String username, String firstName, String lastName) throws IllegalArgumentException {
        Issuer issuer = new Issuer(username, firstName, lastName);
        addUser(issuer);
        return issuer;
    }

    /**
     * Create a new {@link Developer} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Developer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Developer#Developer(String, String, String, String)
     */
    public Developer createDeveloper(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        Developer dev = new Developer(username, firstName, middleName, lastName);
        addUser(dev);
        return dev;
    }

    /**
     * Create a new {@link Developer} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Developer#Developer(String, String, String)
     */
    public Developer createDeveloper(String username, String firstName, String lastName) throws IllegalArgumentException {
        Developer dev = new Developer(username, firstName, lastName);
        addUser(dev);
        return dev;
    }

    /**
     * Create a new {@link Administrator} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Administrator#Administrator(String, String, String, String)
     */
    public Administrator createAdministrator(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        Administrator admin = new Administrator(username, firstName, middleName, lastName);
        addUser(admin);
        return admin;
    }

    /**
     * Create a new {@link Administrator} in this system.
     *
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Administrator}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * @see Administrator#Administrator(String, String, String)
     */
    public Administrator createAdministrator(String username, String firstName, String lastName) throws IllegalArgumentException {
        Administrator admin = new Administrator(username, firstName, lastName);
        addUser(admin);
        return admin;
    }

    /**
     * This method creates a new {@link Project} in the system
     *
     * @param name The name of the project
     * @param description The description of the project
     * @param startDate The start date of the project
     * @param budget The budget estimate for this project
     * @param lead The lead developer of this project
     *
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient
     * permissions
     *
     * @see Project#Project(String, String, Date, long, Developer)
     * @return the created project
     */
    public Project createProject(String name, String description, Date startDate, Developer lead, long budget, User creator)
            throws IllegalArgumentException, PermissionException {
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        Project project = new Project(name, description, lead, startDate, budget);
        this.projectList.add(project);
        return project;
    }

}
