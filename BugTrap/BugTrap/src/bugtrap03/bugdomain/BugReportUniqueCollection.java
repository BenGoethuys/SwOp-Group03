package bugtrap03.bugdomain;

import java.util.Collection;
import bugtrap03.usersystem.UniqueCollection;

/**
 *
 * @author Admin
 */
class BugReportUniqueCollection<T extends Collection<BugReport> & Cloneable> extends UniqueCollection<T,BugReport> {

    public BugReportUniqueCollection(T col) throws IllegalArgumentException {
        super(col);
    }
    
}
