package org.lftechnology.outlier.instantreloader.adapter;

import java.lang.reflect.Modifier;

import org.lftechnology.outlier.instantreloader.ClassReloader;
import org.lftechnology.outlier.instantreloader.constants.Constants;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AddClassReloaderAdapter extends ClassVisitor {

	public AddClassReloaderAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		if (!Modifier.isInterface(access)) {
			cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
					Constants.OUTLIER_CLASS_RELOADER_FIELDS,
					Type.getDescriptor(ClassReloader.class), null, null);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
