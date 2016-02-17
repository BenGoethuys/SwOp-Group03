package bugdomain;

import java.util.ArrayList;

/**
 * Created by Kwinten on 17/02/2016.
 */
public abstract class AbstractSystem {

    private VersionID version;
    private String name = "";
    private String description = "";
    private AbstractSystem parent;
    private ArrayList<AbstractSystem> childs;

    public AbstractSystem(VersionID version, String name, String description, AbstractSystem parent)throws NullPointerException, IllegalArgumentException{
        this(version, name, description);
        this.setParent(parent);
    }

    public AbstractSystem(VersionID version, String name, String description)throws NullPointerException, IllegalArgumentException{
        // not yet implemented
        if (version != null) {
            setVersionID(version);
        } else {
            throw new NullPointerException("Illegal version is null");
        }
        if (this.isValidName(name)) {
            this.setName(name);
        } else {
            throw new IllegalArgumentException("The name is invalid")
        }
        if (description != "") {
            setDescription(description);
        } else {
            throw new IllegalArgumentException("The project description can't be empty.");
        }
        this.childs = new ArrayList<AbstractSystem>();
    }
    // Exceptions werpen in de isValid in plaats van in de constructor, en de isValid in de set...

    /**
     *
     * @return The versionID of the project.
     */
    public VersionID getVersionID() {
        return version;
    }

    /**
     * Sets the versionID of the project to the given versionID.
     *
     * @param version
     *            The versionID of the project.
     */
    private void setVersionID(VersionID version) {
        this.version = version;
    }

    /**
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project to the given name.
     *
     * @param name
     *            The name of the project.
     */
    private void setName(String name) {
        this.name = name;
    }

    protected abstract boolean isValidName(String name);

    /**
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project to the given description.
     *
     * @param description
     *            The description of the project.
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param parent
     */
    private void setParent(AbstractSystem parent){
        if (isValidParent(parent)){
            this.parent = parent;
        }
    }
    protected abstract boolean isValidParent(AbstractSystem parent);

    /**
     *
     * @return
     */
    protected AbstractSystem getParent(){
        if (this.parent != null){
            return this.parent;
        }else {
            return this;
        }
    }

    protected ArrayList<AbstractSystem> getChilds(){
        return this.childs;
    }

    protected void addChild(AbstractSystem child){
        this.getChilds().add(child);
    }

    protected AbstractSystem getParentProject(){
        AbstractSystem localParent = this.getParent();
        while (! localParent instanceof Project){
            localParent = localParent.getParent();
        }
        return localParent;
    }
}
