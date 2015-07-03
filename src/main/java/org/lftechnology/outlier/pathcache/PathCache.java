package org.lftechnology.outlier.pathcache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathCache extends Thread {

	private File dir;
	private boolean running = false;
	private long sleepTimeMillis = 500;
	private Map<String,Long> cache =  new HashMap<String,Long>();
	private PathCacheListener listener;
	
	public PathCache(PathCacheListener l, String path) {
		this.dir = new File(path);
		this.listener = l;
	}
		
	public void run() {
		initialize();
		running = true;
		System.out.println("PathCache: " + dir.getAbsolutePath());
		while(running) {
			try {
				Thread.sleep(sleepTimeMillis);				
				List<String> updates = update();
				listener.updates(updates);			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		System.out.println("Path cache complete.");
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void initialize() {
		List<String> allFiles = new ArrayList<String>();
		walk(dir,allFiles);
	}
	public List<String> update() {
		List<String> allFiles = new ArrayList<String>();
		walk(dir,allFiles);
		return allFiles;
	}
		
	public void walk(File root, List<String> changes) {
        String suffix = ".class"; 
        
        File files[] = root.listFiles();
        if(files == null) 
        	return;
        
        for(int i=0; i<files.length; i++) {
            if(files[i].isDirectory()) {
            	walk(files[i],changes);
            } else {
            	
            	String name = files[i].getAbsolutePath(); 
            	if(name.endsWith(suffix)) {
                	if(cache.containsKey(name)) {                		
                		long lastModified = files[i].lastModified();
                		if(cache.get(name) < lastModified) {
                			changes.add(name);
                			cache.put(name, lastModified);
                		}
                	} else {
                		long lastModified = files[i].lastModified();
                		cache.put(name, lastModified);
                		changes.add(name);
                	}
                }
            }
        }        
    }
	 
	
}
