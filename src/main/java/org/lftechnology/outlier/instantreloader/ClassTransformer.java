package org.lftechnology.outlier.instantreloader;

import org.lftechnology.outlier.instantreloader.adapters.AddClassReloaderAdapter;
import org.lftechnology.outlier.instantreloader.adapters.AddFieldsHolderAdapter;
import org.lftechnology.outlier.instantreloader.adapters.BeforeMethodCheckAdapter;
import org.lftechnology.outlier.instantreloader.adapters.ClinitClassAdapter;
import org.lftechnology.outlier.instantreloader.adapters.FieldTransformAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class ClassTransformer {
	public static byte[] transform(Long classReloaderManagerIndex,
			Long classReloaderIndex, byte[] classFile) {
		System.out.println("Transform " + classReloaderManagerIndex + " - "
				+ classReloaderIndex);
		ClassReader cr = new ClassReader(classFile);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS
				+ ClassWriter.COMPUTE_FRAMES);
		ClassVisitor cv = new AddFieldsHolderAdapter(cw);
		cv = new AddClassReloaderAdapter(cv);
		cv = new FieldTransformAdapter(cv, classReloaderManagerIndex,
				classReloaderIndex);
		cv = new ClinitClassAdapter(cv, classReloaderManagerIndex,
				classReloaderIndex);
		cv = new BeforeMethodCheckAdapter(cv);
		cr.accept(cv, 0);
		return cw.toByteArray();
	}
}
