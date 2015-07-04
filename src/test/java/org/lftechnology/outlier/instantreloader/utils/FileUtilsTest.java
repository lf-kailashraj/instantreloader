package org.lftechnology.outlier.instantreloader.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
 * 
 * @author anish
 *
 */
public class FileUtilsTest {

	@Test
	public void testGetFileBytes() throws Exception{
		//given
		File file = new File(Thread.currentThread().getContextClassLoader().getResource("reload.properties").getPath());
		
		//when
		byte[] resultBytes = FileUtils.getFileBytes(file);
		
		//then
		assertNotEquals(resultBytes,null);
	}
}
