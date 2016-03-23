package bugtrap03.bugdomain.usersystem.notification;

import bugtrap03.bugdomain.bugreport.BugReport;
import bugtrap03.bugdomain.permission.RolePerm;
import bugtrap03.bugdomain.usersystem.User;

/**
 * Created by Kwinten on 23/03/2016.
 */
public class Notification {

    public Notification(String notification, BugReport bugReport, Subject subject) throws IllegalArgumentException{
        this.setMessage(notification);
        this.setBugReport(bugReport);
        this.setSubject(subject);
    }

    String message;
    BugReport bugReport;
    Subject subject;

    private void setMessage(String message) throws  IllegalArgumentException{
        if (this.isValidMessage(message)){
            this.message = message;
        }
        throw new IllegalArgumentException("The given message is invalid");
    }

    public boolean isValidMessage(String string){
        if (string == ""){
            return false;
        }
        if (string == null){
            return false;
        }
        return true;
    }

    private void setBugReport(BugReport bugReport) throws IllegalArgumentException{
        if (this.isValidBugReport(bugReport)){
            this.bugReport = bugReport;
        }
        throw new IllegalArgumentException("The given bug report is invalid");
    }

    public boolean isValidBugReport(BugReport bugReport){
        if (bugReport == null){
            return false;
        }
        return true;
    }

    private void setSubject(Subject subject) throws IllegalArgumentException{
        if (this.isValidSubject(subject)) {
            this.subject = subject;
        }
        throw new IllegalArgumentException("The given subject is invalid");
    }

    public boolean isValidSubject(Subject subject){
        if (subject == null){
            return false;
        }
        return true;
    }

    public String open(User user){
        if (this.bugReport.isPrivate() && ! user.hasRolePermission(RolePerm.OPEN_PRIVATE_NOTIFICATION,
                this.bugReport.getSubsystem().getParentProject())){
            return "This notification is closed for you.";
        }
        StringBuilder message = new StringBuilder(this.message);
        message.append(bugReport.getTitle());
        message.append("\n This notifications is originated from the subscription on: ").append(subject.getName());
    }
}
