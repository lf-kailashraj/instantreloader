package org.lftechnology.outlier.instantreloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import org.apache.commons.io.IOUtils;
import org.lftechnology.outlier.instantreloader.classreload.ClassLoaderAdapter;
import org.lftechnology.outlier.instantreloader.classreload.ClassRedefiner;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

/**
 * <p>
 * Main file which is set in MANIFEST.MF on which pre-main is invoked by JVM
 * before any class is loaded
 * </p>
 * 
 * @author frieddust
 *
 */
public class Agent {

	/**
	 * <p>The first method JVM attempts to invoke on the agent class</p>
	 * @param agentArgs
	 * @param inst
	 */
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

			ClassDefinition classDefinition = new ClassDefinition(
					ClassLoader.class, transformedByte);

			try {
				inst.redefineClasses(classDefinition);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException => " + e);
			} catch (UnmodifiableClassException e) {
				System.out.println("UnmodifiableClassException => " + e);
			}
		} catch (IOException e) {
			System.out.println("IOException => " + e);
		}

	}
}
