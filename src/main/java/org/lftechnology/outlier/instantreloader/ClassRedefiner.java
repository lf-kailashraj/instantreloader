package org.lftechnology.outlier.instantreloader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class ClassRedefiner {
	private static Instrumentation instrumentation;

	public static void setInstrumentation(Instrumentation instrumentation) {
		ClassRedefiner.instrumentation = instrumentation;
	}

	public static void redefine(Class<?> klass, byte[] classFile) {
		System.out.println("redefine: " + klass.getName());
		// new Throwable().printStackTrace();
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
