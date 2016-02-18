package bugtrap03.usersystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public abstract class UniqueCollection<T extends Collection<K> & Cloneable, K> {

    
    /**
     * 
     * @param col
     * @throws IllegalArgumentException 
     */
    public UniqueCollection(T col) throws IllegalArgumentException {
        setCollection(col);
    }
    
    private T col;
    
    /**
     * Get the {@link HashMap} holding all the objects created of this type using this collection.
     * Basically, this has to return a static map that will be used to hold all unique Identifiers
     * used by add();
     * @return 
     */
    abstract HashMap<Object, Integer> getUniqueMap();
    
    boolean isUniqueIdentifier(Object);
    
    //add(parameter, parameter, parameters) <--- constructors
    
    private void addToCollection(K k) {
        boolean foundMatchingUsername = col.parallelStream().anyMatch(u -> username.equalsIgnoreCase(u.getUsername()));
    }
    
    
    private void setCollection(T col) throws IllegalArgumentException {
        if(isValidCollection(col)) {
            
        } else {
            throw new IllegalArgumentException("The given collection " + col.toString() + " is invalid.");
        }
    }

    /**
     * Get whether the given {@link Collection} is valid.
     * @param col The collection to check.
     * @return Whether the given collection contains no two users with the same username.
     * false when col == null.
     */
    public static boolean isValidCollection(T col) {
        if(col == null) {
            return false;
        }
        
        Iterator<User> iter = col.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            String username = user.getUsername();
            boolean foundMatchingUsername = col.parallelStream().filter(u -> u != user).anyMatch(u -> username.equalsIgnoreCase(u.getUsername()));
            if (foundMatchingUsername) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get whether the given {@link Collection} contains a {#link User} with username as the username.
     * @param col The collection to check.
     * @param username The username to compare with.
     * @return 
     */
    //TODO: Verify that Java 8 is allowed.
    public static boolean contains(Collection<User> col, String username) {
        return col.parallelStream().anyMatch(u -> username.equalsIgnoreCase(u.getUsername()));
    }

}
