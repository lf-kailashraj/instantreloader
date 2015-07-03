package org.lftechnology.outlier.pathcache;

import java.util.List;

/**
 * 
 * @author anish
 *
 */
public interface PathCacheListener {
	
	/**
	 * 
	 * @param updates
	 */
	public void updates(List<String> updates);
	
}
