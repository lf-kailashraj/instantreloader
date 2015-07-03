package org.lftechnology.outlier.instantreloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class ClassReloader {
	private Long classReloaderManagerIndex;
	private Long classIndex;
	private PseudoClass originClass;
	private ClassFile classFile;
	private ClassLoader classLoader;

	public ClassReloader(Long classReloaderManagerIndex, Long classIndex,
			ClassFile classFile, PseudoClass originClass,
			ClassLoader classLoader) {
		this.classReloaderManagerIndex = classReloaderManagerIndex;
		this.classIndex = classIndex;
		this.classFile = classFile;
		this.originClass = originClass;
		this.classLoader = classLoader;
	}

	public boolean checkAndReload() {
		// new Throwable().printStackTrace();
		return classFile.changed() && reload();
	}

	public PseudoClass getOriginClass() {
		return originClass;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	private boolean reload() {
		classFile.reload();
		byte[] transformedClassFile = ClassTransformer
				.transform(classReloaderManagerIndex, classIndex,
						classFile.getClassFile());
		try {
			ClassRedefiner.redefine(
					classLoader.loadClass(originClass.getClassName()),
					transformedClassFile);
			Class<?> clazz = classLoader.loadClass(originClass.getClassName());

			// reinit class.
			try {
				Method method = clazz
						.getMethod(Constants.OUTLIER_CLINIT_METHOD_NAME);
				method.invoke(clazz);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
