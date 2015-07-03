package org.lftechnology.outlier.instantreloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) {
		ClassRedefiner.setInstrumentation(inst);

		InputStream is = ClassLoader.getSystemResourceAsStream(Type
				.getInternalName(ClassLoader.class) + ".class");

		try {
			ClassReader classReader = new ClassReader(
					IOUtils.toByteArray(new BufferedInputStream(is)));
			ClassWriter classWriter = new ClassWriter(classReader,
					ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
			ClassVisitor cv = new ClassLoaderAdapter(classWriter);
			classReader.accept(cv, 0);
			byte[] transformedByte = classWriter.toByteArray();

			// will throw ClassFormatError
			// if we pass IOUtils.toByteArray(new BufferedInputStream(is))
			// instead of transfoemdByte
			ClassDefinition classDefinition = new ClassDefinition(
					ClassLoader.class, transformedByte);

			try {
				inst.redefineClasses(classDefinition);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (UnmodifiableClassException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
