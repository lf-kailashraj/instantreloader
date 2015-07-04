package org.lftechnology.outlier.instantreloader;

import java.lang.reflect.Modifier;

import org.lftechnology.outlier.instantreloader.classreload.ClassManager;
import org.lftechnology.outlier.instantreloader.classreload.ClassReloader;
import org.lftechnology.outlier.instantreloader.classreload.ClassReloaderManager;
import org.lftechnology.outlier.instantreloader.constants.Constants;
import org.lftechnology.outlier.instantreloader.data.FieldsHolder;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

/**
 * <p>A Utility class that injects byte code with the help of ASM lib</p>
 * 
 * @author frieddust
 *
 */
public class AsmInjector {

	/**
	 * 
	 * @param mv
	 * @param ownerClassInternalName
	 * @param classReloaderManagerIndex
	 * @param classReloaderIndex
	 */
	public static void clinitFieldInit(MethodVisitor mv,String ownerClassInternalName, Long classReloaderManagerIndex, Long classReloaderIndex) {
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>",Type.getMethodDescriptor(Type.VOID_TYPE));

		mv.visitLdcInsn(classReloaderManagerIndex);
		
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(ClassManager.class), "getClassReloaderManager", 
				Type.getMethodDescriptor(Type.getType(ClassReloaderManager.class),Type.LONG_TYPE));
		mv.visitLdcInsn(classReloaderIndex);
		
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ClassReloaderManager.class),"getClassReloader", 
				Type.getMethodDescriptor(Type.getType(ClassReloader.class), Type.LONG_TYPE));
		
		mv.visitFieldInsn(Opcodes.PUTSTATIC, ownerClassInternalName, Constants.OUTLIER_CLASS_RELOADER_FIELDS,Type.getDescriptor(ClassReloader.class));
	}

	/**
	 * 
	 * @param mv
	 * @param methodAccess
	 * @param methodName
	 * @param methodDesc
	 * @param ownerClassInternalName
	 */
	public static void beforeMethodCheck(MethodVisitor mv, int methodAccess, String methodName, String methodDesc, String ownerClassInternalName) {
		GeneratorAdapter ga = new GeneratorAdapter(mv, methodAccess, methodName, methodDesc);
		Label label = new Label();
		ga.visitFieldInsn(Opcodes.GETSTATIC, ownerClassInternalName, Constants.OUTLIER_CLASS_RELOADER_FIELDS, Type.getDescriptor(ClassReloader.class));
		ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(ClassReloader.class), "checkAndReload", Type.getMethodDescriptor(Type.BOOLEAN_TYPE));
		ga.visitInsn(Opcodes.ICONST_1);
		ga.visitJumpInsn(Opcodes.IF_ICMPNE, label);

		if (!Modifier.isStatic(methodAccess)) {
			ga.loadThis();
		}

		ga.loadArgs();
		ga.visitMethodInsn(Modifier.isStatic(methodAccess) ? Opcodes.INVOKESTATIC : Opcodes.INVOKESPECIAL, ownerClassInternalName, methodName, methodDesc);
		ga.returnValue();
		ga.visitLabel(label);
	}

	/**
	 * 
	 * @param mv
	 * @param fieldOwner
	 */
	public static void initHotCodeInstanceFieldIfNull(MethodVisitor mv,	String fieldOwner) {
		mv.visitFieldInsn(Opcodes.GETFIELD, fieldOwner, Constants.OUTLIER_INSTANCE_FIELDS, Type.getDescriptor(FieldsHolder.class));
		Label label = new Label();
		mv.visitJumpInsn(Opcodes.IFNONNULL, label);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(FieldsHolder.class));
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(FieldsHolder.class), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE));
		mv.visitFieldInsn(Opcodes.PUTFIELD, fieldOwner, Constants.OUTLIER_INSTANCE_FIELDS, Type.getDescriptor(FieldsHolder.class));
		mv.visitLabel(label);
	}
}
