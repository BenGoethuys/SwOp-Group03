package bugtrap03.bugdomain;

import bugtrap03.bugdomain.notificationdomain.AbstractSystemSubjectMemento;
import bugtrap03.bugdomain.notificationdomain.mailboxes.*;

import java.util.HashMap;

import purecollections.PList;

/**
 * A (partial) memento of the abstractSystem.
 * <p>
 * <br> This stores the versionID, name, description, children (and their state), parent (excl state), milestone and isTerminated
 *
 * @author Group 03
 */
public class AbstractSystemMemento extends AbstractSystemSubjectMemento {
    
    public AbstractSystemMemento(PList<TagMailBox> tagMailBoxes, PList<CommentMailBox> commentMailBoxes,
                          PList<CreationMailBox> creationMailBoxes, PList<MilestoneMailbox> milestoneMailboxes,
                          PList<VersionIDMailbox> versionIDMailboxes, VersionID versionID, String name,
                          String description, PList<Subsystem> children, AbstractSystem parent,
                          Milestone milestone, boolean isTerminated) {
        super(tagMailBoxes, commentMailBoxes, creationMailBoxes, milestoneMailboxes, versionIDMailboxes);
        this.versionID = versionID;
        this.name = name;
        this.description = description;
        this.children = children;
        
        childrenMementos = new HashMap<>();
        for(Subsystem subsys : children) {
            childrenMementos.put(subsys, subsys.getMemento());
        }
        
        this.parent = parent;
        this.milestone = milestone;
        this.isTerminated = isTerminated;
    }

    private final VersionID versionID;
    private final String name;
    private final String description;

    private final PList<Subsystem> children;
    private final HashMap<Subsystem, SubsystemMemento> childrenMementos;

    private final AbstractSystem parent;
    private final Milestone milestone;
    private final boolean isTerminated;

    VersionID getVersionID() {
        return this.versionID;
    }

    String getName() {
        return this.name;
    }

    String getDescription() {
        return this.description;
    }

    PList<Subsystem> getChildren() {
        return this.children;
    }

    /**
     * Restore the state of the children by restoring their memento.
     */
    void restoreChildren() {
        for (Subsystem subsys : children) {
            SubsystemMemento mem = childrenMementos.get(subsys);

            if (mem != null) {
                subsys.setMemento(mem);
            }
        }
    }

    AbstractSystem getParent() {
        return this.parent;
    }

    Milestone getMilestone() {
        return this.milestone;
    }
    
    boolean getIsTerminated() {
        return this.isTerminated;
    }

}
