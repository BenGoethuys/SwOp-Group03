package bugtrap03.bugdomain.notificationdomain;

import bugtrap03.bugdomain.bugreport.BugReport;

/**
 * Created by Kwinten on 13/04/2016.
 */
public class SubjectDummy extends Subject {

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
