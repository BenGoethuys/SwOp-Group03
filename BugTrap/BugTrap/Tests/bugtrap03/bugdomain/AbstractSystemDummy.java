package bugtrap03.bugdomain;
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
		return null;
	}

}
