package bugtrap03;

import bugtrap03.usersystem.User;
import java.util.Collection;
import purecollections.PList;

/**
 *
 * @author Group 03
 * @version 0.1
 */
public class DataController {
    
    /**
     * TODO
     * @param model 
     * @throws NullPointerException
     */
    public DataController(DataModel model) throws NullPointerException {
        if(model == null) {
            throw new NullPointerException("DataController requires a non-null DataModel.");
        }
        this.model = model;
    }

    private final DataModel model;

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

}
