package bugtrap03.bugdomain.usersystem.mail;

import bugtrap03.bugdomain.BugReport;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Group03
 */
public class BugReportMailBox extends MailBox<BugReport> {
    
    public BugReportMailBox() {
        this.mails = new ArrayList<>();
    }
    
    public Collection<Notification> getNotifications() {
        return new ArrayList(mails);
    }
    
    private Collection<Notification> mails;
    
    @Override
    public void update(BugReport subj) {
        //TODO: Implement & doc.
    }
    
}
