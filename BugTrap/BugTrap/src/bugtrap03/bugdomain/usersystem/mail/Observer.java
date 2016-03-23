package bugtrap03.bugdomain.usersystem.mail;

/**
 *
 * @author Admin
 */
public interface Observer<T> {
    
    public void update(T subj);
}
