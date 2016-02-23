package bugtrap03;

import bugtrap03.bugdomain.Project;
import bugtrap03.usersystem.User;
import java.util.ArrayList;

/**
 *
 * @author Admin
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
    
    
    
}
