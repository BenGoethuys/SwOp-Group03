package bugtrap03.model;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.*;
import purecollections.PList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * This class is the main controller of the system.
 *
 * @author Group 03
 * @version 0.1
 */
@DomainAPI
public class DataModel {

    /**
     * The constructor of a DataModel
     */
    @DomainAPI
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
    @DomainAPI
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
     * Add the {@link Project} to the list of projects.
     *
     * @param project The project to add.
     * @throws NullPointerException If project is null.
     */
    private void addProject(Project project) throws NullPointerException {
        this.projectList = projectList.plus(project);
    }

    /**
     * Delete the {@link Project} from the list of projects.
     *
     * @param project The project to delete.
     */
    private void deleteProject(Project project) {
        this.projectList = projectList.minus(project);
    }

    /**
     * Get the list of users in this system who have the exact class type
     * userType.
     *
     * @param <U>      extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType.
     */
    @DomainAPI
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
     * @param <U>      extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType
     * or a class type that extends userType.
     */
    @DomainAPI
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
     * @param username   The username of the issuer.
     * @param firstName  The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName   The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Issuer createIssuer(String username, String firstName, String middleName, String lastName)
            throws IllegalArgumentException {
        Issuer issuer = new Issuer(username, firstName, middleName, lastName);
        addUser(issuer);
        return issuer;
    }

    /**
     * Create a new {@link Issuer} in this system.
     *
     * @param username  The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName  The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Issuer createIssuer(String username, String firstName, String lastName) throws IllegalArgumentException {
        Issuer issuer = new Issuer(username, firstName, lastName);
        addUser(issuer);
        return issuer;
    }

    /**
     * Create a new {@link Developer} in this system.
     *
     * @param username   The username of the issuer.
     * @param firstName  The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName   The last name of the issuer.
     * @return The created {@link Developer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Developer createDeveloper(String username, String firstName, String middleName, String lastName)
            throws IllegalArgumentException {
        Developer dev = new Developer(username, firstName, middleName, lastName);
        addUser(dev);
        return dev;
    }

    /**
     * Create a new {@link Developer} in this system.
     *
     * @param username  The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName  The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Developer createDeveloper(String username, String firstName, String lastName)
            throws IllegalArgumentException {
        Developer dev = new Developer(username, firstName, lastName);
        addUser(dev);
        return dev;
    }

    /**
     * Create a new {@link Administrator} in this system.
     *
     * @param username   The username of the issuer.
     * @param firstName  The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName   The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Administrator createAdministrator(String username, String firstName, String middleName, String lastName)
            throws IllegalArgumentException {
        Administrator admin = new Administrator(username, firstName, middleName, lastName);
        addUser(admin);
        return admin;
    }

    /**
     * Create a new {@link Administrator} in this system.
     *
     * @param username  The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param lastName  The last name of the issuer.
     * @return The created {@link Administrator}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Administrator createAdministrator(String username, String firstName, String lastName)
            throws IllegalArgumentException {
        Administrator admin = new Administrator(username, firstName, lastName);
        addUser(admin);
        return admin;
    }

    /**
     * This method creates a new {@link Project} in the system
     *
     * @param name        The name of the project
     * @param description The description of the project
     * @param startDate   The start date of the project
     * @param budget      The budget estimate for this project
     * @param lead        The lead developer of this project
     * @return the created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException      If the given creator has insufficient
     *                                  permissions
     */
    @DomainAPI
    public Project createProject(String name, String description, GregorianCalendar startDate, Developer lead,
                                 long budget, User creator) throws IllegalArgumentException, PermissionException {
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        Project project = new Project(name, description, lead, startDate, budget);
        addProject(project);
        return project;
    }

    /**
     * This method creates a new {@link Project} in the system
     *
     * @param name        The name of the project
     * @param description The description of the project
     * @param budget      The budget estimate for this project
     * @param lead        The lead developer of this project
     * @return the created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException      If the given creator has insufficient
     *                                  permissions
     */
    @DomainAPI
    public Project createProject(String name, String description, Developer lead, long budget, User creator)
            throws IllegalArgumentException, PermissionException {
        if (!creator.hasPermission(UserPerm.CREATE_PROJ)) {
            throw new PermissionException("The given user doesn't have the permission to create a project");
        }
        Project project = new Project(name, description, lead, budget);
        addProject(project);
        return project;
    }

