package bugdomain;

/**
 * Created by Kwinten on 17/02/2016.
 */
public class Subsystem extends AbstractSystem {

    private final AbstractSystem parent;
    /**
     *
     * @param version
     * @param name
     * @param description
     * @param parent
     */
    public Subsystem(AbstractSystem parent, VersionID version, String name, String description) {
        super(version,name,description,parent);
        this.setParentRelation(parent);
    }

    protected boolean isValidName(String name){
        AbstractSystem parentProject = AbstractSystem.getParentProject();
        for ()

    }
    //TODO check recursively to itself
    protected boolean isValidParent(AbstractSystem parent){
        return false;
    }
    private void setParentRelation(AbstractSystem parent){
        parent.addChild(this);


    }

}

