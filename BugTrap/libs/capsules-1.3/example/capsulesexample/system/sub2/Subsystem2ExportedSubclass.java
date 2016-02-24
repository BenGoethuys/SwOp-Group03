package capsulesexample.system.sub2;

import capsulesexample.system.SystemAPI;
import capsulesexample.system.sub1.Subsystem1ExportedSuperclass;

@SystemAPI
public abstract class Subsystem2ExportedSubclass extends Subsystem1ExportedSuperclass implements Runnable {
	
	@SystemAPI
	public int exportedField;
	public int internalField;
	
	public void internalMethod() {}
	
	@SystemAPI
	public static Subsystem2ExportedSubclass create() {
		return new Subsystem2InternalSubsubclass();
	}
}