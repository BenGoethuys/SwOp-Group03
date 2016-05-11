package bugtrap03.bugdomain;

import bugtrap03.bugdomain.notificationdomain.ProjectSubjectMemento;
import bugtrap03.bugdomain.notificationdomain.mailboxes.*;
import bugtrap03.bugdomain.usersystem.Developer;
import bugtrap03.bugdomain.usersystem.Role;
import java.util.GregorianCalendar;
import java.util.HashMap;
import purecollections.PList;

/**
 * A (partial) memento of the project.
 * <br> This stores everything an {@link AbstractSystemMemento} does as well as
 * creationDate (clone), startDate (clone), budgetEsimate, isTerminated and the projectParticipants (clone).
 *
 * @author Group 03
 */
public class ProjectMemento extends ProjectSubjectMemento {

    ProjectMemento(PList<TagMailbox> tagMailboxes, PList<CommentMailbox> commentMailboxes, PList<CreationMailbox> creationMailboxes, PList<MilestoneMailbox> milestoneMailboxes,
                   PList<VersionIDMailbox> versionIDMailboxes, VersionID versionID, String name, String description, PList<Subsystem> children,
                   AbstractSystem parent, Milestone milestone, PList<ForkMailbox> forkSubs, GregorianCalendar creationDate, GregorianCalendar startDate,
                   HashMap<Developer, PList<Role>> projectParticipants, long budgetEstimate, boolean isTerminated) {
        super(tagMailboxes, commentMailboxes, creationMailboxes, milestoneMailboxes, versionIDMailboxes, versionID, name,
                description, children, parent, milestone, isTerminated, forkSubs);


        
        this.creationDate = (GregorianCalendar) creationDate.clone();
        this.startDate = (GregorianCalendar) startDate.clone();
        
        this.projectParticipants = (HashMap<Developer, PList<Role>>) projectParticipants.clone();
        
        this.budgetEstimate = budgetEstimate;
    }

    private final GregorianCalendar creationDate;
    private final GregorianCalendar startDate;
    private final HashMap<Developer, PList<Role>> projectParticipants;
    private long budgetEstimate;
    
    GregorianCalendar getCreationDate() {
        return this.creationDate;
    }
    
    GregorianCalendar getStartDate() {
        return this.startDate;
    }
    
    HashMap<Developer, PList<Role>> getProjectParticipants() {
        return this.projectParticipants;
    }
    
    long getBudgetEstimate() {
        return this.budgetEstimate;
    }
    
}
