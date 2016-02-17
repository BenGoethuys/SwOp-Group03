package bugdomain;

/**
 * Created by Kwinten on 17/02/2016.
 */
public class Subsystem extends AbstractSystem {
    /**
     *
     * @param parent
     * @param version
     * @param name
     * @param description
     */
    public Subsystem(AbstractSystem parent, VersionID version, String name, String description) {
        super(version,name,description,parent);
    }

    protected boolean isValidName(String name, AbstractSystem parent){
        if (! this.isValidName(name)) {
            return false;
        }
        Project parentProject = parent.getParentProject();
        if (name == parentProject.getName()) {
            return false;
        }
        return childNamesEqual(name, parentProject);
    }

    private boolean childNamesEqual(String name, AbstractSystem currentChild){
        for (Subsystem child: currentChild.getChilds())     {
            if (child.getName() == name) {
                return false;
            }
            if (! childNamesEqual(name, child)){
                return false;
            }
        }
        return true;
    }

    protected boolean isValidParent(AbstractSystem parent) {
        Project parentProject = parent.getParentProject();
        AbstractSystem currentSystem = parent;
        while (currentSystem != parentProject) {
            if (currentSystem == this) {
                return false;
            }
            currentSystem = currentSystem.getParent();
        }
        return true;
    }
}


