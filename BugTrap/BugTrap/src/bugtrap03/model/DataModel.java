package bugtrap03.model;

import bugtrap03.bugdomain.*;
import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.bugreport.Comment;
import bugtrap03.bugdomain.bugreport.Tag;
import bugtrap03.bugdomain.notificationdomain.*;
import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import bugtrap03.bugdomain.permission.PermissionException;
import bugtrap03.bugdomain.permission.UserPerm;
import bugtrap03.bugdomain.usersystem.*;
import com.google.java.contract.Ensures;
import purecollections.PList;

import java.util.*;

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
        this.history = new Stack<>();
    }

    private PList<User> userList;
    private PList<Project> projectList;
    private Stack<ModelCmd> history;

    /**
     * Add the cmd to the cmd history.
     * <br> This will only be added when the cmd has been executed.
     *
     * @param cmd The {@link ModelCmd} to add to the history.
     *
     * @throws IllegalStateException When cmd is a null reference or has not been executed yet.
     * @see ModelCmd#isExecuted()
     */
    private void addToHistory(ModelCmd cmd) throws IllegalStateException {
        if (cmd == null || !cmd.isExecuted()) {
            throw new IllegalStateException("Tried to add a ModelCmd that hasn't been executed to the history.");
        }

        history.push(cmd);
    }

    /**
     * Get the last x ModelCmds added to the history of this DataModel. The most recently added ModelCmd will be in the
     * first index of the list. When x is &lt 0 an empty list will be returned. When x is &gt the size of the history a
     * list of the whole history will be returned.
     *
     * @param x The amount of ModelCmds to return. When less than 0 an empty List will be returned.
     * @return The list of x last commands. All commands will be given when x is higher than the current amount.
     */
    @DomainAPI
    public PList<ModelCmd> getHistory(int x) {
        if (x <= 0) {
            return PList.<ModelCmd>empty();
        }

        x = Math.min(x, history.size());

        Stack<ModelCmd> temp = (Stack<ModelCmd>) history.clone();
        PList<ModelCmd> result = PList.<ModelCmd>empty();

        for (int i = 0; i < x; i++) {
            ModelCmd cmd = temp.pop();
            result = result.plus(cmd);
        }

        return result;
    }

    /**
     * Undo the last {@link ModelCmd} executed.
     *
     * @return Whether the undoing was successful. When there was no ModelCmd true will be returned.
     */
    private boolean undoLastModelCmd() {
        if (this.history.empty()) {
            return true;
        }

        ModelCmd cmd = this.history.pop();
        return cmd.undo();
    }

    /**
     * Undo the last x possible model changes.
     * <br> When there are only y changes to undo (y &lt x) only y will be undone.
     * <br> As soon as the undoing of an action fails the undoing is stopped and false is returned.
     *
     * @param x The amount of changes to undo.
     *
     * @return True if all x changes were undone. When x &lt= 0 true is returned.
     * @throws PermissionException When user == null or does not have sufficient permissions.
     */
    @DomainAPI
    public boolean undoLastChanges(User user, int x) throws PermissionException {
        if (user == null || !user.hasPermission(UserPerm.UNDO_COMMANDS)) {
            throw new PermissionException("You do not have sufficient permissions to undo.");
        }

        if (x <= 0) {
            return true;
        }

        for (int i = x; i > 0; i--) {
            if (!undoLastModelCmd()) {
                return false;
            }
        }
        return true;
    }

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
    void addUser(User user) throws NullPointerException {
        this.userList = userList.plus(user);
    }

    /**
     * Add the {@link Project} to the list of projects.
     *
     * @param project The project to add.
     * @throws NullPointerException If project is null.
     */
    void addProject(Project project) throws NullPointerException {
        this.projectList = projectList.plus(project);
    }

    /**
     * Delete the {@link Project} from the list of projects.
     *
     * @param project The project to delete.
     */
    void deleteProject(Project project) {
        this.projectList = projectList.minus(project);
    }

    /**
     * Delete the {@link User} from the list of users.
     *
     * @param user The user to delete.
     */
    void deleteUser(User user) {
        this.userList = userList.minus(user);
    }

    /**
     * Get the list of users in this system who have the exact class type userType.
     *
     * @param <U> extends User type.
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
     * Get the list of users in this system who are of type that is or extends userType.
     *
     * @param <U> extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType or a class type that extends userType.
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
     * @param username The username of the issuer.
     * @param firstName The first name of the issuer.
     * @param middleName The middle name of the issuer.
     * @param lastName The last name of the issuer.
     * @return The created {@link Issuer}
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Issuer createIssuer(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(this, username, firstName, middleName, lastName);
        Issuer issuer = cmd.exec();
        this.addToHistory(cmd);
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
     */
    @DomainAPI
    public Issuer createIssuer(String username, String firstName, String lastName) throws IllegalArgumentException {
        CreateIssuerModelCmd cmd = new CreateIssuerModelCmd(this, username, firstName, lastName);
        Issuer issuer = cmd.exec();
        this.addToHistory(cmd);
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
     */
    @DomainAPI
    public Developer createDeveloper(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(this, username, firstName, middleName, lastName);
        Developer dev = cmd.exec();
        this.addToHistory(cmd);
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
     */
    @DomainAPI
    public Developer createDeveloper(String username, String firstName, String lastName) throws IllegalArgumentException {
        CreateDeveloperModelCmd cmd = new CreateDeveloperModelCmd(this, username, firstName, lastName);
        Developer dev = cmd.exec();
        this.addToHistory(cmd);
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
     */
    @DomainAPI
    public Administrator createAdministrator(String username, String firstName, String middleName, String lastName) throws IllegalArgumentException {
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(this, username, firstName, middleName, lastName);
        Administrator admin = cmd.exec();
        this.addToHistory(cmd);
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
     */
    @DomainAPI
    public Administrator createAdministrator(String username, String firstName, String lastName) throws IllegalArgumentException {
        CreateAdminModelCmd cmd = new CreateAdminModelCmd(this, username, firstName, lastName);
        Administrator admin = cmd.exec();
        this.addToHistory(cmd);
        return admin;
    }

    /**
     * This method creates a new {@link Project} in the system
     *
     *
     * @param versionID
     * @param name The name of the project
     * @param description The description of the project
     * @param startDate The start date of the project
     * @param lead The lead developer of this project
     * @param budget The budget estimate for this project
     * @param creator The creator of this Project
     *
     * @return The created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient permissions
     */
    @DomainAPI
    public Project createProject(VersionID versionID, String name, String description, GregorianCalendar startDate, Developer lead,
                                 long budget, User creator) throws IllegalArgumentException, PermissionException {
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(this, versionID, name, description, startDate, lead, budget, creator);
        Project project = cmd.exec();
        addToHistory(cmd);
        return project;
    }

    /**
     * This method creates a new {@link Project} in the system
     *
     *
     * @param versionID
     * @param name The name of the project
     * @param description The description of the project
     * @param lead The lead developer of this project
     * @param budget The budget estimate for this project
     * @param creator The creator of this project
     *
     * @return The created project
     * @throws IllegalArgumentException if the constructor of project fails
     * @throws PermissionException If the given creator has insufficient permissions
     */
    @DomainAPI
    public Project createProject(VersionID versionID, String name, String description, Developer lead, long budget, User creator)
            throws IllegalArgumentException, PermissionException {
        CreateProjectModelCmd cmd = new CreateProjectModelCmd(this, versionID, name, description, lead, budget, creator);
        Project project = cmd.exec();
        addToHistory(cmd);
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
     * This method gets all bug reports in the system that are visible to the given user.
     *
     * @param user The user to get all bug reports for. All bug reports will be visible for him. null would mean the bug
     * report is visible to everyone.
     * @return a list of all bug reports in the system that are visible for the user.
     *
     * @see BugReport#isVisibleTo(bugtrap03.bugdomain.usersystem.User)
     */
    @DomainAPI
    public PList<BugReport> getAllBugReports(User user) {
        ArrayList<BugReport> list = new ArrayList();
        PList<BugReport> BugReportList = getAllBugReports();

        for (BugReport bugReport : BugReportList) {
            if (bugReport.isVisibleTo(user)) {
                list.add(bugReport);
            }
        }

        return PList.<BugReport>empty().plusAll(list);
    }

    /**
     * This method gets all bug reports in the system.
     *
     * @return a list of all bug reports in the system.
     */
    private PList<BugReport> getAllBugReports() {
        ArrayList<BugReport> list = new ArrayList<>();
        for (Project project : this.projectList) {
            PList<BugReport> bugReports = project.getAllBugReports();
            for (BugReport bugReport : bugReports) {
                list.add(bugReport);
            }
        }
        return PList.<BugReport>empty().plusAll(list);
    }

    /**
     * This method updates the given project with the new given attributes
     *
     * @param name The new name of the given project
     * @param description The new description of the given project
     * @param startDate The new startDate of the given project
     * @param budgetEstimate The new budget estimate of the given project
     * @throws PermissionException if the given user doesn't have the needed permission to update a project.
     * @throws IllegalArgumentException When any of the arguments is invalid.
     * <br><dt><b>Postconditions:</b><dd> The attributes of the given project will not be updated if an error was thrown
     */
    @DomainAPI
    public Project updateProject(Project proj, User user, String name, String description, GregorianCalendar startDate,
            Long budgetEstimate) throws IllegalArgumentException, PermissionException {
        UpdateProjectModelCmd cmd = new UpdateProjectModelCmd(proj, user, name, description, startDate, budgetEstimate);
        Project project = cmd.exec();
        addToHistory(cmd);
        return project;
    }

    /**
     * This method returns all subsystems of a given project
     *
     * @param project The project to print the subsystems from
     * @return a PList containing all subsystems of a project. An empty list when project == null.
     * @see AbstractSystem#getAllSubsystems()
     */
    @DomainAPI
    public PList<Subsystem> getAllSubsystems(Project project) {
        if(project == null) {
            return PList.<Subsystem>empty();
        }
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
     * @param user The user that wants to delete the project
     * @param project The project which has to be removed
     * @return The removed project
     * @throws PermissionException If the given user doesn't have the permission to delete a project
     * @throws IllegalArgumentException When user == null
     */
    @DomainAPI
    public Project deleteProject(User user, Project project) throws PermissionException, IllegalArgumentException {
        DeleteProjectModelCmd cmd = new DeleteProjectModelCmd(this, user, project);
        Project proj = cmd.exec();
        addToHistory(cmd);
        return proj;
    }

    /**
     * This method creates a new subsystem in the given Project/Subsystem
     *
     * @param user The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsystem to
     * @param name The name of the new Subsystem
     * @param description The description of the new Subsystem
     *
     * @return The created subsystem
     * @throws PermissionException If the user doesn't have the permission to create a subsystem
     * @throws IllegalArgumentException When any of the arguments is invalid.
     */
    @DomainAPI
    public Subsystem createSubsystem(User user, AbstractSystem abstractSystem, String name, String description)
            throws PermissionException, IllegalArgumentException {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(user, abstractSystem, name, description);
        Subsystem sub = cmd.exec();
        addToHistory(cmd);
        return sub;
    }

    /**
     * This method creates a new subsystem in the given Project/Subsystem
     *
     * @param user The user that wants to create the subsystem
     * @param abstractSystem The Project/Subsystem to add the new subsystem to
     * @param versionID The versionID of the new Subsystem.
     * @param name The name of the new Subsystem
     * @param description The description of the new Subsystem
     *
     * @return The created subsystem
     * @throws PermissionException If the user doesn't have the permission to create a subsystem
     * @throws IllegalArgumentException When user == null || abstractSystem == null
     */
    @DomainAPI
    public Subsystem createSubsystem(User user, AbstractSystem abstractSystem, VersionID versionID, String name,
            String description) throws PermissionException, IllegalArgumentException {
        CreateSubsystemModelCmd cmd = new CreateSubsystemModelCmd(user, abstractSystem, versionID, name, description);
        Subsystem sub = cmd.exec();
        addToHistory(cmd);
        return sub;
    }

    /**
     * This method creates and adds a bug report to the list of associated bugReports of this subsystem
     *
     * @param subsystem The subsystem the new bugreport belongs to
     * @param user The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param impactFactor  The impact factor of the new bug report
     * @param isPrivate The boolean that says if this bug report should be private or not
     * @throws IllegalArgumentException if isValidCreator(user) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException if the given creator doesn't have the needed permission to create a bug report
     *
     * @return The create bug report
     *
     * <br><dt><b>Postconditions:</b><dd> result.getDate() == current date at the moment of initialization
     * <br><dt><b>Postconditions:</b><dd> result.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    @DomainAPI
    public BugReport createBugReport(Subsystem subsystem, User user, String title, String description,
                                     PList<BugReport> dependencies, Milestone milestone, double impactFactor, boolean isPrivate)
            throws IllegalArgumentException, PermissionException {
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsystem, user, title, description,
                null, dependencies, milestone, impactFactor, isPrivate, null, null, null);
        BugReport bugReport = cmd.exec();
        addToHistory(cmd);
        return bugReport;
    }

    /**
     * This method creates and adds a bug report to the list of associated bugReports of this subsystem
     *
     * @param subsystem The subsystem the new bugreport belongs to
     * @param user The User that wants to create this bug report
     * @param title The title of the bugReport
     * @param description The description of the bugReport
     * @param creationDate The creationDate of the bugReport
     * @param dependencies The depended bug reports of this bug report
     * @param milestone The milestone of the bug report
     * @param impactFactor  the impact factor of the new bug report
     * @param isPrivate The boolean that says if this bug report should be private or not
     * @param trigger A trigger used to trigger the bug. Can be NULL.
     * @param stacktrace The stacktrace got when the bug was triggered. Can be NULL.
     * @param error The error got when the bug was triggered. Can be NULL.
     * @throws IllegalArgumentException if isValidCreator(user) fails
     * @throws IllegalArgumentException if isValidUniqueID(uniqueID) fails
     * @throws IllegalArgumentException if isValidTitle(title) fails
     * @throws IllegalArgumentException if isValidDescription(description) fails
     * @throws IllegalArgumentException if isValidCreationDate(creationDate) fails
     * @throws IllegalArgumentException if isValidDependencies(dependencies) fails
     * @throws IllegalArgumentException if isValidSubSystem(subsystem) fails
     * @throws IllegalArgumentException if isValidMilestone(milestone) fails
     * @throws PermissionException if the given creator doesn't have the needed permission to create a bug report
     *
     * @return The create bug report
     *
     * <br><dt><b>Postconditions:</b><dd> if creationDate == null: result.getDate() == current date at the moment of
     * initialization
     * <br><dt><b>Postconditions:</b><dd> result.getUniqueID() is an unique ID for this bug report
     *
     * @see BugReport#isValidCreator(User)
     * @see BugReport#isValidUniqueID(long)
     * @see BugReport#isValidTitle(String)
     * @see BugReport#isValidDescription(String)
     * @see BugReport#isValidCreationDate(GregorianCalendar)
     * @see BugReport#isValidDependencies(PList)
     * @see BugReport#isValidSubsystem(Subsystem)
     * @see BugReport#isValidMilestone(Milestone)
     */
    @Ensures("result.getTag() == Tag.New && result.getUniqueID() != null")
    @DomainAPI
    public BugReport createBugReport(Subsystem subsystem, User user, String title, String description,
                                     GregorianCalendar creationDate, PList<BugReport> dependencies, Milestone milestone,
                                     double impactFactor, boolean isPrivate, String trigger, String stacktrace, String error)
            throws IllegalArgumentException, PermissionException {
        CreateBugReportModelCmd cmd = new CreateBugReportModelCmd(subsystem, user, title, description,
                creationDate, dependencies, milestone, impactFactor, isPrivate, trigger, stacktrace, error);
        BugReport bugReport = cmd.exec();
        addToHistory(cmd);
        return bugReport;
    }

    /**
     * This method creates a comment on a given BugReport
     *
     * @param user The creator of the comment
     * @param bugReport The bug report to create the comment on
     * @param text The text of the new comment
     * @return The new generated comment
     * @throws PermissionException If the given User doesn't have the permission to create the comment
     * @throws IllegalArgumentException When bugReport == null
     * @throws IllegalArgumentException if the given comment is not valid for this bug report
     * @see BugReport#isValidComment(Comment)
     */
    @DomainAPI
    public Comment createComment(User user, BugReport bugReport, String text) throws PermissionException, IllegalArgumentException {
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(user, bugReport, text);
        Comment comment = cmd.exec();
        addToHistory(cmd);
        return comment;
    }

    /**
     * This method creates a comment on the given comment with the given text by the given user
     *
     * @param user The creator of the new comment
     * @param parentComment The comment to create the comment on (= sub comment)
     * @param text The text of the new Comment
     * @return The new generated comment
     * @throws PermissionException If the given User doesn't have the permission to create the comment
     * @throws IllegalArgumentException When parentComment == null
     * @throws IllegalArgumentException if the given parameters are not valid for this comment
     * @see Comment#isValidSubComment(Comment)
     */
    @DomainAPI
    public Comment createComment(User user, Comment parentComment, String text) throws PermissionException, IllegalArgumentException {
        CreateCommentModelCmd cmd = new CreateCommentModelCmd(user, parentComment, text);
        Comment comment = cmd.exec();
        addToHistory(cmd);
        return comment;
    }

    /**
     * Clone the given {@link Project} and set a few attributes.
     *
     * @param cloneSource The project to clone from.
     * @param versionID The versionID for the clone project.
     * @param lead The lead developer for the clone project.
     * @param startDate The startDate for the clone project.
     * @param budgetEstimate The budgetEstimate for the clone project.
     * @return The resulting clone.
     * @throws IllegalArgumentException Check @see.
     * @see Project#cloneProject(VersionID, Developer, GregorianCalendar, long)
     */
    @DomainAPI
    public Project cloneProject(Project cloneSource, VersionID versionID, Developer lead, GregorianCalendar startDate,
            long budgetEstimate) throws IllegalArgumentException {
        CloneProjectModelCmd cmd = new CloneProjectModelCmd(this, cloneSource, versionID, lead, startDate, budgetEstimate);
        Project proj = cmd.exec();
        addToHistory(cmd);
        return proj;
    }

    /**
     * This method returns all the developers associated with the project the bug report belongs to
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
     * This method adds all the users of the given list to the given project by the given user
     *
     * @param user The user that wants to add all the given developers to the bug report
     * @param bugRep The bug report to add all the developers to
     * @param devList The developers to add to the bug report
     * @throws PermissionException If the given user doesn't have the needed permission to add users to the given bug
     * report
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When bugRep == null
     * @throws IllegalArgumentException If the given developer was not valid for this bug report
     */
    @DomainAPI
    public void addUsersToBugReport(User user, BugReport bugRep, PList<Developer> devList)
            throws PermissionException, IllegalArgumentException {
        AddUsersToBugReportModelCmd cmd = new AddUsersToBugReportModelCmd(user, bugRep, devList);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method return a PList of all the possible tags that can be set.
     *
     * @return a PList of all the possible tags
     */
    @DomainAPI
    public PList<Tag> getAllTags() {
        return PList.<Tag>empty().plusAll(Arrays.asList(Tag.values()));
    }

    /**
     * This method lets the given user set the tag of the given bug report to the given tag
     *
     * @param bugrep The bug report of which the tag gets to be set
     * @param tag The given tag to set
     * @param user The user that wishes to set the tag
     * @throws PermissionException If the user doesn't have the needed permission to set the given tag to the bug report
     * @throws IllegalArgumentException If the given tag isn't a valid tag to set to the bug report
     */
    @DomainAPI
    public void setTag(BugReport bugrep, Tag tag, User user) throws PermissionException, IllegalArgumentException {
        SetTagForBugReportModelCmd cmd = new SetTagForBugReportModelCmd(bugrep, tag, user);
        cmd.exec();
        addToHistory(cmd); 
    }

    /**
     * This method gets the details of a given bug report and checks the needed permission
     *
     * @param user The user that wants to inspect the bugReport
     * @param bugRep The bug report that the user wants to inspect
     * @return The details of the bug report
     * @throws PermissionException If the given user doesn't have the needed permission
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
     * This method lets a user assign a role to a developer in a given project .
     *
     * @param project The project in which the user will be assigned.
     * @param user The user that assigns the role to the developer
     * @param developer The developer that gets a role assigned
     * @param role The role that will be assigned
     *
     * @throws PermissionException When the user does not have sufficient permissions.
     * @throws IllegalArgumentException When role == null || user == null || project == null || developer == null
     */
    @DomainAPI
    public void assignToProject(Project project, User user, Developer developer, Role role) throws PermissionException, IllegalArgumentException {
        AssignToProjectModelCmd cmd = new AssignToProjectModelCmd(project, user, developer, role);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method adds a given test to the bug report state
     *
     * @param bugReport The bug report to add the given test to
     * @param user The user that wants to add the test to this bug report state
     * @param test The test that the user wants to add
     *
     * @throws PermissionException If the given user doesn't have the permission to add a test
     * @throws IllegalStateException If the current state doesn't allow to add a test
     * @throws IllegalArgumentException If the given test is not a valid test for this bug report state
     *
     * @see BugReport#isValidTest(String)
     */
    @DomainAPI
    public void addTest(BugReport bugReport, User user, String test) throws PermissionException, IllegalStateException, IllegalArgumentException {
        AddTestToBugReportModelCmd cmd = new AddTestToBugReportModelCmd(bugReport, user, test);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method adds a given patch to this bug report state
     *
     * @param bugReport The bug report to add the patch to
     * @param user The user that wants to add the patch to this bug report state
     * @param patch The patch that the user wants to submit
     *
     * @throws PermissionException If the given user doesn't have the permission to add a patch to this bug report state
     * @throws IllegalStateException If the given patch is invalid for this bug report
     * @throws IllegalArgumentException If the given patch is not valid for this bug report state
     * @throws IllegalArgumentException When bugReport == null
     */
    @DomainAPI
    public void addPatch(BugReport bugReport, User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException {
        AddPatchToBugReportModelCmd cmd = new AddPatchToBugReportModelCmd(bugReport, user, patch);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method selects a patch for this bug report state
     *
     * @param bugReport The bug report to add the patch to
     * @param user The user that wants to select the patch
     * @param patch The patch that the user wants to select
     *
     * @throws PermissionException If the given user doesn't have the permission to select a patch for this bug report
     * state
     * @throws IllegalStateException If the current state doesn't allow the selecting of a patch
     * @throws IllegalArgumentException If the given patch is not a valid patch to be selected for this bug report state
     * @throws IllegalArgumentException When bugReport == null
     */
    @DomainAPI
    public void selectPatch(BugReport bugReport, User user, String patch) throws PermissionException, IllegalStateException, IllegalArgumentException {
        SelectPatchFromBugReportModelCmd cmd = new SelectPatchFromBugReportModelCmd(bugReport, user, patch);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method gives the selected patch of this bug report states a score
     *
     * @param bugReport The bug report to evaluate
     * @param user The user that wants to assign a score to this bug report
     * @param score The score that the creator wants to give
     *
     * @throws IllegalStateException If the current state doesn't allow assigning a score
     * @throws IllegalArgumentException If the given score is not a valid score for this bug report state
     * @throws IllegalArgumentException When bugReport == null
     */
    @DomainAPI
    public void giveScore(BugReport bugReport, User user, int score) throws IllegalStateException, IllegalArgumentException, PermissionException {
        GiveScoreToBugReportModelCmd cmd = new GiveScoreToBugReportModelCmd(bugReport, user, score);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method sets the duplicate of the given bug report to the given duplicate
     * @param user      The user that wants to set the duplicate of the bug report
     * @param bugReport The bug report that will be assigned the duplicate
     * @param duplicate The duplicate of the given bug report
     *
     * @throws IllegalStateException    If the bugReport doesn't allow a duplicate to be set
     * @throws IllegalArgumentException If the given bugReport/duplicate is invalid
     * @throws PermissionException      If the user doesn't have the needed permission to set the duplicate
     */
    @DomainAPI
    public void setDuplicate(User user, BugReport bugReport, BugReport duplicate)
            throws IllegalStateException, IllegalArgumentException, PermissionException {
        SetDuplicateBugReportModelCmd cmd = new SetDuplicateBugReportModelCmd(bugReport, duplicate, user);
        cmd.exec();
        addToHistory(cmd);
    }

    /**
     * This method lets a user register for notifications concerning a comment creation on a subject.
     *
     * @param user the user that wishes to subscribe for notifications
     * @param subject the subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public CommentMailbox registerForCommentNotifications(User user, Subject subject)
            throws IllegalArgumentException, IllegalStateException{
        RegisterForCommentNotificationsModelCmd cmd = new RegisterForCommentNotificationsModelCmd(user, subject);
        CommentMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a comment creation on a subject.
     *
     * @param user the user that wishes to subscribe for notifications
     * @param abstractSystemSubject the abstract system subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the abstract system subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public CreationMailbox registerForCreationNotifications(User user, AbstractSystemSubject abstractSystemSubject)
            throws IllegalArgumentException, IllegalStateException{
        RegisterForCreationNotificationsModelCmd cmd =
                new RegisterForCreationNotificationsModelCmd(user, abstractSystemSubject);
        CreationMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a specific tag change on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param subject   the subject on which the user wishes to subscribe
     * @param enumSet   the enumset of specific tags to which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public TagMailBox registerForSpecificTagsNotifications(User user, Subject subject, EnumSet<Tag> enumSet)
            throws IllegalArgumentException, IllegalStateException {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(user, subject, enumSet);
        TagMailBox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a tag change on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param subject   the subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public TagMailBox registerForAllTagsNotifications(User user, Subject subject)
            throws IllegalArgumentException, IllegalStateException {
        RegisterForTagNotificationsModelCmd cmd = new RegisterForTagNotificationsModelCmd(user, subject);
        TagMailBox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a forking on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param projectSubject   the subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public ForkMailbox registerForForkNotifactions(User user, ProjectSubject projectSubject)
            throws IllegalArgumentException, IllegalStateException {
        RegisterForForkNotificationsModelCmd cmd = new RegisterForForkNotificationsModelCmd(user, projectSubject);
        ForkMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a specific milestone change on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param asSubject   the subject on which the user wishes to subscribe
     * @param milestone     the milestone for which the user wishes to receive notifications
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public MilestoneMailbox registerForMilestoneNotifactions(User user, AbstractSystemSubject asSubject, Milestone milestone)
            throws IllegalArgumentException, IllegalStateException {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(user, asSubject, milestone);
        MilestoneMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a milestone change on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param asSubject   the subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public MilestoneMailbox registerForAllMilestoneNotifactions(User user, AbstractSystemSubject asSubject)
            throws IllegalArgumentException, IllegalStateException {
        RegisterForMilestoneNotificationsModelCmd cmd = new RegisterForMilestoneNotificationsModelCmd(user, asSubject);
        MilestoneMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }

    /**
     * This method lets a user register for notifications concerning a versionID change on a subject.
     *
     * @param user      the user that wishes to subscribe for notifications
     * @param abstractSystemSubject   the subject on which the user wishes to subscribe
     *
     * @return The newly created mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the subject is invalid (=null).
     * @throws IllegalStateException If the current state of the command is invalid.
     */
    @DomainAPI
    public VersionIDMailbox registerForVersionNotifications(User user, AbstractSystemSubject abstractSystemSubject)
            throws IllegalArgumentException, IllegalStateException{
        RegisterForVersionNotificationsModelCmd cmd =
                new RegisterForVersionNotificationsModelCmd(user, abstractSystemSubject);
        VersionIDMailbox newMailbox = cmd.exec();
        addToHistory(cmd);
        return newMailbox;
    }


    /**
     * This method lets a user unregister for notifications
     *
     * @param user      The user that wishes to unregister.
     * @param mailbox   The mailbox that contains the notifications from which the user wishes to unregister.
     *
     * @return The mailbox that represents the registration for notifications.
     *
     * @throws IllegalArgumentException If the mailbox is not found in
     * @throws IllegalStateException If the command has already been executed.
     */
    @DomainAPI
    public AbstractMailbox unregisterFromNotifications(User user, AbstractMailbox mailbox) throws IllegalArgumentException, IllegalStateException{
        UnregisterFromNotificationsModelCmd cmd = new UnregisterFromNotificationsModelCmd(user, mailbox);
        cmd.exec();
        addToHistory(cmd);
        return  mailbox;
    }

    /**
     * This method lets the given user change the milestone of the given abstractSystem to the given milestone
     *
     * @param user              The user that wants to change the milestone
     * @param abstractSystem    The abstractSystem that needs a milestone change
     * @param milestone         The new milestone
     *
     * @throws PermissionException  If the given user doesn't have the needed permission
     */
    @DomainAPI
    public void setMilestone(User user, AbstractSystem abstractSystem, Milestone milestone) throws PermissionException {
        SetMilestoneAbstractSystemModelCmd cmd = new SetMilestoneAbstractSystemModelCmd(user, abstractSystem, milestone);
        cmd.exec();
        addToHistory(cmd);
    }
    
    /**
     * Split the given subsystem into 2 subsystems, subsystem and a newly created one.
     * <br> subsystem will keep all the direct subsystems and bugReports that are also contained in the list subsystems1
     * or bugReports1 respectively... The rest will go to subsystem2.
     *
     * @param subsystem         The Subsystem to split into itself and another newly created Subsystem.
     * @param subsystem1Name    The new name for subsystem.
     * @param subsystem1Desc    The new description for subsystem.
     * @param subsystem2Name    The name for subsystem 2 resulting from the split.
     * @param subsystem2Desc    The description for subsystem 2 resulting from the split.
     * @param subsystems1       The list of subsystems to keep in subsystem.
     * @param bugReports1       The list of bugReports to keep in subsystem.
     * @param user              The user who wants to split the given subsystem.
     *
     * @return The extra newly created subsystem, subsystem 2. The other subsystem, subsystem 1 remains as subsystem.
     *
     * @throws PermissionException When the user does not have sufficient permissions
     * @throws IllegalArgumentException When subsystem == null
     * @throws IllegalArgumentException When user == null
     * @throws IllegalArgumentException When any of the arguments passed is invalid for a subsystem.
     */
    @DomainAPI
    public Subsystem splitSubsystem(Subsystem subsystem, String subsystem1Name, String subsystem1Desc, String subsystem2Name, String subsystem2Desc, PList<Subsystem> subsystems1, PList<BugReport> bugReports1, User user) throws PermissionException, IllegalArgumentException {
        SplitSubsystemModelCmd cmd = new SplitSubsystemModelCmd(subsystem, subsystem1Name, subsystem1Desc, subsystem2Name, subsystem2Desc, subsystems1, bugReports1, user);
        Subsystem result = cmd.exec();
        addToHistory(cmd);
        return result;
    }

    /**
     * This method safely merges 2 subsystems together
     *
     * @param user              The user that wants to merge the subsystems
     * @param subsystem1        The first subsystem to merge
     * @param subsystem2        The second subsystem to merge
     * @param newName           The new name of the merged subsystem
     * @param newDescription    The new description of the merged subsystem
     *
     * @return  The merged subsystem
     *
     * @throws IllegalArgumentException When there is an illegal argument passed.
     * @throws NullPointerException     When there is a null where it shouldn't. Read ModelCmd specific documentation.
     * @throws PermissionException      When the user does not have sufficient permissions
     */
    @DomainAPI
    public Subsystem mergeSubsystems(User user, Subsystem subsystem1, Subsystem subsystem2,
                                     String newName, String newDescription) throws PermissionException {
        MergeSubsystemsModelCmd cmd = new MergeSubsystemsModelCmd(user, subsystem1, subsystem2, newName, newDescription);
        Subsystem result = cmd.exec();
        addToHistory(cmd);
        return result;
    }

    /**
     * This method returns the number of Closed bug reports where the given user is assigned to
     *
     * @param user  The user that needs to be assigned to the bug report
     *
     * @return  The number of Closed bug reports where the given user is assigned to
     */
    @DomainAPI
    public long getNbClosedBRForDev(User user){
        PList<BugReport> bugList = this.getAllBugReports();
        return bugList.parallelStream().
                filter(u -> (u.getUserList().contains(user) && u.getTag() == Tag.CLOSED)).count();
    }

    /**
     * This method returns the number of unfinished bug reports the given user is assigned to
     * @param user  The user that needs to be assigned to the bug report
     * @return  The number of unfinished bug reports the given user is assigned to
     */
    @DomainAPI
    public long getNbUnfinishedBRForDev(User user){
        PList<BugReport> bugList = this.getAllBugReports();
        return bugList.parallelStream().
                filter(u -> (u.getUserList().contains(user) && ! u.isResolved())).count();
    }
    
    /**
     * Get the amount of bug reports that the given user has submitted and is marked as duplicate.
     *
     * @param user The user who has submitted the bug reports.
     * @return The amount of duplicate bug reports this user has submitted.
     */
    @DomainAPI
    public long getNbDuplicateBRsSubmitted(User user) {
        PList<BugReport> list = getAllBugReports();

        return list.stream().
                filter(u -> u.getCreator() == user && u.getTag() == Tag.DUPLICATE).
                count();
    }
    
    
    
    /**
     * Get the amount of bug reports that the given user has submitted and is marked as not a bug.
     *
     * @param user The user who has submitted the bug reports.
     * @return The amount of notABugReport bug reports this user has submitted.
     */
    @DomainAPI
    public long getNbNotABugReportBRsSubmitted(User user) {
        PList<BugReport> list = getAllBugReports();

        return list.stream().
                filter(u -> u.getCreator() == user && u.getTag() == Tag.NOT_A_BUG).
                count();
    }

    /**
     * Get the amount of bug reports that the given user has submitted.
     *
     * @param user The user who has submitted the bug reports.
     * @return The amount of bug reports this user has submitted.
     */
    @DomainAPI
    public long getNbBRSubmitted(User user) {
        PList<BugReport> list = getAllBugReports();

        return list.stream().
                filter(u -> u.getCreator() == user).
                count();
    }
    
    /**
     * This method returns a list of all project of a lead developer.
     *
     * @param lead The lead developer
     * @return a PList of all projects that the user is a lead of.
     */
    @DomainAPI
    public PList<Project> getAllProjectsOfLead(Developer lead) {
        PList<Project> allProjectsUser = PList.<Project>empty();
        for (Project proj : projectList) {
            if (proj.getLead() == lead) {
                allProjectsUser = allProjectsUser.plus(proj);
            }
        }
        return allProjectsUser;
    }
}
