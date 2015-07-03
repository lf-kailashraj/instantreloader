package org.lftechnology.outlier.instantreloader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class ClassRedefiner {

	private static Instrumentation inst;

	public static void setInstrumentation(Instrumentation inst) {
		ClassRedefiner.inst = inst;
	}

	public static void redefine(Class<?> klass, byte[] classFile) {
		System.out.println("redefine: " + klass.getName());
		// new Throwable().printStackTrace();
		try {
			inst.redefineClasses(new ClassDefinition(klass, classFile));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}
}
