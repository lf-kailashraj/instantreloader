package org.lftechnology.outlier.instantreloader.adapter;

import java.util.Set;

import org.lftechnology.outlier.instantreloader.PseudoField;
import org.lftechnology.outlier.instantreloader.data.PseudoClass;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class FieldReorderAdapter extends ClassVisitor {

	private PseudoClass hotCodeClass;

	public FieldReorderAdapter(PseudoClass hotCodeClass, ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
		this.hotCodeClass = hotCodeClass;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		return null;
	}

	@Override
	public void visitEnd() {
		Set<PseudoField> fields = hotCodeClass.getFields();

		for (PseudoField field : fields) {
			cv.visitField(field.getAccess(), field.getName(), field.getDesc(), null, null);
		}

		super.visitEnd();
	}
}