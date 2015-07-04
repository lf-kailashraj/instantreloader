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
		File file = new File("/home/anish/Development/integration-api-servers/integration-api/RUNNING.txt");
		
		//when
		byte[] resultBytes = FileUtils.getFileBytes(file);
		
		//then
		assertNotEquals(resultBytes,null);
	}
}
