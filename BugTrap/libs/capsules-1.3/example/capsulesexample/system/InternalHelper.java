package capsulesexample.system;

import capsulesexample.system.sub1.Subsystem1ExportedClass;
import capsulesexample.system.sub1.Subsystem1InternalClass;
import capsulesexample.system.sub2.Subsystem2ExportedClass;
import capsulesexample.system.sub2.Subsystem2InternalClass;

public class InternalHelper {
	public static int internalField;
	
	public static void internalHelperMethod() {
		SystemRoot.exportedMethod();
		SystemRoot.internalMethod();
		InternalHelper.internalHelperMethod();
		Subsystem1ExportedClass.exportedMethod();
		Subsystem1ExportedClass.internalMethod();
		Subsystem1InternalClass.internalMethod();
		Subsystem2ExportedClass.exportedMethod();
		Subsystem2ExportedClass.internalMethod();
		Subsystem2InternalClass.internalMethod();
	}
}
