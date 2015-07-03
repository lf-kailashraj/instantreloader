package org.lftechnology.outlier.instantreloader.classtransformer.impl;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

/**
 * 
 * @author anish
 *
 */
public class InstantTransformer implements ClassFileTransformer {

	private String classLoaderName;

	static {
		System.out.println("MegaLoader: Class loader transformer initialized.");
	}

	public InstantTransformer(String classLoaderName) {
		this.classLoaderName = classLoaderName;
	}

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		if (className.endsWith(classLoaderName)) {
			try {
				ClassPool cp = ClassPool.getDefault();
				CtClass klass = cp.makeClass(new ByteArrayInputStream(
						classfileBuffer));
				CtConstructor cts[] = klass.getConstructors();
				for (CtConstructor c : cts) {
					c.insertBeforeBody("com.outliers.app.reloader.MegaLoader.addClassLoader(this);");
				}

				byte[] b = klass.toBytecode();
				klass.detach();
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return classfileBuffer;
	}

}
