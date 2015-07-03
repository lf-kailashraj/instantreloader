package org.lftechnology.outlier.instantreloader;

/**
 * 
 * @author frieddust
 *
 */
public class ClassLoaderMap {
	private ClassLoader classLoader;
	private ClassReloaderManager classReloaderManager;

	public ClassLoaderMap(ClassLoader classLoader,
			ClassReloaderManager classReloaderManager) {
		this.classLoader = classLoader;
		this.classReloaderManager = classReloaderManager;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassReloaderManager getClassReloaderManager() {
		return classReloaderManager;
	}

	public void setClassReloaderManager(
			ClassReloaderManager classReloaderManager) {
		this.classReloaderManager = classReloaderManager;
	}

}