    /**
     * Get the list of projects in this system.
     *
     * @return The list of projects currently in this system.
     */
    @DomainAPI
    public PList<Project> getProjectList() {
        return this.projectList;
    }

    /**
     * This method gets all bug reports in the system
     *
     * @return a list of all bug reports in the system
     */
    @DomainAPI
    public ArrayList<BugReport> getAllBugReports() {
        ArrayList<BugReport> list = new ArrayList<>();
        for (Project project : this.projectList) {
            list.addAll(project.getAllBugReports());
        }
        return list;
    }

    /**
     * This method updates the given project with the new given attributes
     *
     * @param name           The new name of the given project
     * @param description    The new description of the given project
     * @param startDate      The new startDate of the given project
     * @param budgetEstimate The new budget estimate of the given project
     * @throws PermissionException if the given user doesn't have the needed
     *                             permission to update a project.
     * @Ensures The attributes of the given project will not be updated if an
     * error was thrown
     */
    @DomainAPI
    public Project updateProject(Project proj, User user, String name, String description, GregorianCalendar startDate,
                                 Long budgetEstimate) throws IllegalArgumentException, PermissionException {
        // check needed permission
        if (!user.hasPermission(UserPerm.UPDATE_PROJ)) {
            throw new PermissionException("You don't have the needed permission to update a project!");
        }

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

    /**
     * This method returns all subsystems of a given project
     *
     * @param project The project to print the subsystems from
     * @return a PList containing all subsystems of a project
     * @see AbstractSystem#getAllSubsystems()
     */
    @DomainAPI
    public PList<Subsystem> getAllSubsystems(Project project) {
        return project.getAllSubsystems();
    }

    /**
     * This method returns all projects and there subsystems in the system
     *
     * @return the list of all projects and there subsystems
     */
    @DomainAPI
    public PList<AbstractSystem> getAllProjectsAndSubsystems() {
        PList<AbstractSystem> list = PList.<AbstractSystem>empty();
        for (Project proj : this.projectList) {
            list = list.plus(proj);
            list = list.plusAll(new ArrayList<>(proj.getAllSubsystems()));
        }
        return list;
    }

    /**
     * This method removes a project from the project list.
     *
     * @param user    The user that wants to delete the project
     * @param project The project which has to be removed
     * @return The removed project
     * @throws PermissionException If the given user doesn't have the permission
     *                             to delete a project
     */
    @DomainAPI
    public Project deleteProject(User user, Project project) throws PermissionException {
        if (!user.hasPermission(UserPerm.DELETE_PROJ)) {
            throw new PermissionException("You dont have the needed permission to delete a project");
        }
        deleteProject(project);

        return project;
    }

    /**
     * This method creates a new subsytem in the given Project/Subsystem
     *
     * @param user           The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsytem to
     * @param name           The name of the new Subsytem
     * @param description    The description of the new Subsytem
     * @return The created subsytem
     * @throws PermissionException If the user doesn't have the permission to
     *                             create a subsystem
     */
    @DomainAPI
    public Subsystem createSubsystem(User user, AbstractSystem abstractSystem, String name, String description)
            throws PermissionException, IllegalArgumentException {
        if (!user.hasPermission(UserPerm.CREATE_SUBSYS)) {
            throw new PermissionException("You don't have the needed permission");
        }
        return abstractSystem.makeSubsystemChild(name, description);
    }

    /**
     * This method creates a new subsytem in the given Project/Subsystem
     *
     * @param user           The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsytem to
     * @param name           The name of the new Subsytem
     * @param description    The description of the new Subsytem
     * @return The created subsytem
     * @throws PermissionException If the user doesn't have the permission to
     *                             create a subsystem
     */
    @DomainAPI
    public Subsystem createSubsystem(User user, AbstractSystem abstractSystem, VersionID versionID, String name,
                                     String description) throws PermissionException, IllegalArgumentException {
        if (!user.hasPermission(UserPerm.CREATE_SUBSYS)) {
            throw new PermissionException("You don't have the needed permission");
        }
        return abstractSystem.makeSubsystemChild(versionID, name, description);
    }

    /**
     * This method creates a bug report in the system
     *
     * @param user         The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     * @Ensures new.getDate() == current date at the moment of initialisation
     * @Ensures new.getTag() == Tag.New
     * @Ensures new.getUniqueID() is initialised with a valid ID
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#getNewUniqueID()
     */
    @DomainAPI
    public BugReport createBugReport(User user, String title, String description, PList<BugReport> dependencies,
                                     Subsystem subsystem) throws PermissionException, IllegalArgumentException {
        return subsystem.addBugReport(user, title, description, dependencies);
    }

    /**
     * This method creates a bug report in the system
     *
     * @param user         The User that wants to create this bug report
     * @param title        The title of the bugReport
     * @param description  The description of the bugReport
     * @param calendar     The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param subsystem    The subsystem this bug report belongs to
     * @throws IllegalArgumentException if isValidCreator(creator) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws PermissionException      if the given creator doesn't have the needed permission to create a bug report
     * @Ensures new.getDate() == current date at the moment of initialisation
     * @Ensures new.getTag() == Tag.New
     * @Ensures new.getUniqueID() is initialised with a valid ID
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#getNewUniqueID()
     */
    @DomainAPI
    public BugReport createBugReport(User user, String title, String description, GregorianCalendar calendar,
                                     PList<BugReport> dependencies, Subsystem subsystem) throws PermissionException, IllegalArgumentException {
        return subsystem.addBugReport(user, title, description, calendar, dependencies);
    }

    /**
     * This method creates a comment on a given BugReport
     *
     * @param user      The creator of the comment
     * @param bugReport The bug report to create the comment on
     * @param text      The text of the new comment
     * @return The new generated comment
     * @throws PermissionException      If the given User doesn't have the permission
     *                                  to create the comment
     * @throws IllegalArgumentException if the given comment is not valid for this bug report
     * @see BugReport#isValidComment(Comment)
     */
    @DomainAPI
    public Comment createComment(User user, BugReport bugReport, String text) throws PermissionException, IllegalArgumentException {
        return bugReport.addComment(user, text);
    }

    /**
     * This method creates a comment on the given comment with the given text by
     * the given user
     *
     * @param user    The creator of the new comment
     * @param comment The comment to create the comment on (= sub comment)
     * @param text    The text of the new Comment
     * @return The new generated comment
     * @throws PermissionException      If the given User doesn't have the permission
     *                                  to create the comment
     * @throws IllegalArgumentException if the given parameters are not valid for this comment
     * @see Comment#isValidSubComment(Comment)
     */
    @DomainAPI
    public Comment createComment(User user, Comment comment, String text) throws PermissionException, IllegalArgumentException {
        return comment.addSubComment(user, text);
    }

    /**
     * Clone the given {@link Project} and set a few attributes.
     *
     * @param cloneSource    The project to clone from.
     * @param versionID      The versionID for the clone project.
     * @param lead           The lead developer for the clone project.
     * @param startDate      The startDate for the clone project.
     * @param budgetEstimate The budgetEstimate for the clone project.
     * @return The resulting clone. Null if the source Clone is null.
     * @throws IllegalArgumentException Check @see.
     * @see Project#cloneProject(VersionID, Developer, GregorianCalendar, long)
     */
    @DomainAPI
    public Project cloneProject(Project cloneSource, VersionID versionID, Developer lead, GregorianCalendar startDate,
                                long budgetEstimate) throws IllegalArgumentException {
        Project clone = cloneSource.cloneProject(versionID, lead, startDate, budgetEstimate);
        if (clone != null) {
            addProject(clone);
        }
        return clone;
    }

    /**
     * This method returns all the developers associated with the project the
     * bug report belongs to
     *
     * @param bugRep The bug report
     * @return The list of all devs in the project
     * @see BugReport#getSubsystem()
     * @see Subsystem#getAllDev()
     */
    @DomainAPI
    public PList<Developer> getDeveloperInProject(BugReport bugRep) {
        return bugRep.getSubsystem().getAllDev();
    }

    /**
     * This method adds all the users of the given list to the given project by
     * the given user
     *
     * @param user    The user that wants to add all the given developers to the
     *                bug report
     * @param bugRep  The bug report to add all the developers to
     * @param devList The developers to add to the bug report
     * @throws PermissionException      If the given user doesn't have the needed
     *                                  permission to add users to the given bug report
     * @throws IllegalArgumentException If the given user was null
     * @throws IllegalArgumentException If the given developer was not valid for
     *                                  this bug report
     */
    @DomainAPI
    public void addUsersToBugReport(User user, BugReport bugRep, PList<Developer> devList)
            throws PermissionException, IllegalArgumentException {
        for (Developer dev : devList) {
            bugRep.addUser(user, dev);
        }
    }

    /**
     * This method return a PList of all the possible tags that can be set.
     *
     * @return a Plist of all the possible tags
     */
    @DomainAPI
    public PList<Tag> getAllTags() {
        return PList.<Tag>empty().plusAll(Arrays.asList(Tag.values()));
    }

    /**
     * This method lets the given user set the tag of the given bug report to
     * the given tag
     *
     * @param bugrep The bug report of which the tag gets to be set
     * @param tag    The given tag to set
     * @param user   The user that wishes to set the tag
     * @throws PermissionException      If the user doesn't have the needed
     *                                  permission to set the given tag to the bug report
     * @throws IllegalArgumentException If the given tag isn't a valid tag to
     *                                  set to the bug report
     */
    @DomainAPI
    public void setTag(BugReport bugrep, Tag tag, User user) throws PermissionException, IllegalArgumentException {
        bugrep.setTag(tag, user);
    }

    /**
     * This method gets the details of a given bug report and checks the needed
     * permission
     *
     * @param user   The user that wants to inspect the bugReport
     * @param bugRep The bug report that the user wants to inspect
     * @return The details of the bug report
     * @throws PermissionException If the given user doesn't have the needed
     *                             permission
     */
    @DomainAPI
    public String getDetails(User user, BugReport bugRep) throws PermissionException {
        if (!user.hasPermission(UserPerm.INSPECT_BUGREPORT)) {
            throw new PermissionException("the given user doesn't have the needed permission!");
        }
        return bugRep.getDetails();
    }

    /**
     * This method returns a list of all possible roles in the system.
     *
     * @return a PList of all possible roles.
     */
    @DomainAPI
    public PList<Role> getAllRoles() {
        return PList.<Role>empty().plusAll(Arrays.asList(Role.values()));
    }

    /**
     * This method let's a user assign a role to a developer in a given project
     * .
     *
     * @param project   The project in which the user will be assigned.
     * @param user      The user that assigns the role to the dev
     * @param developer The developer that gets a role assigned
     * @param role      The role that will be assigned
     * @throws PermissionException      if the user doesn't have the needed
     *                                  permission.
     * @throws IllegalArgumentException If the given role was null
     * @throws IllegalArgumentException If the given user is null
     * @throws IllegalArgumentException If the given project was null
     */
    @DomainAPI
    public void assignToProject(Project project, User user, Developer developer, Role role) throws PermissionException, IllegalArgumentException {
        if (project == null) {
            throw new IllegalArgumentException("The given project was null");
        }
        project.setRole(user, developer, role);
    }

    /***
     * Get the developers assigned to the given bugReport.
     *
     * @param bugReport The {@link BugReport} to check for.
     * @return The list of developers assigned to the bugreport.
     */
    @DomainAPI
    public PList<Developer> getDevelopersOfBugReport(BugReport bugReport) {
        if (bugReport == null) {
            throw new IllegalArgumentException("The given bugreport was null.");
        }

        return bugReport.getUserList();
    }
}
