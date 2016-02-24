package bugtrap03.bugdomain;

import bugtrap03.permission.RolePerm;
import bugtrap03.usersystem.Developer;

/**
 * Created by Kwinten on 17/02/2016.
 * @author Kwinten Buytaert, Ben Goethuys & Vincent Derkinderen.
 */
public class Subsystem extends AbstractSystem {

	/**
	 * This constructor makes an element of the class subsystem, using it's
	 * superclass, AbstractSystem, constructor.
	 * 
	 * @param version
	 *            The versionID (of that type) of this element.
	 * @param name
	 *            The string name for this element.
	 * @param description
	 *            The string description of this element.
     * @param parent
     *            The parent (a Project or Subsystem) of this element.
	 * @throws NullPointerException
	 *             if the versionID is null.
	 * @throws IllegalArgumentException
	 *             if one of the String arguments is invalid.
	 **/
	public Subsystem(VersionID version, String name, String description, AbstractSystem parent)
			throws NullPointerException, IllegalArgumentException {
		super(version, name, description);
//        if (!this.isValidName(name, parent)) {
//            throw new IllegalArgumentException("The name is invalid with the given parent");
//        }
        this.setParent(parent);
	}

	private AbstractSystem parent;

    /**
     * Sets the parent of the AbstractSystem to the given parent, if valid. Only
     * elements of subclass subsystems have a parent different from null.
     *
     * @param parent
     *            The given parent of the AbstractSystem
     * @throws IllegalArgumentException
     * 				if the given parent isn't valid for this subsystem
     */
    private void setParent(AbstractSystem parent) throws IllegalArgumentException{
        if (! isValidParent(parent)) {
            throw new IllegalArgumentException("Illegal parent for this subsystem");
        }
        this.parent = parent;
    }

	/**
	 * This function checks or the given parent isn't one of subsystem's own
	 * child
	 * @param parent The given parent to be checked.
	 * @return true is the given parent isn't the subsystem's own child
	 */
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

    /**
     * This is a getter for the set parent of the Subsystem;
     * @return the parent of instance AbstractSystem.
     */
    protected AbstractSystem getParent(){
        return this.parent;
    }
    
	/**
	 * This function checks the validity of the given name, in combination with
	 * its parent.
	 * 
	 * @param name
	 *            The string argument to be used as name.
	 * @param parent
	 *            The parent of the element to be named.
	 * @return true if no of the other child of the projectParent has the same
	 *         name.
	 */
//	protected boolean isValidName(String name, AbstractSystem parent) {
//		if (!this.isValidName(name)) {
//			return false;
//		}
//		Project parentProject = parent.getParentProject();
//		if (name == parentProject.getName()) {
//			return false;
//		}
//		return childNamesNotEqual(name, parentProject);
//	}

	/**
	 * This function checks of none of the other subsystems (childs) of the
	 * AbstracSystem (parent) have the same name. This is done recursively. To
	 * be correctly used, the function should be called the first time with the
	 * Project of the three.
	 * 
	 * @param name
	 *            The name to check.
	 * @param parent
	 *            The given parent.
	 * @return True if the name is unique.
	 */
//	private boolean childNamesNotEqual(String name, AbstractSystem parent) {
//		for (Subsystem child : parent.getChilds()) {
//			if (child.getName() == name) {
//				return false;
//			}
//			if (!childNamesNotEqual(name, child)) {
//				return false;
//			}
//		}
//		return true;
//	}
    
    /**
	 * This method checks if the given developer has the requested permission for this subsystem
	 * @param dev the developer to check
	 * @param perm the requested permission
	 * @return true if the developer has the requested permission
	 */
	public boolean hasPermission(Developer dev, RolePerm perm){
		return this.getParentProject().hasPermission(dev, perm);
	}
}
