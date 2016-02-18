package bugtrap03.usersystem;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
class UserUniqueCollection<T extends Collection<User> & Cloneable> extends UniqueCollection<T, User> {

    public UserUniqueCollection(T col) throws IllegalArgumentException {
        super(col);
    }

    
    public void test(T col) {
        T newCol = (T) col.clone();
    }

    @Override
    HashMap<Object, Integer> getUniqueMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
