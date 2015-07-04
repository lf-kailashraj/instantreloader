package org.lftechnology.outlier.instantreloader.adapter;

import org.lftechnology.outlier.instantreloader.AsmInjector;
import org.lftechnology.outlier.instantreloader.ClassReloader;
import org.lftechnology.outlier.instantreloader.ClassReloaderManager;
import org.lftechnology.outlier.instantreloader.Outlier;
import org.lftechnology.outlier.instantreloader.PseudoField;
import org.lftechnology.outlier.instantreloader.constants.Constants;
import org.lftechnology.outlier.instantreloader.data.FieldsHolder;
import org.lftechnology.outlier.instantreloader.data.PseudoClass;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

public class FieldTransformAdapter extends ClassVisitor {

	private ClassReloaderManager classReloaderManager;
	private ClassReloader classReloader;
	private PseudoClass originClass;

	public FieldTransformAdapter(ClassVisitor cv,
			long classReloaderManagerIndex, long classReloaderIndex) {
		super(Opcodes.ASM4, cv);
		classReloaderManager = Outlier
				.getClassReloaderManager(classReloaderManagerIndex);
		classReloader = classReloaderManager
				.getClassReloader(classReloaderIndex);
		originClass = classReloader.getOriginClass();
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		return null;
	}

	@Override
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, String signature, String[] exceptions) {
		return new MethodVisitor(Opcodes.ASM4, super.visitMethod(access, name,
				desc, signature, exceptions)) {

			GeneratorAdapter ga = new GeneratorAdapter(mv, access, name, desc);

			@Override
			public void visitFieldInsn(int opcode, String owner, String name,
					String desc) {
				Long ownerReloaderIndex = classReloaderManager.getIndex(owner);

				if (ownerReloaderIndex != null
						&& !Constants.OUTLIER_ADDED_FIELDS.contains(name)
						&& !classReloaderManager
								.getClassReloader(ownerReloaderIndex)
								.getOriginClass().hasField(name, desc)) {

					if (opcode == Opcodes.GETSTATIC) {
					} else if (opcode == Opcodes.PUTSTATIC) {
					} else if (opcode == Opcodes.GETFIELD) {
						AsmInjector.initHotCodeInstanceFieldIfNull(mv, owner);
						ga.visitVarInsn(Opcodes.ALOAD, 0);
						ga.visitFieldInsn(Opcodes.GETFIELD, owner,
								Constants.OUTLIER_INSTANCE_FIELDS,
								Type.getDescriptor(FieldsHolder.class));
						ga.visitLdcInsn(Constants.FIELD_DELIMITER + name
								+ Constants.FIELD_DELIMITER + desc);
						ga.visitMethodInsn(
								Opcodes.INVOKEVIRTUAL,
								Type.getDescriptor(FieldsHolder.class),
								"getField",
								Type.getMethodDescriptor(
										Type.getType(Object.class),
										Type.getType(String.class)));
						ga.unbox(Type.getType(desc));
					} else if (opcode == Opcodes.PUTFIELD) {
						ga.box(Type.getType(desc));
						ga.visitInsn(Opcodes.SWAP);
						AsmInjector.initHotCodeInstanceFieldIfNull(mv, owner);
						ga.visitVarInsn(Opcodes.ALOAD, 0);
						ga.visitFieldInsn(Opcodes.GETFIELD, owner,
								Constants.OUTLIER_INSTANCE_FIELDS,
								Type.getDescriptor(FieldsHolder.class));
						ga.visitInsn(Opcodes.SWAP);
						ga.visitLdcInsn(Constants.FIELD_DELIMITER + name
								+ Constants.FIELD_DELIMITER + desc);
						ga.visitInsn(Opcodes.SWAP);
						ga.visitMethodInsn(
								Opcodes.INVOKEVIRTUAL,
								Type.getDescriptor(FieldsHolder.class),
								"addField",
								Type.getMethodDescriptor(Type.VOID_TYPE,
										Type.getType(String.class),
										Type.getType(Object.class)));
					}
				} else {
					super.visitFieldInsn(opcode, owner, name, desc);
				}
			}
		};
	}

	@Override
	public void visitEnd() {
		for (PseudoField field : originClass.getFields()) {
			cv.visitField(field.getAccess(), field.getName(), field.getDesc(),
					null, null);
		}

		super.visitEnd();
	}
}
