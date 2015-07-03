package org.lftechnology.outlier.instantreloader.adapters;

import java.lang.reflect.Modifier;

import org.lftechnology.outlier.instantreloader.Constants;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class AddFieldsHolderAdapter extends ClassVisitor {

	public AddFieldsHolderAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {

		// Interfaces don't have any instances field.
		if (!Modifier.isInterface(access)) {
			cv.visitField(Opcodes.ACC_PUBLIC,
					Constants.OUTLIER_INSTANCE_FIELDS,
					Type.getDescriptor(FieldsHolder.class), null, null);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}
