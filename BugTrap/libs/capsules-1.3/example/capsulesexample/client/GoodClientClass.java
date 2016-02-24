package capsulesexample.client;

import capsulesexample.system.ExportedEnum;
import capsulesexample.system.SystemRoot;
import capsulesexample.system.sub1.Subsystem1ExportedClass;
import capsulesexample.system.sub2.Subsystem2ExportedClass;
import capsulesexample.system.sub2.Subsystem2ExportedSubclass;

public class GoodClientClass {
	public static void goodClientMethod() {
		SystemRoot.exportedMethod();
		Subsystem1ExportedClass.exportedMethod();
		Subsystem2ExportedClass.exportedMethod();
		System.out.println(SystemRoot.exportedField);
		System.out.println(Subsystem1ExportedClass.exportedField);
		System.out.println(Subsystem2ExportedClass.exportedField);
		
		Subsystem2ExportedSubclass sub = Subsystem2ExportedSubclass.create();
		sub.exportedInterfaceMethod();
		sub.exportedSuperclassMethod();
		System.out.println(sub.exportedField);
		
		System.out.println(ExportedEnum.X);
		System.out.println(ExportedEnum.valueOf("X"));
		System.out.println(ExportedEnum.values());
	}
}
