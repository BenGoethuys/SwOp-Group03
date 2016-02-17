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
    private ArrayList<Subsystem> childs;

    public AbstractSystem(VersionID version, String name, String description, AbstractSystem parent)throws NullPointerException, IllegalArgumentException{
        this(version, name, description);
        if (! this.isValidName(name, parent)) {
            throw new IllegalArgumentException("The name is invalid");
        }
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
        } else {throw new IllegalArgumentException("The name is invalid");

        }
        if (description != "") {
            setDescription(description);
        } else {
            throw new IllegalArgumentException("The project description can't be empty.");
        }
        this.childs = new ArrayList<Subsystem>();
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

    protected boolean isValidName(String name){
        return (name != "");
    }

    protected abstract boolean isValidName(String name, AbstractSystem parent);

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
            //only a element from type subsystem has a parent
            parent.addChild((Subsystem)this);
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

    protected ArrayList<Subsystem> getChilds(){
        return this.childs;
    }

    protected void addChild(Subsystem child){
        this.getChilds().add(child);
    }

    protected Project getParentProject(){
        AbstractSystem localParent = this.getParent();
        while (localParent != null){
            localParent = localParent.getParent();
        }
        //only an element of type project has null as parent value
        return (Project)localParent;
    }
}
