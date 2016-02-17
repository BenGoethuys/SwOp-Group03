package bugdomain;

/**
 * Created by Kwinten on 17/02/2016.
 */
public class Subsystem extends Systems{

    /**
     *
     * @param version
     * @param name
     * @param description
     * @param project
     */
    public Subsystem(Systems parent, VersionID version, String name, String description) {
        this.setParent(parent);
        this.setVersionID(vers);
        this.setName(name);
        this.setDescription(name);
    }
    // getSubssytems()
    // addSubsystem(Subsystem subsystem)

    private final Systems parent;
    private VersionID version;
    private final String name;
    private String description;


    private void setParent(Systems Parent){
        project.addSubsystem(this);
        this.project = project;
    }

    private void setVersionID(VersionID version){
        if(this.isValidVersionID(version)) {
            this.version = version;
        }
    }
    // setName()
    private void setName(String name) throws IllegalArgumentException{
        for {Subsystem subsys: this.getProject.getSubsystems()){
            if subsys.getName() != name
        }
        this.name = name;
    }
}

