package bugtrap03.bugdomain;

import java.util.HashMap;
import purecollections.PList;

/**
 * A (partial) memento of the abstractSystem.
 * <p>
 * <br> This stores the versionID, name, description, children (and their state), parent (excl state) and milestone.
 *
 * @author Group 03
 */
public class AbstractSystemMemento {
    
    AbstractSystemMemento(VersionID versionID, String name, String description, PList<Subsystem> children, AbstractSystem parent, Milestone milestone) {
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
    }

    private final VersionID versionID;
    private final String name;
    private final String description;

    private final PList<Subsystem> children;
    private final HashMap<Subsystem, SubsystemMemento> childrenMementos;

    private final AbstractSystem parent;
    private final Milestone milestone;

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

}
