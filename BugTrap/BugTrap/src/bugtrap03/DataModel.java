package bugtrap03;

import bugtrap03.bugdomain.BugReport;
import bugtrap03.bugdomain.Project;
import bugtrap03.permission.PermissionException;
import bugtrap03.permission.UserPerm;
import bugtrap03.usersystem.Administrator;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.Issuer;
import bugtrap03.usersystem.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import purecollections.PList;

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
        this.userList = PList.<User>empty();
        this.projectList = PList.<Project>empty();
    }

    private PList<User> userList;
    private PList<Project> projectList;

    /**
     * Get the list of users in this system.
     *
     * @return The list of users currently in this system.
     */
    public PList<User> getUserList() {
        return userList;
    }

    /**
     * Add the {@link User} to the list of users.
     *
     * @param user The user to add.
     * @throws NullPointerException If user is null.
     */
    private void addUser(User user) throws NullPointerException {
        this.userList = userList.plus(user);
    }

    /**
     * Get the list of users in this system who have the exact class type
     * userType.
     *
     * @param <U> extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType.
     */
    public <U extends User> PList<U> getUserListOfExactType(Class<U> userType) {
        PList<U> result = PList.<U>empty();
        for (User user : userList) {
            if (user.getClass().equals(userType)) {
                result = result.plus((U) user);
            }
        }
        return result;
    }

    /**
     * Get the list of users in this system who are of type that is or extends
     * userType.
     *
     * @param <U> extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType
     * or a class type that extends userType.
     */
    public <U extends User> PList<U> getUserListOfType(Class<U> userType) {
        PList<U> result = PList.<U>empty();
        for (User user : userList) {
            if (userType.isInstance(user)) {
                result = result.plus((U) user);
            }
        }
        return result;
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
     * @see Project#Project(String, String, Developer, GregorianCalendar, long)
     * @return the created project
     */
    public Project createProject(String name, String description, GregorianCalendar startDate, Developer lead, long budget, User creator)
            throws IllegalArgumentException, PermissionException {
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        Project project = new Project(name, description, lead, startDate, budget);
        this.projectList = projectList.plus(project);
        return project;
    }
    
    /**
     * This method creates a new {@link Project} in the system
     *
     * @param name The name of the project
     * @param description The description of the project
     * @param budget The budget estimate for this project
     * @param lead The lead developer of this project
     *
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient
     * permissions
     *
     * @see Project#Project(String, String, Developer, long)
     * @return the created project
     */
    public Project createProject(String name, String description, Developer lead, long budget, User creator)
            throws IllegalArgumentException, PermissionException {
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        Project project = new Project(name, description, lead, budget);
        this.projectList = projectList.plus(project);
        return project;
    }
    
    /**
     * Get the list of projects in this system.
     *
     * @return The list of projects currently in this system.
     */
    public PList<Project> getProjectList() {
        return this.projectList;
    }

    /**
     * This method gets all bug reports in the system
     * @return a list of all bugreports in the system
     */
    public ArrayList<BugReport> getAllBugReports(){
        ArrayList<BugReport> list = new ArrayList<>();
        for (Project project : this.projectList){
            list.addAll(project.getAllBugReports());
        }
        return list;
    }

    /**
     *  This method updates the given project with the new given attributes
     *
     * @param name The new name of the given project
     * @param description The new description of the given project
     * @param startDate The new startDate of the given project
     * @param budgetEstimate The new budget estimate of the given project
     *
     * @Ensures The attributes of the given project will not be updated if an error was thrown
     */
    public Project updateProject(Project proj, String name, String description, GregorianCalendar startDate, Long budgetEstimate) throws IllegalArgumentException {
        // Test to prevent inconsistent updating of vars
        Project.isValidName(name);
        Project.isValidDescription(description);
        proj.isValidStartDate(startDate);
        Project.isValidBudgetEstimate(budgetEstimate);

        // update the vars in proj
        proj.setName(name);
        proj.setDescription(description);
        proj.setStartDate(startDate);
        proj.setBudgetEstimate(budgetEstimate);

        return proj;
    }

}
