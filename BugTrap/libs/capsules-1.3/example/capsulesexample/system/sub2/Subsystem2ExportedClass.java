package capsulesexample.system.sub2;

import capsulesexample.system.InternalHelper;
import capsulesexample.system.SystemAPI;
import capsulesexample.system.SystemRoot;
import capsulesexample.system.sub1.Subsystem1ExportedClass;
import capsulesexample.system.sub1.Subsystem1InternalClass;

@SystemAPI
public class Subsystem2ExportedClass {
	@SystemAPI
	public static int exportedField;
	public static int internalField;

	@SystemAPI
	public static void exportedMethod() {
		SystemRoot.exportedMethod();
		SystemRoot.internalMethod();
		InternalHelper.internalHelperMethod();
		Subsystem1ExportedClass.exportedMethod();
		Subsystem1ExportedClass.internalMethod();
		Subsystem1InternalClass.internalMethod();
		Subsystem2ExportedClass.exportedMethod();
		Subsystem2ExportedClass.internalMethod();
		Subsystem2InternalClass.internalMethod();
		System.out.println(SystemRoot.exportedField);
		System.out.println(SystemRoot.internalField);
		System.out.println(InternalHelper.internalField);
		System.out.println(Subsystem1ExportedClass.exportedField);
		System.out.println(Subsystem1ExportedClass.internalField);
		System.out.println(Subsystem1InternalClass.internalField);
		System.out.println(Subsystem2ExportedClass.exportedField);
		System.out.println(Subsystem2ExportedClass.internalField);
		System.out.println(Subsystem2InternalClass.internalField);
	}
	
	public static void internalMethod() {
		SystemRoot.exportedMethod();
		SystemRoot.internalMethod();
		InternalHelper.internalHelperMethod();
		Subsystem1ExportedClass.exportedMethod();
		Subsystem1ExportedClass.internalMethod();
		Subsystem1InternalClass.internalMethod();
		Subsystem2ExportedClass.exportedMethod();
		Subsystem2ExportedClass.internalMethod();
		Subsystem2InternalClass.internalMethod();
		System.out.println(SystemRoot.exportedField);
		System.out.println(SystemRoot.internalField);
		System.out.println(InternalHelper.internalField);
		System.out.println(Subsystem1ExportedClass.exportedField);
		System.out.println(Subsystem1ExportedClass.internalField);
		System.out.println(Subsystem1InternalClass.internalField);
		System.out.println(Subsystem2ExportedClass.exportedField);
		System.out.println(Subsystem2ExportedClass.internalField);
		System.out.println(Subsystem2InternalClass.internalField);
	}
}
