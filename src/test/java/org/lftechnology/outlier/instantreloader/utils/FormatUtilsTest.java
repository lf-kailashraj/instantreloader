package org.lftechnology.outlier.instantreloader.utils;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author anish
 *
 */
public class FormatUtilsTest {

  @Test
  public void testStringZeros() {    
    String res = FormatUtils.stripZeros("0000123");
    assertEquals("123", res);
  }

  @Test
  public void testStringZeros2() {    
    String res = FormatUtils.stripZeros("00ABC123");
    assertEquals("00ABC123", res);
  }
  
}
