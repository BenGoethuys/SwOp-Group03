package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.AbstractSystem;
import bugtrap03.bugdomain.notificationdomain.notification.ASNotification;

/**
 * @author Group 03
 */
public class MilestoneMailbox extends AbstractMailbox<AbstractSystem, ASNotification> {

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public ASNotification update(AbstractSystem changedObject) {
        return null;
    }
}
