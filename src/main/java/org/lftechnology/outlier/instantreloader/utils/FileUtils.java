package org.lftechnology.outlier.instantreloader.utils;

import java.io.File;
import java.io.FileInputStream;

/**
 * 
 * @author anish
 *
 */
public class FileUtils {

	/**
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static byte[] getFileBytes(File f) throws Exception {
	    FileInputStream fis = new FileInputStream(f);
	    byte[] bytes = new byte[(int)f.length()];

	    int offset = 0;
	    int bytesRead = 0;
	    while (offset < bytes.length && (bytesRead = fis.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += bytesRead;
	    }
	    if (offset < bytes.length) {
	        throw new Exception("Error reading file "+f.getName());
	    }
	    fis.close();
	    return bytes;
	}	
}
