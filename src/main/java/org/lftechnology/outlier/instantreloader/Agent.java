package org.lftechnology.outlier.instantreloader;

import java.io.File;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.lftechnology.outlier.instantreloader.classtransformer.impl.InstantTransformer;
import org.lftechnology.outlier.instantreloader.utils.PropertyUtils;
import org.lftechnology.outlier.instantreloader.utils.ReloadUtils;
import org.lftechnology.outlier.pathcache.PathCache;
import org.lftechnology.outlier.pathcache.PathCacheListener;


/**
 * 
 * @author anish
 *
 */
public class Agent {
	
	private  static boolean running = true;
	private static Instrumentation instrumentation;
	private static String[] rootDirNames = null;
	private static String[] packages = null;
	private static BlockingQueue<File> reloadQueue = new LinkedBlockingQueue<File>();
	
	public static Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();
	private static Thread reloader = null;
	private static List<PathCache> pathCaches = new ArrayList<PathCache>();
	
	/**
	 * 
	 * @author anish
	 *
	 */
	static class Reloader implements Runnable {
		public void run() {
			try {
				System.out.println("InstantLoader: reloading thread starting");
				while (running) {
					File file = Agent.reloadQueue.take();
					String cname = ReloadUtils.pathToClassname(file,rootDirNames);
					ReloadUtils.reloadClass(cname, file,instrumentation,classLoaders);
				}
				System.out.println("InstantLoader: reloading thread stopping");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 
	 */
	static PathCacheListener pcl = new PathCacheListener() {
		public void updates(List<String> updates) {
			Agent.updates(updates);
		}
	};
	
	/**
	 * 
	 * @param agentArgs
	 * @param inst
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		
		System.out.println("Agent starting @ " + new java.util.Date());
		System.out.println("--------------------------------------------------------------");
		
		instrumentation = inst;
		try {

			InputStream fis = Agent.class.getResourceAsStream("/reload.properties");
			Properties props = new Properties();
			props.load(fis);
			PropertyUtils.checkRequiredProps(props);

			packages = ((String) props.get("reload.packages")).split(",");
			for (int i = 0; i < packages.length; i++) {
				packages[i] = packages[i].trim();
				System.out.println("Agent: Watching package "+ packages[i]);
			}

			rootDirNames = ((String) props.get("reload.dir")).split(",");
			if (rootDirNames == null || rootDirNames.length < 1) {
				System.out.println("Agent: No directories to watch");
				return;
			}

			for (int i = 0; i < rootDirNames.length; i++) {
				rootDirNames[i] = rootDirNames[i].trim();
				System.out.println("Agent: Watching directory ["+ rootDirNames[i] + "]");
				pathCaches.add(new PathCache(pcl, rootDirNames[i]));

			}

			reloader = new Thread(new Reloader());
			reloader.start();
			String clName = props.getProperty("class.loader.suffix");
			if (clName == null || clName.equals("")) {
				clName = "ClassLoader";
			}
			inst.addTransformer(new InstantTransformer(clName));
			System.out.println("List of path cache " + pathCaches.toString());
			for (PathCache pc : pathCaches) {
				pc.setRunning(true);
				pc.start();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutting down Agent");
				try {
					for (PathCache pc : pathCaches) {
						pc.setRunning(false);
					}
					running = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void addClassLoader(ClassLoader c) {
		if (!classLoaders.contains(c)) {
			classLoaders.add(c);
		}
	}
	
	
	/**
	 * 
	 * @param updates
	 */
	public static void updates(List<String> updates) {
		for (String filename : updates) {
			File file = new File(filename);
			String cname = ReloadUtils.pathToClassname(file,rootDirNames);

			if (!file.getName().endsWith(".class")) {
				return;
			}
			boolean validPackage = false;
			for (String pkg : packages) {
				if (cname.startsWith(pkg)) {
					validPackage = true;
				}
			}
			if (!validPackage) {
				return;
			}

			try {
				reloadQueue.put(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
