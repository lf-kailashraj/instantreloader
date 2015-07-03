package org.lftechnology.outlier.instantreloader;

import java.io.InputStream;
import java.lang.instrument.Instrumentation;

import org.objectweb.asm.Type;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) {

		InputStream is = ClassLoader.getSystemResourceAsStream(Type
				.getInternalName(ClassLoader.class) + ".class");

		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
