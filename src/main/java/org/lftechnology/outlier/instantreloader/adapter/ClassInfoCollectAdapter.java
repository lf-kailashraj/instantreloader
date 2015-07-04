package org.lftechnology.outlier.instantreloader.adapter;

import org.lftechnology.outlier.instantreloader.PseudoField;
import org.lftechnology.outlier.instantreloader.data.PseudoClass;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class ClassInfoCollectAdapter extends ClassVisitor {

	private PseudoClass hotCodeClass;

	public ClassInfoCollectAdapter(ClassVisitor cv, PseudoClass hotCodeClass) {
		super(Opcodes.ASM4, cv);
		this.hotCodeClass = hotCodeClass;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		hotCodeClass.setClassName(name.replace('/', '.'));
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		hotCodeClass.getFields().add(new PseudoField(access, name, desc));
		return super.visitField(access, name, desc, signature, value);
	}
}
