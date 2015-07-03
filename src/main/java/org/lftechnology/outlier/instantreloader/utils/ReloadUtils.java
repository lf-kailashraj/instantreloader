package org.lftechnology.outlier.instantreloader.utils;

import java.io.File;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author anish
 *
 */
public class ReloadUtils {

	/**
	 * 
	 * @param filef
	 * @param rootDirNames
	 * @return
	 */
	public static String pathToClassname(File filef, String[] rootDirNames) {
		String rootDirName = null;
		for (String root : rootDirNames) {
			if (filef.getAbsolutePath().startsWith(root)) {
				rootDirName = root;
			}
		}

		String rootchunks[] = rootDirName.split("\\" + File.separator);
		String chunks[] = filef.getAbsolutePath().split("\\" + File.separator);

		int takelast = chunks.length - rootchunks.length - 1;
		StringBuffer fqcn = new StringBuffer();
		boolean dot = false;
		for (int i = (chunks.length - takelast) - 1; i < chunks.length; i++) {
			if (dot) {
				fqcn.append(".");
			}
			dot = true;
			if (chunks[i].endsWith(".class")) {
				fqcn.append(chunks[i].substring(0, chunks[i].length() - 6));
			} else {
				fqcn.append(chunks[i]);
			}
		}
		return fqcn.toString();
	}
	
	
	/**
	 * 
	 * @param className
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void reloadClass(String className, File file,Instrumentation instrument,Set<ClassLoader> classLoaders) throws Exception {
		System.out.println("Reloading class:" + className + " @ "+ file.getAbsolutePath());
		byte bytes[] = FileUtils.getFileBytes(file);
		
		System.out.println("bytes :"+bytes);
		try {

			List<Class> klasses = findClassInLoader(className,classLoaders);
			
			for (Class klass : klasses) {
				ClassDefinition[] d = { new ClassDefinition(klass, bytes) };
				System.out.println("Class definiations : "+d.toString());
				instrument.redefineClasses(d);
				System.out.println("after reload " + className);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MegaLoader can't load " + className);
		}
	}
	
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	public static List<Class> findClassInLoader(String className,Set<ClassLoader> classLoaders) {
		System.out.println("Cname : "+ className);
		List<Class> classes = new ArrayList<Class>();
		System.out.println("After cname :");
		try {
			System.out.println("Inside tryblock");
			Class klass = Class.forName(className);
			System.out.println("Found class in default : "+ klass.getName());
			classes.add(klass);
			System.out.println("Classes : " + classes.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ClassLoader loader : classLoaders) {
			try {
				Class klass = Class.forName(className, true, loader);
				System.out.println("Found class in "+ loader.getClass().getName() + " out of " + classLoaders.size() +" classloader");
				classes.add(klass);
			} catch (ClassNotFoundException e) {

			}
		}
		return classes;
	}


}
