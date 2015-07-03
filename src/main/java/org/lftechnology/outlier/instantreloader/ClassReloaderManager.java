package org.lftechnology.outlier.instantreloader;

import java.util.HashMap;
import java.util.Map;

public class ClassReloaderManager {

	private ClassLoader classLoader;
	private Long counter = 0L;

	// Map from a internal name of a class to index.
	private Map<String, Long> classMap = new HashMap<String, Long>();

	// Map from indexGenerator to class reloader.
	private Map<Long, ClassReloader> classReloaderMap = new HashMap<Long, ClassReloader>();

	public ClassReloaderManager(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Long getIndex(String classInternalName) {
		return classMap.get(classInternalName);
	}

	public Long getNextAvailableIndex() {
		return ++counter;
	}

	public ClassReloader getClassReloader(long index) {
		return classReloaderMap.get(index);
	}

	public void putClassReloader(Long index, String classInternalName,
			ClassReloader classReloader) {
		classMap.put(classInternalName, index);
		classReloaderMap.put(index, classReloader);
	}

	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
}
