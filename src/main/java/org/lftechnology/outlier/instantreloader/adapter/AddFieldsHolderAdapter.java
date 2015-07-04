package org.lftechnology.outlier.instantreloader.adapter;

import java.lang.reflect.Modifier;

import org.lftechnology.outlier.instantreloader.Constants;
import org.lftechnology.outlier.instantreloader.FieldsHolder;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AddFieldsHolderAdapter extends ClassVisitor {

	public AddFieldsHolderAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {

		if (!Modifier.isInterface(access)) {
			cv.visitField(Opcodes.ACC_PUBLIC,
					Constants.OUTLIER_INSTANCE_FIELDS,
					Type.getDescriptor(FieldsHolder.class), null, null);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}
