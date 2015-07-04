package org.lftechnology.outlier.instantreloader.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * @author anish
 *
 */
public class PropertyUtilsTest {

	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testCheckRequiredProperties() throws IOException{
		//given
		InputStream fis = PropertyUtilsTest.class.getResourceAsStream("/reload.properties");
		Properties props = new Properties();
		props.load(fis);
		
		//when
		boolean hasAllRequiredValue = PropertyUtils.checkRequiredProps(props);
		
		assertEquals(hasAllRequiredValue,true);
	}
}
