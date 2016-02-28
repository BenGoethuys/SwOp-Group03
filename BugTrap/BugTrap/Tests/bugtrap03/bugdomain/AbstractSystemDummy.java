package bugtrap03.bugdomain;

import bugtrap03.permission.RolePerm;
import bugtrap03.usersystem.Developer;

/**
 * This class is a dummy implementation for the AbstractSystem Class, so it's methods can be tested.
 * @author Kwinten
 * @date 
 */
public class AbstractSystemDummy extends AbstractSystem {

	public AbstractSystemDummy(VersionID version, String name, String description)
			throws NullPointerException, IllegalArgumentException {
		super(version, name, description);
	}

	@Override
	protected AbstractSystem getParent() {
		return this;
	}

	/**
	 * This method checks if the given developer has the requested permission for this subsystem
	 *
	 * @param dev  the developer to check
	 * @param perm the requested permission
	 * @return true if the developer has the requested permission
	 */
	@Override
	public boolean hasPermission(Developer dev, RolePerm perm) {
		return false;
	}

}
