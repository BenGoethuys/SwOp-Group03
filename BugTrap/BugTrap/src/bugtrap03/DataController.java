package bugtrap03;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.Project;
import bugtrap03.bugdomain.Subsystem;
import bugtrap03.permission.PermissionException;
import bugtrap03.usersystem.Developer;
import bugtrap03.usersystem.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import purecollections.PList;

/**
 *
 * @author Group 03
 * @version 0.1
 */
public class DataController {

    /**
     * TODO
     *
     * @param model
     * @throws NullPointerException
     */
    public DataController(DataModel model) throws NullPointerException {
        if (model == null) {
            throw new NullPointerException("DataController requires a non-null DataModel.");
        }
        this.model = model;
    }

    private final DataModel model;

    /**
     * This method uses the {@link DataModel} to create a new {@link Project}.
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
     * @see DataModel#createProject(java.lang.String, java.lang.String, java.util.GregorianCalendar, bugtrap03.usersystem.Developer, long, bugtrap03.usersystem.User) 
     * @see Project#Project(String, String, Developer, GregorianCalendar, long)
     * @return the created project
     */
    public Project createProject(String name, String description, GregorianCalendar startDate, Developer lead, long budget, User creator) throws PermissionException {
        return model.createProject(name, description, startDate, lead, budget, creator);
    }

    /**
     * Get the list of users in this system who have the exact class type
     * userType.
     *
     * @param <U> extends User type.
     * @param userType The type of users returned.
     * @return All users of this system who have the exact class type userType.
     */
    public <U extends User> PList<U> getUsersOfExactType(Class<U> userType) {
        return model.getUserListOfExactType(userType);
    }
    
    /**
     * @see DataModel#getProjectList()
     */
    public PList<Project> getProjectList(){
    	return this.model.getProjectList();
    }

    /**
     * @see DataModel#updateProject(Project, User, String, String, GregorianCalendar, Long)
     */
    public Project updateProject(Project proj, User user, String name, String description, GregorianCalendar startDate, Long budgetEstimate) throws IllegalArgumentException, PermissionException {
        return this.model.updateProject(proj, user, name, description, startDate, budgetEstimate);
    }
    
    /**
     * @see DataModel#deleteProject(User, Project)
     */
    public Project deleteProject(User user, Project project) throws PermissionException {
        return this.model.deleteProject(user, project);
    }

    /**
     * @see DataModel#getAllSubsystems(Project)
     */
    public PList<Subsystem> getAllSubsystems(Project project) {
        return this.model.getAllSubsystems(project);
    }

    /**
     * @see DataModel#getAllProjectsAndSubsystems()
     */
    public PList<AbstractSystem> getAllProjectsAndSubsystems() {
        return this.getAllProjectsAndSubsystems();
    }

    /**
     * @see DataModel#createSubsystem(User, AbstractSystem, String, String)
     */
    public Subsystem createSubsystem(User user, AbstractSystem abstractSystem, String name, String description) throws PermissionException, IllegalArgumentException {
        return this.model.createSubsystem(user, abstractSystem, name, description);
    }
}
