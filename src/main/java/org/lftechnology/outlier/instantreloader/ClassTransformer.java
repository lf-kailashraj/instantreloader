package org.lftechnology.outlier.instantreloader;

import org.lftechnology.outlier.instantreloader.adapter.AddClassReloaderAdapter;
import org.lftechnology.outlier.instantreloader.adapter.AddFieldsHolderAdapter;
import org.lftechnology.outlier.instantreloader.adapter.BeforeMethodCheckAdapter;
import org.lftechnology.outlier.instantreloader.adapter.ClinitClassAdapter;
import org.lftechnology.outlier.instantreloader.adapter.FieldTransformAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

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
