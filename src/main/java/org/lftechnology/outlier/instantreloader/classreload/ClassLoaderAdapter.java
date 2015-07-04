package org.lftechnology.outlier.instantreloader.classreload;

import java.security.ProtectionDomain;

import org.lftechnology.outlier.instantreloader.InitialClassTransformer;
import org.lftechnology.outlier.instantreloader.Outlier;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassLoaderAdapter extends ClassVisitor {

	public ClassLoaderAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor methodVisitor = super.visitMethod(access, name, desc,
				signature, exceptions);
		if (name.equals("defineClass")
				&& desc.equals(Type.getMethodDescriptor(
						Type.getType(Class.class), Type.getType(String.class),
						Type.getType(byte[].class), Type.INT_TYPE,
						Type.INT_TYPE, Type.getType(ProtectionDomain.class)))) {
			return new MethodVisitor(Opcodes.ASM4, methodVisitor) {

				@Override
				public void visitCode() {
					mv.visitVarInsn(Opcodes.ALOAD, 0);
					mv.visitMethodInsn(Opcodes.INVOKESTATIC,
							Type.getInternalName(Outlier.class),
							"addClassLoader", "(Ljava/lang/ClassLoader;)V");
					mv.visitVarInsn(Opcodes.ALOAD, 1);
					mv.visitVarInsn(Opcodes.ALOAD, 0);
					mv.visitVarInsn(Opcodes.ALOAD, 2);
					mv.visitMethodInsn(
							Opcodes.INVOKESTATIC,
							Type.getInternalName(InitialClassTransformer.class),
							"transform",
							"(Ljava/lang/String;Ljava/lang/ClassLoader;[B)[B");
					mv.visitVarInsn(Opcodes.ASTORE, 2);
					mv.visitVarInsn(Opcodes.ALOAD, 2);
					mv.visitInsn(Opcodes.ARRAYLENGTH);
					mv.visitVarInsn(Opcodes.ASTORE, 4);
					super.visitCode();
				}

			};
		}

		return methodVisitor;
	}
}
