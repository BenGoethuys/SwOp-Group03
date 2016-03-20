package bugtrap03.bugdomain.usersystem.mail;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Admin
 */
public abstract class Subject {
    
    private Set<Observer> observers = new HashSet<>();
    
    public void sub(Observer obs) throws IllegalArgumentException {
        if(obs == null) {
            throw new IllegalArgumentException("Can not subscribe a null reference as observer for this subject.");
        }
        
        this.observers.add(obs);
    }
    
    public boolean unsub(Observer obs) throws IllegalArgumentException {
        if(obs == null) {
            throw new IllegalArgumentException("Can not unsubscribe a null reference observer.");
        }
        
        return this.observers.remove(obs);
    }
    
    /**
     * Notify all our observers that the state has been updated.
     */
    public void notifyChanges() {
        for(Observer obs : observers) {
            obs.update(this);
        }
    }
    
}
