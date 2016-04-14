package bugtrap03.bugdomain.notification;

import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * @author Group 03
 */
public class AbstractSystemSubjectDummy extends AbstractSystemSubject {
    @Override
    public void notifyCreationSubs(BugReport br) {
        this.updateCreationSubs(br);
    }

    @Override
    public String getSubjectName() {
        return "naam";
    }

    @Override
    public void notifyTagSubs(BugReport br) {
        this.updateTagSubs(br);
    }

    @Override
    public void notifyCommentSubs(BugReport br) {
        this.updateCommentSubs(br);
    }

    @Override
    public boolean isTerminated() {
        return false;
    }
}
