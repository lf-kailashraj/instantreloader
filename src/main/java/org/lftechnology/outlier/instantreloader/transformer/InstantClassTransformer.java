package org.lftechnology.outlier.instantreloader.transformer;

public class InstantClassTransformer {
	private static final String[] IGNORE_PACKAGE = { "java", "sun" };

	// Invoked everytime a new class is loaded.

	public static byte[] transform(String className, ClassLoader classLoader,
			byte[] classfileBuffer) {
		return null;
	}
}
