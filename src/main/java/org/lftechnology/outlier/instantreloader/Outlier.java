package org.lftechnology.outlier.instantreloader;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author frieddust
 *
 */
public class Outlier {

	private static Long counter = 0L;
	private static Map<ClassLoader, Long> classLoaderIndexMap = new HashMap<ClassLoader, Long>();
	private static Map<Long, ClassReloaderManager> classReloaderManagerMap = new HashMap<Long, ClassReloaderManager>();

	public static Long getIndex(ClassLoader classLoader) {
		return classLoaderIndexMap.get(classLoader);
	}

	public static ClassReloaderManager getClassReloaderManager(long index) {
		return classReloaderManagerMap.get(index);
	}

	public static Long putClassReloaderManager(ClassLoader classLoader,
			ClassReloaderManager classReloaderManager) {
		counter++;
		classLoaderIndexMap.put(classLoader, counter);
		classReloaderManagerMap.put(counter, classReloaderManager);
		return counter;
	}

	public static void addClassLoader(ClassLoader classLoader) {
		if (classLoaderIndexMap.get(classLoader) != null) {
			return;
		}

		putClassReloaderManager(classLoader, new ClassReloaderManager(
				classLoader));
	}
}
