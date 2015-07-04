package org.lftechnology.outlier.instantreloader;

import java.io.File;
import java.net.URL;

import org.lftechnology.outlier.instantreloader.adapter.AddClassReloaderAdapter;
import org.lftechnology.outlier.instantreloader.adapter.AddFieldsHolderAdapter;
import org.lftechnology.outlier.instantreloader.adapter.BeforeMethodCheckAdapter;
import org.lftechnology.outlier.instantreloader.adapter.ClassInfoCollectAdapter;
import org.lftechnology.outlier.instantreloader.adapter.ClinitClassAdapter;
import org.lftechnology.outlier.instantreloader.adapter.FieldReorderAdapter;
import org.lftechnology.outlier.instantreloader.classreload.ClassManager;
import org.lftechnology.outlier.instantreloader.classreload.ClassReloader;
import org.lftechnology.outlier.instantreloader.classreload.ClassReloaderManager;
import org.lftechnology.outlier.instantreloader.data.ClassFile;
import org.lftechnology.outlier.instantreloader.data.PseudoClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * <p>Any class that is loaded by jvm is passed through this class's transform method.</p>
 * 
 * @author frieddust
 *
 */
public class InitialClassTransformer {

	private static final String[] IGNORE_PACKAGE = { "java", "sun" };

	public static byte[] transform(String className, ClassLoader classLoader,
			byte[] classfileBuffer) {
		System.out.println("NEW LOAD " + className);
		for (String PACKAGE : IGNORE_PACKAGE) {
			if (className.startsWith(PACKAGE)) {
				return classfileBuffer;
			}
		}

		Long classReloaderManagerIndex = ClassManager.getIndex(classLoader);

		if (classReloaderManagerIndex == null) {
			classReloaderManagerIndex = ClassManager.putClassReloaderManager(classLoader, new ClassReloaderManager(classLoader));
		}

		ClassReloaderManager classReloaderManager = ClassManager.getClassReloaderManager(classReloaderManagerIndex);

		Long classReloaderIndex = classReloaderManager.getIndex(className
				.replace('.', '/'));

		URL classFileURL = classLoader.getResource(className.replace('.', '/')+ ".class");
		File classFile = new File(classFileURL.getFile());

		if (!classFile.exists()) {
			return classfileBuffer;
		}

		ClassFile fileSystemVersionedClassFile = new ClassFile(classFile);

		PseudoClass pseudoClass = new PseudoClass();

		if (classReloaderIndex == null) {
			classReloaderIndex = classReloaderManager.getNextAvailableIndex();
			classReloaderManager.putClassReloader(classReloaderIndex, className.replace('.', '/'), new ClassReloader(
					classReloaderManagerIndex, classReloaderIndex, fileSystemVersionedClassFile, pseudoClass, classLoader));
		}

		ClassReader cr = new ClassReader(classfileBuffer); 
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
		ClassVisitor cv = new AddFieldsHolderAdapter(cw);
		cv = new AddClassReloaderAdapter(cv);
		cv = new FieldReorderAdapter(pseudoClass, cv);
		cv = new ClinitClassAdapter(cv, classReloaderManagerIndex, classReloaderIndex);
		cv = new BeforeMethodCheckAdapter(cv);
		cv = new ClassInfoCollectAdapter(cv, pseudoClass);
		cr.accept(cv, 0);
		byte[] classRedefined = cw.toByteArray();
		return classRedefined;
	}
}
