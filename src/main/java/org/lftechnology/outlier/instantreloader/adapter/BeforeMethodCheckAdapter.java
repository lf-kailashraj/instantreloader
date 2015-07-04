package org.lftechnology.outlier.instantreloader.adapter;

import org.apache.commons.lang.StringUtils;
import org.lftechnology.outlier.instantreloader.AsmInjector;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 
 * @author anish
 *
 */
public class BeforeMethodCheckAdapter extends ClassVisitor {

	private String classInternalName;

	public BeforeMethodCheckAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.classInternalName = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature,
				exceptions);

		if (StringUtils.equals(name, "<clinit>")) {
			return mv;
		}

		return new MethodVisitor(Opcodes.ASM4, mv) {

			@Override
			public void visitCode() {
				AsmInjector.beforeMethodCheck(mv, access, name, desc,
						classInternalName);
				super.visitCode();
			}
		};
	}
}
