package org.lftechnology.outlier.instantreloader;

import java.lang.instrument.Instrumentation;

public class InstantRedefiner {
	private static Instrumentation instrumentation;

	public static void setInstrumentation(Instrumentation instrumentation) {
		InstantRedefiner.instrumentation = instrumentation;
	}

	public static void redefine(Class<?> klass, byte[] classFile) {

	}
}
