package bugtrap03.bugdomain.notificationdomain.notification;

import bugtrap03.bugdomain.notificationdomain.Subject;
import bugtrap03.bugdomain.usersystem.User;

/**
 * Created by Kwinten on 28/04/2016.
 */
public class ProjectNotification extends Notification {

    public ProjectNotification(String message, Subject subject) {
        super(message, subject);
    }

    @Override
    public String open(User user) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
