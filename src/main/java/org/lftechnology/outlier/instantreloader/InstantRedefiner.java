package org.lftechnology.outlier.instantreloader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class InstantRedefiner {
	private static Instrumentation instrumentation;

	public static void setInstrumentation(Instrumentation instrumentation) {
		InstantRedefiner.instrumentation = instrumentation;
	}

	public static void redefine(Class<?> klass, byte[] classFile) {
		try {
			instrumentation.redefineClasses(new ClassDefinition(klass,
					classFile));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}
}
